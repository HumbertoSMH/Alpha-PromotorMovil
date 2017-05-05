package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

/**
 * Created by devmac03 on 21/04/15.
 */
public class Cadena {

    private String idCadena;
    private String nombreCadena;
    private byte[] imagenCadena;
    private Tienda[] tiendas;

    public Cadena( ) {
        this.idCadena = "";
        this.nombreCadena = "";
        this.imagenCadena = new byte[0];
        this.tiendas = new Tienda[0];
    }

    public String getIdCadena() {
        return idCadena;
    }

    public void setIdCadena(String idCadena) {
        this.idCadena = idCadena;
    }

    public String getNombreCadena() {
        return nombreCadena;
    }

    public void setNombreCadena(String nombreCadena) {
        this.nombreCadena = nombreCadena;
    }

    public byte[] getImagenCadena() {
        return imagenCadena;
    }

    public void setImagenCadena(byte[] imagenCadena) {
        this.imagenCadena = imagenCadena;
    }

    public Tienda[] getTiendas() {
        return tiendas;
    }

    public void setTiendas(Tienda[] tiendas) {
        this.tiendas = tiendas;
    }

    @Override
    public String toString() {
        return "Cadena{" +
                "idCadena='" + idCadena + '\'' +
                ", nombreCadena='" + nombreCadena + '\'' +
                //", imagenCadena=" + Arrays.toString(imagenCadena) +
                ", imagenCadena=" + ">>>AQUI VA LA IMAGEN DE LA CADENA<<<<" +
                ", tiendas=" + Arrays.toString(tiendas) +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cadena cadena = (Cadena) o;

        if (!idCadena.equals(cadena.idCadena)) return false;
        if (!Arrays.equals(imagenCadena, cadena.imagenCadena)) return false;
        if (!nombreCadena.equals(cadena.nombreCadena)) return false;
        if (!Arrays.equals(tiendas, cadena.tiendas)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCadena.hashCode();
        result = 31 * result + nombreCadena.hashCode();
        result = 31 * result + Arrays.hashCode(imagenCadena);
        result = 31 * result + Arrays.hashCode(tiendas);
        return result;
    }
}
