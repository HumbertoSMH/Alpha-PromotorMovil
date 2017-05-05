package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.dao.ProductoDao;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class ProductoDaoImpl implements ProductoDao {
    private static String CLASSNAME = ProductoDaoImpl.class.getSimpleName();

    private static ProductoDao productoDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public ProductoDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static ProductoDao getSingleton( ) {
        if (productoDao == null) {
            productoDao = new ProductoDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return productoDao;
    }



   @Override
   public long insertProducto( RevisionProducto producto , int idVisita){
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = this.rellenarDatosAInsertar( producto , idVisita );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.Productos.TABLE_NAME,
                    null,
                    values);
            LogUtil.printLog(CLASSNAME, "insertProducto: producto:" + producto);
       return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    public RevisionProducto getProductoById( String codigoProducto){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        RevisionProducto revisionProducto = null;

        Cursor cursor = null;
        try{
                 cursor = db.query(
                        Table.Productos.TABLE_NAME,  // The table to query
                        Table.Productos.getColumns(),                                // The columns to return
                        Table.Productos.COLUMN_NAME_CODIGO.column + " = " + codigoProducto,  // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                revisionProducto = this.cargarObjetoRevisionProducto(cursor);
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return revisionProducto;
    }

    public RevisionProducto[] getProductosByIdVisita( Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        RevisionProducto[] revisionProductosArray = new RevisionProducto[0];

        Cursor cursor = null;
        try{
            cursor = db.query(
                        Table.Productos.TABLE_NAME,  // The table to query
                        Table.Productos.getColumns(),                                // The columns to return
                        Table.Productos.COLUMN_NAME_IDVISITA.column + " = " + idVisita,                                        // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

                int size = cursor.getCount();
                if( size  > 0){
                    revisionProductosArray = new RevisionProducto[size];
                    cursor.moveToFirst();
                    for (int i = 0; i < size; i++) {
                        revisionProductosArray[i] = this.cargarObjetoRevisionProducto(cursor);
                        cursor.moveToNext();
                    }
                }
            }finally {
                if( cursor != null ){
                    cursor.close();
                }
            }

        return revisionProductosArray;

    }

    public long updateProducto( RevisionProducto revisionProducto , int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( revisionProducto , idVisita );

        return db.update( Table.Productos.TABLE_NAME , values, Table.Productos.COLUMN_NAME_CODIGO.column + " = "
                + revisionProducto.getProducto().getCodigo() , null);

    }

    public long deleteProductoById( String codigoProducto , int  idVisita ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Productos.TABLE_NAME , Table.Productos.COLUMN_NAME_CODIGO.column + " = '"
                + codigoProducto + "'" , null);
    }

    public long deleteProductoByIdVisita( int idVisita ){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Productos.TABLE_NAME , Table.Productos.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita , null);
    }


    private ContentValues rellenarDatosAInsertar( RevisionProducto revisionProducto , int idVisita ) {
        ContentValues values = new ContentValues();
        values.put( Table.Productos.COLUMN_NAME_CODIGO.column, revisionProducto.getProducto().getCodigo() );
        values.put( Table.Productos.COLUMN_NAME_DESCRIPCION.column, revisionProducto.getProducto().getDescripcion() );
        values.put( Table.Productos.COLUMN_NAME_PRECIOBASE.column, revisionProducto.getProducto().getPrecioBase() );
        values.put( Table.Productos.COLUMN_NAME_EXISTENCIA.column, revisionProducto.getExistencia() );
        values.put( Table.Productos.COLUMN_NAME_PRECIOENTIENDA.column, revisionProducto.getPrecioEnTienda() );
        values.put( Table.Productos.COLUMN_NAME_NUMEROFRENTE.column, revisionProducto.getNumeroFrente() );
        values.put( Table.Productos.COLUMN_NAME_EXHIBICIONADICIONAL.column, revisionProducto.getExhibicionAdicional().name() );
        values.put( Table.Productos.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( RevisionProducto revisionProducto , int idVisita  ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Productos.COLUMN_NAME_CODIGO.column, revisionProducto.getProducto().getCodigo() );
        values.put( Table.Productos.COLUMN_NAME_DESCRIPCION.column, revisionProducto.getProducto().getDescripcion() );
        values.put( Table.Productos.COLUMN_NAME_PRECIOBASE.column, revisionProducto.getProducto().getPrecioBase() );
        values.put( Table.Productos.COLUMN_NAME_EXISTENCIA.column, revisionProducto.getExistencia() );
        values.put( Table.Productos.COLUMN_NAME_PRECIOENTIENDA.column, revisionProducto.getPrecioEnTienda() );
        values.put( Table.Productos.COLUMN_NAME_NUMEROFRENTE.column, revisionProducto.getNumeroFrente() );
        values.put( Table.Productos.COLUMN_NAME_EXHIBICIONADICIONAL.column, revisionProducto.getExhibicionAdicional().name() );
        values.put( Table.Productos.COLUMN_NAME_IDVISITA.column, idVisita );
        return values;
    }

    private RevisionProducto cargarObjetoRevisionProducto( Cursor cursor ){
        RevisionProducto revisionProducto = new RevisionProducto();
        revisionProducto.getProducto().setCodigo(cursor.getString(Table.Productos.COLUMN_NAME_CODIGO.index));
        revisionProducto.getProducto().setDescripcion(cursor.getString(Table.Productos.COLUMN_NAME_DESCRIPCION.index));
        revisionProducto.setExistencia(cursor.getInt(Table.Productos.COLUMN_NAME_EXISTENCIA.index));
        revisionProducto.setPrecioEnTienda(cursor.getInt(Table.Productos.COLUMN_NAME_PRECIOENTIENDA.index));
        revisionProducto.setNumeroFrente(cursor.getInt(Table.Productos.COLUMN_NAME_NUMEROFRENTE.index));

        String exhibicionAdicional = cursor.getString(Table.Productos.COLUMN_NAME_EXHIBICIONADICIONAL.index);
        revisionProducto.setExhibicionAdicional( RespuestaBinaria.valueOf( exhibicionAdicional ) );

        return revisionProducto;
    }

}
