package mx.com.algroup.promotormovil.utils;

import android.util.Log;

/**
 * Clase de constantes y parametros de la aplicación
 *
 * Created by MAMM on 15/04/15.
 *
 */
public class Const {

    /*Nivel de logger*/
    public static final int LOG_LEVEL = Log.INFO;

    public enum Enviroment{
        MOCK,
        FAKE,
        DES,
        DIS;

        public static Enviroment currentEnviroment = MOCK;
    }

    public static final int POSICION_CERO = 0;
    public static final int POSICION_UNO = 1;
    public static final int POSICION_DOS = 2;
    public static final int POSICION_TRES = 3;
    public static final int POSICION_CUATRO = 4;
    public static final int POSICION_CINCO = 5;
    public static final int POSICION_SEIS = 6;
    public static final int POSICION_SIETE = 7;
    public static final int POSICION_OCHO = 8;
    public static final int POSICION_NUEVE = 9;
    public static final int POSICION_DIEZ = 10;
    public static final int POSICION_ONCE = 11;
    public static final int POSICION_DOCE = 12;
    public static final int POSICION_TRECE = 13;
    public static final int POSICION_CATORCE = 14;
    public static final int POSICION_QUINCE = 15;
    public static final int POSICION_DIECISEIS = 16;
    public static final int POSICION_DIECISIETE = 17;
    public static final int POSICION_DIECIOCHO = 18;
    public static final int POSICION_DIECINUEVE = 19;

    public static final int LONGITUD_ARRAY_VACIO = 0;
    public static final int CANTIDAD_MINIMA_FOTOS = 3;
    public static final int ID_MOTIVO_RETIRO_OTRO = 999;
    public static final int CALIDAD_DE_IMAGEN = 60;  //la calidad puede ir de 0 a 100




    public static final String[][] VOWELS = new String[][] {
            { "á", "é", "í", "ó", "ú", "Á", "É", "Í", "Ó", "Ú", "ñ" , "Ñ" , "ü" , "Ü" },
            { "a", "e", "i", "o", "u", "A", "E", "I", "O", "U" , "n" , "N" , "u" , "U"} };

    public enum ParametrosIntent{
        POSICION_VISITA( "posicionVisita" , 1),
        ID_VISITA_ACTUAL( "idVisitaActual" , 2),
        POSICION_PRODUCTO( "posicionProducto" , 3),
        CODIGO_PRODUCTO( "codigoProducto" , 4),
        PRODUCTO_SIN_CATALOGO( "codigoProductoSinCatalogo" , 5);

        private String nombre;
        private int idParametro;
        private ParametrosIntent( String nombre , int idParametro ){
            this.nombre = nombre;
            this.idParametro = idParametro;
        }

        public String getNombre() { return nombre; }

        public int getIdParametro() {
            return idParametro;
        }
    }

    public enum TipoDespliegueActivity{
        ALTA,
        CONSULTA;
    }

    public enum MedidasReduccionImagen{
        GRANDE_PORTRAIT( 2424 ,3232 , "Medidas aproximadas WxH:2424x3232 , pesos en Megas: 2M" ),
        MEDIANA_PORTRAIT( 1212 , 1616 , "Medidas aproximadas WxH: 1212x1616 , pesos en Megas: 1M" ),
        PEQUENA_PORTRAIT(  606 , 808 , "Medidas aproximadas WxH: 606x808 , pesos en Megas: 0.3M" ),
        GRANDE_LANDSCAPE( 3232 , 2424 , "Medidas aproximadas WxH:3232x2424 , pesos en Megas: 2M" ),
        MEDIANA_LANDSCAPE( 1616 , 1212 ,  "Medidas aproximadas WxH: 1616x1212 , pesos en Megas: 1M" ),
        PEQUENA_LANDSCAPE(  808 , 606 ,  "Medidas aproximadas WxH: 808x606 , pesos en Megas: 0.3M" );

        public int width;
        public int heigh;
        public String descripcion;


        MedidasReduccionImagen(  int width , int heigh ,  String descripcion ){
            this.width = width;
            this.heigh = heigh;
            this.descripcion = descripcion;
        }
    }


    public enum UrlPromotoriaWeb{
        URL_LOGIN( "ValidarLoginPromotor" ),
        URL_CATALOGO_PRODUCTOS( "ObtenerProductosCadena" ),
        URL_RUTA( "ObtenerRutaPromotor" ),
        URL_CHECKIN( "checkin" );

        private String url;

        private UrlPromotoriaWeb( String url){
            this.url = url;
        }

        public String getUrl() {
            return PATH + url;
        }

        public static final String PATH = "http://services.alphagroup.mx/AlphaMerchandising.svc/";

    }


    public enum VersionAPK{
        VER_1_1_0( "1.1.0" , "Version Productiva, con pantalla de error que muestra el stack trace" ),
        VER_1_1_1( "1.1.1" , "Version Productiva, Se agrega soporte a SQLIte, para el manejo de la persistencia" ),
        VER_1_1_2( "1.1.2" , "Se corrige el id de Tienda en el Chekout, Cuando el location es null se devuelve cero en lat y log, se muestra un toast con la alerta" ),
        VER_1_1_3( "1.1.3" , "Se agregan las estatus de la visita que no se soportaban: Cancelada y no visitada" ),
        VER_1_1_4( "1.1.4" , "Se define el maximo de caracteres para la caja de texto en \"otro motivo\"" ),
        VER_1_1_5( "1.1.5" , "Se agrega soporte para escalar las imagenes capturadas y volverlas mas grandes y se agrega el guin en check-out y check-in" ),
        VER_1_1_6( "1.1.6" , "AJUSTE 20150811 , Se agrega el soporte para enviar via web service los errores generados en la aplicacion" ),
        VER_1_1_7( "1.1.7" , "AJUSTE 20150907 , Se incrementa el heap de la aplicacion y se optimiza el procesamiento en el chekout para no eliminar el ascci a toda la cadena" ),
        VER_1_1_8( "1.1.9" , "AJUSTE 20151001 , Correcion a las opciones de CheckoutRequest offline, para poder cargar nuevas rutas" );

        public String version;
        public String descripcion;
        VersionAPK( String version , String descripcion ){
            this.version = version;
            this.descripcion = descripcion;
        }

        public static VersionAPK getUltimaVersion() {
            VersionAPK[]  versiones = VersionAPK.values();
            return versiones[ versiones.length -1 ];
        }
    }

    public enum Aplicacion{
        PRO( "Promotoria móvil"),
        HOM( "Home Delivery" ),
        GRO( "Promotoría y pedidos grocus" );

        public String nombre;

        private Aplicacion(String nombre){
            this.nombre = nombre;
        }
    }


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String PATH = "/data/data/mx.com.algroup.promotormovil/databases/";
    public static final String DATABASE_NAME = "PromotorMovilDB.db";

}
