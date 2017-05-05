package mx.com.algroup.promotormovil.business;

import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;

/**
 * Created by MAMM on 19/04/15.
 */
public class RevisionProducto {
    private Producto producto;
    private int existencia;
    private int precioEnTienda;
    private int numeroFrente;
    private RespuestaBinaria exhibicionAdicional;

    public RevisionProducto() {
        this.producto =  new Producto();
        this.existencia = 0;
        this.precioEnTienda = 0;
        this.numeroFrente = 0;
        this.exhibicionAdicional = RespuestaBinaria.NO;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getPrecioEnTienda() {
        return precioEnTienda;
    }

    public void setPrecioEnTienda(int precioEnTienda) {
        this.precioEnTienda = precioEnTienda;
    }

    public int getNumeroFrente() {
        return numeroFrente;
    }

    public void setNumeroFrente(int numeroFrente) {
        this.numeroFrente = numeroFrente;
    }

    public RespuestaBinaria getExhibicionAdicional() {
        return exhibicionAdicional;
    }

    public void setExhibicionAdicional(RespuestaBinaria exhibicionAdicional) {
        this.exhibicionAdicional = exhibicionAdicional;
    }


    @Override
    public String toString() {
        return "RevisionProducto{" +
                "producto=" + producto +
                ", existencia=" + existencia +
                ", precioEnTienda=" + precioEnTienda +
                ", numeroFrente=" + numeroFrente +
                ", exhibicionAdicional=" + exhibicionAdicional +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RevisionProducto that = (RevisionProducto) o;

        if (existencia != that.existencia) return false;
        if (numeroFrente != that.numeroFrente) return false;
        if (precioEnTienda != that.precioEnTienda) return false;
        if (exhibicionAdicional != that.exhibicionAdicional) return false;
        if (!producto.equals(that.producto)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = producto.hashCode();
        result = 31 * result + existencia;
        result = 31 * result + precioEnTienda;
        result = 31 * result + numeroFrente;
        result = 31 * result + exhibicionAdicional.hashCode();
        return result;
    }
}
