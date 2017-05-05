package mx.com.algroup.promotormovil.business.rest.Post;

import mx.com.algroup.promotormovil.business.rest.Response;

/**
 * Created by devmac03 on 01/10/15.
 */
public class GuardarImagenResponse {

    private Response guardarImagenResult;

    public GuardarImagenResponse( ) {
        this.guardarImagenResult = new Response();
    }

    public Response getGuardarImagenResult() {
        return guardarImagenResult;
    }

    public void setGuardarImagenResult(Response guardarImagenResult) {
        this.guardarImagenResult = guardarImagenResult;
    }

    @Override
    public String toString() {
        return "GuardarImagenResponse{" +
                "guardarImagenResult=" + guardarImagenResult +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuardarImagenResponse)) return false;

        GuardarImagenResponse that = (GuardarImagenResponse) o;

        if (guardarImagenResult != null ? !guardarImagenResult.equals(that.guardarImagenResult) : that.guardarImagenResult != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return guardarImagenResult != null ? guardarImagenResult.hashCode() : 0;
    }
}
