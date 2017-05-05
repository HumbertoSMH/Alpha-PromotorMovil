package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by devmac03 on 30/04/15.
 */
public class CheckOutTiendaRequest {

    private CheckOutTienda checkOutTienda;

    public CheckOutTiendaRequest( ) {
        this.checkOutTienda = new CheckOutTienda();
    }

    public CheckOutTienda getCheckOutTienda() {
        return checkOutTienda;
    }

    public void setCheckOutTienda(CheckOutTienda checkOutTienda) {
        this.checkOutTienda = checkOutTienda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckOutTiendaRequest that = (CheckOutTiendaRequest) o;

        if (!checkOutTienda.equals(that.checkOutTienda)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return checkOutTienda.hashCode();
    }

    @Override
    public String toString() {
        return "CheckOutTiendaRequest{" +
                "checkOutTienda=" + checkOutTienda +
                '}';
    }
}
