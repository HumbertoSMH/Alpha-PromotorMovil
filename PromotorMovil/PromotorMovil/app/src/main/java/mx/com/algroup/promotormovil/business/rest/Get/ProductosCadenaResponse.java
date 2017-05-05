package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.ArrayList;
import java.util.List;

import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by MAMM on 28/04/2015.
 */
public class ProductosCadenaResponse extends Response {

    private List<Producto> productos;

    public ProductosCadenaResponse() {

        productos = new ArrayList<Producto>();

    }

    @Override
    public String toString() {
        return "ProductosCadenaResponse{" +
                "productos=" + productos +
                '}';
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
