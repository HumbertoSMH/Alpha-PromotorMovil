package mx.com.algroup.promotormovil.business.persistence;

/**
 * Created by devmac03 on 10/06/15.
 */
public class Table {

    // To prevent someone from accidentally instantiating the PromotorMovilPersistence class,
    // give it an empty constructor.
    public Table() {}
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " integer ";
    private static final String COMMA_SEP = " , ";
    private static final String SEMICOMMA_SEP = " ; ";
    private static final String NOT_NULL = " NOT NULL ";
//    private static final String TEXT_DEFINITION_1 = TEXT_TYPE + NOT_NULL + COMMA_SEP;
//    private static final String TEXT_DEFINITION_2 = TEXT_TYPE + NOT_NULL ;
//    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE + NOT_NULL + COMMA_SEP;
//    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE + NOT_NULL ;
private static final String TEXT_DEFINITION_1 = TEXT_TYPE +  COMMA_SEP;
    private static final String TEXT_DEFINITION_2 = TEXT_TYPE  ;
    private static final String INTEGER_DEFINITION_1 = INTEGER_TYPE +  COMMA_SEP;
    private static final String INTEGER_DEFINITION_2 = INTEGER_TYPE  ;



    /* Inner class that defines the table contents */
    public static enum Rutas {
        COLUMN_NAME_IDRUTA (0 , "idRuta"),
        COLUMN_NAME_FECHAINICIO( 1 , "fechaInicio" ),
        COLUMN_NAME_FECHAFIN ( 2 , "fechaFin" ),
        COLUMN_NAME_FECHAPROGRAMADASTRING (3 , "fechaProgramadaString" ),
        COLUMN_NAME_FECHACREACIONSTRING ( 4 ,  "fechaCreacionString" ),
        COLUMN_NAME_FECHAULTIMAMODIFICACION(5 , "fechaUltimaModificacion" ),
        COLUMN_NAME_CLAVEPROMOTOR( 6 , "clavePromotor"),
        COLUMN_NAME_PASSWORDPROMOTOR( 7 , "passwordPromotor");

        public static final String TABLE_NAME = "Rutas";

        public int index;
        public String column;

        private Rutas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Rutas[] rutasArray = Rutas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }

    /* Inner class that defines the table contents */
    public static enum Visitas {
        COLUMN_NAME_IDVISITA ( 0 , "idVisita"),
        COLUMN_NAME_ESTATUSVISITA ( 1 , "estatusVisita" ),
        COLUMN_NAME_FECHACHECKIN ( 2 , "fechaCheckIn" ),
        COLUMN_NAME_FECHACHECKOUT (3 , "fechaCheckout" ),
        COLUMN_NAME_GERENTE ( 4 ,  "gerente" ),
        COLUMN_NAME_FIRMAGERENTE (5 , "firmaGerente" ),
        COLUMN_NAME_COMENTARIOS ( 6 , "comentarios" ),
        COLUMN_NAME_APLICARENCUESTA( 7 , "aplicarEncuesta" ),
        COLUMN_NAME_APLICARCAPTUTAPRODUCTOS ( 8 , "aplicarCapturaProductos" ),
        COLUMN_NAME_IDENCUESTA ( 9 , "idEncuesta" ),
        COLUMN_NAME_REPORTECAPTURADO ( 10 , "reporteCapturado" ),
        COLUMN_NAME_ENCUESTACAPTURADA ( 11 , "encuestaCapturada" ),
        COLUMN_NAME_IDMOTIVORETIRO ( 12 , "idMotivoRetiro" ),
        COLUMN_NAME_DESCRIPCIONMOTIVORETIRO ( 13 , "descripcionMotivoRetiro" ),
        COLUMN_NAME_IDCADENA ( 14 , "idCadena" ),
        COLUMN_NAME_NOMBRECADENA ( 15 , "nombreCadena" ),

