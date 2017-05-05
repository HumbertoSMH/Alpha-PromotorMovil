package mx.com.algroup.promotormovil.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.PreguntaRespuesta;
import mx.com.algroup.promotormovil.controller.Adapter.PreguntasListAdapter;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.EncuestaService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.CatalogosServiceImpl;
import mx.com.algroup.promotormovil.services.impl.EncuestaServiceImpl;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.Util;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class EncuestaPreguntasActivity extends ActionBarActivity implements View.OnClickListener , DialogInterface.OnClickListener{
    private static final String CLASSNAME = EncuestaPreguntasActivity.class.getSimpleName();

    //services
    private VisitaService visitaService;
    private EncuestaService encuestaService;
    private CatalogosService catalogosService;

    //Business
    private Visita visita;
    private Encuesta encuesta;

    //UI ELEMENTS
    private TextView tiendaTexView;
    private Button cancelarEncuestaProductoButton;
    private Button guardarEncuestaProductoCodigoButton;
    private ListView preguntasEncuestaListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.encuestaService = EncuestaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        //String idVisita = intent.getStringExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() );
        //this.visita = this.visitaService.recuperarVisitaPorIdVisita( idVisita );
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM
        this.encuesta = this.catalogosService.recuperarEncuestaPorId( visita.getIdEncuesta() );


        this.prepararPantalla();

    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.encuesta_preguntas_layout);

        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );

        this.cancelarEncuestaProductoButton = (Button)findViewById( R.id.cancelarEncuestaProductoButton );
        this.guardarEncuestaProductoCodigoButton = (Button)findViewById( R.id.guardarEncuestaProductoCodigoButton );

        this.cancelarEncuestaProductoButton.setOnClickListener( this );
        this.guardarEncuestaProductoCodigoButton.setOnClickListener( this );

        this.preguntasEncuestaListView = (ListView)findViewById( R.id.preguntasEncuestaListView );
        this.preguntasEncuestaListView.setAdapter( new PreguntasListAdapter( this.visita, this.encuesta , this ) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encuesta_preguntas, menu);
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

        if( v == cancelarEncuestaProductoButton ){
            this.finish();
            return;
        }else
        if( v == guardarEncuestaProductoCodigoButton ){
            ViewUtil.mostrarAlertaAceptarCancelar(getResources().getString(R.string.pregunta_guardar_encuesta) , this , this );
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog( CLASSNAME , "AlertClicked: dialog:" + dialog + " , wich:" + which);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            if( this.todasLasPreguntasContestadas() == true){
                this.guardarEncuestaEnVisitaActual();
                this.finish();
            }else{
                String message = getResources().getString( R.string.respuestas_incompletas );
                ViewUtil.mostrarMensajeRapido( this , message);
            }
        }else {
             //No se realiza nada
         }


    }

    private boolean todasLasPreguntasContestadas() {
        boolean esValido = true;
        PreguntasListAdapter preguntasListAdapter = (PreguntasListAdapter)preguntasEncuestaListView.getAdapter();
        return preguntasListAdapter.todasLasCeldasFueronVistas();
    }

    private void guardarEncuestaEnVisitaActual() {
        //ININ MAMM Se procede a guardar la visita en base de datos
        this.visitaService.guardarEncuesta( this.recuperarEncuestaPersona() );
//        EncuestaPersona[] encuestaPersona = this.visita.getEncuestaPersonas();
//        encuestaPersona = Arrays.copyOf( encuestaPersona , encuestaPersona.length + 1 );
//        encuestaPersona[ encuestaPersona.length -1 ] = this.recuperarEncuestaPersona();
//        this.visita.setEncuestaPersonas( encuestaPersona );
        //END MAMM
    }

    private EncuestaPersona recuperarEncuestaPersona() {
        PreguntasListAdapter preguntasListAdapter = (PreguntasListAdapter)preguntasEncuestaListView.getAdapter();
        EncuestaPersona ep = new EncuestaPersona();

        View[] cells = preguntasListAdapter.getCells();
        Pregunta[] preguntas = preguntasListAdapter.getPreguntas();
        ep.setPreguntaRespuesta( new PreguntaRespuesta[ preguntas.length] );
        ep.setIdEncuesta( Util.getDateTimeFromMilis_hastaSegundos(new Date().getTime() ) );

        for( int i = 0; i < cells.length ; i++ ){
            Pregunta itemPregunta = preguntas[i];
            Spinner respuestaEncuestaSpinner = (Spinner) cells[i].findViewById(R.id.respuestaEncuestaSpinner);
            int posicionRespuesta = respuestaEncuestaSpinner.getSelectedItemPosition();

            PreguntaRespuesta pr = new PreguntaRespuesta();
            pr.setPregunta( itemPregunta );
            pr.setRespuestaElegida( itemPregunta.getRespuestasPregunta()[ posicionRespuesta] );
            ep.getPreguntaRespuesta()[i] = pr;
        }
        return ep;
    }
}
