package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.Respuesta;
import mx.com.algroup.promotormovil.dao.CatalogoDao;
import mx.com.algroup.promotormovil.dao.impl.CatalogoDaoImpl;
import mx.com.algroup.promotormovil.services.CadenaService;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 30/09/15.
 */
public class CatalogosServiceImpl implements CatalogosService {
    private static final String CLASSNAME = CatalogosServiceImpl.class.getSimpleName();

    private static CatalogosService catalogosServices;
    private CatalogoDao catalogoDao;
    private JsonService jsonService;

    private Context context;

    public CatalogosServiceImpl(Context context) {
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.catalogoDao = CatalogoDaoImpl.getSingleton();

    }

    public static CatalogosService getSingleton() {
        if (catalogosServices == null) {
            catalogosServices = new CatalogosServiceImpl(PromotorMovilApp.getPromotorMovilApp());
        }
        return catalogosServices;
    }

    private Map< String , Map<String , Producto> > mapaProductosPorCadena;
    private Map< String , Encuesta > mapaEncuestas;
    private List<MotivoRetiro> catalogoMotivoRetiro;

    @Override
    public ProductosCadenaResponse cargarTodosLosProductosDesdeWeb(  Set<Cadena> cadenas  ) {
        LogUtil.printLog(CLASSNAME, "cargarTodosLosProductosDesdeWeb .." + cadenas);
        ProductosCadenaResponse productosCadenaResponse = null;
        mapaProductosPorCadena = new HashMap< String , Map<String , Producto> >();
        for( Cadena itemCadena : cadenas ){
            productosCadenaResponse = this.jsonService.realizarPeticionProductosCadena( Integer.parseInt( itemCadena.getIdCadena() ) );
            if( productosCadenaResponse.isSeEjecutoConExito() == false ){
                //Si por alguna razon se presenta un problema al recuperar los productos se regresa el resultado
                return productosCadenaResponse;
            }else{
                this.cargarEnMapaLosProductos( itemCadena , productosCadenaResponse );
            }
        }
        return productosCadenaResponse;
    }

    private void cargarEnMapaLosProductos(Cadena itemCadena, ProductosCadenaResponse productosCadenaResponse) {
        List<Producto> productosEnCadena = productosCadenaResponse.getProductos();
        Map< String , Producto > mapaProducto = new HashMap< String , Producto >();

        for( Producto itemProducto : productosEnCadena  ){
            mapaProducto.put( itemProducto.getCodigo() , itemProducto );
        }
        mapaProductosPorCadena.put( itemCadena.getIdCadena() , mapaProducto );
        LogUtil.printLog(CLASSNAME, "Elemento en Catalog de producto: cadena:" + itemCadena + " , productos:" + productosEnCadena);
    }



    @Override
    public EncuestasRutaResponse cargarTodasLasEncuestasDesdeWeb(int idRuta) {
        EncuestasRutaResponse response = this.jsonService.realizarPeticionEncuestasRuta(idRuta);
        if( response.isSeEjecutoConExito() == false ){
            return response;
        }
        //De ser correcto seguimos con el procesamiento
        mapaEncuestas = new HashMap< String , Encuesta>();
        List<Encuesta> encuestasRest = response.getEncuestasRuta();
        for( Encuesta itemEncuesta : encuestasRest ){
            mapaEncuestas.put( "" + itemEncuesta.getIdEncuesta() , itemEncuesta );
        }
        return response;
    }

    @Override
    public void recuperarCatalogosDesdeBase() {
        this.recuperarCatalogoMedicamentosDesdeBase();
        this.recuperarCatalogoEncuestaDesdeBase();
        this.recuperarCatalogoMotivoRetiroDesdeBase();
    }

