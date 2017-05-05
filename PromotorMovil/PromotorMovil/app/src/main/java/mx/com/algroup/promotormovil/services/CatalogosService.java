package mx.com.algroup.promotormovil.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;

/**
 * Created by devmac03 on 30/09/15.
 */
public interface CatalogosService {

    public ProductosCadenaResponse cargarTodosLosProductosDesdeWeb(  Set<Cadena> cadenas  );
    public EncuestasRutaResponse cargarTodasLasEncuestasDesdeWeb( int idRuta );
    public CatalogoMotivoRetiroResponse cargarTodasLosMotivosRetiroDesdeWeb(  );

    public void recuperarCatalogosDesdeBase();
    public void actualizarCatalogosEnBase();


    public Map<String , Producto> getMapaProductosPorCadena( Cadena cadena );
    public Encuesta recuperarEncuestaPorId( String idEncuesta );
    public List<MotivoRetiro> getCatalogoMotivoRetiro();

    public boolean esValidoProductoEnCadena(String codigoProducto, Cadena cadena ) ;
    public Producto recuperarProducto(String codigoProducto, Cadena cadena) ;


}
