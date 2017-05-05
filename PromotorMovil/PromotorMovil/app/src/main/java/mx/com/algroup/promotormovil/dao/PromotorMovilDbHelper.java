package mx.com.algroup.promotormovil.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mx.com.algroup.promotormovil.business.persistence.TablaErrores;
import mx.com.algroup.promotormovil.business.persistence.TablasDeCatalogos;
import mx.com.algroup.promotormovil.business.persistence.Table;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 10/06/15.
 */
public class PromotorMovilDbHelper extends SQLiteOpenHelper{
    private static final String CLASSNAME = PromotorMovilDbHelper.class.getSimpleName();
    private Context context;


    public PromotorMovilDbHelper(Context context) {
        super(context, Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table.SQL_CREATE_TABLE_RUTAS);
        db.execSQL(Table.SQL_CREATE_TABLE_VISITAS);
        db.execSQL(Table.SQL_CREATE_TABLE_ENCUESTA);
        db.execSQL(Table.SQL_CREATE_TABLE_FOTOGRAFIA);
        db.execSQL(Table.SQL_CREATE_TABLE_PRODUCTO);
        db.execSQL(TablaErrores.SQL_CREATE_TABLE_ERRORES);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_PRODUCTOS);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_PREGUNTAS);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_RESPUESTAS);
        db.execSQL(TablasDeCatalogos.SQL_CREATE_TABLE_MOTIVOS_DE_RETIRO);
        LogUtil.logInfo( CLASSNAME , "onCreate(SQLiteDatabase db)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL( Table.SQL_DELETE_PRODUCTOS );
        db.execSQL( Table.SQL_DELETE_FOTOGRAFIAS );
        db.execSQL( Table.SQL_DELETE_ENCUESTAS );
        db.execSQL( Table.SQL_DELETE_VISISTAS );
        db.execSQL( Table.SQL_DELETE_RUTAS );
        db.execSQL( TablaErrores.SQL_DELETE_ERRORES );
        db.execSQL( TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_PRODUCTOS );
        db.execSQL( TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_PREGUNTAS );
        db.execSQL( TablasDeCatalogos.SQL_CREATE_TABLE_CATALOGO_RESPUESTAS );
        db.execSQL( TablasDeCatalogos.SQL_MOTIVOS_DE_RETIRO );

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /*ublic boolean checkDataBaseExists() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase( Const.PATH + Const.DATABASE_NAME , null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // La base de datos no existe aun
            LogUtil.logInfo( CLASSNAME , "La base de datos no existe error:" + e.getMessage() ) ;
        }
        LogUtil.logInfo( CLASSNAME , "checkDataBaseExists...La base de datos existe?: " + (checkDB != null) );
        return checkDB != null;
    }*/

}
