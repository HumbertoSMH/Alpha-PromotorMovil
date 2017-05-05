package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class CheckOutTienda {

    private String clavePromotor;
    private int idVisita;
    private String fechaCreacion;
    private String latitud;
    private String longitud;
    private VisitaTienda visitaTienda;

    public String getClavePromotor() {
        return clavePromotor;
    }

    public void setClavePromotor(String clavePromotor) {
        this.clavePromotor = clavePromotor;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public VisitaTienda getVisitaTienda() {
        return visitaTienda;
    }

    public void setVisitaTienda(VisitaTienda visitaTienda) {
        this.visitaTienda = visitaTienda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckOutTienda)) return false;

        CheckOutTienda that = (CheckOutTienda) o;

        if (idVisita != that.idVisita) return false;
        if (clavePromotor != null ? !clavePromotor.equals(that.clavePromotor) : that.clavePromotor != null)
            return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null)
            return false;
        if (latitud != null ? !latitud.equals(that.latitud) : that.latitud != null) return false;
        if (longitud != null ? !longitud.equals(that.longitud) : that.longitud != null)
            return false;
        if (visitaTienda != null ? !visitaTienda.equals(that.visitaTienda) : that.visitaTienda != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clavePromotor != null ? clavePromotor.hashCode() : 0;
        result = 31 * result + idVisita;
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (latitud != null ? latitud.hashCode() : 0);
        result = 31 * result + (longitud != null ? longitud.hashCode() : 0);
        result = 31 * result + (visitaTienda != null ? visitaTienda.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CheckOutTienda{" +
                "clavePromotor='" + clavePromotor + '\'' +
                ", idVisita=" + idVisita +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", visitaTienda=" + visitaTienda +
                '}';
    }
}
