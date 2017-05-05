package mx.com.algroup.promotormovil.dao;

import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.RevisionProducto;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface EncuestaDao {

    public void insertEncuesta( EncuestaPersona encuestaPersona, int idVisita);
    //public EncuestaPersona getEncuesta(String codigoProducto);
    public EncuestaPersona[] getEncuestasByIdVisita(Integer idVisita);
    //public long updateEn(RevisionProducto revisionProducto, int idVisita);
    //public long deleteEncuestaById(String codigoProducto, int idVisita);
    public long deleteEncuestaByIdVisita( int idVisita);
}
