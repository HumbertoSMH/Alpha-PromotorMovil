package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.List;

/**
 * Created by MAMM on 28/04/2015.
 */
public class RutasPromotor {

    private int idRuta;
    private String FechaCreacion;
    private String FechaProgramada;
    private String FechaUltimaModificacion;
    private List<Visitas> visitas;

    @Override
    public String toString() {
        return "RutasPromotor{" +
                "idRuta=" + idRuta +
                ", FechaCreacion='" + FechaCreacion + '\'' +
                ", FechaProgramada='" + FechaProgramada + '\'' +
                ", FechaUltimaModificacion='" + FechaUltimaModificacion + '\'' +
                ", visitas=" + visitas +
                '}';
    }


    public RutasPromotor( ) {
        this.idRuta = 0;
        FechaCreacion = "";
        FechaProgramada = "";
        FechaUltimaModificacion = "";
        this.visitas = visitas;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public String getFechaProgramada() {
        return FechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        FechaProgramada = fechaProgramada;
    }

    public String getFechaUltimaModificacion() {
        return FechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        FechaUltimaModificacion = fechaUltimaModificacion;
    }

    public List<Visitas> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<Visitas> visitas) {
        this.visitas = visitas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RutasPromotor that = (RutasPromotor) o;

        if (idRuta != that.idRuta) return false;
        if (!FechaCreacion.equals(that.FechaCreacion)) return false;
        if (!FechaProgramada.equals(that.FechaProgramada)) return false;
        if (!FechaUltimaModificacion.equals(that.FechaUltimaModificacion)) return false;
        if (!visitas.equals(that.visitas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRuta;
        result = 31 * result + FechaCreacion.hashCode();
        result = 31 * result + FechaProgramada.hashCode();
        result = 31 * result + FechaUltimaModificacion.hashCode();
        result = 31 * result + visitas.hashCode();
        return result;
    }
}