    private void recuperarCatalogoEncuestaDesdeBase() {
        this.mapaEncuestas = new HashMap<String, Encuesta>();

        List<Integer> listaIdEncuestas = this.catalogoDao.recuperarListaIdEncuestaEnCatalogo();
        for( Integer idEncuestaItem : listaIdEncuestas ){
            Encuesta encuesta = new Encuesta();
            List<Pregunta> catalogoPreguntas = this.catalogoDao.recuperarCatalogoPreguntasPorIdEncuesta(idEncuestaItem);
            for( Pregunta itemPregunta : catalogoPreguntas ){
                int idPregunta = Integer.parseInt(itemPregunta.getIdPregunta());
                List<Respuesta> catalogoRespuestas = this.catalogoDao.recuperarCatalogoRespuestaPorIdPregunta( idPregunta );
                itemPregunta.setRespuestasPregunta(catalogoRespuestas.toArray(new Respuesta[0]));
            }
            encuesta.setIdEncuesta( "" + idEncuestaItem );
            encuesta.setPreguntasEncuesta(catalogoPreguntas.toArray(new Pregunta[0]));
            this.mapaEncuestas.put( "" + idEncuestaItem , encuesta );
        }
    }

    private void recuperarCatalogoMedicamentosDesdeBase() {
        this.mapaProductosPorCadena = new HashMap< String , Map<String , Producto> >();
        List<Integer> listaIdCadenas =  this.catalogoDao.recuperarListaDeIdCadenasEnCatalogoDeProductos();
        for( Integer idCadena : listaIdCadenas ){
            List<Producto> productos = this.catalogoDao.recuperarCatalogoProductosPorIdCadena(idCadena);
            Map< String , Producto > mapaProducto = new HashMap< String , Producto >();

            for( Producto itemProducto : productos  ){
                mapaProducto.put( itemProducto.getCodigo() , itemProducto );
            }
            this.mapaProductosPorCadena.put( "" + idCadena , mapaProducto );
        }
    }

    private void recuperarCatalogoMotivoRetiroDesdeBase(){
        this.catalogoMotivoRetiro = this.catalogoDao.recuperarTodosLosMotivosDeRetiro();
    }


    @Override
    public void actualizarCatalogosEnBase() {
        this.actualizarCatalogoProductosEnBase();
        this.actualizarCatalogoEncuestaEnBase();
        this.actualizarCatalogoMotivoRetiroEnBase();
    }

    private void actualizarCatalogoMotivoRetiroEnBase() {
        //eliminar el contenido en tablas
        this.catalogoDao.eliminarTodosLosMotivosDeRetiro();
        //Insertar los catalogos de motivos de retiro
        for( MotivoRetiro itemMotivo : this.catalogoMotivoRetiro){
            this.catalogoDao.insertarMotivosRetiro( itemMotivo );
        }

    }

    private void actualizarCatalogoEncuestaEnBase() {
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoPreguntasRespuestas();
        //Insertar los catalogos de preguntas y resgpuestas
        Set< String > idEncuestaSet = this.mapaEncuestas.keySet();
        for( String idEncuestaItem : idEncuestaSet ){
            Encuesta encuesta = this.mapaEncuestas.get( idEncuestaItem );
            Pregunta[] preguntas = encuesta.getPreguntasEncuesta();
            for( Pregunta itemPregunta : preguntas ){
                int idEncuesta = Integer.parseInt( idEncuestaItem);
                this.catalogoDao.insertarCatalogoPreguntas( itemPregunta , idEncuesta );
                Respuesta[] respuestas = itemPregunta.getRespuestasPregunta();
                for( Respuesta itemRespuesta : respuestas ){
                    int idPregunta = Integer.parseInt( itemPregunta.getIdPregunta() );
                    this.catalogoDao.insertarCatalogoRespuesta( itemRespuesta , idPregunta );
                }
            }
        }
    }

