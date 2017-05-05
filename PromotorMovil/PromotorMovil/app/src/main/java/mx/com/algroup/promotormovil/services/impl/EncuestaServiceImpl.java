package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.Respuesta;
import mx.com.algroup.promotormovil.services.EncuestaService;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by MAMM on 19/04/15.
 */
public class EncuestaServiceImpl implements EncuestaService{
    private static final String CLASSNAME = EncuestaServiceImpl.class.getSimpleName();


    private static EncuestaService encuestaService;
    private static JsonService jsonService;
    private Context context;

    private Map< String , Encuesta > mapaEncuestas;

    private EncuestaServiceImpl( Context context ){
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.mapaEncuestas = new HashMap< String , Encuesta >();

    }

    public static EncuestaService getSingleton(){
        if( encuestaService == null){
            encuestaService = new EncuestaServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return encuestaService;
    }

//    public Encuesta recuperarEncuestaPorId( String idEncuesta ){
//        Encuesta encuesta = null;
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            encuesta = this.crearEncuestaMock( idEncuesta );
//        }else{
//            encuesta =  this.mapaEncuestas.get( idEncuesta );
//        }
//        return encuesta;
//    }

//    private Encuesta crearEncuestaMock( String idEncuesta ) {
//        Encuesta encuesta = new Encuesta();
//        encuesta.setIdEncuesta( idEncuesta );
//        Pregunta[] preguntas = this.crearPreguntasMock( idEncuesta );
//        encuesta.setPreguntasEncuesta(preguntas);
//        return encuesta;
//    }
//
//    private Pregunta[] crearPreguntasMock( String idEncuesta ){
//        int numeroPreguntas = 2;
//        Pregunta[] preguntas = new Pregunta[ numeroPreguntas ];
//        for( int j = 1 ; j <= numeroPreguntas ; j++ ){
//            Pregunta pregunta = new Pregunta();
//            pregunta.setIdPregunta( idEncuesta + j );
//            pregunta.setDescripcion("¿Esta es la pregunta 10" + j);
//            Respuesta[] respuestas = this.crearRespuestaMock( idEncuesta + j );
//            pregunta.setRespuestasPregunta(respuestas);
//            preguntas[ j-1 ] = pregunta;
//        }
//        return preguntas;
//    }
//
//    private Respuesta[] crearRespuestaMock( String idPregunta ){
//        int numeroRespuestas = 3;
//        Respuesta[] respuestas = new Respuesta[ numeroRespuestas ];
//        for( int j = 1 ; j<= numeroRespuestas ; j++ ){
//            Respuesta respuesta = new Respuesta();
//            respuesta.setIdRespuesta( "" + idPregunta + j );
//            respuesta.setDescripcion("Respuesta opción " + j);
//            respuestas[ j - 1 ] = respuesta;
//        }
//        return respuestas;
//    }

    public void actualizarMapaEncuesta(String idRuta){
        LogUtil.printLog(CLASSNAME, "actualizarMapaEncuesta ..");
        this.mapaEncuestas = new HashMap< String , Encuesta>() ;
        EncuestasRutaResponse response = jsonService.realizarPeticionEncuestasRuta( Integer.parseInt( idRuta )  );
        if( !response.isSeEjecutoConExito() ){
            Json.solicitarMsgError(response, Json.ERROR_JSON.LOGIN);
            return;
        }else{
            List<Encuesta> encuestas = response.getEncuestasRuta();
            for( Encuesta itemEncuesta : encuestas ){
                this.mapaEncuestas.put( itemEncuesta.getIdEncuesta(), itemEncuesta );
            }
        }
        LogUtil.printLog(CLASSNAME, "actualizarMapaEncuesta finaliza con:" + this.mapaEncuestas.size() + " encuestas cargadas");
    }

}
