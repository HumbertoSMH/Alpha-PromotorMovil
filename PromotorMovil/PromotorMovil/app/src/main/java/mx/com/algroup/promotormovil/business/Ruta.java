package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

/**
 * Created by MAMM on 19/04/15.
 */
public class Ruta {
    private String idRuta;
    private Visita[] visitas;
    private Promotor promotor;
    private String fechaInicio;
    private String fechaFin;
    private String fechaProgramadaString;
    private String fechaCreacionString;
    private String fechaUltimaModificacion;

    public Ruta() {
        this.idRuta = "";
        this.visitas = new Visita[0];
        this.promotor = new Promotor();
        this.fechaInicio = "";
        this.fechaFin = "";
        this.fechaProgramadaString = "";
        this.fechaCreacionString = "";
        this.fechaUltimaModificacion = "";
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public Visita[] getVisitas() {
        return visitas;
    }

    public void setVisitas(Visita[] visitas) {
        this.visitas = visitas;
    }

    public Promotor getPromotor() {
        return promotor;
    }

    public void setPromotor(Promotor promotor) {
        this.promotor = promotor;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaProgramadaString() {
        return fechaProgramadaString;
    }

    public void setFechaProgramadaString(String fechaProgramadaString) {
        this.fechaProgramadaString = fechaProgramadaString;
    }

    public String getFechaCreacionString() {
        return fechaCreacionString;
    }

    public void setFechaCreacionString(String fechaCreacionString) {
        this.fechaCreacionString = fechaCreacionString;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "idRuta='" + idRuta + '\'' +
                ", visitas=" + Arrays.toString(visitas) +
                ", promotor=" + promotor +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", fechaProgramadaString='" + fechaProgramadaString + '\'' +
                ", fechaCreacionString='" + fechaCreacionString + '\'' +
                ", fechaUltimaModificacion='" + fechaUltimaModificacion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ruta ruta = (Ruta) o;

        if (!fechaCreacionString.equals(ruta.fechaCreacionString)) return false;
        if (!fechaFin.equals(ruta.fechaFin)) return false;
        if (!fechaInicio.equals(ruta.fechaInicio)) return false;
        if (!fechaProgramadaString.equals(ruta.fechaProgramadaString)) return false;
        if (!fechaUltimaModificacion.equals(ruta.fechaUltimaModificacion)) return false;
        if (!idRuta.equals(ruta.idRuta)) return false;
        if (!promotor.equals(ruta.promotor)) return false;
        if (!Arrays.equals(visitas, ruta.visitas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRuta.hashCode();
        result = 31 * result + Arrays.hashCode(visitas);
        result = 31 * result + promotor.hashCode();
        result = 31 * result + fechaInicio.hashCode();
        result = 31 * result + fechaFin.hashCode();
        result = 31 * result + fechaProgramadaString.hashCode();
        result = 31 * result + fechaCreacionString.hashCode();
        result = 31 * result + fechaUltimaModificacion.hashCode();
        return result;
    }
}
