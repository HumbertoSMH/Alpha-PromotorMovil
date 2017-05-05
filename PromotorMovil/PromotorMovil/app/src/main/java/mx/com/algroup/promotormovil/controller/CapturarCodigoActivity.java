package mx.com.algroup.promotormovil.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class CapturarCodigoActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String CLASSNAME = CapturarCodigoActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;
    private CadenaService cadenaService;
    private CatalogosService catalogosService;
    private ProductoService productoService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private EditText codigoEditText;
    private TextView mensajeErrorCapturarCodigoTextView;
    private Button guardarProductobutton;
    private Button cancelarCapturarCodigobutton;
    private Button continuarCapturarCodigobutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
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
        setContentView(R.layout.capturar_codigo_layout);

        this.tiendaTexView = (TextView )findViewById( R.id.tiendaTexView);
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.codigoEditText = (EditText )findViewById( R.id.codigoEditText);
        this.mensajeErrorCapturarCodigoTextView = (TextView )findViewById( R.id.mensajeErrorCapturarCodigoTextView);
        this.mensajeErrorCapturarCodigoTextView.setVisibility( View.GONE );

        this.cancelarCapturarCodigobutton = (Button )findViewById( R.id.cancelarCapturarCodigobutton);
        this.continuarCapturarCodigobutton = (Button )findViewById( R.id.continuarCapturarCodigobutton);
        this.guardarProductobutton = (Button )findViewById( R.id.guardarProductobutton);

        //
        this.guardarProductobutton.setOnClickListener( this );
        this.cancelarCapturarCodigobutton.setOnClickListener( this );
        this.continuarCapturarCodigobutton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capturar_codigo, menu);
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
        if( v == this.cancelarCapturarCodigobutton ){
            this.finish();
            return;
        }else
        if( v == this.continuarCapturarCodigobutton ){
            LogUtil.printLog( CLASSNAME , "Se presiona continuar");
            String codigoProducto = this.codigoEditText.getText().toString();
            if( this.validarCodigoProducto( codigoProducto ) ) {
                intent = new Intent( this , DetalleProductoActivity.class);
                intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() ,  codigoProducto );
            }else{
                this.mensajeErrorCapturarCodigoTextView.setVisibility( View.VISIBLE );
                this.guardarProductobutton.setVisibility( View.VISIBLE );
                ViewUtil.mostrarMensajeRapido(this, "El producto:" + codigoProducto + ", no fue localizado en la cadena comercial");
                return;
            }
        }else
        if( v == this.guardarProductobutton ){
            LogUtil.printLog( CLASSNAME , "Se guarda el producto que no se localizó en catalogo");
            String codigoProducto = this.codigoEditText.getText().toString();
            intent = new Intent( this , DetalleProductoActivity.class);
            intent.putExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() ,  codigoProducto );
            intent.putExtra( Const.ParametrosIntent.PRODUCTO_SIN_CATALOGO.getNombre() ,  true );
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }



    private boolean validarCodigoProducto( String codigoProducto ) {
        LogUtil.printLog( CLASSNAME , "validarCodigoProducto codProducto:" + codigoProducto );
        boolean esValido = true;
        //FIXME Realizar validaciones de codigo de producto

        Cadena cadena = this.visita.getCadena();
        esValido = this.catalogosService.esValidoProductoEnCadena( codigoProducto , cadena );
        return esValido;
    }
}
