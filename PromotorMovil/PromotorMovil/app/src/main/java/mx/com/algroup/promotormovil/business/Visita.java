package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;

/**
 * Created by MAMM on 19/04/15.
 */
public class Visita {
    private String idVisita;
    private Tienda tienda;
    private EstatusVisita estatusVisita;
    private String fechaCheckIn;
    private String fechaCheckout;
    private Persona gerente;
    private byte[] firmaGerente;
    private String comentarios;
    private RespuestaBinaria aplicarCapturaProductos;
    private RespuestaBinaria aplicarEncuesta;
    private EncuestaPersona[] encuestaPersonas;
    private RevisionProducto[] revisionProductos;
    private RevisionFoto[] revisionFoto;
    private String IdEncuesta;
    private Cadena cadena;
    private RespuestaBinaria reporteCapturado;
    private RespuestaBinaria encuestaCapturada;
    private int idMotivoRetiro ;
    private String descripcionMotivoRetiro ;
    private RespuestaBinaria checkInNotificado;


    public Visita( ) {
        this.idVisita = "";
        this.tienda = new Tienda();
        this.estatusVisita = EstatusVisita.EN_RUTA;
        this.fechaCheckIn = "";
        this.fechaCheckout = "";
        this.gerente = new Persona();
        this.firmaGerente = new byte[0];
        this.comentarios = "";
        this.aplicarEncuesta = RespuestaBinaria.NO;
        this.aplicarCapturaProductos = RespuestaBinaria.NO;
        this.encuestaPersonas = new EncuestaPersona[0];
        this.revisionProductos = new RevisionProducto[0];
        this.revisionFoto = new RevisionFoto[0];
        this.IdEncuesta = "";
        this.reporteCapturado = RespuestaBinaria.NO;
        this.encuestaCapturada = RespuestaBinaria.NO;
        this.idMotivoRetiro = -1;
        this.descripcionMotivoRetiro = "";
        this.cadena = new Cadena();
        this.checkInNotificado = RespuestaBinaria.NO;
    }

    public Cadena getCadena() {
        return cadena;
    }

    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public RespuestaBinaria getEncuestaCapturada() {

        return encuestaCapturada;
    }

