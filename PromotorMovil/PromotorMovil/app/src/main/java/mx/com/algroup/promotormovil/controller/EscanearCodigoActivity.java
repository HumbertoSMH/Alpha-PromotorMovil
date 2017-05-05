package mx.com.algroup.promotormovil.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jim.h.common.android.zxinglib.integrator.IntentIntegratorBiaani;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Visita;
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

public class EscanearCodigoActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String CLASSNAME = EscanearCodigoActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private String codigoLeido;

    //UI Elements
    private TextView tiendaTexView;
    private TextView mensajeErrorScanearTextView;
    private Button guardarProductobutton;
    private Button nuevoEscanearButton;
    private Button cancelarEscanearButton;

    //Control aux
    private Handler handler = new Handler();

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


        this.codigoLeido = intent.getStringExtra(Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre());

        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.escanear_codigo_layout);

        this.tiendaTexView = (TextView) this.findViewById(R.id.tiendaTexView);
        this.tiendaTexView.setText(this.visita.getTienda().getNombreTienda());

        this.mensajeErrorScanearTextView = (TextView) this.findViewById(R.id.mensajeErrorScanearTextView);
        this.mensajeErrorScanearTextView.setText( "El producto:" + codigoLeido + ", no fue localizado en la cadena comercial" );
        if (this.existeProductoEnCadena() == true) {
            this.mensajeErrorScanearTextView.setVisibility(View.GONE);
        }else{
            ViewUtil.mostrarMensajeRapido( this , "El producto:" + codigoLeido + ", no fue localizado en la cadena comercial" );
        }

        this.guardarProductobutton = (Button) this.findViewById(R.id.guardarProductobutton);
        this.nuevoEscanearButton = (Button) this.findViewById(R.id.nuevoEscanearButton);
        this.cancelarEscanearButton = (Button) this.findViewById(R.id.cancelarEscanearButton);

        this.guardarProductobutton.setOnClickListener(this);
        this.nuevoEscanearButton.setOnClickListener(this);
        this.cancelarEscanearButton.setOnClickListener(this);

    }

    private boolean existeProductoEnCadena() {
        Boolean existeProducto = false;

        //Cadena cadena = this.cadenaService.recuperarCadenaApartirDeTienda(this.visita.getTienda());
        Cadena cadena = this.visita.getCadena();
        if (this.catalogosService.esValidoProductoEnCadena(codigoLeido, cadena)) {
            existeProducto =  false;
        }
        return existeProducto;
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_escanear_codigo, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }

            return super.onOptionsItemSelected(item);
        }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v == nuevoEscanearButton) {
            IntentIntegratorBiaani.initiateScan(this, R.layout.capture_bar_code,
                    R.id.viewfinder_view, R.id.preview_view, true);
            return;
        } else if (v == cancelarEscanearButton) {
            intent = new Intent( this , ProductsListActivity.class );
        }
        if( v == this.guardarProductobutton ){
            LogUtil.printLog( CLASSNAME , "Se guarda el producto que no se localizó en catalogo");
            String codigoProducto = this.codigoLeido ;
            intent = new Intent( this , DetalleProductoActivity.class);
            intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() ,  codigoProducto );
            intent.putExtra( Const.ParametrosIntent.PRODUCTO_SIN_CATALOGO.getNombre() ,  true );
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity( intent );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        LogUtil.printLog(CLASSNAME, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case IntentIntegratorBiaani.REQUEST_CODE:
                IntentResult scanResult = IntentIntegratorBiaani.parseActivityResult(requestCode,
                        resultCode, intent);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                if (result != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //txtScanResult.setText(result);
                            LogUtil.printLog( CLASSNAME , "El valor recuperado:" +  result.toString() );
                            this.preparaActivity();
                        }

                        private void preparaActivity() {
                            //Cadena cadena = EscanearCodigoActivity.this.cadenaService.recuperarCadenaApartirDeTienda( EscanearCodigoActivity.this.visita.getTienda() );
                            Cadena cadena = EscanearCodigoActivity.this.visita.getCadena();
                            Boolean existeProducto = EscanearCodigoActivity.this.catalogosService.esValidoProductoEnCadena( result.toString() , cadena );
                            Intent intent = null;
                            if( existeProducto == true ){
                                intent = new Intent( EscanearCodigoActivity.this ,  DetalleProductoActivity.class );
                            }else{
                                intent = new Intent( EscanearCodigoActivity.this ,  EscanearCodigoActivity.class );
                            }
                            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , EscanearCodigoActivity.this.visita.getIdVisita() );
                            intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() , result.toString() );
                            startActivity(intent);
                        }
                    });
                }
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( this , ProductsListActivity.class );
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity( intent );
        //super.onBackPressed();
    }
}