package mx.com.algroup.promotormovil.business.rest.Post;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by devmac03 on 30/04/15.
 */
public class CheckOutTiendaResponse {
    private Response hacerCheckOutTiendaResult;

    public CheckOutTiendaResponse( ) {
        this.hacerCheckOutTiendaResult = new Response();
    }

    public Response getHacerCheckOutTiendaResult() {
        return hacerCheckOutTiendaResult;
    }

    public void setHacerCheckOutTiendaResult(Response hacerCheckOutTiendaResult) {
        this.hacerCheckOutTiendaResult = hacerCheckOutTiendaResult;
    }

    @Override
    public String toString() {
        return "CheckOutTiendaResponse{" +
                "hacerCheckOutTiendaResult=" + hacerCheckOutTiendaResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckOutTiendaResponse that = (CheckOutTiendaResponse) o;

        if (!hacerCheckOutTiendaResult.equals(that.hacerCheckOutTiendaResult)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hacerCheckOutTiendaResult.hashCode();
    }
}
