package mx.com.algroup.promotormovil.business;

import mx.com.algroup.promotormovil.business.utils.UtilLocation;

/**
 * Created by MAMM on 19/04/15.
 */
public class Tienda {
    private String idTienda;
    private String nombreTienda;
    private UtilLocation location;
    private Direccion direccion;
    private String telefono;

    public Tienda( ) {
        this.idTienda = "" ;
        this.nombreTienda = "";
        this.location = new UtilLocation();
        this.direccion = new Direccion();
        this.telefono = "";
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public UtilLocation getLocation() {
        return location;
    }

    public void setLocation(UtilLocation location) {
        this.location = location;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "idTienda='" + idTienda + '\'' +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", location=" + location +
                ", direccion=" + direccion +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
