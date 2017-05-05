package mx.com.algroup.promotormovil.business;

/**
 * Created by MAMM on 19/04/15.
 */
public class Direccion {

    private String calle;
    private String numeroInterior;
    private String numeroExterior;
    private String codigoPostal;
    private String colonia;
    private String delegacion;
    private String estado;
    private String pais;
    private String referencia;

    public Direccion( ) {
        this.calle = "";
        this.numeroInterior = "";
        this.numeroExterior = "";
        this.codigoPostal = "";
        this.colonia = "";
        this.delegacion = "";
        this.estado = "";
        this.pais = "";
        this.referencia = "";
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
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

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", numeroInterior='" + numeroInterior + '\'' +
                ", numeroExterior='" + numeroExterior + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", colonia='" + colonia + '\'' +
                ", delegacion='" + delegacion + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", referencia='" + referencia + '\'' +
                '}';
    }
}
