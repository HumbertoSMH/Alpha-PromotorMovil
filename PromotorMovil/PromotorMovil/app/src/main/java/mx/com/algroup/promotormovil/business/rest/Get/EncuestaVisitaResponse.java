package mx.com.algroup.promotormovil.business.rest.Get;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class EncuestaVisitaResponse extends Response {

    private EncuestaVisita encuestaVisita;

    @Override
    public String toString() {
        return "EncuestaVisitaResponse{" +
                "encuestaVisita=" + encuestaVisita +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaVisitaResponse)) return false;
        if (!super.equals(o)) return false;

        EncuestaVisitaResponse that = (EncuestaVisitaResponse) o;

        if (encuestaVisita != null ? !encuestaVisita.equals(that.encuestaVisita) : that.encuestaVisita != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (encuestaVisita != null ? encuestaVisita.hashCode() : 0);
        return result;
    }

    public EncuestaVisita getEncuestaVisita() {
        return encuestaVisita;
    }

    public void setEncuestaVisita(EncuestaVisita encuestaVisita) {
        this.encuestaVisita = encuestaVisita;
    }
}
