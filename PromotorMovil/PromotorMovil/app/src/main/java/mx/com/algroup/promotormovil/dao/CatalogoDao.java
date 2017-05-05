package mx.com.algroup.promotormovil.dao;

import java.util.List;

import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.Respuesta;

/**
 * Created by devmac02 on 29/09/15.
 */
public interface CatalogoDao {

    public long insertarCatalogoProductos(Producto producto, Integer idCadena);
    public long eliminarCatalogoProductos( );
    public List<Producto> recuperarCatalogoProductosPorIdCadena(Integer idCadena);
    public List<Integer> recuperarListaDeIdCadenasEnCatalogoDeProductos();

    public long insertarCatalogoPreguntas( Pregunta pregunta , int idEncuesta );
    public long eliminarCatalogoPreguntasRespuestas( );
    public List<Pregunta> recuperarCatalogoPreguntasPorIdEncuesta( int idEncuesta );
    public List<Integer> recuperarListaIdEncuestaEnCatalogo( ) ;

    public long insertarCatalogoRespuesta( Respuesta respuesta , int idPregunta );
    public List<Respuesta> recuperarCatalogoRespuestaPorIdPregunta(int idPregunta);

    public long insertarMotivosRetiro(MotivoRetiro motivoRetiro);
    public List<MotivoRetiro> recuperarTodosLosMotivosDeRetiro();
    public long eliminarTodosLosMotivosDeRetiro();

}
