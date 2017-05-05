package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by devmac03 on 01/10/15.
 */
public class ImagenVisita {

        private int idVisita;
        private String foto;
        private String fechaHoraCaptura;
        private String promotor;

    public ImagenVisita( ) {
        this.idVisita = 0;
        this.foto = "";
        this.fechaHoraCaptura = "";
        this.promotor = "";
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechaHoraCaptura() {
        return fechaHoraCaptura;
    }

    public void setFechaHoraCaptura(String fechaHoraCaptura) {
        this.fechaHoraCaptura = fechaHoraCaptura;
    }

    public String getPromotor() {
        return promotor;
    }

    public void setPromotor(String promotor) {
        this.promotor = promotor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImagenVisita)) return false;

        ImagenVisita that = (ImagenVisita) o;

        if (idVisita != that.idVisita) return false;
        if (!fechaHoraCaptura.equals(that.fechaHoraCaptura)) return false;
        if (!foto.equals(that.foto)) return false;
        if (!promotor.equals(that.promotor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idVisita;
        result = 31 * result + foto.hashCode();
        result = 31 * result + fechaHoraCaptura.hashCode();
        result = 31 * result + promotor.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "ImagenVisita{" +
                "idVisita=" + idVisita +
                ", foto='" + foto + '\'' +
                ", fechaHoraCaptura='" + fechaHoraCaptura + '\'' +
                ", promotor='" + promotor + '\'' +
                '}';
    }
}
