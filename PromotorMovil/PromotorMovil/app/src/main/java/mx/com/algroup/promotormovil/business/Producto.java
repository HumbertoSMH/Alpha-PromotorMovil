package mx.com.algroup.promotormovil.business;

/**
 * Created by MAMM on 19/04/15.
 */
public class Producto {
    private String codigo;
    private String descripcion;
    private int precioBase;

    public Producto( ) {
        this.precioBase = 0;
        this.codigo = "";
        this.descripcion = "" ;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioBase=" + precioBase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        if (precioBase != producto.precioBase) return false;
        if (!codigo.equals(producto.codigo)) return false;
        if (!descripcion.equals(producto.descripcion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigo.hashCode();
        result = 31 * result + descripcion.hashCode();
        result = 31 * result + precioBase;
        return result;
    }
}
