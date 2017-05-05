package mx.com.algroup.promotormovil.services;

import mx.com.algroup.promotormovil.business.Ruta;

/**
 * Created by devmac03 on 12/06/15.
 */
public interface RutaService {
    public Ruta cargarRuta(Ruta ruta);
    public Ruta refrescarRutaDesdeBase( Ruta rutaReferencia );
    public Ruta getRutaPorClaveYPasswordDePromotor( String clavePromotor, String passwordPromotor );
}
