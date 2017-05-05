package mx.com.algroup.promotormovil.utils;

/**
 * Created by devmac03 on 11/08/15.
 */
public class NotificacionError {
    private int idError;
    private String aplicacion;
    private String version;
    private String usuario;
    private String fechaHora;
    private String traza;

    public NotificacionError(  ) {
        this.idError = 0;
        this.aplicacion = "";
        this.version = "";
        this.usuario = "";
        this.fechaHora = "";
        this.traza = "";
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTraza() {
        return traza;
    }

    public void setTraza(String traza) {
        this.traza = traza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificacionError)) return false;

        NotificacionError that = (NotificacionError) o;

        if (idError != that.idError) return false;
        if (!aplicacion.equals(that.aplicacion)) return false;
        if (!fechaHora.equals(that.fechaHora)) return false;
        if (!traza.equals(that.traza)) return false;
        if (!usuario.equals(that.usuario)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idError;
        result = 31 * result + aplicacion.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + usuario.hashCode();
        result = 31 * result + fechaHora.hashCode();
        result = 31 * result + traza.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "NotificacionError{" +
                "idError=" + idError +
                ", aplicacion='" + aplicacion + '\'' +
                ", version='" + version + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fechaHora='" + fechaHora + '\'' +
                ", traza='" + traza + '\'' +
                '}';
    }
}
