package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.Ruta;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.dao.EncuestaDao;
import mx.com.algroup.promotormovil.dao.FotografiaDao;
import mx.com.algroup.promotormovil.dao.ProductoDao;
import mx.com.algroup.promotormovil.dao.RutaDao;
import mx.com.algroup.promotormovil.dao.VisitaDao;

import mx.com.algroup.promotormovil.dao.impl.EncuestaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.FotografiaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.ProductoDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.RutaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.VisitaDaoImpl;
import mx.com.algroup.promotormovil.services.RutaService;
import mx.com.algroup.promotormovil.services.VisitaService;

/**
 * Created by devmac03 on 12/06/15.
 */
public class RutaServiceImpl implements RutaService{

    private static RutaService rutaService;
    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private ProductoDao productoDao;
    private FotografiaDao fotografiaDao;
    private EncuestaDao encuestaDao;
    private Context context;

    public RutaServiceImpl( Context context ){
        this.context = context;
        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
        this.productoDao = ProductoDaoImpl.getSingleton();
        this.fotografiaDao = FotografiaDaoImpl.getSingleton();
        this.encuestaDao = EncuestaDaoImpl.getSingleton();
    }

    public static RutaService getSingleton(){
        if( rutaService == null ){
            rutaService = new RutaServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return rutaService;
    }

    @Override
    public Ruta cargarRuta(Ruta ruta) {
        int idRutaNueva = Integer.parseInt( ruta.getIdRuta() );
        Ruta rutaEnBase = this.rutaDao.getRutaById( idRutaNueva );
        if( rutaEnBase == null ){
            //Se carga la nueva ruta
            this.rutaDao.insertRuta( ruta );
        }else{
            //se actualiza la ruta
            this.rutaDao.updateRuta( ruta );
        }
        //Se cargan las visitas de la ruta
        Visita[] visitas = ruta.getVisitas();
        if( visitas != null ){
            for( Visita itemVisita : visitas ){
                Visita visitaEnBase = this.visitaDao.getVisitaById( Integer.parseInt( itemVisita.getIdVisita() ) );
                if( visitaEnBase == null ){
                    this.visitaDao.insertVisita( itemVisita , idRutaNueva );
                }else{
                    //this.visitaDao.updateIdRutaEnVisita(itemVisita, idRutaNueva);
                    if( itemVisita.getEstatusVisita() == EstatusVisita.EN_RUTA ){  //si esta en RUTA solo se actualiza el id de ruta de la visita
                        this.visitaDao.updateIdRutaEnVisita(itemVisita, idRutaNueva);
                    }else if( itemVisita.getEstatusVisita() != EstatusVisita.CHECK_IN  ){
                        this.visitaDao.updateVisita(itemVisita, idRutaNueva);
                    }
                }
            }
        }

        //Se eliminan las visitas que no tienen ruta asociada y todas sus dependencias
        Integer[] visitasAEliminar = this.visitaDao.getIdVisitasQueNoSonDeRuta( idRutaNueva);
        for( Integer idVisita: visitasAEliminar ){
            this.encuestaDao.deleteEncuestaByIdVisita( idVisita );
            this.productoDao.deleteProductoByIdVisita( idVisita );
            this.fotografiaDao.deleteFotoByIdVisita( idVisita );
            this.visitaDao.deleteVisitaById( idVisita );
        }
        //TODO MAMM Pendiene eliminar Ruta anterior
        this.rutaDao.deleteRutaAnterior( idRutaNueva );
        return this.armarRutaDeBase( idRutaNueva , ruta.getPromotor());
    }

    private Ruta armarRutaDeBase(int idRutaNueva , Promotor promotor) {
        Ruta ruta = this.rutaDao.getRutaById( idRutaNueva );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRutaNueva);
        ruta.setVisitas( visitas );
        ruta.setPromotor( promotor );
        return ruta;
    }


    public Ruta refrescarRutaDesdeBase( Ruta rutaReferencia ){
        int idRuta = Integer.parseInt( rutaReferencia.getIdRuta() );
        Ruta ruta = this.rutaDao.getRutaById( idRuta );
        Visita[] visitas = this.visitaDao.getVisitasByIdRuta(idRuta );
        ruta.setVisitas( visitas );
        ruta.setPromotor( rutaReferencia.getPromotor()  );
        return ruta;
    }

    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor ){
        return this.rutaDao.getRutaPorClaveYPasswordDePromotor( clavePromotor , passwordPromotor );
    }

}
