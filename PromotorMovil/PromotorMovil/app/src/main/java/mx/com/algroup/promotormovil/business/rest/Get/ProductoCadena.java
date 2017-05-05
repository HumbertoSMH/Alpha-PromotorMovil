package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.List;

import mx.com.algroup.promotormovil.business.Producto;

/**
 * Created by MAMM on 28/04/2015.
 */
public class ProductoCadena {

    private CadenaTienda cadenaTiendas;
    private List<Producto> productos;

    @Override
    public String toString() {
        return "ProductoCadena{" +
                "cadenaTiendas=" + cadenaTiendas +
                ", productos=" + productos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoCadena that = (ProductoCadena) o;

        if (cadenaTiendas != null ? !cadenaTiendas.equals(that.cadenaTiendas) : that.cadenaTiendas != null)
            return false;
        if (productos != null ? !productos.equals(that.productos) : that.productos != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cadenaTiendas != null ? cadenaTiendas.hashCode() : 0;
        result = 31 * result + (productos != null ? productos.hashCode() : 0);
        return result;
    }

    public CadenaTienda getCadenaTiendas() {
        return cadenaTiendas;
    }

    public void setCadenaTiendas(CadenaTienda cadenaTiendas) {
        this.cadenaTiendas = cadenaTiendas;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
