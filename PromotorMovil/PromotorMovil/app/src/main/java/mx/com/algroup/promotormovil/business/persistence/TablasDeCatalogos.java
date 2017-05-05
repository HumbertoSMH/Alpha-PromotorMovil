package mx.com.algroup.promotormovil.business.persistence;

/**
 * Created by devmac02 on 29/09/15.
 */
public class TablasDeCatalogos {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public TablasDeCatalogos() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE;

    public static enum CatalogoProductos {
        COLUMN_NAME_CODIGO ( 0 , "codigo"),
        COLUMN_NAME_DESCRIPCION ( 1 , "descripcion"),
        COLUMN_NAME_PRECIOBASE ( 2 , "precioBase" ),
        COLUMN_NAME_EXISTENCIA ( 3 , "existencia" ),
        COLUMN_NAME_PRECIOENTIENDA (4 , "precioEnTienda" ),
        COLUMN_NAME_NUMEROFRENTE ( 5 ,  "numeroFrente" ),
        COLUMN_NAME_EXHIBICIONADICIONAL (6 , "exhibicionAdicional" ),
        COLUMN_NAME_IDCADENA ( 7 , "idCadena" );

        public static final String TABLE_NAME = "CatalogoProductos";

        public int index;
        public String column;

        private CatalogoProductos( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoProductos[] catalogoProductosArray = CatalogoProductos.values();
            int size = catalogoProductosArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = catalogoProductosArray[j].column;
            }
            return columnas;
        }
    }

    public static enum CatalogoPreguntas {
        COLUMN_NAME_IDPREGUNTA ( 0 , "idPregunta"),
        COLUMN_NAME_DESCRIPCIONPREGUNTA ( 1 , "descripcionPregunta"),
        COLUMN_NAME_IDENCUESTA( 2 , "idEncuesta" );

        public static final String TABLE_NAME = "CatalogoPreguntas";

        public int index;
        public String column;

        private CatalogoPreguntas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoPreguntas[] catalogoPreguntasArray = CatalogoPreguntas.values();
            int size = catalogoPreguntasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = catalogoPreguntasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum CatalogoRespuestas {
        COLUMN_NAME_IDRESPUESTA ( 0 , "idRespuesta"),
        COLUMN_NAME_DESCRIPCIONRESPUESTA ( 1 , "descripcionRespuesta"),
        COLUMN_NAME_IDPREGUNTA( 2 , "idPregunta" );

        public static final String TABLE_NAME = "CatalogoRespuestas";

        public int index;
        public String column;

        private CatalogoRespuestas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            CatalogoRespuestas[] CatalogoRespuestasArray = CatalogoRespuestas.values();
            int size = CatalogoRespuestasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = CatalogoRespuestasArray[j].column;
            }
            return columnas;
        }
    }

    public static enum MotivosDeRetiro {
        COLUMN_NAME_IDMOTIVORETIRO( 0 , "idMotivoRetiro"),
        COLUMN_NAME_DESCRIPCIONMOTIVORETIRO ( 1 , "descripcionMotivoRetiro");

        public static final String TABLE_NAME = "MotivosDeRetiro";

        public int index;
        public String column;

        private MotivosDeRetiro( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            MotivosDeRetiro[] motivoRetiroArray = MotivosDeRetiro.values();
            int size = motivoRetiroArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = motivoRetiroArray[j].column;
            }
            return columnas;
        }
    }


    public static final String SQL_CREATE_TABLE_CATALOGO_PRODUCTOS =
            "CREATE TABLE " + CatalogoProductos.TABLE_NAME + " (" +
                    CatalogoProductos.COLUMN_NAME_CODIGO.column + TEXT_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_DESCRIPCION.column + TEXT_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_PRECIOBASE.column + INTEGER_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_EXISTENCIA.column + INTEGER_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_PRECIOENTIENDA.column + INTEGER_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_NUMEROFRENTE.column + INTEGER_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_EXHIBICIONADICIONAL.column + TEXT_DEFINITION_1 +
                    CatalogoProductos.COLUMN_NAME_IDCADENA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_PRODUCTOS = "DROP TABLE IF EXISTS " + CatalogoProductos.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_CATALOGO_PREGUNTAS =
            "CREATE TABLE " + CatalogoPreguntas.TABLE_NAME + " (" +
                    CatalogoPreguntas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_1  +
                    CatalogoPreguntas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column + TEXT_DEFINITION_1 +
                    CatalogoPreguntas.COLUMN_NAME_IDENCUESTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_PREGUNTAS = "DROP TABLE IF EXISTS " + CatalogoPreguntas.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_CATALOGO_RESPUESTAS =
            "CREATE TABLE " + CatalogoRespuestas.TABLE_NAME + " (" +
                    CatalogoRespuestas.COLUMN_NAME_IDRESPUESTA.column + INTEGER_DEFINITION_1  +
                    CatalogoRespuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column + TEXT_DEFINITION_1 +
                    CatalogoRespuestas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_CATALOGO_RESPUESTAS = "DROP TABLE IF EXISTS " + CatalogoRespuestas.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_MOTIVOS_DE_RETIRO =
            "CREATE TABLE " + MotivosDeRetiro.TABLE_NAME + " (" +
                    MotivosDeRetiro.COLUMN_NAME_IDMOTIVORETIRO.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    MotivosDeRetiro.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column + TEXT_DEFINITION_2 +
                    " ); ";

    public static final String SQL_MOTIVOS_DE_RETIRO = "DROP TABLE IF EXISTS " + MotivosDeRetiro.TABLE_NAME + " ; ";




}
