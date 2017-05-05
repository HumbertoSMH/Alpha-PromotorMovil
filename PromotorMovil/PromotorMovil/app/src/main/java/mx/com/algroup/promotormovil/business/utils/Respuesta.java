package mx.com.algroup.promotormovil.business.utils;

/**
 * Created by MAMM on 19/04/15.
 */
public class Respuesta {
    private String idRespuesta;
    private String descripcion;

    public Respuesta( ) {
        this.idRespuesta = "" ;
        this.descripcion = "" ;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "idRespuesta='" + idRespuesta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
