package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by devmac03 on 11/08/15.
 */
public class GuardarErroresRequest {
    private NotificacionErrorRest notificacionError;

    public GuardarErroresRequest( ) {
        this.notificacionError = new NotificacionErrorRest();
    }

    public NotificacionErrorRest getNotificacionError() {
        return notificacionError;
    }

    public void setNotificacionError(NotificacionErrorRest notificacionError) {
        this.notificacionError = notificacionError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuardarErroresRequest)) return false;

        GuardarErroresRequest that = (GuardarErroresRequest) o;

        if (!notificacionError.equals(that.notificacionError)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return notificacionError.hashCode();
    }

    @Override
    public String toString() {
        return "GuardarErroresRequest{" +
                "notificacionError=" + notificacionError +
                '}';
    }
}
