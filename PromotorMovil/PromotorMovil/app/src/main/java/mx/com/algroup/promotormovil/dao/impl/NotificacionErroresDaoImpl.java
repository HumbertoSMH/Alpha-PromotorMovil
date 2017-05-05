package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Persona;
import mx.com.algroup.promotormovil.business.persistence.TablaErrores;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.business.utils.PreguntaRespuesta;
import mx.com.algroup.promotormovil.dao.EncuestaDao;
import mx.com.algroup.promotormovil.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.NotificacionError;

/**
 * Created by devmac03 on 11/08/15.
 */
public class NotificacionErroresDaoImpl implements NotificacionErroresDao {
    private static String CLASSNAME = NotificacionErroresDaoImpl.class.getSimpleName();

    private static NotificacionErroresDao notificacionErroresDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public NotificacionErroresDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static NotificacionErroresDao getSingleton( ) {
        if (notificacionErroresDao == null) {
            notificacionErroresDao = new NotificacionErroresDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return notificacionErroresDao;
    }


    @Override
    public long insertarErrores(NotificacionError notificacionError) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = this.rellenarDatosAInsertar(notificacionError);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    TablaErrores.Errores.TABLE_NAME,
                    null,
                    values);
        notificacionError.setIdError( (int)newRowId );
        LogUtil.printLog(CLASSNAME, "Se inserta el error:" + notificacionError);
        return newRowId;
    }


    @Override
    public List<NotificacionError> recuperarErroresNoEnviados() {
        List<NotificacionError> erroresNoEnviados = new ArrayList<NotificacionError>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = null;
        try{
            cursor = db.query(
                TablaErrores.Errores.TABLE_NAME,  // The table to query
                TablaErrores.Errores.getColumns(),                                // The columns to return
                TablaErrores.Errores.COLUMN_NAME_NOTIFICADO.column + " = " + 0,  // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,  // don't group the rows
                null,                                                   // don't filter by row groups
                null                                                    // The sort order
            );

            int count = cursor.getCount();
            if( count > 0 ){
                cursor.moveToFirst();
                for( int j = 0 ; j < count; j++){
                    erroresNoEnviados.add(this.cargarObjetoErrores(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }
        return erroresNoEnviados;
    }



    @Override
    public void actualizarErroresEnviados(List<Integer> idErrores) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        try{
            for( Integer idErrorItem : idErrores ){

                ContentValues value = new ContentValues();
                value.put(TablaErrores.Errores.COLUMN_NAME_NOTIFICADO.column, true);

                db.update( TablaErrores.Errores.TABLE_NAME , value, TablaErrores.Errores.COLUMN_NAME_IDERROR.column + " = "
                        + idErrorItem , null);
            }
        }catch(Exception exc ){
            LogUtil.printLog( CLASSNAME , "Ocurrio un error en actualizarErroresEnviados: ids:" + idErrores );
            LogUtil.printLog( CLASSNAME , "exc:" + exc.getMessage() );
        }
    }




    private ContentValues rellenarDatosAInsertar(NotificacionError notificacionError) {
        ContentValues values = new ContentValues();
        //values.put(TablaErrores.Errores.COLUMN_NAME_IDERROR.column, notificacionError.getIdError());
        values.put(TablaErrores.Errores.COLUMN_NAME_APLICACION.column, notificacionError.getAplicacion());
        values.put(TablaErrores.Errores.COLUMN_NAME_FECHAHORA.column, notificacionError.getFechaHora());
        values.put(TablaErrores.Errores.COLUMN_NAME_TRAZA.column, notificacionError.getTraza());
        values.put(TablaErrores.Errores.COLUMN_NAME_USUARIO.column, notificacionError.getUsuario());
        values.put(TablaErrores.Errores.COLUMN_NAME_VERSION.column, notificacionError.getVersion());
        values.put(TablaErrores.Errores.COLUMN_NAME_NOTIFICADO.column, false);
        return values;
    }

    private NotificacionError cargarObjetoErrores(Cursor cursor) {
            NotificacionError notificacionError = new NotificacionError();
            notificacionError.setIdError(cursor.getInt(TablaErrores.Errores.COLUMN_NAME_IDERROR.index));
            notificacionError.setAplicacion(cursor.getString(TablaErrores.Errores.COLUMN_NAME_APLICACION.index));
            notificacionError.setFechaHora(cursor.getString(TablaErrores.Errores.COLUMN_NAME_FECHAHORA.index));
            notificacionError.setTraza(cursor.getString(TablaErrores.Errores.COLUMN_NAME_TRAZA.index));
            notificacionError.setUsuario(cursor.getString(TablaErrores.Errores.COLUMN_NAME_USUARIO.index));
            notificacionError.setVersion(cursor.getString(TablaErrores.Errores.COLUMN_NAME_VERSION.index));
        return notificacionError;
    }


}