        //SeccionTienda
        COLUMN_NAME_IDTIENDA ( 16 , "idTienda" ),
        COLUMN_NAME_NOMBRETIENDA( 17 , "nombreTienda" ),
        //SeccionDireccion
        COLUMN_NAME_CALLE( 18 , "calle" ),
        COLUMN_NAME_NUMEROINTERIOR( 19 , "numeroInterior" ),
        COLUMN_NAME_NUMEROEXTERIOR( 20 , "numeroExterior" ),
        COLUMN_NAME_CODIGOPOSTAL( 21 , "codigoPostal" ),
        COLUMN_NAME_COLONIA( 22 , "colonia" ),
        COLUMN_NAME_DELEGACION( 23 , "delegacion" ),
        COLUMN_NAME_ESTADO( 24 , "estado" ),
        COLUMN_NAME_PAIS( 25 , "pais" ),
        COLUMN_NAME_REFERENCIA( 26 , "referencia" ),
        //SeccionLocation
        COLUMN_NAME_LATITUD( 27 , "latitud" ),
        COLUMN_NAME_LONGITUD( 28 , "longitud" ),

        //Verificación de CheckIn
        COLUMN_NAME_CHECKINNOTIFICADO ( 29 , "checkInNotificado"),


        //RUTA
        COLUMN_NAME_IDRUTA ( 30 , "idRuta" );

        public static final String TABLE_NAME = "Visita";

        public int index;
        public String column;

