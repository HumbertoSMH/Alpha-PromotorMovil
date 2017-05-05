package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.persistence.TablasDeCatalogos;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.Respuesta;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.dao.CatalogoDao;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;

/**
 * Created by devmac02 on 29/09/15.
 */
public class CatalogoDaoImpl implements CatalogoDao {
    private static String CLASSNAME = CatalogoDaoImpl.class.getSimpleName();

    private static CatalogoDao catalogoDao;
    private Context context;


    PromotorMovilDbHelper mDbHelper;

    public CatalogoDaoImpl(Context context){
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static CatalogoDao getSingleton(){
        if (catalogoDao == null){
            catalogoDao = new CatalogoDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return catalogoDao;
    }

    //*** Métodos de Catálogo de Productos
    @Override
    public long insertarCatalogoProductos(Producto producto, Integer idCadena){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues valoreAinsertar = this.rellenarDatosAinsertarEnCatalogoProductos(producto, idCadena);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoProductos.TABLE_NAME,
                null,
                valoreAinsertar
        );

        return  newRowId;
    }

    @Override
    public long eliminarCatalogoProductos() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  TablasDeCatalogos.CatalogoProductos.TABLE_NAME , null , null);
    }

    @Override
    public List<Producto> recuperarCatalogoProductosPorIdCadena(Integer idCadena){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Producto> productos = new ArrayList<Producto>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    TablasDeCatalogos.CatalogoProductos.TABLE_NAME,
                    TablasDeCatalogos.CatalogoProductos.getColumns(),
                    TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_IDCADENA.column + " = " + idCadena,
                    null,
                    null,
                    null,
                    null
            );

            int size = cursor.getCount();
            if( size > 0 ){
                cursor.moveToFirst();
                for( int i = 0; i < size; i++ ){
                    productos.add(this.cargarObjetoProductosDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null){
                cursor.close();
            }
        }
        return productos;
    }

    @Override
    public List<Integer> recuperarListaDeIdCadenasEnCatalogoDeProductos(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Integer> idTiendasList = new ArrayList<Integer>();

        String[] nombreColumnaIdTienda = {TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_IDCADENA.column };
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeCatalogos.CatalogoProductos.TABLE_NAME,       // The table to query
                    nombreColumnaIdTienda,                                 // The columns to return
                    null,                                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_IDCADENA.column  ,     // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    Integer idTienda = cursor.getInt(0);
                    idTiendasList.add( idTienda );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }
        return idTiendasList;
    }

    //*** Métodos de Catálogo de Preguntas
    @Override
    public long insertarCatalogoPreguntas( Pregunta pregunta , int idEncuesta ){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues valoresAinsertar = this.rellenarDatosAinsertarEnCatalogoPreguntas(pregunta, idEncuesta);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,
                null,
                valoresAinsertar);

        return newRowId;
    }

    @Override
    public long eliminarCatalogoPreguntasRespuestas() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(  TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME , null , null);
        db.delete(  TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME , null , null);
        return 0;
    }

    @Override
    public List<Pregunta> recuperarCatalogoPreguntasPorIdEncuesta(int idEncuesta) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Pregunta> preguntas = new ArrayList<Pregunta>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,  // The table to query
                    TablasDeCatalogos.CatalogoPreguntas.getColumns(),                                // The columns to return
                    TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column + " = " + idEncuesta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    preguntas.add(this.cargarObjetoPreguntasDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null ){
                cursor.close();
            }
        }
        return preguntas;
    }

    @Override
    public List<Integer> recuperarListaIdEncuestaEnCatalogo( ) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Integer> idEncuestaList = new ArrayList<Integer>();

        String[] nombreColumnaIdEncuesta = {TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column };
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeCatalogos.CatalogoPreguntas.TABLE_NAME,       // The table to query
                    nombreColumnaIdEncuesta,                                 // The columns to return
                    null,                                                    // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column  ,     // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );


            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    Integer idEncuesta = cursor.getInt(0);
                    idEncuestaList.add( idEncuesta );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return idEncuestaList;
    }

    //*** Métodos de Catálogo de Respuestas
    @Override
    public long insertarCatalogoRespuesta(Respuesta respuesta, int idPregunta) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues valoresAinsertar = this.rellenarDatosAinsertarEnCatalogoRespuesta(respuesta, idPregunta);

        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME,
                null,
                valoresAinsertar);

        return newRowId;
    }

    @Override
    public List<Respuesta> recuperarCatalogoRespuestaPorIdPregunta(int idPregunta) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Respuesta> respuestas = new ArrayList<Respuesta>();

        Cursor cursor = null;
        try{
            cursor = db.query(
                    TablasDeCatalogos.CatalogoRespuestas.TABLE_NAME,  // The table to query
                    TablasDeCatalogos.CatalogoRespuestas.getColumns(),                                // The columns to return
                    TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column + " = " + idPregunta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    respuestas.add(this.cargarObjetoRespuestaDesdeCursor(cursor));
                    cursor.moveToNext();
                }
            }
        }finally{
            if( cursor != null ){
                cursor.close();
            }
        }
        return respuestas;
    }

    //*** Métodos de Catálogo de Respuestas
    public long insertarMotivosRetiro(MotivoRetiro motivoRetiro){

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = this.rellenarDatosAInsertarEnCatalogoMotivoRetiro(motivoRetiro);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TablasDeCatalogos.MotivosDeRetiro.TABLE_NAME,
                null,
                values);
        return newRowId; //Noi es necesario recuperar un id de la tabla
    }

    public List<MotivoRetiro> recuperarTodosLosMotivosDeRetiro(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<MotivoRetiro> motivoRetiroList = new ArrayList<MotivoRetiro>();

        Cursor cursor = null;
        try {
            cursor = db.query(
                    TablasDeCatalogos.MotivosDeRetiro.TABLE_NAME,                              // The table to query
                    TablasDeCatalogos.MotivosDeRetiro.getColumns(),                           // The columns to return
                    TablasDeCatalogos.MotivosDeRetiro.COLUMN_NAME_IDMOTIVORETIRO.column,     // The columns for the WHERE clause
                    null,                                                                   // The values for the WHERE clause
                    null,                                                                  // don't group the rows
                    null,                                                                 // don't filter by row groups
                    null                                                                 // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    motivoRetiroList.add( this.cargarObjetoMotivoRetiro(cursor) );
                    cursor.moveToNext();
                }
            }

        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return motivoRetiroList;
    }

    public long eliminarTodosLosMotivosDeRetiro(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(TablasDeCatalogos.MotivosDeRetiro.TABLE_NAME , null , null);
    }


    /***
     *
     * BLOQUE PARA PREPARAR LOS DATOS
     *
     * ***/
    private ContentValues rellenarDatosAinsertarEnCatalogoProductos(Producto producto, Integer idCadena) {
        ContentValues values = new ContentValues();

        values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_CODIGO.column , producto.getCodigo() );
        values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_DESCRIPCION.column , producto.getDescripcion() );
        values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_PRECIOBASE.column , producto.getPrecioBase() );
        //values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_EXISTENCIA.column , producto.getExistencia());
        //values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_PRECIOENTIENDA.column ,  producto.getPrecioEnTienda());
        //values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_NUMEROFRENTE.column, producto.getNumeroFrente());
        //values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_EXHIBICIONADICIONAL.column, producto.getExhibicionAdicional().name());
        values.put(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_IDCADENA.column, idCadena);

        return values;
    }

    private Producto cargarObjetoProductosDesdeCursor(Cursor cursor) {
        Producto producto = new Producto();

        producto.setCodigo(cursor.getString(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_CODIGO.index));
        producto.setDescripcion(cursor.getString(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_DESCRIPCION.index));
        producto.setPrecioBase(cursor.getInt(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_PRECIOBASE.index));
        //producto.setExistencia(cursor.getInt(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_EXISTENCIA.index));
        //producto.setPrecioEnTienda(cursor.getInt(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_PRECIOENTIENDA.index));
        //producto.setNumeroFrente(cursor.getInt(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_NUMEROFRENTE.index));
        //String exhibicionAdicional = cursor.getString(TablasDeCatalogos.CatalogoProductos.COLUMN_NAME_EXHIBICIONADICIONAL.index);
        //producto.setExhibicionAdicional(RespuestaBinaria.valueOf(exhibicionAdicional));

        return producto;
    }

    private ContentValues rellenarDatosAinsertarEnCatalogoPreguntas(Pregunta pregunta , int idEncuesta) {
        ContentValues values = new ContentValues();
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.column, pregunta.getIdPregunta());
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column, pregunta.getDescripcion());
        values.put(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column, idEncuesta);
        return values;
    }

    private Pregunta cargarObjetoPreguntasDesdeCursor(Cursor cursor) {
        Pregunta pregunta = new Pregunta();
        pregunta.setIdPregunta(cursor.getString(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.index));
        pregunta.setDescripcion(cursor.getString(TablasDeCatalogos.CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.index));
        return pregunta;
    }

    private ContentValues rellenarDatosAinsertarEnCatalogoRespuesta(Respuesta respuesta, int idPregunta) {
        ContentValues values = new ContentValues();
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.column , respuesta.getIdRespuesta() );
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column, respuesta.getDescripcion());
        values.put(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column, idPregunta);
        return values;
    }

    private Respuesta cargarObjetoRespuestaDesdeCursor(Cursor cursor) {
        Respuesta respuesta = new Respuesta();
        respuesta.setIdRespuesta(cursor.getString(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.index));
        respuesta.setDescripcion(cursor.getString(TablasDeCatalogos.CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.index));
        return respuesta;
    }

    private ContentValues rellenarDatosAInsertarEnCatalogoMotivoRetiro( MotivoRetiro motivoRetiro ) {
        ContentValues values = new ContentValues();

        values.put(TablasDeCatalogos.MotivosDeRetiro.COLUMN_NAME_IDMOTIVORETIRO.column, motivoRetiro.getIdMotivoRetiro());
        values.put(TablasDeCatalogos.MotivosDeRetiro.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column, motivoRetiro.getDescripcionMotivoRetiro());

        return values;
    }

    private MotivoRetiro cargarObjetoMotivoRetiro( Cursor cursor ){
        MotivoRetiro motivoRetiro = new MotivoRetiro();

        motivoRetiro.setIdMotivoRetiro(cursor.getInt(TablasDeCatalogos.MotivosDeRetiro.COLUMN_NAME_IDMOTIVORETIRO.index));
        motivoRetiro.setDescripcionMotivoRetiro(cursor.getString(TablasDeCatalogos.MotivosDeRetiro.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.index));

        return motivoRetiro;
    }



}//Fin clase CatalogoDaioimpl
