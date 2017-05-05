package mx.com.algroup.promotormovil.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.controller.Adapter.EncuestaPersonasListAdapter;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class EncuestaListaActivity extends ActionBarActivity implements View.OnClickListener  {
    private final static String CLASSNAME = EncuestaListaActivity.class.getName();

    //services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private Button capturarEncuestaButton;
    private Button cancelarCapturaEncuestaButton;
    private Button guardarEncuestaButton;
    private ListView encuentaListView;




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

        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        LogUtil.printLog( CLASSNAME , "prepararPantalla start.. encuestados:" + this.visita.getEncuestaPersonas().length );
        setContentView(R.layout.encuesta_lista_layout);
        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.capturarEncuestaButton = (Button)findViewById( R.id.capturarEncuestaButton );
        this.cancelarCapturaEncuestaButton = (Button)findViewById( R.id.cancelarCapturaEncuestaButton );
        this.guardarEncuestaButton = (Button)findViewById( R.id.guardarEncuestaButton );


        this.capturarEncuestaButton.setOnClickListener( this );
        this.cancelarCapturaEncuestaButton.setOnClickListener( this );
        this.guardarEncuestaButton.setOnClickListener( this );

        this.encuentaListView = (ListView)findViewById( R.id.encuentaListView );
        this.encuentaListView.setAdapter( new EncuestaPersonasListAdapter( this.visita.getEncuestaPersonas() , this) );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encuesta_lista, menu);
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
        if( v == this.capturarEncuestaButton ){
            intent = new Intent( this,  EncuestaPreguntasActivity.class );
        }else
        if( v == this.cancelarCapturaEncuestaButton ){
            this.finish();
            return;
        }else
        if( v == guardarEncuestaButton ){
            if( this.validarAntesDeGuardarEncuestas() == true ){
                this.visita.setEncuestaCapturada(RespuestaBinaria.SI);
                this.visitaService.actualizarVisitaActual();
                intent = new Intent( this,  ShopWorkMenuActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ViewUtil.mostrarMensajeRapido( this , "¡Las encuestas se han guardado exitosamente!" );
            }else{
                return;
            }
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );

    }

    private boolean validarAntesDeGuardarEncuestas() {
        boolean esValido = true;
        if( this.visita.getEncuestaPersonas().length == 0 ){
            ViewUtil.mostrarMensajeRapido( this , "No se tienen registradas encuestas para guardar" );
            esValido = false;
        }
        return esValido;
    }

    @Override
    protected void onResume() {
        this.prepararPantalla();
        super.onResume();
    }

}