        private Visitas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Visitas[] rutasArray = Visitas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum Productos {
        COLUMN_NAME_CODIGO ( 0 , "codigo"),
        COLUMN_NAME_DESCRIPCION ( 1 , "descripcion"),
        COLUMN_NAME_PRECIOBASE ( 2 , "precioBase" ),
        COLUMN_NAME_EXISTENCIA ( 3 , "existencia" ),
        COLUMN_NAME_PRECIOENTIENDA (4 , "precioEnTienda" ),
        COLUMN_NAME_NUMEROFRENTE ( 5 ,  "numeroFrente" ),
        COLUMN_NAME_EXHIBICIONADICIONAL (6 , "exhibicionAdicional" ),
        COLUMN_NAME_IDVISITA ( 7 , "idVisita" );

        public static final String TABLE_NAME = "Productos";

        public int index;
        public String column;

        private Productos( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Productos[] rutasArray = Productos.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum Fotografias {
        COLUMN_NAME_IDFOTO ( 0 , "idFoto"),
        COLUMN_NAME_FOTO ( 1 , "foto" ),
        COLUMN_NAME_FECHACAPTURA ( 2 , "fechaCaptura" ),
        COLUMN_NAME_IDVISITA (3 , "idVisita" );

        public static final String TABLE_NAME = "Fotografias";

        public int index;
        public String column;

        private Fotografias( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Fotografias[] rutasArray = Fotografias.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }


    public static enum Encuestas {
        COLUMN_NAME_IDPREGUNTA ( 0 , "idPregunta"),
        COLUMN_NAME_DESCRIPCIONPREGUNTA ( 1 , "descripcionPregunta"),
        COLUMN_NAME_IDRESPUESTA ( 2 , "idRespuesta" ),
        COLUMN_NAME_DESCRIPCIONRESPUESTA ( 3 , "descripcionRespuesta" ),
        COLUMN_NAME_IDENTIFICADORENCUESTA( 4 , "identificadorEncuesta"),
        COLUMN_NAME_IDVISITA ( 5 , "idVisita" );

        public static final String TABLE_NAME = "Encuestas";

        public int index;
        public String column;

        private Encuestas( int index , String column ){
            this.index = index;
            this.column = column;
        }

        public static String[] getColumns(){
            Encuestas[] rutasArray = Encuestas.values();
            int size = rutasArray.length;
            String[] columnas = new String[ size];
            for( int j = 0; j < size ; j ++ ){
                columnas[j] = rutasArray[j].column;
            }
            return columnas;
        }
    }





    public static final String SQL_CREATE_TABLE_RUTAS =
            "CREATE TABLE " + Rutas.TABLE_NAME + " (" +
                    Rutas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Rutas.COLUMN_NAME_FECHAINICIO.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAFIN.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAPROGRAMADASTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHACREACIONSTRING.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_FECHAULTIMAMODIFICACION.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_CLAVEPROMOTOR.column + TEXT_DEFINITION_1 +
                    Rutas.COLUMN_NAME_PASSWORDPROMOTOR.column + TEXT_DEFINITION_2 +
            " ); ";

    public static final String SQL_DELETE_RUTAS = "DROP TABLE IF EXISTS " + Rutas.TABLE_NAME + " ; ";

    public static final String SQL_CREATE_TABLE_VISITAS =
            "CREATE TABLE " + Visitas.TABLE_NAME + " (" +
                    Visitas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 + " PRIMARY KEY, " +
                    Visitas.COLUMN_NAME_ESTATUSVISITA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKIN.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FECHACHECKOUT.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_GERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_FIRMAGERENTE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_COMENTARIOS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_APLICARENCUESTA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_APLICARCAPTUTAPRODUCTOS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDENCUESTA.column + INTEGER_DEFINITION_1 +
                    Visitas.COLUMN_NAME_REPORTECAPTURADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ENCUESTACAPTURADA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDMOTIVORETIRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DESCRIPCIONMOTIVORETIRO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_IDCADENA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBRECADENA.column + TEXT_DEFINITION_1 +


                    Visitas.COLUMN_NAME_IDTIENDA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NOMBRETIENDA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CALLE.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NUMEROINTERIOR.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_NUMEROEXTERIOR.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CODIGOPOSTAL.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_COLONIA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_DELEGACION.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_ESTADO.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_PAIS.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_REFERENCIA.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LATITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_LONGITUD.column + TEXT_DEFINITION_1 +
                    Visitas.COLUMN_NAME_CHECKINNOTIFICADO.column + TEXT_DEFINITION_1 +

                    Visitas.COLUMN_NAME_IDRUTA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_VISISTAS = "DROP TABLE IF EXISTS " + Visitas.TABLE_NAME + " ; ";



    public static final String SQL_CREATE_TABLE_PRODUCTO =
            "CREATE TABLE " + Productos.TABLE_NAME + " (" +
                    Productos.COLUMN_NAME_CODIGO.column + TEXT_DEFINITION_2 + " PRIMARY KEY, " +
                    Productos.COLUMN_NAME_DESCRIPCION.column + TEXT_DEFINITION_1 +
                    Productos.COLUMN_NAME_PRECIOBASE.column + INTEGER_DEFINITION_1 +
                    Productos.COLUMN_NAME_EXISTENCIA.column + INTEGER_DEFINITION_1 +
                    Productos.COLUMN_NAME_PRECIOENTIENDA.column + INTEGER_DEFINITION_1 +
                    Productos.COLUMN_NAME_NUMEROFRENTE.column + INTEGER_DEFINITION_1 +
                    Productos.COLUMN_NAME_EXHIBICIONADICIONAL.column + TEXT_DEFINITION_1 +
                    Productos.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_PRODUCTOS = "DROP TABLE IF EXISTS " + Productos.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_FOTOGRAFIA =
            "CREATE TABLE " + Fotografias.TABLE_NAME + " (" +
                    Fotografias.COLUMN_NAME_IDFOTO.column + TEXT_DEFINITION_2 + " PRIMARY KEY, " +
                    Fotografias.COLUMN_NAME_FOTO.column + TEXT_DEFINITION_1 +
                    Fotografias.COLUMN_NAME_FECHACAPTURA.column + TEXT_DEFINITION_1 +
                    Fotografias.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_FOTOGRAFIAS = "DROP TABLE IF EXISTS " + Fotografias.TABLE_NAME + " ; ";


    public static final String SQL_CREATE_TABLE_ENCUESTA =
            "CREATE TABLE " + Encuestas.TABLE_NAME + " (" +
                    Encuestas.COLUMN_NAME_IDPREGUNTA.column + INTEGER_DEFINITION_1  +
                    Encuestas.COLUMN_NAME_DESCRIPCIONPREGUNTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDRESPUESTA.column + INTEGER_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_DESCRIPCIONRESPUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDENTIFICADORENCUESTA.column + TEXT_DEFINITION_1 +
                    Encuestas.COLUMN_NAME_IDVISITA.column + INTEGER_DEFINITION_2 +
                    " ); ";

    public static final String SQL_DELETE_ENCUESTAS = "DROP TABLE IF EXISTS " + Encuestas.TABLE_NAME + " ; ";

}