    private void actualizarCatalogoProductosEnBase() {
        StringBuilder sb = new StringBuilder( "PRODUCTOS CARGADOS" );
        //EliminarContenidoDelCatalogo
        this.catalogoDao.eliminarCatalogoProductos();
        //InsertarNuevoCatalogo
        Set<String> idCadenasSet = this.mapaProductosPorCadena.keySet();
        for( String idCadenaItem : idCadenasSet  ){
            Map<String, Producto> mapaProductos = this.mapaProductosPorCadena.get(idCadenaItem);
            sb.append( "\n--->Cadena:" + idCadenaItem );
            for(Producto itemProducto : mapaProductos.values() ){
                sb.append( "\n------->codigo:" + itemProducto.getCodigo() + ", desc:" + itemProducto.getDescripcion());
                this.catalogoDao.insertarCatalogoProductos(itemProducto, Integer.parseInt(idCadenaItem));
            }
        }
        LogUtil.printLog( CLASSNAME , sb.toString() );
    }

    @Override
    public Map<String, Producto> getMapaProductosPorCadena(Cadena cadena) {
        return null;
    }

    @Override
    public Encuesta recuperarEncuestaPorId(String idEncuesta) {
        return mapaEncuestas.get( idEncuesta );
    }

    public boolean esValidoProductoEnCadena(String codigoProducto, Cadena cadena) {
        boolean esValido = false;
        LogUtil.printLog(CLASSNAME, "esValidoProductoEnCadenao: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            if( codigoProducto.contains( "1" )){
                esValido = true;
            }else{
                esValido = false;
            }
        }else{
            Producto productoEncontrado= this.mapaProductosPorCadena.get( cadena.getIdCadena() ).get(codigoProducto);
            if( productoEncontrado != null ){
                esValido = true;
            }
        }
        return esValido;
    }


    @Override
    public Producto recuperarProducto(String codigoProducto, Cadena cadena) {
        LogUtil.printLog(CLASSNAME, "recuperarProducto: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
        Producto productoEncontrado = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            productoEncontrado = new Producto();
            productoEncontrado.setPrecioBase(1050);
            productoEncontrado.setDescripcion( "Producto básico " + codigoProducto );
            productoEncontrado.setCodigo(codigoProducto);
        }else{
            productoEncontrado= this.mapaProductosPorCadena.get( cadena.getIdCadena() ).get(codigoProducto);
        }

        return productoEncontrado;
    }


    public CatalogoMotivoRetiroResponse cargarTodasLosMotivosRetiroDesdeWeb(  ){
        LogUtil.printLog(CLASSNAME, "cargarTodasLosMotivosRetiroDesdeWeb .." );
        CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = null;
        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            this.catalogoMotivoRetiro  = this.prepararCatalogoMotivoRetiroMock( );
        }else{
            catalogoMotivoRetiroResponse = jsonService.realizarPeticionCatalogoMotivoRetiro( );
            if ( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError(catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN);
            } else {
                this.catalogoMotivoRetiro = catalogoMotivoRetiroResponse.getCatalogoMotivoRetiro();
                //INI MAMM Se ordenan los motivos
                Collections.sort(this.catalogoMotivoRetiro, new Comparator<MotivoRetiro>() {
                    @Override
                    public int compare(MotivoRetiro p1, MotivoRetiro p2) {
                        return p1.getIdMotivoRetiro() - p2.getIdMotivoRetiro(); // Ascending
                    }
                });
                //END MAMM
            }
        }
        return catalogoMotivoRetiroResponse;
    }

    private List<MotivoRetiro> prepararCatalogoMotivoRetiroMock() {
        List<MotivoRetiro> catMotivoRetiro = new ArrayList<MotivoRetiro>();
        for( int j = 0; j < 5 ; j++ ){
            MotivoRetiro motivo = new MotivoRetiro();
            motivo.setIdMotivoRetiro( j + 1 );
            motivo.setDescripcionMotivoRetiro("Motivo de retiro " + (j + 1));
            catMotivoRetiro.add( motivo );
        }
        MotivoRetiro motivoOtro = new MotivoRetiro();
        motivoOtro.setIdMotivoRetiro(Const.ID_MOTIVO_RETIRO_OTRO);
        motivoOtro.setDescripcionMotivoRetiro( "Otro" );
        catMotivoRetiro.add( motivoOtro );
        return catMotivoRetiro;
    }

    public List<MotivoRetiro> getCatalogoMotivoRetiro(){
        return this.catalogoMotivoRetiro;
    }

}
