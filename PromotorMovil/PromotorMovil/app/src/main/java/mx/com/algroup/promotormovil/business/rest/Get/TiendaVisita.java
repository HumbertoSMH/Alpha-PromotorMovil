package mx.com.algroup.promotormovil.business.rest.Get;

/**
 * Created by MAMM on 28/04/2015.
 */
public class TiendaVisita {

    private int idTienda;
    private CadenaTienda cadenaTienda;
    private String nombre;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String codigoPostal;
    private String colonia;
    private String delegacionMunicpio;
    private String estado;
    private String referencia;
    private String telefono;
    private String latitud;
    private String longitud;

    @Override
    public String toString() {
        return "TiendaVisita{" +
                "idTienda=" + idTienda +
                ", cadenaTienda=" + cadenaTienda +
                ", nombre='" + nombre + '\'' +
                ", calle='" + calle + '\'' +
                ", numeroExterior='" + numeroExterior + '\'' +
                ", numeroInterior='" + numeroInterior + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", colonia='" + colonia + '\'' +
                ", delegacionMunicpio='" + delegacionMunicpio + '\'' +
                ", estado='" + estado + '\'' +
                ", referencia='" + referencia + '\'' +
                ", telefono='" + telefono + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }


    public TiendaVisita( ) {
        this.idTienda = 0;
        this.cadenaTienda = new CadenaTienda();
        this.nombre = "";
        this.calle = "";
        this.numeroExterior = "";
        this.numeroInterior = "";
        this.codigoPostal = "";
        this.colonia = "";
        this.delegacionMunicpio = "";
        this.estado = "";
        this.referencia = "";
        this.telefono = "";
        this.latitud = "";
        this.longitud = "";
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public CadenaTienda getCadenaTienda() {
        return cadenaTienda;
    }

    public void setCadenaTienda(CadenaTienda cadenaTienda) {
        this.cadenaTienda = cadenaTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacionMunicpio() {
        return delegacionMunicpio;
    }

    public void setDelegacionMunicpio(String delegacionMunicpio) {
        this.delegacionMunicpio = delegacionMunicpio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TiendaVisita that = (TiendaVisita) o;

        if (idTienda != that.idTienda) return false;
        if (!cadenaTienda.equals(that.cadenaTienda)) return false;
        if (!calle.equals(that.calle)) return false;
        if (!codigoPostal.equals(that.codigoPostal)) return false;
        if (!colonia.equals(that.colonia)) return false;
        if (!delegacionMunicpio.equals(that.delegacionMunicpio)) return false;
        if (!estado.equals(that.estado)) return false;
        if (!latitud.equals(that.latitud)) return false;
        if (!longitud.equals(that.longitud)) return false;
        if (!nombre.equals(that.nombre)) return false;
        if (!numeroExterior.equals(that.numeroExterior)) return false;
        if (!numeroInterior.equals(that.numeroInterior)) return false;
        if (!referencia.equals(that.referencia)) return false;
        if (!telefono.equals(that.telefono)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTienda;
        result = 31 * result + cadenaTienda.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + calle.hashCode();
        result = 31 * result + numeroExterior.hashCode();
        result = 31 * result + numeroInterior.hashCode();
        result = 31 * result + codigoPostal.hashCode();
        result = 31 * result + colonia.hashCode();
        result = 31 * result + delegacionMunicpio.hashCode();
        result = 31 * result + estado.hashCode();
        result = 31 * result + referencia.hashCode();
        result = 31 * result + telefono.hashCode();
        result = 31 * result + latitud.hashCode();
        result = 31 * result + longitud.hashCode();
        return result;
    }
}
