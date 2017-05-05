package mx.com.algroup.promotormovil.business;

import java.util.Arrays;

import mx.com.algroup.promotormovil.business.utils.PreguntaRespuesta;

/**
 * Created by MAMM on 19/04/15.
 */
public class EncuestaPersona {
    private String idEncuesta;
    private Persona persona;
    private PreguntaRespuesta[] preguntaRespuesta;

    public EncuestaPersona() {
        this.persona = new Persona();
        this.preguntaRespuesta = new PreguntaRespuesta[0];
        this.idEncuesta = "";
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }



    public PreguntaRespuesta[] getPreguntaRespuesta() {
        return preguntaRespuesta;
    }

    public void setPreguntaRespuesta(PreguntaRespuesta[] preguntaRespuesta) {
        this.preguntaRespuesta = preguntaRespuesta;
    }

    public String getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(String idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EncuestaPersona that = (EncuestaPersona) o;

        if (!idEncuesta.equals(that.idEncuesta)) return false;
        if (!persona.equals(that.persona)) return false;
        if (!Arrays.equals(preguntaRespuesta, that.preguntaRespuesta)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEncuesta.hashCode();
        result = 31 * result + persona.hashCode();
        result = 31 * result + Arrays.hashCode(preguntaRespuesta);
        return result;
    }

    @Override
    public String toString() {
        return "EncuestaPersona{" +
                "idEncuesta='" + idEncuesta + '\'' +
                ", persona=" + persona +
                ", preguntaRespuesta=" + Arrays.toString(preguntaRespuesta) +
                '}';
    }


}
