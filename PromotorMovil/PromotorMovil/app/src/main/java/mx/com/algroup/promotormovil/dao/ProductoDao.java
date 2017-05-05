package mx.com.algroup.promotormovil.dao;

import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Ruta;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface ProductoDao {

    public long insertProducto( RevisionProducto revisionProducto , int idVisita);
    public RevisionProducto getProductoById( String codigoProducto);
    public RevisionProducto[] getProductosByIdVisita( Integer idVisita);
    public long updateProducto( RevisionProducto revisionProducto , int idVisita);
    public long deleteProductoById( String codigoProducto , int  idVisita );
    public long deleteProductoByIdVisita( int  idVisita );
}
