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
import mx.com.algroup.promotormovil.business.Tienda;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class ShopCheckInActivity extends ActionBarActivity implements View.OnClickListener{
    public static final String CLASSNAME = ShopCheckInActivity.class.getSimpleName();

    //Services
    private VisitaService visitaService;
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private TextView direccionTiendatextView;
    private Button checkInbutton;
    private Button continuarbutton;
    private Button cancelarCheckButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        int posicion = intent.getIntExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() ,  -1 );
        //INI MAMM Se ajusta para recuperar la visita desde la base de datos
        //this.visita = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        this.visita = this.visitaService.getVisitaPorPosicionEnLista( posicion );
        //END MAMM


        this.prepararPantalla();
    }

    private void prepararPantalla() {
        setContentView(R.layout.shop_check_in_layout);
        Tienda tienda = this.visita.getTienda();

        this.tiendaTexView = (TextView) findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( tienda.getNombreTienda() );
        this.direccionTiendatextView = (TextView) findViewById( R.id.direccionTiendatextView );
        this.direccionTiendatextView.setText( StringUtils.obtenerDireccionCompleta( tienda.getDireccion() ));

        this.checkInbutton = (Button)findViewById( R.id.checkInbutton );
        this.continuarbutton = (Button)findViewById( R.id.continuarbutton );
        this.cancelarCheckButton = (Button)findViewById( R.id.cancelarCheckButton );


        this.checkInbutton.setOnClickListener( this );
        this.continuarbutton.setOnClickListener( this );
        this.cancelarCheckButton.setOnClickListener( this );

        if( this.visita.getEstatusVisita() == EstatusVisita.CHECK_IN ||
                this.visita.getEstatusVisita() == EstatusVisita.CHECK_OUT_REQUEST){
            this.checkInbutton.setVisibility( View.GONE );
            this.continuarbutton.setVisibility( View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_check_in, menu);
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
        if( v == this.checkInbutton ){
            progressDialog = ProgressDialog.show( this, "", "Realizando CheckIn" );
            CheckinTask checkinTask = new CheckinTask();
            checkinTask.execute( visita );
        }else
        if( v == this.continuarbutton ){
            Intent intent = new Intent(ShopCheckInActivity.this, ShopWorkMenuActivity.class);
            intent.putExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre(), visita.getIdVisita());
            startActivity( intent );
        }else
        if( v == cancelarCheckButton ){
            this.finish();
            Intent intent = new Intent(this, ShopsListActivity.class);
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent);
            return;
        }
    }

//    private void notificarCheckInEnTienda(Visita visita) {
//        this.visitaService.realizarCheckIn( visita );
//    }


    private class CheckinTask extends AsyncTask<Visita, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Visita... params) {
            LogUtil.printLog( CLASSNAME , "doInBackground..." );
            //Promotor promotor = promotorService.realizarLoggin(params[0], params[1]);
            ShopCheckInActivity.this.visitaService.realizarCheckIn(params[0]);
            return true;
        }

        @Override
        protected void onPostExecute( Boolean aBoolean) {
            LogUtil.printLog( CLASSNAME , "doInBackground..." );
            super.onPostExecute( aBoolean );

            progressDialog.dismiss();

            boolean mostrarSiguienteAct = false;

            String msg = Json.getMsgError(Json.ERROR_JSON.CHECK_IN);
            if (msg != null){
                //showAlertError(msg);
                ViewUtil.mostrarAlertaDeError(msg, ShopCheckInActivity.this);
            } else{
                mostrarSiguienteAct = true;
            }

            if ( mostrarSiguienteAct ){
                Intent intent = new Intent(ShopCheckInActivity.this, ShopWorkMenuActivity.class);
                intent.putExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre(), visita.getIdVisita());
                startActivity( intent );
            }
        }

    }

//    private void showAlertError( String msg){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false).setNeutralButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        builder.setTitle(msg);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this, ShopsListActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }
}
