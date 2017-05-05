package mx.com.algroup.promotormovil.business.rest.Get;

/**
 * Created by MAMM on 28/04/2015.
 */
public class RespuestaPregunta {

    private int idRespuesta;
    private String descripcion;

    @Override
    public String toString() {
        return "RespuestaPregunta{" +
                "idRespuesta=" + idRespuesta +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespuestaPregunta that = (RespuestaPregunta) o;

        if (idRespuesta != that.idRespuesta) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRespuesta;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }
}
