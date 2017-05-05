package mx.com.algroup.promotormovil.business.rest.Get;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class RutaPromotorResponse extends Response {

    private RutasPromotor rutaPromotor;

    @Override
    public String toString() {
        return "RutaPromotorResponse{" +
                "rutaPromotor=" + rutaPromotor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RutaPromotorResponse that = (RutaPromotorResponse) o;

        if (rutaPromotor != null ? !rutaPromotor.equals(that.rutaPromotor) : that.rutaPromotor != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rutaPromotor != null ? rutaPromotor.hashCode() : 0);
        return result;
    }

    public RutasPromotor getRutaPromotor() {
        return this.rutaPromotor;
    }

    public void setRutaPromotor(RutasPromotor rutaPromotor) {
        this.rutaPromotor = rutaPromotor;
    }
}
