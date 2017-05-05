package mx.com.algroup.promotormovil.business.rest.Post;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by devmac03 on 29/04/15.
 */
public class CheckInTiendaResponse {

    private Response hacerCheckInTiendaResult;

    public CheckInTiendaResponse( ) {
        this.hacerCheckInTiendaResult = new Response();
    }

    public Response getHacerCheckInTiendaResult() {

        return hacerCheckInTiendaResult;
    }

    public void setHacerCheckInTiendaResult(Response hacerCheckInTiendaResult) {
        this.hacerCheckInTiendaResult = hacerCheckInTiendaResult;
    }

    @Override
    public String toString() {
        return "CheckInTiendaResponse{" +
                "hacerCheckInTiendaResult=" + hacerCheckInTiendaResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInTiendaResponse that = (CheckInTiendaResponse) o;

        if (!hacerCheckInTiendaResult.equals(that.hacerCheckInTiendaResult)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hacerCheckInTiendaResult.hashCode();
    }
}
