package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.rest.Post.GuardarErroresResponse;
import mx.com.algroup.promotormovil.business.rest.Post.NotificacionErrorRest;
import mx.com.algroup.promotormovil.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovil.dao.impl.NotificacionErroresDaoImpl;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.services.NotificacionErroresService;
import mx.com.algroup.promotormovil.services.PromotorService;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.NotificacionError;

/**
 * Created by devmac03 on 12/08/15.
 */
public class NotificacionErroresServiceImpl implements NotificacionErroresService {
    private static final String CLASSNAME = PromotorServiceImpl.class.getSimpleName();


    private static NotificacionErroresService notificacionErroresService;

    //Services
    private JsonService jsonService;

    //DAOs
    private NotificacionErroresDao notificacionErroresDao;
    private Context context;

    private NotificacionErroresServiceImpl(Context context) {
        LogUtil.logInfo( CLASSNAME, " Se crea el singleton" );
        this.context = context;
        this.notificacionErroresDao = NotificacionErroresDaoImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();

    }

    public static NotificacionErroresService getSingleton(  ) {
        if (notificacionErroresService == null) {
            notificacionErroresService = new NotificacionErroresServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return notificacionErroresService;
    }



    @Override
    public void notificarErroresPendientes() {
            LogUtil.printLog(CLASSNAME, "enviarNotificacionesPendientes");
            try{
                List<NotificacionError> notificacionesPendientes = this.notificacionErroresDao.recuperarErroresNoEnviados();
                LogUtil.printLog( CLASSNAME , "notificacionesPendientes SIZE:" + notificacionesPendientes.size() );
                LogUtil.printLog( CLASSNAME , "notificacionesPendientes:" + notificacionesPendientes );
                List<Integer> idErrores = new ArrayList<Integer>();
                for( NotificacionError itemError : notificacionesPendientes ){
                    NotificacionErrorRest errorRest = this.parsearNotificacionErrorRest(itemError);
                    GuardarErroresResponse result = this.jsonService.realizarPeticionGuardarErrores(errorRest);
                    if( result.getGuardarErroresResult().isSeEjecutoConExito() == true ){
                        idErrores.add( itemError.getIdError() );
                    }
                }
                LogUtil.printLog( CLASSNAME , "idErrores enviados a guardar al WS:" + idErrores );
                this.notificacionErroresDao.actualizarErroresEnviados( idErrores );
            }catch ( Exception exc ){
                LogUtil.printLog( CLASSNAME , "Ocurrio un error al querer notificar los errores:" + exc.getMessage());
            }
    }


    private NotificacionErrorRest parsearNotificacionErrorRest(NotificacionError itemError) {
        NotificacionErrorRest errorRest = new NotificacionErrorRest();
        errorRest.setAplicacion(itemError.getAplicacion());
        errorRest.setVersion( itemError.getVersion() );
        errorRest.setUsuario( itemError.getUsuario() );
        errorRest.setFechaHora( itemError.getFechaHora() );
        errorRest.setTraza( itemError.getTraza() );
        return errorRest;
    }

}
