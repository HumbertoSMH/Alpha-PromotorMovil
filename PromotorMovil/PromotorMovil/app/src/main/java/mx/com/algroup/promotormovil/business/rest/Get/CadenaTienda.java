package mx.com.algroup.promotormovil.business.rest.Get;

/**
 * Created by MAMM on 28/04/2015.
 */
public class CadenaTienda {

    private int idCadena;
    private String nombre;

    @Override
    public String toString() {
        return "CadenaTienda{" +
                "idCadena=" + idCadena +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CadenaTienda that = (CadenaTienda) o;

        if (idCadena != that.idCadena) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCadena;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public int getIdCadena() {
        return this.idCadena;
    }

    public void setIdCadena(int idCadena) {
        this.idCadena = idCadena;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
