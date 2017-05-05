package mx.com.algroup.promotormovil.business.rest.Post;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by devmac03 on 11/08/15.
 */
public class GuardarErroresResponse {

    private Response guardarErroresResult;

    public GuardarErroresResponse() {
        this.guardarErroresResult =  new Response();
    }

    public Response getGuardarErroresResult() {
        return guardarErroresResult;
    }

    public void setGuardarErroresResult(Response guardarErroresResult) {
        this.guardarErroresResult = guardarErroresResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuardarErroresResponse that = (GuardarErroresResponse) o;

        if (!guardarErroresResult.equals(that.guardarErroresResult)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return guardarErroresResult.hashCode();
    }

    @Override
    public String toString() {
        return "GuardaErroresResponse{" +
                "guardarErroresResult=" + guardarErroresResult +
                '}';
    }
}
