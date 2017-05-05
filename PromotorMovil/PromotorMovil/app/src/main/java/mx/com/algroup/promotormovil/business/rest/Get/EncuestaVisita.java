package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.List;

/**
 * Created by MAMM on 28/04/2015.
 */
public class EncuestaVisita {

    private int idEncuesta;
    private String descripcion;
    private List<PreguntaEncuesta> preguntasEncuesta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaVisita)) return false;

        EncuestaVisita that = (EncuestaVisita) o;

        if (idEncuesta != that.idEncuesta) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;
        if (preguntasEncuesta != null ? !preguntasEncuesta.equals(that.preguntasEncuesta) : that.preguntasEncuesta != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEncuesta;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (preguntasEncuesta != null ? preguntasEncuesta.hashCode() : 0);
        return result;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PreguntaEncuesta> getPreguntasEncuesta() {
        return preguntasEncuesta;
    }

    public void setPreguntasEncuesta(List<PreguntaEncuesta> preguntasEncuesta) {
        this.preguntasEncuesta = preguntasEncuesta;
    }

    @Override
    public String toString() {
        return "EncuestaVisita{" +
                "idEncuesta=" + idEncuesta +
                ", descripcion='" + descripcion + '\'' +
                ", preguntasEncuesta=" + preguntasEncuesta +
                '}';
    }
}
