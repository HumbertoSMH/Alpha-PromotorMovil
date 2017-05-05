package mx.com.algroup.promotormovil.business.rest.Get;

/**
 * Created by MAMM on 28/04/2015.
 */
public class Visitas {

    private int idVisita;
    private TiendaVisita tiendaVisita;
    private boolean capturaProductos;
    private int idEncuesta;
    private int idEstatus;

    @Override
    public String toString() {
        return "Visitas{" +
                "idVisita=" + idVisita +
                ", tiendaVisita=" + tiendaVisita +
                ", capturaProductos=" + capturaProductos +
                ", idEncuesta=" + idEncuesta +
                ", idEstatus=" + idEstatus +
                '}';
    }

    public Visitas( ) {
        this.idVisita = 0;
        this.tiendaVisita = new TiendaVisita();
        this.capturaProductos = false;
        this.idEstatus = 0;
        this.idEncuesta = 0;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public TiendaVisita getTiendaVisita() {
        return tiendaVisita;
    }

    public void setTiendaVisita(TiendaVisita tiendaVisita) {
        this.tiendaVisita = tiendaVisita;
    }

    public boolean isCapturaProductos() {
        return capturaProductos;
    }

    public void setCapturaProductos(boolean capturaProductos) {
        this.capturaProductos = capturaProductos;
    }

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visitas visitas = (Visitas) o;

        if (capturaProductos != visitas.capturaProductos) return false;
        if (idEncuesta != visitas.idEncuesta) return false;
        if (idEstatus != visitas.idEstatus) return false;
        if (idVisita != visitas.idVisita) return false;
        if (!tiendaVisita.equals(visitas.tiendaVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idVisita;
        result = 31 * result + tiendaVisita.hashCode();
        result = 31 * result + (capturaProductos ? 1 : 0);
        result = 31 * result + idEncuesta;
        result = 31 * result + idEstatus;
        return result;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;

    }

}
