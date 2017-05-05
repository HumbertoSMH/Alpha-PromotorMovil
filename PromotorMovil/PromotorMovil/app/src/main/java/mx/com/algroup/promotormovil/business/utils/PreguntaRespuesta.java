package mx.com.algroup.promotormovil.business.utils;

/**
 * Created by MAMM on 19/04/15.
 */
public class PreguntaRespuesta {
    private Pregunta pregunta;
    private Respuesta respuestaElegida;

    public PreguntaRespuesta() {
        this.pregunta = new Pregunta();
        this.respuestaElegida = new Respuesta();
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Respuesta getRespuestaElegida() {
        return respuestaElegida;
    }

    public void setRespuestaElegida(Respuesta respuestaElegida) {
        this.respuestaElegida = respuestaElegida;
    }

    @Override
    public String toString() {
        return "PreguntaEncuesta{" +
                "pregunta=" + pregunta +
                ", respuestaElegida=" + respuestaElegida +
                '}';
    }
}
