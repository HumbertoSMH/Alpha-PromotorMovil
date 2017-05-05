package mx.com.algroup.promotormovil.utils;

import mx.com.algroup.promotormovil.business.utils.Respuesta;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;

/**
 * Created by devmac03 on 21/04/15.
 */
public class RespuestaDialog {
    private RespuestaBinaria respuestaBinaria;

    public RespuestaDialog(){
        this.respuestaBinaria = RespuestaBinaria.NO;
    }

    public RespuestaBinaria getRespuestaBinaria() {
        return respuestaBinaria;
    }

    public void setRespuestaBinaria(RespuestaBinaria respuestaBinaria) {
        this.respuestaBinaria = respuestaBinaria;
    }

    @Override
    public String toString() {
        return "RespuestaDialog{" +
                "respuestaBinaria=" + respuestaBinaria +
                '}';
    }
}
