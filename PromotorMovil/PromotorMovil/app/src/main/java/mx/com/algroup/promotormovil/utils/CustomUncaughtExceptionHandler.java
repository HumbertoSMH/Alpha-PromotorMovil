package mx.com.algroup.promotormovil.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.controller.DisplayErrorActivity;
import mx.com.algroup.promotormovil.controller.ShopsListActivity;
import mx.com.algroup.promotormovil.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovil.dao.impl.NotificacionErroresDaoImpl;
import mx.com.algroup.promotormovil.services.NotificacionErroresService;
import mx.com.algroup.promotormovil.services.PromotorService;
import mx.com.algroup.promotormovil.services.impl.NotificacionErroresServiceImpl;
import mx.com.algroup.promotormovil.services.impl.PromotorServiceImpl;

/**
 * Created by devmac03 on 03/06/15.
 */
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static String CLASSNAME = CustomUncaughtExceptionHandler.class.getSimpleName();

    //Servicios
    private PromotorService promotorService;
    private NotificacionErroresService notificacionErroresService;

    //Dao
    private NotificacionErroresDao notificacionErroresDao;

    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public CustomUncaughtExceptionHandler(Activity context) {
        myContext = context;
        this.notificacionErroresDao = NotificacionErroresDaoImpl.getSingleton();
        this.promotorService = PromotorServiceImpl.getSingleton();
        this.notificacionErroresService = NotificacionErroresServiceImpl.getSingleton();
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSA DEL ERROR ************\n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n************ INFORMACIÓN DEL DISPOSITIVO ***********\n");
        errorReport.append("Version: ");
        errorReport.append(Const.VersionAPK.getUltimaVersion());
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK_INT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        Intent intent = new Intent(myContext, DisplayErrorActivity.class);
        intent.putExtra("errorAMostrar", errorReport.toString());
        this.guardarErrorEnBase( errorReport.toString() );
        //EnviarErrorTask task = new EnviarErrorTask();
        //task.execute( null , null);
        myContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private void guardarErrorEnBase( String mensajeError) {
        NotificacionError notificacionError = new NotificacionError();
        notificacionError.setAplicacion( Const.Aplicacion.PRO.name() );
        notificacionError.setVersion(Const.VersionAPK.getUltimaVersion().version);
        notificacionError.setFechaHora(Util.getDateTimeFromMilis(new Date().getTime()));
        Promotor promotor = promotorService.getPromotorActual();
        notificacionError.setUsuario( promotor.getUsuario() );
        notificacionError.setTraza(mensajeError);
        LogUtil.printLog(CLASSNAME, "Se registra el error:" + notificacionError);
        this.notificacionErroresDao.insertarErrores( notificacionError );

    }



    private class EnviarErrorTask extends AsyncTask<String, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            LogUtil.printLog( CLASSNAME , "Se procede a intentar notificar los errores generados" );

            //promotorService.realizarLoggin(params[0], params[1]);
            CustomUncaughtExceptionHandler.this.notificacionErroresService.notificarErroresPendientes();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           //No se ejecuta nada
        }
    }


}
