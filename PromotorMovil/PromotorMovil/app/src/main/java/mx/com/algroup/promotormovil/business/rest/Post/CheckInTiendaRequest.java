package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by devmac03 on 29/04/15.
 */
public class CheckInTiendaRequest {
    private CheckInTienda checkInTienda;

    public CheckInTiendaRequest( ) {
        this.checkInTienda = checkInTienda;
    }

    public CheckInTienda getCheckInTienda() {
        return checkInTienda;
    }

    public void setCheckInTienda(CheckInTienda checkInTienda) {
        this.checkInTienda = checkInTienda;
    }

    @Override
    public String toString() {
        return "CheckInTiendaRequest{" +
                "checkInTienda=" + checkInTienda +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckInTiendaRequest that = (CheckInTiendaRequest) o;

        if (!checkInTienda.equals(that.checkInTienda)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return checkInTienda.hashCode();
    }


}
