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
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Ruta;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.controller.validator.PromotorValidator;
import mx.com.algroup.promotormovil.services.PromotorService;
import mx.com.algroup.promotormovil.services.RutaService;
import mx.com.algroup.promotormovil.services.impl.PromotorServiceImpl;
import mx.com.algroup.promotormovil.services.impl.RutaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.Util;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class MainLoginActivity extends ActionBarActivity implements View.OnClickListener{

    private static final String CLASSNAME = MainLoginActivity.class.getSimpleName();

    //Servicios
    private PromotorService promotorService;
    private RutaService rutaService;
    private PromotorValidator promotorValidator;

    //UI Elements
    EditText usuarioEditText = null;
    EditText passwordEditText = null;
    TextView idVersionTextView = null;
    Button continuarButton = null;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));

        //Inicializacion de singleton
        this.promotorService = PromotorServiceImpl.getSingleton(  );
        this.promotorValidator = PromotorValidator.getSingleton(  );
        this.rutaService = RutaServiceImpl.getSingleton();

        this.prepararPantalla();
    }

    private void prepararPantalla(){
        setContentView(R.layout.main_login_layout);
        findViewById(R.id.continuarLoginButton).setOnClickListener(this);
        usuarioEditText = (EditText)findViewById(R.id.usuarioEditText);
        passwordEditText = (EditText)findViewById(R.id.contrasenaEditText);
        continuarButton = (Button)findViewById(R.id.continuarLoginButton);

        idVersionTextView = (TextView)findViewById(R.id.idVersionTextView);
        StringBuilder sbVersion = new StringBuilder();
        sbVersion.append( "Ver. " );
        sbVersion.append( Const.VersionAPK.getUltimaVersion().version  );
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ||
                Const.Enviroment.currentEnviroment == Const.Enviroment.FAKE  ){
            sbVersion.append( " " +  Const.Enviroment.currentEnviroment );
        }
        idVersionTextView.setText( sbVersion.toString() );

        continuarButton.setOnClickListener( this );
        continuarButton.setOnClickListener( this );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_login, menu);
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
        if( findViewById(R.id.continuarLoginButton) == v ){
            LogUtil.printLog( CLASSNAME , "Hacen click al boton Continuar");
        }

        Boolean existenErrores = this.realizarValidacionesAntesDeLogin( usuarioEditText , passwordEditText );
        if( existenErrores == true ){
            LogUtil.printLog( CLASSNAME , "Existen errores en el registro" );
        }else{
            String usuario = usuarioEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            progressDialog = ProgressDialog.show( this, "", "Realizando Login" );
            LoginTask loginTask = new LoginTask();
            loginTask.execute( usuario, password );
        }
    }
    private boolean realizarValidacionesAntesDeLogin( EditText usuarioEditText , EditText passwordEditText ){
        Boolean existenErrores = false;
        String usuario = usuarioEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        ValidadorUIMensajes userValidatorMessage = promotorValidator.validarUsuario( usuarioEditText );
        if( userValidatorMessage.isCorrecto() == false ){
            usuarioEditText.setError( userValidatorMessage.getMensaje() );
            existenErrores = true;
        }

        ValidadorUIMensajes passwordValidatorMessage = promotorValidator.validarPassword( passwordEditText );
        if( passwordValidatorMessage.isCorrecto() == false ){
            passwordEditText.setError( passwordValidatorMessage.getMensaje() );
            existenErrores = true;
        }
        return existenErrores;
    }


    private class LoginTask extends AsyncTask<String, Boolean, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {

            promotorService.realizarLoggin(params[0], params[1]);
//            if ( promotor == null ){
//                return false;
//            }
//
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progressDialog.dismiss();

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.LOGIN);
            if (msg != null) {
                LogUtil.printLog( CLASSNAME , "===>>>No fue posible realizar el Loggin" );
                String user = MainLoginActivity.this.usuarioEditText.getText().toString();
                String pass = MainLoginActivity.this.passwordEditText.getText().toString();
                //No se pudo realizar el login, Se verifica que el usuario que se intente loggear tenga ruta guardada y que sea vigente.
                if (MainLoginActivity.this.validarPromotorConRutaEnBase(user, pass) == false) {
                    LogUtil.printLog( CLASSNAME , "===>>>No existe una ruta para el día de hoy(" + Util.getDateFromMilis(new Date().getTime()) + " 00:00" + ") para este usuario" );
                    ViewUtil.mostrarAlertaDeError(msg, MainLoginActivity.this);
                } else {
                    LogUtil.printLog( CLASSNAME , "===>>>Se localiza la ruta del día de hoy(" + Util.getDateFromMilis(new Date().getTime()) + " 00:00" + "), se recarga desde la base de datos" );
                    promotorService.prepararDatosPromotorMovilDesdeInformacionEnBase(user, pass);
                    mostrarSiguienteAct = true;
                }
            } else {
                LogUtil.printLog( CLASSNAME , "===>>>Loggin exitoso" );
                mostrarSiguienteAct = true;
            }


            if ( mostrarSiguienteAct ){
                startActivity(new Intent(MainLoginActivity.this, ShopsListActivity.class));
            }
        }
    }

    private boolean validarPromotorConRutaEnBase( String user , String pass ){
        boolean tieneRutaEnBase = false;

        Ruta rutaEnBase = this.rutaService.getRutaPorClaveYPasswordDePromotor( user , pass );
        if(    rutaEnBase != null &&   //que la ruta exista
                rutaEnBase.getFechaProgramadaString().equals( Util.getDateFromMilis(new Date().getTime()) + " 00:00") == true  ){  //Que la ruta sea del día de hoy
            tieneRutaEnBase = true;
        }
        return tieneRutaEnBase;
    }

}
