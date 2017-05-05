package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.Collections;
import java.util.List;

import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class EncuestasRutaResponse extends Response {

    private List<Encuesta> encuestasRuta;

    public List<Encuesta> getEncuestasRuta() {
        return encuestasRuta;
    }

    public void setEncuestasRuta(List<Encuesta> encuestasRuta) {
        this.encuestasRuta = encuestasRuta;
    }

    public EncuestasRutaResponse() {
        this.encuestasRuta = Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EncuestasRutaResponse that = (EncuestasRutaResponse) o;

        if (encuestasRuta != null ? !encuestasRuta.equals(that.encuestasRuta) : that.encuestasRuta != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (encuestasRuta != null ? encuestasRuta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EncuestasRutaResponse{" +
                "encuestasRuta=" + encuestasRuta +
                '}';
    }
}

