package mx.com.algroup.promotormovil.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import jim.h.common.android.zxinglib.integrator.IntentIntegratorBiaani;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.controller.Adapter.RevisionProductoListAdapter;
import mx.com.algroup.promotormovil.services.CadenaService;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.ProductoService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.CadenaServiceImpl;
import mx.com.algroup.promotormovil.services.impl.CatalogosServiceImpl;
import mx.com.algroup.promotormovil.services.impl.ProductoServiceImpl;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;

import mx.com.algroup.promotormovil.utils.ViewUtil;

public class ProductsListActivity extends ActionBarActivity implements View.OnClickListener{
    private static final String CLASSNAME = ProductsListActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private Button escanearProductoButton;
    private Button capturarProductoButton;
    private Button cancelarProductoButton;
    private Button continuarProductoButton;

    //Control aux
    private Handler handler = new Handler();

    private ListView productosListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();


        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        //String idVisita = intent.getStringExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() );
        //this.visita = this.visitaService.recuperarVisitaPorIdVisita( idVisita );
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM
        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.products_list_layout);

        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.escanearProductoButton = (Button)findViewById( R.id.escanearProductoButton );
        this.capturarProductoButton = (Button)findViewById( R.id.capturarProductoButton );
        this.cancelarProductoButton = (Button)findViewById( R.id.cancelarProductoButton );
        this.continuarProductoButton = (Button)findViewById( R.id.continuarProductoButton  );

        this.productosListView = (ListView)findViewById( R.id.productosListView );
        this.productosListView.setAdapter( new RevisionProductoListAdapter( this.visita.getRevisionProductos() , this , this.visita ) );

        this.escanearProductoButton.setOnClickListener( this );
        this.capturarProductoButton.setOnClickListener( this );
        this.cancelarProductoButton.setOnClickListener( this );
        this.continuarProductoButton.setOnClickListener( this );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == cancelarProductoButton ){
            this.finish();
            return;
        }else
        if( v == continuarProductoButton ){
            if( this.visita.getAplicarCapturaProductos() == RespuestaBinaria.SI ){
                if( this.visita.getRevisionProductos().length == Const.LONGITUD_ARRAY_VACIO ){
                    String message = this.getResources().getString( R.string.no_permitido_continuar_sin_productos );
                    ViewUtil.mostrarMensajeRapido( this , message );
                    return;
                }else{
                    intent = new Intent( this ,  FotoListaActivity.class );
                }
            }else{
                intent = new Intent( this ,  FotoListaActivity.class );
            }
        }else
        if( v == escanearProductoButton ){
            IntentIntegratorBiaani.initiateScan(this, R.layout.capture_bar_code,
                    R.id.viewfinder_view, R.id.preview_view, true);
             return;
        }else
        if( v == capturarProductoButton ){
            intent = new Intent( this ,  CapturarCodigoActivity.class );
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }

    @Override
    protected void onResume() {
        this.prepararPantalla();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        LogUtil.printLog( CLASSNAME , "onActivityResult" );
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegratorBiaani.REQUEST_CODE:
                IntentResult scanResult = IntentIntegratorBiaani.parseActivityResult(requestCode,
                        resultCode, intent);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //txtScanResult.setText(result);
                            LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                            this.preparaActivity();
                        }

                        private void preparaActivity() {
                            //Cadena cadena = ProductsListActivity.this.cadenaService.recuperarCadenaApartirDeTienda( ProductsListActivity.this.visita.getTienda() );
                            Cadena cadena = ProductsListActivity.this.visita.getCadena();
                            Boolean existeProducto = ProductsListActivity.this.catalogosService.esValidoProductoEnCadena( result.toString() , cadena );
                            Intent intent = null;
                            if( existeProducto == true ){
                                intent = new Intent( ProductsListActivity.this ,  DetalleProductoActivity.class );
                            }else{
                                intent = new Intent( ProductsListActivity.this ,  EscanearCodigoActivity.class );
                            }
                            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , ProductsListActivity.this.visita.getIdVisita() );
                            intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() , result.toString() );
                            startActivity(intent);
                        }
                    });
                }
                break;
            default:
        }
    }
}
