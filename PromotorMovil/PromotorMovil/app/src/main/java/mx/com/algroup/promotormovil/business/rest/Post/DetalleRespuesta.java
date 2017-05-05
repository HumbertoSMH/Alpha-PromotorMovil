package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class DetalleRespuesta {

    private int idPregunta;
    private int idRespuestaSeleccionada;

    @Override
    public String toString() {
        return "DetalleRespuesta{" +
                "idPregunta=" + idPregunta +
                ", idRespuestaSeleccionada=" + idRespuestaSeleccionada +
                '}';
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdRespuestaSeleccionada() {
        return idRespuestaSeleccionada;
    }

    public void setIdRespuestaSeleccionada(int idRespuestaSeleccionada) {
        this.idRespuestaSeleccionada = idRespuestaSeleccionada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleRespuesta)) return false;

        DetalleRespuesta that = (DetalleRespuesta) o;

        if (idPregunta != that.idPregunta) return false;
        if (idRespuestaSeleccionada != that.idRespuestaSeleccionada) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPregunta;
        result = 31 * result + idRespuestaSeleccionada;
        return result;
    }
}
