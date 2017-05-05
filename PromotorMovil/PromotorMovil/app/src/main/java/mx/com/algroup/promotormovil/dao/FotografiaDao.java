package mx.com.algroup.promotormovil.dao;

import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.RevisionProducto;

/**
 * Created by devmac03 on 10/06/15.
 */
public interface FotografiaDao {

    public long insertFotografia(RevisionFoto revisionFoto, int idVisita);
    public RevisionFoto getRevisionFotoById(String idFoto);
    public RevisionFoto[] getRevisionFotoByIdVisita(Integer idVisita);
    public long updateFoto(RevisionFoto revisionFoto, int idVisita);
    public long deleteFotoById(String idFoto, int idVisita);
    public long deleteFotoByIdVisita(  int idVisita);
}
