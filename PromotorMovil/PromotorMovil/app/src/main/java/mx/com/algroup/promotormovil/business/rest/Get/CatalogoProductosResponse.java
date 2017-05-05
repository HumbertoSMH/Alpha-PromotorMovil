package mx.com.algroup.promotormovil.business.rest.Get;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class CatalogoProductosResponse extends Response {

    private ProductoCadena productosCadena;

    @Override
    public String toString() {
        return "CatalogoProductosResponse{" +
                "productosCadena=" + productosCadena +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CatalogoProductosResponse that = (CatalogoProductosResponse) o;

        if (productosCadena != null ? !productosCadena.equals(that.productosCadena) : that.productosCadena != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productosCadena != null ? productosCadena.hashCode() : 0);
        return result;
    }

    public ProductoCadena getProductosCadena() {
        return productosCadena;
    }

    public void setProductosCadena(ProductoCadena productosCadena) {
        this.productosCadena = productosCadena;
    }
}
