package mx.com.algroup.promotormovil.business.rest.Post;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class ProductoTienda {

    private int precioBase;
    private int existencia;
    private int precioTienda;
    private int numeroFrente;
    private boolean exhibicionAdicional;
    private String codigo;
    private String descripcion;

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getPrecioTienda() {
        return precioTienda;
    }

    public void setPrecioTienda(int precioTienda) {
        this.precioTienda = precioTienda;
    }

    public int getNumeroFrente() {
        return numeroFrente;
    }

    public void setNumeroFrente(int numeroFrente) {
        this.numeroFrente = numeroFrente;
    }

    public boolean isExhibicionAdicional() {
        return exhibicionAdicional;
    }

    public void setExhibicionAdicional(boolean exhibicionAdicional) {
        this.exhibicionAdicional = exhibicionAdicional;
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

    @Override
    public String toString() {
        return "ProductoTienda{" +
                "precioBase=" + precioBase +
                ", existencia=" + existencia +
                ", precioTienda=" + precioTienda +
                ", numeroFrente=" + numeroFrente +
                ", exhibicionAdicional=" + exhibicionAdicional +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoTienda)) return false;

        ProductoTienda that = (ProductoTienda) o;

        if (exhibicionAdicional != that.exhibicionAdicional) return false;
        if (existencia != that.existencia) return false;
        if (numeroFrente != that.numeroFrente) return false;
        if (precioBase != that.precioBase) return false;
        if (precioTienda != that.precioTienda) return false;
        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = precioBase;
        result = 31 * result + existencia;
        result = 31 * result + precioTienda;
        result = 31 * result + numeroFrente;
        result = 31 * result + (exhibicionAdicional ? 1 : 0);
        result = 31 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        return result;
    }
}
