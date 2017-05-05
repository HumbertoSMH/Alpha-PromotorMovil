package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.rest.Get.LoginResponse;
import mx.com.algroup.promotormovil.business.rest.Post.GuardarErroresResponse;
import mx.com.algroup.promotormovil.business.rest.Post.NotificacionErrorRest;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovil.dao.impl.NotificacionErroresDaoImpl;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.EncuestaService;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.services.NotificacionErroresService;
import mx.com.algroup.promotormovil.services.ProductoService;
import mx.com.algroup.promotormovil.services.PromotorService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.NotificacionError;
import mx.com.algroup.promotormovil.utils.PromotorMovilException;

/**
 * Created by MAMM on 16/04/15.
 */
public class PromotorServiceImpl implements PromotorService {
    private static final String CLASSNAME = PromotorServiceImpl.class.getSimpleName();

    private static PromotorService promotorService;
    private VisitaService visitaService;
    private ProductoService productoService;
    private EncuestaService encuestaService;
    private CatalogosService catalogoService;
    private Context context;
    private Promotor promotorActual;
    private JsonService jsonService;
    private NotificacionErroresService notificacionErroresService;



    private PromotorServiceImpl(Context context) {
        LogUtil.logInfo( CLASSNAME, " Se crea el singleton" );
        this.context = context;
        this.visitaService = VisitaServiceImpl.getSingleton();
        this.productoService = ProductoServiceImpl.getSingleton();
        this.encuestaService = EncuestaServiceImpl.getSingleton();
        this.catalogoService = CatalogosServiceImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
        this.notificacionErroresService = NotificacionErroresServiceImpl.getSingleton();


    }

    public static PromotorService getSingleton(  ) {
        if (promotorService == null) {
            promotorService = new PromotorServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return promotorService;
    }


    @Override
    public void realizarLoggin(String usuario, String password) {
        Promotor promotor = null;
        String responseDoLogin = null;
        LoginResponse loginResponse = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            promotor = this.crearPromotorMock( usuario , password );
            this.visitaService.recuperarRuta( promotor );
            //this.visitaService.recuperarMotivosDeRetiro();
        }else{
            loginResponse = jsonService.realizarPeticionLoginGet(usuario, password);
            if ( !loginResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError( loginResponse, Json.ERROR_JSON.LOGIN );

                promotor = null;
            } else {
                promotor = this.crearPromotor(usuario, password);
                loginResponse = this.cargaDatosIniciales( promotor );
                if( !loginResponse.isSeEjecutoConExito() ){
                    promotor = null;
                }
                //AJUSTE 20150811
                //Se notifican al servicio de ariel de las notificaciones que están en sesion
                this.notificacionErroresService.notificarErroresPendientes();
            }
        }
        this.promotorActual = promotor;
        LogUtil.printLog( CLASSNAME , ".realizarLoggin:" + promotor);
        LogUtil.printLog( CLASSNAME , ".responseDoLogin:" + responseDoLogin);

    }


    private LoginResponse cargaDatosIniciales(Promotor promotor) {
        LoginResponse response = LoginResponse.generarResponseExitoso();
        this.visitaService.recuperarRuta( promotor );
        String mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
        if( mensajeError == null ){
           /* this.productoService.actualizarCatalogoProductos(this.visitaService.getCadenasEnRuta());
            mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
            if( mensajeError == null){
                this.encuestaService.actualizarMapaEncuesta( this.visitaService.getRutaActual().getIdRuta() );
                mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
            }if( mensajeError == null ){
                this.visitaService.recuperarMotivosDeRetiro();
                mensajeError = Json.getMsgError(Json.ERROR_JSON.LOGIN);
            }*/
        }
        if( mensajeError != null ){
            response.setMensaje( mensajeError );
            response.setClaveError( "1999");
            response.setSeEjecutoConExito( false );
            Json.solicitarMsgError( response, Json.ERROR_JSON.LOGIN );
        }
        return response;
    }

    private Promotor crearPromotor(String usuario , String password) {
        Promotor p = new Promotor();
        p.setUsuario( usuario );
        p.setPassword(password);
        p.setClavePromotor(usuario);
        return p;
    }

    private Promotor crearPromotorMock( String usuario , String password ) {
        Promotor promotor = new Promotor();
        promotor.getPersona().setNombre( usuario );
        promotor.getPersona().setApellidoPaterno("");
        promotor.getPersona().setApellidoMaterno("");
        promotor.setClavePromotor( usuario );
        promotor.setUsuario( usuario );
        promotor.setPassword( password );
        return promotor;
    }

    public Promotor getPromotorActual() {
        return promotorActual;
    }



    public void prepararDatosPromotorMovilDesdeInformacionEnBase( String user , String pass ){

        this.promotorActual = this.crearPromotor(user, pass);
        this.visitaService.recuperarRutaDesdeBase( user, pass );
        this.catalogoService.recuperarCatalogosDesdeBase();

    }
}