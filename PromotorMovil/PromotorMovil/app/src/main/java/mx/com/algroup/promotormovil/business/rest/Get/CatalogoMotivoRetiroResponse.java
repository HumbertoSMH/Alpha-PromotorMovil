package mx.com.algroup.promotormovil.business.rest.Get;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mx.com.algroup.promotormovil.business.rest.Response;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;

/**
 * Created by devmac03 on 26/05/15.
 */
public class CatalogoMotivoRetiroResponse extends Response {

    private List<MotivoRetiro> catalogoMotivoRetiro;


    public CatalogoMotivoRetiroResponse( ) {
        this.catalogoMotivoRetiro = Collections.emptyList();
    }


    public List<MotivoRetiro> getCatalogoMotivoRetiro() {
        return catalogoMotivoRetiro;
    }

    public void setCatalogoMotivoRetiro(List<MotivoRetiro> catalogoMotivoRetiro) {
        this.catalogoMotivoRetiro = catalogoMotivoRetiro;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CatalogoMotivoRetiroResponse that = (CatalogoMotivoRetiroResponse) o;

        if (!catalogoMotivoRetiro.equals(that.catalogoMotivoRetiro)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + catalogoMotivoRetiro.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CatalogoMotivoRetiroResponse{" +
                "catalogoMotivoRetiro=" + catalogoMotivoRetiro +
                '}';
    }
}
