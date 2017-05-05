package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by devmac03 on 01/10/15.
 */
public class GuardarImagenRequest {

    private ImagenVisita imagenVisita;

    public GuardarImagenRequest( ) {
        this.imagenVisita = new ImagenVisita();
    }

    public ImagenVisita getImagenVisita() {
        return imagenVisita;
    }

    public void setImagenVisita(ImagenVisita imagenVisita) {
        this.imagenVisita = imagenVisita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuardarImagenRequest)) return false;

        GuardarImagenRequest that = (GuardarImagenRequest) o;

        if (!imagenVisita.equals(that.imagenVisita)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return imagenVisita.hashCode();
    }

    @Override
    public String toString() {
        return "GuardarImagenRequest{" +
                "imagenVisita=" + imagenVisita +
                '}';
    }
}
