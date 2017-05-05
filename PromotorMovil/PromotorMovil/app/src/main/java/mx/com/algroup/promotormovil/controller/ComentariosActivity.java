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
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.controller.validator.ComentariosValidator;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class ComentariosActivity extends ActionBarActivity implements View.OnClickListener {
    //services
    private VisitaService visitaService;

    //Business
    private Visita visita;

    //UI Elements
    private TextView tiendaTexView;
    private EditText comentarioEditText;
    private Button cancelarCapturarCodigobutton;
    private Button continuarCapturarCodigobutton;


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
        setContentView(R.layout.comentarios_layout);

        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );
        this.comentarioEditText = (EditText)findViewById( R.id.comentarioEditText );
        this.comentarioEditText.setText( this.visita.getComentarios() );
        this.cancelarCapturarCodigobutton = (Button)findViewById( R.id.cancelarCapturarCodigobutton );
        this.continuarCapturarCodigobutton = (Button)findViewById( R.id.continuarCapturarCodigobutton );

        this.cancelarCapturarCodigobutton.setOnClickListener( this );
        this.continuarCapturarCodigobutton.setOnClickListener( this );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comentarios, menu);
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
        if( v == cancelarCapturarCodigobutton ){
            this.finish();
            return;
        }else
        if( v == continuarCapturarCodigobutton ){
            ValidadorUIMensajes validator = ComentariosValidator.getSingleton().validarComentario( comentarioEditText );
            if( validator.isCorrecto() == false ){
                comentarioEditText.setError( validator.getMensaje() );
                return;
            }
            else{
                this.visita.setComentarios( comentarioEditText.getText().toString() );
                this.visitaService.actualizarVisitaActual();
                intent = new Intent( this , FirmaDeptoActivity.class );
            }
            intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
            startActivity( intent );
        }
    }
}
