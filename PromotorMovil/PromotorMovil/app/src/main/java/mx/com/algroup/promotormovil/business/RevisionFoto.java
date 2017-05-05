package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

/**
 * Created by MAMM on 19/04/15.
 */
public class RevisionFoto {
    private String idFoto;
    private byte[] foto;
    private String fechaCaptura;

    public RevisionFoto(  ) {
        this.idFoto = "";
        this.foto = new byte[0];
        this.fechaCaptura = "";
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(String fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    @Override
    public String toString() {
        return "RevisionFoto{" +
                "idFoto='" + idFoto + '\'' +
                //", foto=" + Arrays.toString(foto) +
                ", foto=" + ">>>LA FOTO VA AQUI<<<" +
                ", fechaCaptura='" + fechaCaptura + '\'' +
                '}';
    }
}
