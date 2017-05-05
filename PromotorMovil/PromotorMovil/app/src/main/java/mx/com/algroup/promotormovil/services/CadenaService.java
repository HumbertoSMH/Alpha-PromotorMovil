package mx.com.algroup.promotormovil.services;

import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Tienda;

/**
 * Created by devmac03 on 21/04/15.
 */
public interface CadenaService {

    public Cadena recuperarCadenaApartirDeTienda( Tienda tienda );
    public Cadena recuperarCadenaPorIdCadena( String idCadena );
}
