package mx.com.algroup.promotormovil.business.rest.Post;

import java.util.List;

/**
 * Created by carlosemg on 28/04/2015.
 */
public class VisitaTienda {

    private int idVisita;
    private int idTienda;
    private String nombreJefeDepartamento;
    private String firmaJefeDepartamento;
    private String comentarios;
    private List<String> fotosTienda;
    private List<ProductoTienda> productosTienda;
    private List<EntrevistaEncuesta> entrevistasEncuesta;

    //INI MAM Motivo checkout
    private int idMotivoRetiro;
    private String descripcionMotivoRetiro;
    //END MAM Motivo checkout

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombreJefeDepartamento() {
        return nombreJefeDepartamento;
    }

    public void setNombreJefeDepartamento(String nombreJefeDepartamento) {
        this.nombreJefeDepartamento = nombreJefeDepartamento;
    }

    public String getFirmaJefeDepartamento() {
        return firmaJefeDepartamento;
    }

    public void setFirmaJefeDepartamento(String firmaJefeDepartamento) {
        this.firmaJefeDepartamento = firmaJefeDepartamento;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public List<String> getFotosTienda() {
        return fotosTienda;
    }

    public void setFotosTienda(List<String> fotosTienda) {
        this.fotosTienda = fotosTienda;
    }

    public List<ProductoTienda> getProductosTienda() {
        return productosTienda;
    }

    public void setProductosTienda(List<ProductoTienda> productosTienda) {
        this.productosTienda = productosTienda;
    }

    public List<EntrevistaEncuesta> getEntrevistasEncuesta() {
        return entrevistasEncuesta;
    }

    public void setEntrevistasEncuesta(List<EntrevistaEncuesta> entrevistasEncuesta) {
        this.entrevistasEncuesta = entrevistasEncuesta;
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
        return "VisitaTienda{" +
                "idVisita=" + idVisita +
                ", idTienda=" + idTienda +
                ", nombreJefeDepartamento='" + nombreJefeDepartamento + '\'' +
                ", firmaJefeDepartamento='" + firmaJefeDepartamento + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", fotosTienda=" + fotosTienda +
                ", productosTienda=" + productosTienda +
                ", entrevistasEncuesta=" + entrevistasEncuesta +
                ", idMotivoRetiro=" + idMotivoRetiro +
                ", descripcionMotivoRetiro='" + descripcionMotivoRetiro + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VisitaTienda that = (VisitaTienda) o;

        if (idMotivoRetiro != that.idMotivoRetiro) return false;
        if (idTienda != that.idTienda) return false;
        if (idVisita != that.idVisita) return false;
        if (!comentarios.equals(that.comentarios)) return false;
        if (!descripcionMotivoRetiro.equals(that.descripcionMotivoRetiro)) return false;
        if (!entrevistasEncuesta.equals(that.entrevistasEncuesta)) return false;
        if (!firmaJefeDepartamento.equals(that.firmaJefeDepartamento)) return false;
        if (!fotosTienda.equals(that.fotosTienda)) return false;
        if (!nombreJefeDepartamento.equals(that.nombreJefeDepartamento)) return false;
        if (!productosTienda.equals(that.productosTienda)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idVisita;
        result = 31 * result + idTienda;
        result = 31 * result + nombreJefeDepartamento.hashCode();
        result = 31 * result + firmaJefeDepartamento.hashCode();
        result = 31 * result + comentarios.hashCode();
        result = 31 * result + fotosTienda.hashCode();
        result = 31 * result + productosTienda.hashCode();
        result = 31 * result + entrevistasEncuesta.hashCode();
        result = 31 * result + idMotivoRetiro;
        result = 31 * result + descripcionMotivoRetiro.hashCode();
        return result;
    }
}
