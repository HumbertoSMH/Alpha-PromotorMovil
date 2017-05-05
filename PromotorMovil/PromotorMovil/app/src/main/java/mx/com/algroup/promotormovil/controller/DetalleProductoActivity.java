package mx.com.algroup.promotormovil.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.controller.validator.ProductoValidator;
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
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class DetalleProductoActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {
    private final static String CLASSNAME = DetalleProductoActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private ProductoService productoService;
    private CadenaService cadenaService;
    private ProductoValidator productoValidator;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private Const.TipoDespliegueActivity tipoDespliegueActivity;
    private RevisionProducto revisionProductoActual;
    private boolean productoSinCatalogo;


    //UI ELements
    private TextView tiendaTexView;
    private TextView codigoProductotextView;
    private EditText nombreProductoEditText;
    private TextView precioTiendatextView;
    private EditText stockEditText;
    private EditText precioRealEditText;
    private EditText numFrenteEditText;
    private CheckBox precioCheckBox;
    private Spinner exhibicionSpinner;


    private Button cancelarCapturarCodigobutton;
    private Button eliminarCapturarCodigobutton;


    private Button continuarCapturarCodigobutton;
    private Button editarCapturabutton;
    private Button actualizarCapturabutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.cadenaService = CadenaServiceImpl.getSingleton();
        this.productoValidator = ProductoValidator.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        //String idVisita = intent.getStringExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() );
        //this.visita = this.visitaService.recuperarVisitaPorIdVisita( idVisita );
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }

        int posicionProducto = intent.getIntExtra(Const.ParametrosIntent.POSICION_PRODUCTO.getNombre() , -1 );
        if( posicionProducto != -1) {
            this.tipoDespliegueActivity = Const.TipoDespliegueActivity.CONSULTA;
            this.revisionProductoActual = this.visita.getRevisionProductos()[ posicionProducto ];
        }else{
            this.tipoDespliegueActivity = Const.TipoDespliegueActivity.ALTA;
            String codigoProducto = intent.getStringExtra( Const.ParametrosIntent.CODIGO_PRODUCTO.getNombre() );

            this.productoSinCatalogo = intent.getBooleanExtra(Const.ParametrosIntent.PRODUCTO_SIN_CATALOGO.getNombre(), false );
            Producto producto = null;
            if( productoSinCatalogo ){
                producto = new Producto();
                producto.setCodigo( codigoProducto );
            }else{
                Cadena cadena = this.visita.getCadena();
                producto = this.catalogosService.recuperarProducto( codigoProducto , cadena );
            }

            this.revisionProductoActual = new RevisionProducto();
            this.revisionProductoActual.setProducto( producto );
        }

        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.detalle_producto_layout);

        this.tiendaTexView = ( TextView ) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.codigoProductotextView = (TextView ) findViewById( R.id.codigoProductotextView );
        this.codigoProductotextView.setText( this.revisionProductoActual.getProducto().getCodigo() );

        this.nombreProductoEditText = ( EditText ) findViewById( R.id.nombreProductoEditText );
        this.precioTiendatextView = ( TextView ) findViewById( R.id.precioTiendatextView );
        this.stockEditText = ( EditText ) findViewById( R.id.stockEditText );
        this.precioRealEditText = ( EditText ) findViewById( R.id.precioRealEditText );
        this.numFrenteEditText = ( EditText ) findViewById( R.id.numFrenteEditText );
        this.precioCheckBox = ( CheckBox ) findViewById( R.id.precioCheckBox );
        this.precioCheckBox.setChecked( false );
        this.precioCheckBox.setOnClickListener( this );


        //Carga de datos del prodcuto asociado
        this.nombreProductoEditText.setText( this.revisionProductoActual.getProducto().getDescripcion() );


        String precioDecimal = StringUtils.
                transformarPrecio100APrecioDecimal( "" + this.revisionProductoActual.getProducto().getPrecioBase() );
        this.precioTiendatextView.setText( precioDecimal );



        this.exhibicionSpinner = ( Spinner ) findViewById( R.id.motivoRetiroSpinner);
        String[] respuestasString = RespuestaBinaria.recuperarArrayNombresOpcionBinaria();
        ArrayAdapter<String> respuestasAdapter = new ArrayAdapter<String>( this , android.R.layout.simple_spinner_item, respuestasString );
        exhibicionSpinner.setAdapter( respuestasAdapter );


        this.cancelarCapturarCodigobutton = ( Button ) findViewById( R.id.cancelarCapturarCodigobutton );
        this.eliminarCapturarCodigobutton = ( Button ) findViewById( R.id.eliminarCapturarCodigobutton );

        this.continuarCapturarCodigobutton = ( Button ) findViewById( R.id.continuarCapturarCodigobutton );
        this.editarCapturabutton = ( Button ) findViewById( R.id.editarCapturabutton );
        this.actualizarCapturabutton = ( Button ) findViewById( R.id.actualizarCapturabutton );

        this.cancelarCapturarCodigobutton.setOnClickListener( this );
        this.eliminarCapturarCodigobutton.setOnClickListener( this );
        this.continuarCapturarCodigobutton.setOnClickListener( this );
        this.editarCapturabutton.setOnClickListener( this );
        this.actualizarCapturabutton.setOnClickListener( this );

        if( this.tipoDespliegueActivity == Const.TipoDespliegueActivity.CONSULTA ){
          this.configurarUIelementosEnModoConsulta();
        }else{
            this.prepararBotonesParaGuardarNuevoRegistro();
        }
    }

    private void configurarUIelementosEnModoConsulta() {
        this.stockEditText.setText( "" + this.revisionProductoActual.getExistencia() );

       String precioDecimal = StringUtils.
               transformarPrecio100APrecioDecimal( "" + this.revisionProductoActual.getPrecioEnTienda() );
        this.precioRealEditText.setText( precioDecimal );
        this.numFrenteEditText.setText( "" + this.revisionProductoActual.getNumeroFrente()  );
        if( this.revisionProductoActual.getProducto().getPrecioBase() == this.revisionProductoActual.getPrecioEnTienda() ){
            this.precioCheckBox.setChecked( true );
        }
        this.exhibicionSpinner.setSelection( this.revisionProductoActual.getExhibicionAdicional().getIdRespuesta() );
        this.prepararBotonesParaHabilitarEdicionDeRegistro();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle_producto, menu);
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
        if( v == this.precioCheckBox ){
            if( this.precioCheckBox.isChecked() == true ){
                int precioproducto = this.revisionProductoActual.getProducto().getPrecioBase();
                String precioDecimal = StringUtils.
                        transformarPrecio100APrecioDecimal( "" + precioproducto );
                this.precioRealEditText.setText( precioDecimal );
            }else{
                this.precioRealEditText.setText( "" );
            }
            return;
        }

        if( v == this.cancelarCapturarCodigobutton ){
            if( this.tipoDespliegueActivity == Const.TipoDespliegueActivity.ALTA ){
                ViewUtil.mostrarAlertaAceptarCancelar( "¡Si sale de la pantalla actual se perderán los datos capturados!", this, this );
            }else{
                this.finish();
            }
            return;
        }else
        if( v == this.eliminarCapturarCodigobutton ){
            this.eliminarRevisionProductoEnVisita();
            ViewUtil.mostrarMensajeRapido( this , "Se ha eliminado el producto" );
            intent = new Intent( this , ProductsListActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else
        if( v == this.continuarCapturarCodigobutton ){
            Boolean existenErrores = this.realizarValidacionesAntesDeGuardar();
            if( existenErrores == true ){
                return;
            }else{
                if( this.codigoGuardadoPreviamente() == false ){
                    this.guardarRevisionProductoEnVisita();
                    ViewUtil.mostrarMensajeRapido( this , "El producto se ha guardado" );

                    intent = new Intent( this , ProductsListActivity.class );
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }else{
                    ViewUtil.mostrarMensajeRapido( this , "Error: El producto fue guardado previamente, ¡captura otro producto!");
                    return;
                }
            }
        }else if(  v == this.editarCapturabutton ){
            this.prepararBotonesParaActualizarRegistro();
            return;
        }
        else if( v == this.actualizarCapturabutton){
            Boolean existenErrores = this.realizarValidacionesAntesDeGuardar();
            if( existenErrores == true ){
                return;
            }else{
                this.modificarRevisionProductoEnVisita();
                ViewUtil.mostrarMensajeRapido( this , "El producto se ha actualizado" );

                intent = new Intent( this , ProductsListActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }

    private boolean codigoGuardadoPreviamente() {
        boolean fueGuardado = false;
        RevisionProducto[] productosGuardados = this.visita.getRevisionProductos();
        for( RevisionProducto item : productosGuardados){
            if( item.getProducto().getCodigo().equals( revisionProductoActual.getProducto().getCodigo() ) ){
                fueGuardado = true;
                break;
            }
        }
        return fueGuardado;
    }

    private Boolean realizarValidacionesAntesDeGuardar() {
        Boolean existenErrores = false;
        ValidadorUIMensajes existenciaValidatorMessage = productoValidator.validarExistencia(this.stockEditText);
        if( existenciaValidatorMessage.isCorrecto() == false ){
            this.stockEditText.setError( existenciaValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes numFrenteValidatorMessage = productoValidator.validarNumeroFrente(this.numFrenteEditText);
        if( numFrenteValidatorMessage.isCorrecto() == false ){
            this.numFrenteEditText.setError( numFrenteValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes precioValidatorMessage = productoValidator.validarPrecio(this.precioRealEditText);
        if( precioValidatorMessage.isCorrecto() == false ){
            this.precioRealEditText.setError( precioValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes nombreProductoValidator = productoValidator.validarNombreProducto(this.nombreProductoEditText);
        if( nombreProductoValidator.isCorrecto() == false ){
            this.nombreProductoEditText.setError( nombreProductoValidator.getMensaje() );
            existenErrores = true;
        }
        return existenErrores;

    }

    private void guardarRevisionProductoEnVisita(  ) {
        //Ajustar el Arreglo de Revision de productos
        revisionProductoActual.setNumeroFrente( Integer.parseInt(this.numFrenteEditText.getText().toString()) );
        revisionProductoActual.setExistencia(Integer.parseInt(this.stockEditText.getText().toString()));

        String precioConDecimal = StringUtils.transformarPrecioADecimalConCentavos( this.precioRealEditText.getText().toString() );
        String precioEnInteger =  precioConDecimal.replace( "." , "" );
        revisionProductoActual.setPrecioEnTienda( Integer.parseInt( precioEnInteger) );

        RespuestaBinaria rb = RespuestaBinaria.recuperarRespuestaPorId( (int)this.exhibicionSpinner.getSelectedItemId()  );
        revisionProductoActual.setExhibicionAdicional(rb);

        if( productoSinCatalogo == true ){
            revisionProductoActual.getProducto().setDescripcion( this.nombreProductoEditText.getText().toString() );
            revisionProductoActual.getProducto().setPrecioBase(Integer.parseInt(precioEnInteger));
        }

        //ININ MAMM Se agrega el manejo por base de datos del producto a actualizar
        this.visitaService.agregarRevisionProductoAVisitaActual( this.revisionProductoActual );
//            //Ajustar el Arreglo de Revision de productos
//            RevisionProducto[] revProductos = this.visita.getRevisionProductos();
//            revProductos = Arrays.copyOf(revProductos, revProductos.length + 1);
//            revProductos[ revProductos.length -1 ] = this.revisionProductoActual;
//            this.visita.setRevisionProductos( revProductos );
        //END MAMM

    }

    private void modificarRevisionProductoEnVisita(  ) {

        //Ajustar el Arreglo de Revision de productos
        revisionProductoActual.setNumeroFrente( Integer.parseInt(this.numFrenteEditText.getText().toString()) );
        revisionProductoActual.setExistencia(Integer.parseInt(this.stockEditText.getText().toString()));

        String precioConDecimal = StringUtils.transformarPrecioADecimalConCentavos( this.precioRealEditText.getText().toString() );
        String precioEnInteger =  precioConDecimal.replace( "." , "" );
        revisionProductoActual.setPrecioEnTienda( Integer.parseInt( precioEnInteger) );

        RespuestaBinaria rb = RespuestaBinaria.recuperarRespuestaPorId( (int)this.exhibicionSpinner.getSelectedItemId()  );
        revisionProductoActual.setExhibicionAdicional(rb);
        //INI MAMM se procede a acutalizar por base de datos
        this.visitaService.actualizarRevisionProductoAVisitaActual( this.revisionProductoActual );
        //END MAMM
    }

    private void eliminarRevisionProductoEnVisita(  ) {
        //INI MAMM Se elimina el producto actual por base de datos
        this.visitaService.eliminarRevisionProductoDeVisita( this.revisionProductoActual );
//        //Ajustar el Arreglo de Revision de productos
//        int totProductosActual = this.visita.getRevisionProductos().length;
//        List<RevisionProducto> revisionProductoActual = new ArrayList<RevisionProducto>();
//        for( int j = 0; j < totProductosActual ; j++ ){
//            if( this.visita.getRevisionProductos()[j] != this.revisionProductoActual ){
//                revisionProductoActual.add( this.visita.getRevisionProductos()[j] );
//            }
//        }
//        this.visita.setRevisionProductos( revisionProductoActual.toArray( new RevisionProducto[0] ) );
        //END MAMM
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which );
        if( which == AlertDialog.BUTTON_POSITIVE ){
            //this.finish();
            super.onBackPressed();
        }else {
            //No se realiza nada
        }
    }

    @Override
    public void onBackPressed() {
        if( this.tipoDespliegueActivity == Const.TipoDespliegueActivity.ALTA ){
            ViewUtil.mostrarAlertaAceptarCancelar( "¡Si sale de la pantalla actual se perderán los datos capturados!", this, this );
        }else{
            super.onBackPressed();
        }
    }



    private void prepararBotonesParaGuardarNuevoRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.GONE );
        this.cancelarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.continuarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.editarCapturabutton.setVisibility( View.GONE );
        this.actualizarCapturabutton.setVisibility( View.GONE );
        this.precioTiendatextView.setEnabled( true );
        this.stockEditText.setEnabled( true );
        this.precioRealEditText.setEnabled( true );
        this.numFrenteEditText.setEnabled( true );
        this.precioCheckBox.setEnabled( true );
        this.exhibicionSpinner.setEnabled( true );

        if( productoSinCatalogo == true){
            this.nombreProductoEditText.setEnabled( true );
            this.precioCheckBox.setEnabled( false );
        }else{
            this.nombreProductoEditText.setEnabled( false );
        }
    }

    private void prepararBotonesParaHabilitarEdicionDeRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.cancelarCapturarCodigobutton.setVisibility( View.INVISIBLE );
        this.continuarCapturarCodigobutton.setVisibility( View.GONE );
        this.editarCapturabutton.setVisibility( View.VISIBLE );
        this.actualizarCapturabutton.setVisibility( View.GONE );
        this.nombreProductoEditText.setEnabled( false );
        this.precioTiendatextView.setEnabled( false );
        this.stockEditText.setEnabled( false );
        this.precioRealEditText.setEnabled( false );
        this.numFrenteEditText.setEnabled( false );
        this.precioCheckBox.setEnabled( false );
        this.exhibicionSpinner.setEnabled( false );
    }

    private void prepararBotonesParaActualizarRegistro(){
        this.eliminarCapturarCodigobutton.setVisibility( View.GONE );
        this.cancelarCapturarCodigobutton.setVisibility( View.VISIBLE );
        this.continuarCapturarCodigobutton.setVisibility( View.GONE );
        this.editarCapturabutton.setVisibility( View.GONE );
        this.actualizarCapturabutton.setVisibility( View.VISIBLE );
        this.nombreProductoEditText.setEnabled( false );
        this.precioTiendatextView.setEnabled( true );
        this.stockEditText.setEnabled( true );
        this.precioRealEditText.setEnabled( true );
        this.numFrenteEditText.setEnabled( true );
        this.precioCheckBox.setEnabled( true );
        this.exhibicionSpinner.setEnabled( true );
    }

}
