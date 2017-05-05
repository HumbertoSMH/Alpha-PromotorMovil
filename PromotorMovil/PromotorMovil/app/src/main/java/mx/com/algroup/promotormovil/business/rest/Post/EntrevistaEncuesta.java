package mx.com.algroup.promotormovil.business.rest.Post;

import java.util.List;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class EntrevistaEncuesta {

    private int idEncuesta;
    private List<DetalleRespuesta> detalleRespuestas;

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public List<DetalleRespuesta> getDetalleRespuestas() {
        return detalleRespuestas;
    }

    public void setDetalleRespuestas(List<DetalleRespuesta> detalleRespuestas) {
        this.detalleRespuestas = detalleRespuestas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntrevistaEncuesta)) return false;

        EntrevistaEncuesta that = (EntrevistaEncuesta) o;

        if (idEncuesta != that.idEncuesta) return false;
        if (detalleRespuestas != null ? !detalleRespuestas.equals(that.detalleRespuestas) : that.detalleRespuestas != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEncuesta;
        result = 31 * result + (detalleRespuestas != null ? detalleRespuestas.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntrevistaEncuesta{" +
                "idEncuesta=" + idEncuesta +
                ", detalleRespuestas=" + detalleRespuestas +
                '}';
    }
}
