package mx.com.algroup.promotormovil.business.persistence;

/**
 * Created by devmac03 on 11/08/15.
 */
public class TablaErrores {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public TablaErrores() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE  ;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE  ;

    /* Inner class that defines the table contents */
    public static enum Errores {
        COLUMN_NAME_IDERROR (0 , "idError"),
        COLUMN_NAME_APLICACION( 1 , "aplicacion" ),
        COLUMN_NAME_VERSION ( 2 , "version" ),
        COLUMN_NAME_USUARIO(3 , "usuario" ),
        COLUMN_NAME_FECHAHORA ( 4 ,  "fechaHora" ),
        COLUMN_NAME_TRAZA(5 , "traza" ),
        COLUMN_NAME_NOTIFICADO(6 , "notificado" );

        public static final String TABLE_NAME = "Errores";

        public int index;
        public String column;

        private Errores( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Errores[] rutasArray = Errores.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    public static final String SQL_CREATE_TABLE_ERRORES =
            "CREATE TABLE " + Errores.TABLE_NAME + " (" +
                    Errores.COLUMN_NAME_IDERROR.column + INTEGER_DEFINITION_2 + "PRIMARY KEY ASC AUTOINCREMENT , " +
                    Errores.COLUMN_NAME_APLICACION.column + TEXT_DEFINITION_1 +
                    Errores.COLUMN_NAME_VERSION.column + TEXT_DEFINITION_1 +
                    Errores.COLUMN_NAME_USUARIO.column + TEXT_DEFINITION_1 +
                    Errores.COLUMN_NAME_FECHAHORA.column + TEXT_DEFINITION_1 +
                    Errores.COLUMN_NAME_TRAZA.column + TEXT_DEFINITION_1 +
                    Errores.COLUMN_NAME_NOTIFICADO.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_ERRORES = "DROP TABLE IF EXISTS " + Errores.TABLE_NAME + " ; ";

}
