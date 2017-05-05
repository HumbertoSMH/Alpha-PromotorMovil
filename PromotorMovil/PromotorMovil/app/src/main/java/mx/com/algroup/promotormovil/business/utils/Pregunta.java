package mx.com.algroup.promotormovil.business.utils;

import java.util.Arrays;

/**
 * Created by MAMM on 19/04/15.
 */
public class Pregunta {
    private String idPregunta;
    private String descripcion;
    private Respuesta[] respuestasPregunta;


    public Pregunta( ) {
        this.idPregunta = "";
        this.descripcion = "";
        this.respuestasPregunta = new Respuesta[0];
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Respuesta[] getRespuestasPregunta() {
        return respuestasPregunta;
    }

    public void setRespuestasPregunta(Respuesta[] respuestasPregunta) {
        this.respuestasPregunta = respuestasPregunta;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "idPregunta='" + idPregunta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", respuestasPregunta=" + Arrays.toString(respuestasPregunta) +
                '}';
    }
}
