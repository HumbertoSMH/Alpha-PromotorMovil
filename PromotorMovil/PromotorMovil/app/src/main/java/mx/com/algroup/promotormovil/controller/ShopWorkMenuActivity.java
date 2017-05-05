package mx.com.algroup.promotormovil.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class ShopWorkMenuActivity extends ActionBarActivity implements View.OnClickListener, DialogInterface.OnClickListener {
    private final static String CLASSNAME = ShopWorkMenuActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI ELements
    private TextView tiendaTexView;
    private Button capturarProductosMenuButton;
    private Button capturarEncuestaMenuButton;
    private Button checkOutSinValidarButton;
    private Button checkOutButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();

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

        this.prepararPantalla();

    }

    private void prepararPantalla() {
        setContentView(R.layout.shop_work_menu_layout);
        this.tiendaTexView = (TextView) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.capturarProductosMenuButton = (Button) findViewById( R.id.capturarProductosMenuButton );
        this.capturarEncuestaMenuButton = (Button) findViewById( R.id.capturarEncuestaMenuButton );
        this.checkOutSinValidarButton = (Button) findViewById( R.id.checkOutSinValidarButton );
        this.checkOutButton = (Button) findViewById( R.id.checkOutButton );

        this.capturarProductosMenuButton.setOnClickListener( this );
        this.capturarEncuestaMenuButton.setOnClickListener( this );
        this.checkOutSinValidarButton.setOnClickListener( this );
        this.checkOutButton.setOnClickListener( this );

        //validaciones para mostrar en pantalla
        this.aplicarReglasParaHabilitarBotones();
    }

    private void aplicarReglasParaHabilitarBotones() {
        this.capturarProductosMenuButton.setEnabled( false );
        this.capturarEncuestaMenuButton.setEnabled( false );
        this.checkOutButton.setEnabled( false );

        if( this.visita.getReporteCapturado() == RespuestaBinaria.NO ){
            this.capturarProductosMenuButton.setEnabled( true );
        }

        if( this.visita.getAplicarEncuesta() == RespuestaBinaria.SI &&
                this.visita.getEncuestaCapturada() == RespuestaBinaria.NO   ){
            this.capturarEncuestaMenuButton.setEnabled( true );
        }

        if( this.capturarProductosMenuButton.isEnabled() == false &&
            this.capturarEncuestaMenuButton.isEnabled() == false   ){
            this.checkOutButton.setEnabled( true );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_work_menu, menu);
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
        if( v == capturarProductosMenuButton ){
            intent = new Intent( this , ProductsListActivity.class );
        }else
        if( v == capturarEncuestaMenuButton ){
            intent = new Intent( this , EncuestaListaActivity.class );
        }else
        if( v == checkOutSinValidarButton ){
            if( this.validarAntesDeCheckOutSinValidacion() == false ){
                ViewUtil.mostrarMensajeRapido( this , "Existen elementos pendientes de capturar" );
                return;
            }else{
                intent = new Intent( this, MotivoCheckoutSinValidarActivity.class );
            }
        }else
        if( v == checkOutButton ){
                if( this.validarAntesDeCheckOutCompleto() == false ){
                    ViewUtil.mostrarMensajeRapido( this , "Existen elementos pendientes de capturar" );
                }else{
                    ViewUtil.mostrarAlertaAceptarCancelar( "¿Desea dar por terminada esta visita?" , this , this );
                }
            return;
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }

    private boolean validarAntesDeCheckOutSinValidacion() {
        boolean esValido = false;
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            esValido = true;
//            return esValido;
//        }
        if( (this.visita.getAplicarCapturaProductos() == RespuestaBinaria.NO ||
                this.visita.getAplicarCapturaProductos() == RespuestaBinaria.SI &&
                        this.visita.getRevisionProductos().length > 0 ) &&
                this.visita.getRevisionProductos().length > 0 &&
            this.visita.getComentarios().trim().length() > 0 &&
                ( this.visita.getAplicarEncuesta() == RespuestaBinaria.NO ||
                        this.visita.getAplicarEncuesta() == RespuestaBinaria.SI &&
                  this.visita.getEncuestaPersonas().length > 0
                )
             ){
            esValido = true;

        }
        return esValido;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            progressDialog = ProgressDialog.show( this, "", "Realizando Chekout" );
            CheckOutTask checkOutTask = new CheckOutTask();
            checkOutTask.execute( visita );
        }else {
            //No se realiza nada
        }
    }

    private boolean validarAntesDeCheckOutCompleto() {
        boolean esValido = false;
        if( this.visita.getReporteCapturado() == RespuestaBinaria.SI &&
                ( this.visita.getAplicarEncuesta() == RespuestaBinaria.NO ||
                        this.visita.getAplicarEncuesta() == RespuestaBinaria.SI &&
                          this.visita.getEncuestaCapturada() == RespuestaBinaria.SI
                )
            ){
            esValido = true;

        }
        return esValido;
    }




    private class CheckOutTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {

            visitaService.realizarCheckOut( params[0] );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressDialog.dismiss();

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_OUT);
            if (msg != null){
                //showAlertError(msg);
                ViewUtil.mostrarAlertaDeError(msg, ShopWorkMenuActivity.this);
            } else{
                mostrarSiguienteAct = true;
            }


            if ( mostrarSiguienteAct ){
                Intent intent = new Intent(ShopWorkMenuActivity.this, ShopsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }


}