    public void setEncuestaCapturada(RespuestaBinaria encuestaCapturada) {
        this.encuestaCapturada = encuestaCapturada;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public EstatusVisita getEstatusVisita() {
        return estatusVisita;
    }

    public void setEstatusVisita(EstatusVisita estatusVisita) {
        this.estatusVisita = estatusVisita;
    }

    public String getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(String fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public String getFechaCheckout() {
        return fechaCheckout;
    }

    public void setFechaCheckout(String fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }

    public Persona getGerente() {
        return gerente;
    }

    public void setGerente(Persona gerente) {
        this.gerente = gerente;
    }

    public byte[] getFirmaGerente() {
        return firmaGerente;
    }

    public void setFirmaGerente(byte[] firmaGerente) {
        this.firmaGerente = firmaGerente;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public RespuestaBinaria getAplicarEncuesta() {
        return aplicarEncuesta;
    }

    public void setAplicarEncuesta(RespuestaBinaria aplicarEncuesta) {
        this.aplicarEncuesta = aplicarEncuesta;
    }

    public RespuestaBinaria getCheckInNotificado() {
        return checkInNotificado;
    }

    public void setCheckInNotificado(RespuestaBinaria checkInNotificado) {
        this.checkInNotificado = checkInNotificado;
    }

    public EncuestaPersona[] getEncuestaPersonas() {
        return encuestaPersonas;
    }

    public void setEncuestaPersonas(EncuestaPersona[] encuestaPersonas) {
        this.encuestaPersonas = encuestaPersonas;
    }

    public RevisionProducto[] getRevisionProductos() {
        return revisionProductos;
    }

    public void setRevisionProductos(RevisionProducto[] revisionProductos) {
        this.revisionProductos = revisionProductos;
    }

    public RevisionFoto[] getRevisionFoto() {
        return revisionFoto;
    }

    public void setRevisionFoto(RevisionFoto[] revisionFoto) {
        this.revisionFoto = revisionFoto;
    }

    public String getIdEncuesta() {
        return IdEncuesta;
    }

    public RespuestaBinaria getReporteCapturado() {
        return reporteCapturado;
    }

    public void setReporteCapturado(RespuestaBinaria reporteCapturado) {
        this.reporteCapturado = reporteCapturado;
    }

    public RespuestaBinaria getAplicarCapturaProductos() {
        return aplicarCapturaProductos;
    }

    public void setAplicarCapturaProductos(RespuestaBinaria aplicarCapturaProductos) {
        this.aplicarCapturaProductos = aplicarCapturaProductos;
    }

    public void setIdEncuesta(String idEncuesta) {
        IdEncuesta = idEncuesta;
    }

    public int getIdMotivoRetiro() {
        return idMotivoRetiro;
    }

    public void setIdMotivoRetiro(int idMotivoRetiro) {
        this.idMotivoRetiro = idMotivoRetiro;
    }

    public String getDescripcionMotivoRetiro() {
        return descripcionMotivoRetiro;
    }

    public void setDescripcionMotivoRetiro(String descripcionMotivoRetiro) {
        this.descripcionMotivoRetiro = descripcionMotivoRetiro;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "idVisita='" + idVisita + '\'' +
                ", tienda=" + tienda +
                ", estatusVisita=" + estatusVisita +
                ", fechaCheckIn='" + fechaCheckIn + '\'' +
                ", fechaCheckout='" + fechaCheckout + '\'' +
                ", gerente=" + gerente +
                ", firmaGerente=" + Arrays.toString(firmaGerente) +
                ", comentarios='" + comentarios + '\'' +
                ", aplicarCapturaProductos=" + aplicarCapturaProductos +
                ", aplicarEncuesta=" + aplicarEncuesta +
                ", encuestaPersonas=" + Arrays.toString(encuestaPersonas) +
                ", revisionProductos=" + Arrays.toString(revisionProductos) +
                ", revisionFoto=" + Arrays.toString(revisionFoto) +
                ", IdEncuesta='" + IdEncuesta + '\'' +
                ", cadena=" + cadena +
                ", reporteCapturado=" + reporteCapturado +
                ", encuestaCapturada=" + encuestaCapturada +
                ", idMotivoRetiro=" + idMotivoRetiro +
                ", descripcionMotivoRetiro='" + descripcionMotivoRetiro + '\'' +
                ", checkInNotificado=" + checkInNotificado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visita)) return false;

        Visita visita = (Visita) o;

        if (idMotivoRetiro != visita.idMotivoRetiro) return false;
        if (!IdEncuesta.equals(visita.IdEncuesta)) return false;
        if (aplicarCapturaProductos != visita.aplicarCapturaProductos) return false;
        if (aplicarEncuesta != visita.aplicarEncuesta) return false;
        if (!cadena.equals(visita.cadena)) return false;
        if (checkInNotificado != visita.checkInNotificado) return false;
        if (!comentarios.equals(visita.comentarios)) return false;
        if (!descripcionMotivoRetiro.equals(visita.descripcionMotivoRetiro)) return false;
        if (encuestaCapturada != visita.encuestaCapturada) return false;
        if (!Arrays.equals(encuestaPersonas, visita.encuestaPersonas)) return false;
        if (estatusVisita != visita.estatusVisita) return false;
        if (!fechaCheckIn.equals(visita.fechaCheckIn)) return false;
        if (!fechaCheckout.equals(visita.fechaCheckout)) return false;
        if (!Arrays.equals(firmaGerente, visita.firmaGerente)) return false;
        if (!gerente.equals(visita.gerente)) return false;
        if (!idVisita.equals(visita.idVisita)) return false;
        if (reporteCapturado != visita.reporteCapturado) return false;
        if (!Arrays.equals(revisionFoto, visita.revisionFoto)) return false;
        if (!Arrays.equals(revisionProductos, visita.revisionProductos)) return false;
        if (!tienda.equals(visita.tienda)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idVisita.hashCode();
        result = 31 * result + tienda.hashCode();
        result = 31 * result + estatusVisita.hashCode();
        result = 31 * result + fechaCheckIn.hashCode();
        result = 31 * result + fechaCheckout.hashCode();
        result = 31 * result + gerente.hashCode();
        result = 31 * result + Arrays.hashCode(firmaGerente);
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + aplicarCapturaProductos.hashCode();
        result = 31 * result + aplicarEncuesta.hashCode();
        result = 31 * result + Arrays.hashCode(encuestaPersonas);
        result = 31 * result + Arrays.hashCode(revisionProductos);
        result = 31 * result + Arrays.hashCode(revisionFoto);
        result = 31 * result + IdEncuesta.hashCode();
        result = 31 * result + cadena.hashCode();
        result = 31 * result + reporteCapturado.hashCode();
        result = 31 * result + encuestaCapturada.hashCode();
        result = 31 * result + idMotivoRetiro;
        result = 31 * result + descripcionMotivoRetiro.hashCode();
        result = 31 * result + checkInNotificado.hashCode();
        return result;
    }
}
