package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.Persona;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.business.utils.PreguntaRespuesta;
import mx.com.algroup.promotormovil.dao.EncuestaDao;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class EncuestaDaoImpl implements EncuestaDao {
    private static String CLASSNAME = EncuestaDaoImpl.class.getSimpleName();

    private static EncuestaDao encuestaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public EncuestaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static EncuestaDao getSingleton( ) {
        if (encuestaDao == null) {
            encuestaDao = new EncuestaDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return encuestaDao;
    }



   @Override
   public void insertEncuesta( EncuestaPersona encuestaPersona, int idVisita){
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            Persona persona = encuestaPersona.getPersona();
            int totPreguntas = encuestaPersona.getPreguntaRespuesta().length;
            for( int j= 0; j < totPreguntas ; j++ ){

            ContentValues values = this.rellenarDatosAInsertar( encuestaPersona.getPreguntaRespuesta()[j] , encuestaPersona , idVisita );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.Encuestas.TABLE_NAME,
                    null,
                    values);
            }
            LogUtil.printLog(CLASSNAME, "insertaEncuestas: encuestaPersona:" + encuestaPersona );

    }

    public EncuestaPersona[] getEncuestasByIdVisita(Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        EncuestaPersona[] encuestaPersonaArray = new EncuestaPersona[0];

        List<String> identificadorEncuesta = this.recuperarIdentificadorEncuesta(db, idVisita);
        int size = identificadorEncuesta.size();
        if( size > 0  ){
             encuestaPersonaArray = new EncuestaPersona[ size ];
            for( int j = 0 ; j < size ; j++ ){
                Cursor cursor = null;
                try{
                    cursor = db.query(
                            Table.Encuestas.TABLE_NAME,  // The table to query
                            Table.Encuestas.getColumns(),                                // The columns to return
                            Table.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column + " = '" + identificadorEncuesta.get( j ) + "'",  // The columns for the WHERE clause
                            null,                                                    // The values for the WHERE clause
                            null,                                                   // don't group the rows
                            null,                                                   // don't filter by row groups
                            null                                                    // The sort order
                    );

                    int count = cursor.getCount();
                    PreguntaRespuesta[] pr = new PreguntaRespuesta[ count ];
                    cursor.moveToFirst();
                    for( int i = 0 ; i < count ; i++ ){
                        pr[i] = this.cargarObjetoPreguntaRespuesta(cursor);
                        cursor.moveToNext();
                    }
                    encuestaPersonaArray[j] = new EncuestaPersona();
                    encuestaPersonaArray[j].setPreguntaRespuesta( pr );
                    //encuestaPersonaArray[j].getPersona().setNombre( nombres.get( j ) );
                    encuestaPersonaArray[j].setIdEncuesta( identificadorEncuesta.get( j ) );
                }finally{
                    if( cursor != null){
                        cursor.close();
                    }
                }

            }

        }
        return encuestaPersonaArray;
    }

    public long deleteEncuestaByIdVisita( int idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.delete(
                    Table.Encuestas.TABLE_NAME ,
                    Table.Encuestas.COLUMN_NAME_IDVISITA.column + " = " + idVisita ,
                     null);
    }


    private List<String> recuperarIdentificadorEncuesta( SQLiteDatabase db  , Integer idVisita) {
        List<String> personas = new ArrayList<String>();
        String[] columns = {Table.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column};
        Cursor cursor = null;
        try{
            cursor = db.query(
                    Table.Encuestas.TABLE_NAME,  // The table to query
                    columns,                                // The columns to return
                    Table.Encuestas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,  // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    Table.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column,  // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int count = cursor.getCount();
            if( count > 0 ){
                cursor.moveToFirst();
                for( int j = 0 ; j < count; j++){
                    String persona = cursor.getString( 0 );
                    personas.add( persona );
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }

    return personas;
    }


    private ContentValues rellenarDatosAInsertar( PreguntaRespuesta preguntaRespuesta , EncuestaPersona encuestaPersona , int idVisita ) {
        ContentValues values = new ContentValues();
        values.put(Table.Encuestas.COLUMN_NAME_IDPREGUNTA.column, preguntaRespuesta.getPregunta().getIdPregunta());
        values.put( Table.Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column, preguntaRespuesta.getPregunta().getDescripcion() );
        values.put( Table.Encuestas.COLUMN_NAME_IDRESPUESTA.column, preguntaRespuesta.getRespuestaElegida().getIdRespuesta() );
        values.put( Table.Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column, preguntaRespuesta.getRespuestaElegida().getDescripcion() );
        values.put( Table.Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column, encuestaPersona.getIdEncuesta() );
        values.put( Table.Encuestas.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private PreguntaRespuesta cargarObjetoPreguntaRespuesta(Cursor cursor ) {
        PreguntaRespuesta preguntaRespuesta = new PreguntaRespuesta();
        preguntaRespuesta.getPregunta().setIdPregunta(cursor.getString(Table.Encuestas.COLUMN_NAME_IDPREGUNTA.index));
        preguntaRespuesta.getPregunta().setDescripcion(cursor.getString(Table.Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.index));
        preguntaRespuesta.getRespuestaElegida().setIdRespuesta(cursor.getString(Table.Encuestas.COLUMN_NAME_IDRESPUESTA.index));
        preguntaRespuesta.getRespuestaElegida().setDescripcion(cursor.getString(Table.Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.index));
        return preguntaRespuesta;
    }

}
