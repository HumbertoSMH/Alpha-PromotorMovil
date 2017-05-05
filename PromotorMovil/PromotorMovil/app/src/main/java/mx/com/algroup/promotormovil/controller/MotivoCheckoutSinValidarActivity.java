package mx.com.algroup.promotormovil.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.controller.validator.CheckOutValidator;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.CatalogosServiceImpl;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class MotivoCheckoutSinValidarActivity extends ActionBarActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {


    //services
    private VisitaService visitaService;
    private CatalogosService catalogosService;
    private CheckOutValidator checkOutValidator;

    //Business
    private Visita visita;

    //UI ELements
    private TextView tiendaTexView;
    private Spinner motivoRetiroSpinner;
    private EditText motivoRetiroEditText;
    private Button cancelarCheckoutbutton;
    private Button checkoutbutton;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();
        this.checkOutValidator = CheckOutValidator.getSingleton();

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


    private void prepararPantalla(){
        setContentView(R.layout.motivo_checkout_sin_validar);

        this.tiendaTexView = (TextView) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );
        this.motivoRetiroEditText = (EditText) findViewById( R.id.motivoRetiroEditText );

        this.cancelarCheckoutbutton = (Button)findViewById( R.id.cancelarCheckoutbutton );
        this.checkoutbutton = (Button)findViewById( R.id.checkoutbutton );

        this.cancelarCheckoutbutton.setOnClickListener( this );
        this.checkoutbutton.setOnClickListener( this );

        this.motivoRetiroSpinner = ( Spinner ) findViewById( R.id.motivoRetiroSpinner);
        List<MotivoRetiro> catalogoMotivoRetiro = this.catalogosService.getCatalogoMotivoRetiro();
        String[] motivosArray = recuperarArrayDeMotivos( catalogoMotivoRetiro );
        ArrayAdapter<String> respuestasAdapter = new ArrayAdapter<String>( this , R.layout.custom_spinner_item, motivosArray );
        this.motivoRetiroSpinner.setAdapter( respuestasAdapter );
        this.motivoRetiroSpinner.setOnItemSelectedListener(this);
    }

    private String[] recuperarArrayDeMotivos( List<MotivoRetiro> catalogoMotivosRetiro ){
        String[] motivosArray = new String[0];
        if(catalogoMotivosRetiro != null ){
            int size = catalogoMotivosRetiro.size();
            motivosArray = new String[ size ];
            for( int j = 0 ; j < size ; j++ ){
                motivosArray[j] = catalogoMotivosRetiro.get( j ).getDescripcionMotivoRetiro();
            }
        }
        return  motivosArray;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_motivo_checkout_sin_validar, menu);
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
        if( v == cancelarCheckoutbutton ){
            this.finish();
            return;
        }
        if( v == checkoutbutton ){
            ValidadorUIMensajes validator =  this.checkOutValidator.
                    validarMotivoCheckOut( this.motivoRetiroSpinner , this.motivoRetiroEditText );
            if( validator.isCorrecto() == false ){
                ViewUtil.mostrarMensajeRapido( this , validator.getMensaje() );
            }else{
                    int index = this.motivoRetiroSpinner.getSelectedItemPosition();
                    MotivoRetiro motivoRetiro = this.catalogosService.getCatalogoMotivoRetiro().get(index);
                    this.visita.setIdMotivoRetiro( motivoRetiro.getIdMotivoRetiro() );
                    this.visita.setDescripcionMotivoRetiro( this.motivoRetiroEditText.getText().toString() );
                //this.visitaService.actualizarVisitaActual();
                progressDialog = ProgressDialog.show( this, "", "Realizando Chekout" );
                CheckOutSinValidarTask task = new CheckOutSinValidarTask();
                task.execute( visita );

            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MotivoRetiro motivoRetiro = this.catalogosService.getCatalogoMotivoRetiro().get( position );
        if( motivoRetiro.getIdMotivoRetiro() == Const.ID_MOTIVO_RETIRO_OTRO ){
            this.motivoRetiroEditText.setEnabled(true);
            this.motivoRetiroEditText.setBackgroundColor( 0xFFF5F6CE );
        }else{
            this.motivoRetiroEditText.setEnabled( false );
            this.motivoRetiroEditText.setText( "" );
            this.motivoRetiroEditText.setBackgroundColor(Color.TRANSPARENT);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //No se ejecuta nada
    }



    private class CheckOutSinValidarTask extends AsyncTask<Visita, Boolean, Boolean> {

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
                ViewUtil.mostrarAlertaDeError(msg, MotivoCheckoutSinValidarActivity.this);
            } else{
                mostrarSiguienteAct = true;
            }


            if ( mostrarSiguienteAct ){
                Intent intent = new Intent(MotivoCheckoutSinValidarActivity.this, ShopsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }





}
