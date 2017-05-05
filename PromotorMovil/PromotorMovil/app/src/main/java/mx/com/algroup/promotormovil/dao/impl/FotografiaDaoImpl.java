package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.dao.FotografiaDao;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class FotografiaDaoImpl implements FotografiaDao {
    private static String CLASSNAME = FotografiaDaoImpl.class.getSimpleName();

    private static FotografiaDao fotografiaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public FotografiaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static FotografiaDao getSingleton( ) {
        if (fotografiaDao == null) {
            fotografiaDao = new FotografiaDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return fotografiaDao;
    }



   @Override
   public long insertFotografia(RevisionFoto revisionFoto, int idVisita){
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = this.rellenarDatosAInsertar( revisionFoto , idVisita );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.Fotografias.TABLE_NAME,
                    null,
                    values);
            LogUtil.printLog(CLASSNAME, "insert: revisionFoto:" + revisionFoto);
       return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    public RevisionFoto getRevisionFotoById(String idFoto ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        RevisionFoto revisionFoto = null;

        Cursor cursor = null;
        try{
             cursor = db.query(
                        Table.Fotografias.TABLE_NAME,  // The table to query
                        Table.Fotografias.getColumns(),                                // The columns to return
                        Table.Fotografias.COLUMN_NAME_IDFOTO.column + " = " + idFoto,  // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                revisionFoto = this.cargarObjetoRevisionFoto(cursor);
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }

        return revisionFoto;
    }

    public RevisionFoto[] getRevisionFotoByIdVisita(Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        RevisionFoto[] revisionFotoArray = new RevisionFoto[0];

        Cursor cursor = null;
        try{
            cursor = db.query(
                    Table.Fotografias.TABLE_NAME,  // The table to query
                    Table.Fotografias.getColumns(),                                // The columns to return
                    Table.Fotografias.COLUMN_NAME_IDVISITA.column + " = " + idVisita,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                revisionFotoArray = new RevisionFoto[size];
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    revisionFotoArray[i] = this.cargarObjetoRevisionFoto(cursor);
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }

        return revisionFotoArray;

    }

    public long updateFoto(RevisionFoto revisionFoto, int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( revisionFoto , idVisita );

        return db.update( Table.Productos.TABLE_NAME , values, Table.Fotografias.COLUMN_NAME_IDFOTO.column + " = "
                + revisionFoto.getIdFoto() , null);

    }

    public long deleteFotoById(String idFoto, int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Fotografias.TABLE_NAME , Table.Fotografias.COLUMN_NAME_IDFOTO.column + " = '"
                + idFoto + "'", null);
    }

    public long deleteFotoByIdVisita(  int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Fotografias.TABLE_NAME , Table.Fotografias.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita , null);
    }


    private ContentValues rellenarDatosAInsertar( RevisionFoto revisionFoto , int idVisita ) {
        ContentValues values = new ContentValues();
        values.put( Table.Fotografias.COLUMN_NAME_IDFOTO.column, revisionFoto.getIdFoto() );
        values.put( Table.Fotografias.COLUMN_NAME_FOTO.column, revisionFoto.getFoto() );
        values.put( Table.Fotografias.COLUMN_NAME_FECHACAPTURA.column, revisionFoto.getFechaCaptura() );
        values.put( Table.Fotografias.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( RevisionFoto revisionFoto , int idVisita  ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Productos.COLUMN_NAME_CODIGO.column, revisionProducto.getProducto().getCodigo() );
        values.put( Table.Fotografias.COLUMN_NAME_IDFOTO.column, revisionFoto.getIdFoto() );
        values.put( Table.Fotografias.COLUMN_NAME_FOTO.column, revisionFoto.getFoto() );
        values.put( Table.Fotografias.COLUMN_NAME_FECHACAPTURA.column, revisionFoto.getFechaCaptura() );
        values.put( Table.Fotografias.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private RevisionFoto cargarObjetoRevisionFoto( Cursor cursor ){
        RevisionFoto revisionFoto = new RevisionFoto();
        revisionFoto.setIdFoto(cursor.getString(Table.Fotografias.COLUMN_NAME_IDFOTO.index));
        revisionFoto.setFoto(cursor.getBlob(Table.Fotografias.COLUMN_NAME_FOTO.index));
        revisionFoto.setFechaCaptura(cursor.getString(Table.Fotografias.COLUMN_NAME_FECHACAPTURA.index));
        return revisionFoto;
    }

}
