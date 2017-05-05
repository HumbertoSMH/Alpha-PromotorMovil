package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

import mx.com.algroup.promotormovil.business.utils.Pregunta;

/**
 * Created by MAMM on 19/04/15.
 */
public class Encuesta {

    private String idEncuesta;
    private String descripcion;
    private Pregunta[] preguntasEncuesta;

    public Encuesta( ) {
        this.idEncuesta = "";
        this.preguntasEncuesta = new Pregunta[0];
        this.descripcion = "";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdEncuesta() {
        return idEncuesta;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "idEncuesta='" + idEncuesta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", preguntasEncuesta=" + Arrays.toString(preguntasEncuesta) +
                '}';
    }

    public void setIdEncuesta(String idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Pregunta[] getPreguntasEncuesta() {
        return preguntasEncuesta;
    }

    public void setPreguntasEncuesta(Pregunta[] preguntasEncuesta) {
        this.preguntasEncuesta = preguntasEncuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Encuesta encuesta = (Encuesta) o;

        if (!descripcion.equals(encuesta.descripcion)) return false;
        if (!idEncuesta.equals(encuesta.idEncuesta)) return false;
        if (!Arrays.equals(preguntasEncuesta, encuesta.preguntasEncuesta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEncuesta.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + Arrays.hashCode(preguntasEncuesta);
        return result;
    }

}
