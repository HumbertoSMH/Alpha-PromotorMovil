package mx.com.algroup.promotormovil.services;

import java.util.List;
import java.util.Set;

import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Ruta;
import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.rest.Post.CheckInTiendaResponse;
import mx.com.algroup.promotormovil.business.rest.Response;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.utils.PromotorMovilException;

/**
 * Created by MAMM on 19/04/15.
 */
public interface VisitaService {

    public void recuperarRuta( Promotor promotorLogged );
//    public void recuperarMotivosDeRetiro();
    public Ruta getRutaActual();
    public Response realizarCheckIn( Visita visita  );
    public Response realizarCheckOut( Visita visita );

    public Visita recuperarVisitaPorIdVisita(String idVisita);
    public Set<Cadena> getCadenasEnRuta();
//    public List<MotivoRetiro> getCatalogoMotivoRetiro();


    public void agregarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual);

    public Visita getVisitaPorPosicionEnLista(int posicion);
    public Visita getVisitaActual();

    public void actualizarVisitaActual( );
    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual );
    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual );

    public void guardarRevisionFoto(RevisionFoto revFoto);
    public void guardarEncuesta( EncuestaPersona encuestaPersona);

    public void eliminarRevisionFotografia( String idRevisionFoto );
    public void recuperarRutaDesdeBase( String user , String pass );

}
