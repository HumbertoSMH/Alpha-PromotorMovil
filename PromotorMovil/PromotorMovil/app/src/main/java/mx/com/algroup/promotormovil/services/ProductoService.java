package mx.com.algroup.promotormovil.services;

import java.util.Set;

import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.Tienda;

/**
 * Created by devmac03 on 21/04/15.
 */
public interface ProductoService {

    //public boolean esValidoProductoEnCadena( String codigoProducto , Cadena cadena );
    //public Producto recuperarProducto( String codigoProducto , Cadena cadena );

    public void actualizarCatalogoProductos(Set<Cadena> cadenasEnRuta);

}
