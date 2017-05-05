package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.List;

/**
 * Created by MAMM on 28/04/2015.
 */
public class PreguntaEncuesta {

    private int idPregunta;
    private String descripcion;
    private List<RespuestaPregunta> respuestasPregunta;

    @Override
    public String toString() {
        return "PreguntaEncuesta{" +
                "idPregunta=" + idPregunta +
                ", descripcion='" + descripcion + '\'' +
                ", respuestasPregunta=" + respuestasPregunta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreguntaEncuesta)) return false;

        PreguntaEncuesta that = (PreguntaEncuesta) o;

        if (idPregunta != that.idPregunta) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;
        if (respuestasPregunta != null ? !respuestasPregunta.equals(that.respuestasPregunta) : that.respuestasPregunta != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPregunta;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (respuestasPregunta != null ? respuestasPregunta.hashCode() : 0);
        return result;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RespuestaPregunta> getRespuestasPregunta() {
        return respuestasPregunta;
    }

    public void setRespuestasPregunta(List<RespuestaPregunta> respuestasPregunta) {
        this.respuestasPregunta = respuestasPregunta;
    }
}
