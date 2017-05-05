package mx.com.algroup.promotormovil.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.dao.PromotorMovilDbHelper;
import mx.com.algroup.promotormovil.dao.VisitaDao;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class VisitaDaoImpl implements VisitaDao{
    private static String CLASSNAME = VisitaDaoImpl.class.getSimpleName();

    private static VisitaDao visitaDao;
    private Context context;
    PromotorMovilDbHelper mDbHelper;

    public VisitaDaoImpl(Context context) {
        this.context = context;
        this.mDbHelper = new PromotorMovilDbHelper( context );
    }

    public static VisitaDao getSingleton( ) {
        if (visitaDao == null) {
            visitaDao = new VisitaDaoImpl(PromotorMovilApp.getPromotorMovilApp() );
        }
        return visitaDao;
    }



   @Override
        public void insertVisita( Visita visita , int idRuta) {
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = this.rellenarDatosAInsertar( visita , idRuta );

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    Table.Visitas.TABLE_NAME,
                    null,
                    values);
            LogUtil.printLog(CLASSNAME, "insertVisita: visita:" + visita);
    }

    public Visita getVisitaById( Integer idVisita){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita visita = null;

        Cursor cursor = null;
        try{
                cursor = db.query(
                        Table.Visitas.TABLE_NAME,  // The table to query
                        Table.Visitas.getColumns(),                                // The columns to return
                        Table.Visitas.COLUMN_NAME_IDVISITA.column + " = " + idVisita,  // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                    );

            if( cursor.getCount() > 0){
                cursor.moveToFirst();
                visita = this.cargarObjetoVisita( cursor);
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return visita;
    }

    public Visita[] getVisitasByIdRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Visita[] visitasArray = new Visita[0];

        Cursor cursor = null;
        try{
            cursor = db.query(
                        Table.Visitas.TABLE_NAME,  // The table to query
                        Table.Visitas.getColumns(),                                // The columns to return
                        Table.Visitas.COLUMN_NAME_IDRUTA.column + " = " + idRuta,                                        // The columns for the WHERE clause
                        null,                                                    // The values for the WHERE clause
                        null,                                                   // don't group the rows
                        null,                                                   // don't filter by row groups
                        null                                                    // The sort order
                );

            int size = cursor.getCount();
            if( size  > 0){
                visitasArray = new Visita[size];
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    visitasArray[i] = this.cargarObjetoVisita(cursor);
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return visitasArray;

    }


    public Integer[] getIdVisitasQueNoSonDeRuta(  Integer idRuta){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Integer[] idVisitaSinRutaArray = new Integer[0];

        String[] columnsIdVisita = {Table.Visitas.COLUMN_NAME_IDVISITA.column};
        Cursor cursor = null;
        try{
            cursor = db.query(
                    Table.Visitas.TABLE_NAME,  // The table to query
                    columnsIdVisita,                                // The columns to return
                    Table.Visitas.COLUMN_NAME_IDRUTA.column + " <> " + idRuta,                                        // The columns for the WHERE clause
                    null,                                                    // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    null                                                    // The sort order
            );

            int size = cursor.getCount();
            if( size  > 0){
                idVisitaSinRutaArray = new Integer[size];
                cursor.moveToFirst();
                for (int i = 0; i < size; i++) {
                    idVisitaSinRutaArray[i] = cursor.getInt( 0 );
                    cursor.moveToNext();
                }
            }
        }finally {
            if( cursor != null ){
                cursor.close();
            }
        }

        return idVisitaSinRutaArray;

    }


    public long updateVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizar( visita , idRuta );

        return db.update( Table.Visitas.TABLE_NAME , values, Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);

    }

    public long updateIdRutaEnVisita( Visita visita , int idRuta){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = this.rellenarDatosAActualizarSoloIdRuta( visita , idRuta );
        return db.update( Table.Visitas.TABLE_NAME , values, Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + visita.getIdVisita() , null);
    }

    public long deleteVisitaById( int idVisita){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(  Table.Visitas.TABLE_NAME , Table.Visitas.COLUMN_NAME_IDVISITA.column + " = "
                + idVisita , null);
    }


    private ContentValues rellenarDatosAInsertar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name() );
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getFechaCheckIn() );
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKOUT.column, visita.getFechaCheckout() );
        values.put( Table.Visitas.COLUMN_NAME_GERENTE.column, visita.getGerente().getNombre() );
        values.put( Table.Visitas.COLUMN_NAME_FIRMAGERENTE.column, visita.getFirmaGerente() );
        values.put( Table.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios() );
        values.put( Table.Visitas.COLUMN_NAME_APLICARENCUESTA.column, visita.getAplicarEncuesta().name() );
        values.put( Table.Visitas.COLUMN_NAME_APLICARCAPTUTAPRODUCTOS.column, visita.getAplicarCapturaProductos().name() );
        values.put( Table.Visitas.COLUMN_NAME_IDENCUESTA.column, visita.getIdEncuesta() );
        values.put( Table.Visitas.COLUMN_NAME_REPORTECAPTURADO.column, visita.getReporteCapturado().name() );
        values.put( Table.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column, visita.getEncuestaCapturada().name() );
        values.put( Table.Visitas.COLUMN_NAME_IDMOTIVORETIRO.column, visita.getIdMotivoRetiro() );
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column, visita.getDescripcionMotivoRetiro() );
        values.put( Table.Visitas.COLUMN_NAME_IDCADENA.column, visita.getCadena().getIdCadena() );
        values.put( Table.Visitas.COLUMN_NAME_NOMBRECADENA.column, visita.getCadena().getNombreCadena() );

        values.put( Table.Visitas.COLUMN_NAME_IDTIENDA.column, visita.getTienda().getIdTienda() );
        values.put( Table.Visitas.COLUMN_NAME_NOMBRETIENDA.column, visita.getTienda().getNombreTienda() );
        values.put( Table.Visitas.COLUMN_NAME_CALLE.column, visita.getTienda().getDireccion().getCalle() );
        values.put( Table.Visitas.COLUMN_NAME_NUMEROINTERIOR.column, visita.getTienda().getDireccion().getNumeroInterior() );
        values.put( Table.Visitas.COLUMN_NAME_NUMEROEXTERIOR.column, visita.getTienda().getDireccion().getNumeroExterior() );
        values.put( Table.Visitas.COLUMN_NAME_CODIGOPOSTAL.column, visita.getTienda().getDireccion().getCodigoPostal() );
        values.put( Table.Visitas.COLUMN_NAME_COLONIA.column, visita.getTienda().getDireccion().getColonia() );
        values.put( Table.Visitas.COLUMN_NAME_DELEGACION.column, visita.getTienda().getDireccion().getDelegacion() );
        values.put( Table.Visitas.COLUMN_NAME_ESTADO.column, visita.getTienda().getDireccion().getEstado() );
        values.put( Table.Visitas.COLUMN_NAME_PAIS.column, visita.getTienda().getDireccion().getPais() );
        values.put( Table.Visitas.COLUMN_NAME_REFERENCIA.column, visita.getTienda().getDireccion().getReferencia() );
        values.put( Table.Visitas.COLUMN_NAME_LATITUD.column, visita.getTienda().getLocation().getLatitud() );
        values.put( Table.Visitas.COLUMN_NAME_LONGITUD.column, visita.getTienda().getLocation().getLongitud() );
        values.put( Table.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column, visita.getCheckInNotificado().name() );

        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private ContentValues rellenarDatosAActualizar( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.column, visita.getEstatusVisita().name() );
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKIN.column, visita.getFechaCheckIn() );
        values.put( Table.Visitas.COLUMN_NAME_FECHACHECKOUT.column, visita.getFechaCheckout() );
        values.put( Table.Visitas.COLUMN_NAME_GERENTE.column, visita.getGerente().getNombre() );
        values.put( Table.Visitas.COLUMN_NAME_FIRMAGERENTE.column, visita.getFirmaGerente() );
        values.put( Table.Visitas.COLUMN_NAME_COMENTARIOS.column, visita.getComentarios() );
        values.put( Table.Visitas.COLUMN_NAME_APLICARENCUESTA.column, visita.getAplicarEncuesta().name() );
        values.put( Table.Visitas.COLUMN_NAME_APLICARCAPTUTAPRODUCTOS.column, visita.getAplicarCapturaProductos().name() );
        values.put( Table.Visitas.COLUMN_NAME_IDENCUESTA.column, visita.getIdEncuesta() );
        values.put( Table.Visitas.COLUMN_NAME_REPORTECAPTURADO.column, visita.getReporteCapturado().name() );
        values.put( Table.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column, visita.getEncuestaCapturada().name() );
        values.put( Table.Visitas.COLUMN_NAME_IDMOTIVORETIRO.column, visita.getIdMotivoRetiro() );
        values.put( Table.Visitas.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column, visita.getDescripcionMotivoRetiro() );
        values.put( Table.Visitas.COLUMN_NAME_IDCADENA.column, visita.getCadena().getIdCadena() );
        values.put( Table.Visitas.COLUMN_NAME_NOMBRECADENA.column, visita.getCadena().getNombreCadena() );

        values.put( Table.Visitas.COLUMN_NAME_IDTIENDA.column, visita.getTienda().getIdTienda() );
        values.put( Table.Visitas.COLUMN_NAME_NOMBRETIENDA.column, visita.getTienda().getNombreTienda() );
        values.put( Table.Visitas.COLUMN_NAME_CALLE.column, visita.getTienda().getDireccion().getCalle() );
        values.put( Table.Visitas.COLUMN_NAME_NUMEROINTERIOR.column, visita.getTienda().getDireccion().getNumeroInterior() );
        values.put( Table.Visitas.COLUMN_NAME_NUMEROEXTERIOR.column, visita.getTienda().getDireccion().getNumeroExterior() );
        values.put( Table.Visitas.COLUMN_NAME_CODIGOPOSTAL.column, visita.getTienda().getDireccion().getCodigoPostal() );
        values.put( Table.Visitas.COLUMN_NAME_COLONIA.column, visita.getTienda().getDireccion().getColonia() );
        values.put( Table.Visitas.COLUMN_NAME_DELEGACION.column, visita.getTienda().getDireccion().getDelegacion() );
        values.put( Table.Visitas.COLUMN_NAME_ESTADO.column, visita.getTienda().getDireccion().getEstado() );
        values.put( Table.Visitas.COLUMN_NAME_PAIS.column, visita.getTienda().getDireccion().getPais() );
        values.put( Table.Visitas.COLUMN_NAME_REFERENCIA.column, visita.getTienda().getDireccion().getReferencia() );
        values.put( Table.Visitas.COLUMN_NAME_LATITUD.column, visita.getTienda().getLocation().getLatitud() );
        values.put( Table.Visitas.COLUMN_NAME_LONGITUD.column, visita.getTienda().getLocation().getLongitud() );

        values.put(Table.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column, visita.getCheckInNotificado().name());

        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private ContentValues rellenarDatosAActualizarSoloIdRuta( Visita visita , int idRuta ) {
        ContentValues values = new ContentValues();
        //values.put( Table.Visitas.COLUMN_NAME_IDVISITA.column, visita.getIdVisita() );
        values.put( Table.Visitas.COLUMN_NAME_IDRUTA.column, idRuta );
        return values;
    }

    private Visita cargarObjetoVisita( Cursor cursor ){
        Visita visita = new Visita();
        visita.setIdVisita( "" + cursor.getInt( Table.Visitas.COLUMN_NAME_IDVISITA.index ) );

        String estatusNombre = cursor.getString( Table.Visitas.COLUMN_NAME_ESTATUSVISITA.index );
        visita.setEstatusVisita(EstatusVisita.valueOf( estatusNombre)) ;

        visita.setFechaCheckIn(cursor.getString(Table.Visitas.COLUMN_NAME_FECHACHECKIN.index)) ;
        visita.setFechaCheckout(cursor.getString(Table.Visitas.COLUMN_NAME_FECHACHECKOUT.index));

        visita.getGerente().setNombre(cursor.getString(Table.Visitas.COLUMN_NAME_GERENTE.index));

        visita.setFirmaGerente(cursor.getBlob(Table.Visitas.COLUMN_NAME_FIRMAGERENTE.index)) ;
        visita.setComentarios(cursor.getString(Table.Visitas.COLUMN_NAME_COMENTARIOS.index));

        String aplicarEncuestaName = cursor.getString(Table.Visitas.COLUMN_NAME_APLICARENCUESTA.index);
        visita.setAplicarEncuesta(RespuestaBinaria.valueOf( aplicarEncuestaName ) );

        String aplciarCapturaProductosName = cursor.getString(Table.Visitas.COLUMN_NAME_APLICARCAPTUTAPRODUCTOS.index);
        visita.setAplicarCapturaProductos(RespuestaBinaria.valueOf( aplciarCapturaProductosName ) );

        visita.setIdEncuesta("" + cursor.getInt(Table.Visitas.COLUMN_NAME_IDENCUESTA.index));

        String reporteCapturadoName = cursor.getString(Table.Visitas.COLUMN_NAME_REPORTECAPTURADO.index);
        visita.setReporteCapturado(RespuestaBinaria.valueOf(reporteCapturadoName));

        String encuestaCapturadaName = cursor.getString(Table.Visitas.COLUMN_NAME_ENCUESTACAPTURADA.index);
        visita.setEncuestaCapturada(RespuestaBinaria.valueOf(encuestaCapturadaName));

        visita.setIdMotivoRetiro(cursor.getInt(Table.Visitas.COLUMN_NAME_IDMOTIVORETIRO.index));
        visita.setDescripcionMotivoRetiro(cursor.getString(Table.Visitas.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.index));

        visita.getCadena().setIdCadena( "" + cursor.getInt(Table.Visitas.COLUMN_NAME_IDCADENA.index));
        visita.getCadena().setNombreCadena(cursor.getString(Table.Visitas.COLUMN_NAME_NOMBRECADENA.index));

        visita.getTienda().setIdTienda(cursor.getString(Table.Visitas.COLUMN_NAME_IDTIENDA.index));
        visita.getTienda().setNombreTienda(cursor.getString(Table.Visitas.COLUMN_NAME_NOMBRETIENDA.index));
        visita.getTienda().getDireccion().setCalle(cursor.getString(Table.Visitas.COLUMN_NAME_CALLE.index));
        visita.getTienda().getDireccion().setNumeroInterior(cursor.getString(Table.Visitas.COLUMN_NAME_NUMEROINTERIOR.index));
        visita.getTienda().getDireccion().setNumeroExterior(cursor.getString(Table.Visitas.COLUMN_NAME_NUMEROEXTERIOR.index));
        visita.getTienda().getDireccion().setCodigoPostal(cursor.getString(Table.Visitas.COLUMN_NAME_CODIGOPOSTAL.index));
        visita.getTienda().getDireccion().setColonia(cursor.getString(Table.Visitas.COLUMN_NAME_COLONIA.index));
        visita.getTienda().getDireccion().setDelegacion(cursor.getString(Table.Visitas.COLUMN_NAME_DELEGACION.index));
        visita.getTienda().getDireccion().setEstado(cursor.getString(Table.Visitas.COLUMN_NAME_ESTADO.index));
        visita.getTienda().getDireccion().setPais(cursor.getString(Table.Visitas.COLUMN_NAME_PAIS.index));
        visita.getTienda().getDireccion().setReferencia(cursor.getString(Table.Visitas.COLUMN_NAME_REFERENCIA.index));
        visita.getTienda().getLocation().setLatitud(cursor.getString(Table.Visitas.COLUMN_NAME_LATITUD.index));
        visita.getTienda().getLocation().setLongitud(cursor.getString(Table.Visitas.COLUMN_NAME_LONGITUD.index));

        String checkInNotificadoName = cursor.getString(Table.Visitas.COLUMN_NAME_CHECKINNOTIFICADO.index);
        visita.setCheckInNotificado(RespuestaBinaria.valueOf(checkInNotificadoName));

        return visita;
    }

}
