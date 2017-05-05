package mx.com.algroup.promotormovil.business.utils;

import android.graphics.Color;

/**
 * Created by MAMM on 19/04/15.
 */
public enum EstatusVisita {
    EN_RUTA( 1 , "En ruta" , 0xFFFFFFFF ), //Gris
    CHECK_IN ( 2 , "Check-in" , 0xFFFFFF66 ),  //amarillo
    CHECK_OUT_REQUEST( 3 , "Checkout Pendiente" , 0xFFFE506E), //Rojo
    CHECK_OUT( 4 , "Check-out" , 0xFFA0D6B4 ),  //verde
    CANCELADA( 5 , "Cancelada" , 0xFF473C8B ),  //Morado
    NO_VISITADA( 6 , "No visitada" , 0xFF473C8B );  //Morado


    //http://www.color-hex.com/Color

    public static final int EN_RUTA_ID_WEB = 1;
    public static final int CHECK_IN_ID_WEB = 2;
    public static final int CHECK_OUT_INCOMPLETO_ID_WEB = 3;
    public static final int CHECK_OUT_COMPLETO_ID_WEB = 4;
    public static final int CANCELADA_ID_WEB = 5;
    public static final int NO_VISITADA_ID_WEB = 6;


    private int idEstatus;
    private String nombreEstatus;
    private int color;

    private EstatusVisita( int idEstatus , String nombreEstatus , int color){
        this.idEstatus = idEstatus;
        this.nombreEstatus = nombreEstatus;
        this.color = color;
    }

    public int getIdEstatus() { return idEstatus; }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public int getColor() {
        return color;
    }
}
