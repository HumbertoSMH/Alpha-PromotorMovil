package mx.com.algroup.promotormovil.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Ruta;
import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.controller.Adapter.TiendasListAdapter;
import mx.com.algroup.promotormovil.services.PromotorService;
import mx.com.algroup.promotormovil.services.RutaService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.PromotorServiceImpl;
import mx.com.algroup.promotormovil.services.impl.RutaServiceImpl;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.ViewUtil;

public class ShopsListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private final static String CLASSNAME = ShopsListActivity.class.getSimpleName();

    //Services
    private PromotorService promotorService;
    private VisitaService visitaService;
    private RutaService rutaService;

    //business
    private Promotor promotorActual;

    //UI ELements
    private ListView tiendasListView;
    private TextView usuarioTextView;
    private TextView fechaTiendasTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton( );
        this.promotorService = PromotorServiceImpl.getSingleton( );
        this.promotorActual = this.promotorService.getPromotorActual();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.prepararPantalla();
    }


    private void prepararPantalla(){
        setContentView(R.layout.shops_list_layout);
        this.usuarioTextView = (TextView) findViewById( R.id.usuarioTiendasTextView );
        //Ruta orderTrabajo = this.visitaService.getRutaActual();
        Ruta orderTrabajo = this.rutaService.refrescarRutaDesdeBase(this.visitaService.getRutaActual());

        usuarioTextView.setText( "Bienvenido: " + this.promotorActual.getUsuario()  );

        this.fechaTiendasTextView = (TextView) findViewById( R.id.fechaTiendasTextView );
        this.fechaTiendasTextView.setText( orderTrabajo.getFechaProgramadaString().substring( 0 , 10) );

        findViewById(R.id.salirTiendasButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopsListActivity.this, MainLoginActivity.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent );
            }
        });

        this.tiendasListView = (ListView) findViewById(R.id.tiendasListView);
        this.tiendasListView.setOnItemClickListener(this);

        Visita[] visitas = orderTrabajo.getVisitas();
        this.tiendasListView.setAdapter( new TiendasListAdapter( visitas , this ) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shops_list, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.printLog( CLASSNAME , "onItemClick position:" + position + ", id:" + id);
        if( this.esValidoEntrarAVisita( position ) == true){
            Intent intent = new Intent(ShopsListActivity.this, ShopCheckInActivity.class);
            intent.putExtra( Const.ParametrosIntent.POSICION_VISITA.getNombre() , position );
            this.startActivity( intent );
        }else{
            ViewUtil.mostrarMensajeRapido( this , "No se puede ingresar a la visita seleccionada, verifique su estatus" );
        }
    }

    private boolean esValidoEntrarAVisita( int position ) {
        boolean esValido = false;
        Visita visitaEnEstatusCheckIn = recuperarVisitaEnEstatusCheckIn();
        Visita visitaSeleccionada = this.visitaService.getRutaActual().getVisitas()[ position ];
        if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.CHECK_OUT_REQUEST ){
            esValido = true;
        }else
        if( visitaEnEstatusCheckIn == null ){
            if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.EN_RUTA ){
                esValido = true;
            }
        }else
        if( visitaEnEstatusCheckIn != null ){
            if( visitaEnEstatusCheckIn == visitaSeleccionada){
                esValido = true;
            }
        }
        //else
        //if( visitaSeleccionada.getEstatusVisita() == EstatusVisita.EN_RUTA ||
        //        visitaSeleccionada.getEstatusVisita() == EstatusVisita.CHECK_OUT_REQUEST  ){
        //    esValido = true;
        //}
        return esValido;
    }

    private Visita recuperarVisitaEnEstatusCheckIn( ){
        Visita[] visitas = this.visitaService.getRutaActual().getVisitas();
        Visita visitaEnCheckIn = null;
        for( Visita itemVisita : visitas ){
            if(itemVisita.getEstatusVisita() == EstatusVisita.CHECK_IN ){
                visitaEnCheckIn = itemVisita;
                break;
            }
        }
        return visitaEnCheckIn;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.prepararPantalla();
    }
}
