package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Direccion;
import mx.com.algroup.promotormovil.business.Encuesta;
import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.Persona;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Ruta;
import mx.com.algroup.promotormovil.business.Promotor;
import mx.com.algroup.promotormovil.business.Tienda;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.rest.Get.CadenaTienda;
import mx.com.algroup.promotormovil.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.RutaPromotorResponse;
import mx.com.algroup.promotormovil.business.rest.Get.RutasPromotor;
import mx.com.algroup.promotormovil.business.rest.Get.TiendaVisita;
import mx.com.algroup.promotormovil.business.rest.Get.Visitas;
import mx.com.algroup.promotormovil.business.rest.Post.CheckInTienda;
import mx.com.algroup.promotormovil.business.rest.Post.CheckInTiendaResponse;
import mx.com.algroup.promotormovil.business.rest.Post.CheckOutTienda;
import mx.com.algroup.promotormovil.business.rest.Post.CheckOutTiendaResponse;
import mx.com.algroup.promotormovil.business.rest.Post.DetalleRespuesta;
import mx.com.algroup.promotormovil.business.rest.Post.EntrevistaEncuesta;
import mx.com.algroup.promotormovil.business.rest.Post.GuardarImagenResponse;
import mx.com.algroup.promotormovil.business.rest.Post.ImagenVisita;
import mx.com.algroup.promotormovil.business.rest.Post.ProductoTienda;
import mx.com.algroup.promotormovil.business.rest.Post.VisitaTienda;
import mx.com.algroup.promotormovil.business.rest.Response;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.business.utils.Pregunta;
import mx.com.algroup.promotormovil.business.utils.PreguntaRespuesta;
import mx.com.algroup.promotormovil.business.utils.Respuesta;
import mx.com.algroup.promotormovil.business.utils.RespuestaBinaria;
import mx.com.algroup.promotormovil.business.utils.UtilLocation;
import mx.com.algroup.promotormovil.dao.CatalogoDao;
import mx.com.algroup.promotormovil.dao.EncuestaDao;
import mx.com.algroup.promotormovil.dao.FotografiaDao;
import mx.com.algroup.promotormovil.dao.NotificacionErroresDao;
import mx.com.algroup.promotormovil.dao.ProductoDao;
import mx.com.algroup.promotormovil.dao.RutaDao;
import mx.com.algroup.promotormovil.dao.VisitaDao;
import mx.com.algroup.promotormovil.dao.impl.CatalogoDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.EncuestaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.FotografiaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.NotificacionErroresDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.ProductoDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.RutaDaoImpl;
import mx.com.algroup.promotormovil.dao.impl.VisitaDaoImpl;
import mx.com.algroup.promotormovil.services.CatalogosService;
import mx.com.algroup.promotormovil.services.EncuestaService;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.services.LocationService;
import mx.com.algroup.promotormovil.services.RutaService;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.NotificacionError;
import mx.com.algroup.promotormovil.utils.Util;
import mx.com.algroup.promotormovil.utils.UtilsMock;
import mx.com.algroup.promotormovil.utils.ViewUtil;

/**
 * Created by MAMM on 19/04/15.
 */
public class VisitaServiceImpl implements VisitaService{
    private static final String CLASSNAME = VisitaServiceImpl.class.getSimpleName();

    private static VisitaService visitaService;
    private EncuestaService encuestaService;
    private LocationService locationService;
    private RutaService rutaService;
    private CatalogosService catalogosService;

    private RutaDao rutaDao;
    private VisitaDao visitaDao;
    private EncuestaDao encuestaDao;
    private ProductoDao productoDao;
    private FotografiaDao fotografiaDao;
    private NotificacionErroresDao notificacionErroresDao;
    private Context context;
    private Ruta rutaActual;
    private Visita visitaActual;
    private Set<Cadena> cadenasAplicadasEnRuta;
    //private List<MotivoRetiro> catalogoMotivoRetiro;
    private JsonService jsonService;

    //Catalogo
    private CatalogoDao catalogosDao;

    public VisitaServiceImpl( Context context ){
        this.context = context;
        this.encuestaService = EncuestaServiceImpl.getSingleton();
        this.locationService = LocationServiceImpl.getSingleton();
        this.notificacionErroresDao = NotificacionErroresDaoImpl.getSingleton();
        this.jsonService = JsonServiceImpl.getSingleton();
        this.rutaService = RutaServiceImpl.getSingleton();
        this.catalogosService = CatalogosServiceImpl.getSingleton();

        this.rutaDao = RutaDaoImpl.getSingleton();
        this.visitaDao = VisitaDaoImpl.getSingleton();
        this.encuestaDao = EncuestaDaoImpl.getSingleton();
        this.productoDao = ProductoDaoImpl.getSingleton();
        this.fotografiaDao = FotografiaDaoImpl.getSingleton();
        this.catalogosDao = CatalogoDaoImpl.getSingleton();


        this.rutaActual = new Ruta();
    }

    public static VisitaService getSingleton(){
        if( visitaService == null ){
            visitaService = new VisitaServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return visitaService;
    }



    public void recuperarRuta( Promotor promotor ) {
        LogUtil.printLog( CLASSNAME , "recuperarRuta promotor:" + promotor );
        Ruta ruta = null;

        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            ruta = this.prepararRutaMock(promotor);
            this.rutaActual = ruta;
            this.testDaoMock( ruta);
            //this.testDaoTablaErrores();
        }else{
            RutaPromotorResponse rutaPromotorResponse = jsonService.realizarPeticionRutaPromotor(promotor.getUsuario());
            if ( !rutaPromotorResponse.isSeEjecutoConExito() ){
                Json.solicitarMsgError( rutaPromotorResponse, Json.ERROR_JSON.LOGIN );
            } else {
                ruta = parsearRuta( rutaPromotorResponse.getRutaPromotor() , promotor );
                //INI MAMM siempre que se registre un usuario se actualizan las rutas en base.
                this.rutaActual = this.rutaService.cargarRuta( ruta );
                this.armarMapaDeCadenasEnRuta();
                this.cargarCatalogos(promotor);
//                boolean actualizarRuta = false;
//                actualizarRuta = this.actualizarRutaSiExisteCambio( ruta , promotor );
//                if( actualizarRuta == true ){
//                    this.rutaActual = ruta;
//
//                    this.armarMapaDeCadenasEnRuta();
//                }
                //END MAMM
            }
        }
    }

    public void cargarCatalogos( Promotor promotor ) {
        LogUtil.printLog(CLASSNAME, "cargarCatalogos:");

        if (Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK) {
            //Cuando se es mock no se actualizan
        } else {

            int idRuta = Integer.parseInt( this.rutaActual.getIdRuta() );
            ProductosCadenaResponse productosCadenaResponse = this.catalogosService.cargarTodosLosProductosDesdeWeb( cadenasAplicadasEnRuta );
            if (!productosCadenaResponse.isSeEjecutoConExito()) {
                Json.solicitarMsgError(productosCadenaResponse, Json.ERROR_JSON.LOGIN);
            } else {
                EncuestasRutaResponse encuestasRutaResponse = this.catalogosService.cargarTodasLasEncuestasDesdeWeb( idRuta );
                if (!encuestasRutaResponse.isSeEjecutoConExito()) {
                    Json.solicitarMsgError(encuestasRutaResponse, Json.ERROR_JSON.LOGIN);
                } else {
                    CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = this.catalogosService.cargarTodasLosMotivosRetiroDesdeWeb();
                    if( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
                        Json.solicitarMsgError(catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN);
                    }else{
                        //Actualizar los catalogos
                        this.catalogosService.actualizarCatalogosEnBase();
                    }
                }
            }
        }
    }



//    public void recuperarMotivosDeRetiro( ) {
//        LogUtil.printLog( CLASSNAME , "recuperarMotivosDeRetiro:"  );
//        Ruta ruta = null;
//
//        if(Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            this.catalogoMotivoRetiro  = this.prepararCatalogoMotivoRetiroMock( );
//        }else{
//            CatalogoMotivoRetiroResponse catalogoMotivoRetiroResponse = jsonService.realizarPeticionCatalogoMotivoRetiro( );
//            if ( !catalogoMotivoRetiroResponse.isSeEjecutoConExito() ){
//                Json.solicitarMsgError( catalogoMotivoRetiroResponse, Json.ERROR_JSON.LOGIN );
//            } else {
//                this.catalogoMotivoRetiro = catalogoMotivoRetiroResponse.getCatalogoMotivoRetiro();
//                //INI MAMM Se ordenan los motivos
//                Collections.sort(this.catalogoMotivoRetiro, new Comparator<MotivoRetiro>() {
//                    @Override
//                    public int compare(MotivoRetiro p1, MotivoRetiro p2) {
//                        return p1.getIdMotivoRetiro() - p2.getIdMotivoRetiro(); // Ascending
//                    }
//                });
//                //END MAMM
//            }
//        }
//    }

//    private List<MotivoRetiro> prepararCatalogoMotivoRetiroMock() {
//        List<MotivoRetiro> catMotivoRetiro = new ArrayList<MotivoRetiro>();
//        for( int j = 0; j < 5 ; j++ ){
//            MotivoRetiro motivo = new MotivoRetiro();
//            motivo.setIdMotivoRetiro( j + 1 );
//            motivo.setDescripcionMotivoRetiro("Motivo de retiro " + (j + 1));
//            catMotivoRetiro.add( motivo );
//        }
//        MotivoRetiro motivoOtro = new MotivoRetiro();
//        motivoOtro.setIdMotivoRetiro(Const.ID_MOTIVO_RETIRO_OTRO);
//        motivoOtro.setDescripcionMotivoRetiro( "Otro" );
//        catMotivoRetiro.add( motivoOtro );
//        return catMotivoRetiro;
//    }

    private boolean actualizarRutaSiExisteCambio(Ruta ruta , Promotor promotorLogueado) {
        boolean actualizarRuta = false;
        Promotor promotorActual = (PromotorServiceImpl.getSingleton()).getPromotorActual();
        if( promotorActual == null  ){
            LogUtil.printLog( CLASSNAME , "No se tiene registros previos de promotor, actualizarRuta = true" );
            actualizarRuta = true;
        }else
        if( promotorActual.getClavePromotor().equals( promotorLogueado.getClavePromotor() ) == false ){
            LogUtil.printLog( CLASSNAME , "Cambia de promotor registrado, actualizarRuta = true" );
            actualizarRuta = true;
        }else{
            LogUtil.printLog( CLASSNAME , "Se vuelve a loggear el promotor, se valida para actualizar la ruta" );
            if( this.rutaActual.getIdRuta().equals( ruta.getIdRuta()) == true ){
                if( this.rutaActual.getFechaUltimaModificacion().equals( ruta.getFechaUltimaModificacion()) == false){
                    LogUtil.printLog( CLASSNAME , "La ruta del día sufrio un cambio, actualizarRuta = true" );
                    actualizarRuta = true;
                }else{  /* SI NO HAY CAMBIO EN LA RUTA NO SE REALIZA LA ACTUALIZACION*/
                    LogUtil.printLog( CLASSNAME , "La ruta del día no ha sufrido cambio, actualizarRuta = false" );
                }
            }else{
                    LogUtil.printLog( CLASSNAME , "Cambia el idRuta, actualizarRuta = true" );
                    actualizarRuta = true;
            }
        }
        return actualizarRuta;
    }

    private void armarMapaDeCadenasEnRuta() {
        Visita[] visitas = rutaActual.getVisitas();
        this.cadenasAplicadasEnRuta = new HashSet< Cadena>();
        for( Visita itemVisita : visitas ){
            this.cadenasAplicadasEnRuta.add( itemVisita.getCadena() );
        }
    }

    private Ruta parsearRuta( RutasPromotor rutasPromotor , Promotor promotor){

        Ruta ruta = new Ruta();
        ruta.setIdRuta( "" + rutasPromotor.getIdRuta() );
        ruta.setFechaCreacionString( rutasPromotor.getFechaCreacion() );
        ruta.setFechaUltimaModificacion(rutasPromotor.getFechaUltimaModificacion());
        ruta.setFechaProgramadaString(rutasPromotor.getFechaProgramada());

        ruta.setPromotor( promotor );
        if( rutasPromotor.getVisitas().size() > 0 ){
            ruta.setVisitas( this.parsearVisitasDesdeResponse( rutasPromotor.getVisitas() ) );
        }
        return ruta;
    }

    private Visita[] parsearVisitasDesdeResponse(List<Visitas> visitas) {
        int size = visitas.size();
        Visita[] arrayvisitas = new Visita[ size ];
        for( int j = 0; j<size;j++){
            Visita v = new Visita();
            Visitas vResponse = visitas.get( j );
            v.setIdVisita( "" + vResponse.getIdVisita() );
            v.setAplicarEncuesta(vResponse.getIdEncuesta() > 0 ? RespuestaBinaria.SI : RespuestaBinaria.NO);
            v.setAplicarCapturaProductos(vResponse.isCapturaProductos() ? RespuestaBinaria.SI : RespuestaBinaria.NO);
            v.setIdEncuesta("" + vResponse.getIdEncuesta());
            v.setEstatusVisita(EstatusVisita.EN_RUTA);
            v.setTienda(parsearTiendaDesdeVisitaTiendaResponse(vResponse.getTiendaVisita()));
            v.setCadena( parsearCadenaDesdeVisitaTiendaResponse(vResponse.getTiendaVisita()));
            v.setEstatusVisita( this.obtenerEstatusEnMovil(vResponse.getIdEstatus()) );
            arrayvisitas[j] = v;
        }
        return arrayvisitas;
    }

    private EstatusVisita obtenerEstatusEnMovil(int idEstatus) {
        EstatusVisita estatusVisita = null;
        if( idEstatus > 0 ){
            switch ( idEstatus ) {
                case EstatusVisita.EN_RUTA_ID_WEB:
                    estatusVisita = EstatusVisita.EN_RUTA;
                    break;
                case EstatusVisita.CHECK_IN_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_IN;
                    break;
                case EstatusVisita.CHECK_OUT_INCOMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CHECK_OUT_COMPLETO_ID_WEB:
                    estatusVisita = EstatusVisita.CHECK_OUT;
                    break;
                case EstatusVisita.CANCELADA_ID_WEB:
                    estatusVisita = EstatusVisita.CANCELADA;
                    break;
                case EstatusVisita.NO_VISITADA_ID_WEB:
                    estatusVisita = EstatusVisita.NO_VISITADA;
                    break;
                default:
                    break;
            }
        }
        return estatusVisita;
    }

    private Cadena parsearCadenaDesdeVisitaTiendaResponse(TiendaVisita tiendaResponse) {
        Cadena c = new Cadena();
        CadenaTienda ctResponse = tiendaResponse.getCadenaTienda();
        c.setIdCadena( "" + ctResponse.getIdCadena() );
        c.setNombreCadena(ctResponse.getNombre());
        return c;
    }

    private Tienda parsearTiendaDesdeVisitaTiendaResponse( TiendaVisita tiendaResponse ){
        Tienda tienda = new Tienda();
        tienda.setIdTienda( "" + tiendaResponse.getIdTienda() );
        tienda.setNombreTienda( tiendaResponse.getNombre() );
        tienda.setTelefono(  tiendaResponse.getTelefono() );

        Direccion dir = new Direccion();
        dir.setCalle( tiendaResponse.getCalle() );
        dir.setNumeroExterior(tiendaResponse.getNumeroExterior());
        dir.setNumeroInterior(tiendaResponse.getNumeroInterior());
        dir.setCodigoPostal(tiendaResponse.getCodigoPostal());
        dir.setColonia(tiendaResponse.getColonia());
        dir.setDelegacion(tiendaResponse.getDelegacionMunicpio());
        dir.setEstado(tiendaResponse.getEstado());
        dir.setPais("México");
        dir.setReferencia(tiendaResponse.getReferencia());
        tienda.setDireccion( dir );

        UtilLocation loc = new UtilLocation();
        loc.setLatitud( "" + tiendaResponse.getLatitud() );
        loc.setLongitud("" + tiendaResponse.getLongitud());
        tienda.setLocation(loc);

        return tienda;
    }


    private Ruta prepararRutaMock(Promotor promotor) {
        LogUtil.printLog( CLASSNAME , "Inicia prepararRutaMock" );
        Ruta ruta = new Ruta();
        ruta.setPromotor( promotor );
        ruta.setFechaInicio(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaFin(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaCreacionString(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaProgramadaString(Util.getDateTimeFromMilis(new Date().getTime()));
        ruta.setFechaUltimaModificacion( Util.getDateTimeFromMilis(new Date().getTime()) );
        ruta.setIdRuta("100001");
        Visita[] visitas = this.crearVisitasMock();
        ruta.setVisitas( visitas );
        LogUtil.printLog( CLASSNAME , " Ruta:"  + ruta);
        return ruta;
    }

    private Visita[] crearVisitasMock(){
        int numeroVisitas = 8;
        Visita[] visitas = new Visita[ numeroVisitas ];
        for( int j = 1 ; j <= numeroVisitas ; j++ ){
            Visita visita = new Visita();
            visita.setIdVisita( "" + j );
            if(j < 5 ){
                if(j == 1 ){ visita.setEstatusVisita(EstatusVisita.EN_RUTA);}
                if(j == 2 ){ visita.setEstatusVisita(EstatusVisita.CHECK_IN);}
                if(j == 3 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT);}
                if(j == 4 ){ visita.setEstatusVisita(EstatusVisita.CHECK_OUT_REQUEST);}
            }else{
                visita.setEstatusVisita(EstatusVisita.EN_RUTA);
            }
            Tienda tienda = this.crearTiendaMock( j );
            visita.setTienda( tienda );
            visita.setRevisionProductos( this.crearProductosMock( "" + j ) );
            visita.setRevisionFoto( this.crearRervisionFotosMock("" + j) );

            Cadena cadenaMock = new Cadena();
            cadenaMock.setIdCadena( "100" );
            cadenaMock.setNombreCadena( "Cadena Comercial 100" );
            visita.setCadena( cadenaMock );

            boolean esPar = j%2==0?true:false;
            if( esPar ){
                visita.setAplicarEncuesta(RespuestaBinaria.SI);
                visita.setIdEncuesta( "" + j );
                EncuestaPersona[] encuestaPersonas = this.crearEncuestaPersonaMock( "" + j );
                visita.setEncuestaPersonas( encuestaPersonas );
            }
            visitas[ j-1 ] = visita;
        }
        return visitas;
    }

    private RevisionProducto[] crearProductosMock(String idVisita ) {
        int numeroProductoInicial = 3;
        RevisionProducto[] rpArray = new RevisionProducto[ numeroProductoInicial ];
        for( int j = 1 ; j <= numeroProductoInicial ; j++ ){
            RevisionProducto rp = new RevisionProducto();
            Producto p = new Producto();
            p.setCodigo("Prod 0" + j);
            p.setDescripcion( "Producto especial básico " + j );
            p.setPrecioBase(j * 100);
            rp.setProducto( p );
            rp.setExistencia( 5 );
            rp.setPrecioEnTienda( j * 100 );
            rp.setExhibicionAdicional( RespuestaBinaria.SI );
            rp.setNumeroFrente( 4 );
            rpArray[j - 1] = rp;
        }

        return rpArray;
    }


    private RevisionFoto[] crearRervisionFotosMock(String idVisita ) {
        int numeroFotosIniciales = 3;
        RevisionFoto[] rfArray = new RevisionFoto[ numeroFotosIniciales ];
        for( int j = 1 ; j <= numeroFotosIniciales ; j++ ){
            RevisionFoto rf = new RevisionFoto();
            long milisec = j*5000;
            rf.setIdFoto( Util.getDateTimeFromMilis_hastaSegundos( new Date().getTime() + milisec )  );
            rf.setFechaCaptura( "2015/04/20 12:34" );
            rf.setFoto(UtilsMock.getImageMock( this.context ) );
            rfArray[j - 1] = rf;
        }

        return rfArray;
    }

    private EncuestaPersona[] crearEncuestaPersonaMock(String idEncuesta) {
        Encuesta encuesta = this.catalogosService.recuperarEncuestaPorId(idEncuesta);
        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();

        int numPersonasEncuestadas = 3;
        EncuestaPersona[] encuestaPersonas = new EncuestaPersona[ numPersonasEncuestadas ];

        for( int j = 1 ; j <= numPersonasEncuestadas ; j++ ){
            EncuestaPersona encuestaPersona = new EncuestaPersona();
            PreguntaRespuesta[] preguntaRespuesta = new PreguntaRespuesta[ preguntas.length ];


            for( int i = 0 ; i < preguntas.length ; i++ ){
                PreguntaRespuesta pr = new PreguntaRespuesta();
                pr.setPregunta( preguntas[i] );
                pr.setRespuestaElegida( preguntas[i].getRespuestasPregunta()[0]);
                preguntaRespuesta[ i ] = pr;
            }

            encuestaPersona.setPreguntaRespuesta( preguntaRespuesta );
            Persona persona = new Persona();
            persona.setNombre( "Persona " + j );
            encuestaPersona.setPersona(persona);
            encuestaPersonas[j - 1 ] = encuestaPersona;
        }

        return encuestaPersonas;
    }

    private Tienda crearTiendaMock( int item ){
        Tienda tienda = new Tienda();
        tienda.setIdTienda( "" + item );
        tienda.setNombreTienda( "Comercializadora Suc. 0" + item );
        tienda.setTelefono( "5512000" + item);

        Direccion dir = new Direccion();
        dir.setCalle( "Calle " + item );
        dir.setNumeroExterior("10" + item);
        dir.setNumeroInterior( "Interior 1" + item );
        dir.setCodigoPostal( "0660" + item );
        dir.setColonia( "Colonia "+ item );
        dir.setDelegacion( "Cuauhtemoc" );
        dir.setEstado( "Distrito Federal" );
        dir.setPais( "México" );
        dir.setReferencia( "Frente al oxxo #0" + item );
        tienda.setDireccion( dir );

        UtilLocation loc = new UtilLocation();
        loc.setLatitud( "19.43260" );
        loc.setLongitud("-99.13320");
        tienda.setLocation(loc);

        return tienda;
    }

    public Ruta getRutaActual() {
        return rutaActual;
    }


    public Response realizarCheckIn( Visita visita ){
        Response response = null;
        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
        }else{
            CheckInTienda checkIn = armarCheckIn(visita);
//            CheckInTienda checkIn = new CheckInTienda();
//            checkIn.setIdTienda( Integer.parseInt( visita.getTienda().getIdTienda() ) );
//            checkIn.setIdVisita(Integer.parseInt(visita.getIdVisita()));
//            checkIn.setClavePromotor( promotor.getClavePromotor() );
//            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
//
//            Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
//            checkIn.setLatitud( "" + this.locationService.getLatitude() );
//            checkIn.setLongitud("" + this.locationService.getLongitude());
//
            visita.setEstatusVisita(EstatusVisita.CHECK_IN);

            CheckInTiendaResponse CheckInResponse =  this.jsonService.realizarCheckinPost( checkIn );
//            if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
//                Json.solicitarMsgError( CheckInResponse.getHacerCheckInTiendaResult(), Json.ERROR_JSON.CHECK_IN );
//                response =  CheckInResponse.getHacerCheckInTiendaResult();
//            } else {
//                visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
//                visita.setEstatusVisita( EstatusVisita.CHECK_IN );
//                this.visitaDao.updateVisita( visita , Integer.parseInt(this.rutaActual.getIdRuta()) );
//                this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );
//            }
            if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
                visita.setCheckInNotificado(RespuestaBinaria.NO);
            } else {
                visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
                visita.setCheckInNotificado(RespuestaBinaria.SI);
            }
            this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
            this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );
        }

        return response;
    }


    public Response realizarCheckOut( Visita visita ){
        LogUtil.printLog( CLASSNAME , "realizarCheckOut visita:" + visita  );
        Response response = null;

        //Proceso para validar si se requeire realizar el CheckIn
        if( visita.getCheckInNotificado() == RespuestaBinaria.NO ){
            response = this.realizarCheckInEnProcesoCheckOut( visita );
            if( response != null &&
                    response.isSeEjecutoConExito() == false ){
                return response;
            } //ELSE Si no existe error se continua con el proceso de CheckOut de manera normal
        }


        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckOutTienda checkout = new CheckOutTienda();

        checkout.setClavePromotor( promotor.getClavePromotor() );
        checkout.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
        checkout.setIdVisita(Integer.parseInt(visita.getIdVisita()));

        Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
        checkout.setLatitud( "" + this.locationService.getLatitude() );
        checkout.setLongitud("" + this.locationService.getLongitude());

        checkout.setVisitaTienda( this.armarVisitaTienda( visita ) );


        CheckOutTiendaResponse checkOutResponse = this.jsonService.realizarCheckOutPost(checkout);
        if ( !checkOutResponse.getHacerCheckOutTiendaResult().isSeEjecutoConExito() ){
            Json.solicitarMsgError( checkOutResponse.getHacerCheckOutTiendaResult(), Json.ERROR_JSON.CHECK_OUT );
            response =  checkOutResponse.getHacerCheckOutTiendaResult();
            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_OUT_REQUEST);
            this.actualizarVisitaActual();
            this.recuperarRuta( promotor );
        } else {
            visita.setFechaCheckout( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_OUT);
            this.actualizarVisitaActual();
            this.limpiarDatosEnMemoriaDeLaVisita(visita);
            this.eliminarRegistrosEnTablasDeLaVisita( );
            this.recuperarRuta( promotor );
        }
        return response;
    }

//    private GuardarImagenResponse enviarFotografias(Visita visita , Promotor promotor) {
//        GuardarImagenResponse guardarImagenResponse = null;
//        List<String> fotos = this.armarListFotosCapturadas( visita );
//        ImagenVisita imgVisita = new ImagenVisita();
//        imgVisita.setFechaHoraCaptura( visita.getFechaCheckout() );
//        imgVisita.setPromotor( promotor.getClavePromotor() );
//        imgVisita.setIdVisita( Integer.parseInt( visita.getIdVisita() ) );
//        for( String itemFoto : fotos ){
//            imgVisita.setFoto( itemFoto );
//            guardarImagenResponse = this.jsonService.realizarPeticionGuardarImagen( imgVisita );
//        }
//        return guardarImagenResponse;
//    }

    private Response realizarCheckInEnProcesoCheckOut( Visita visita  ){
        Response response = null;
        CheckInTienda checkIn = armarCheckIn(visita);
        CheckInTiendaResponse CheckInResponse =  this.jsonService.realizarCheckinPost(checkIn);


        if ( !CheckInResponse.getHacerCheckInTiendaResult().isSeEjecutoConExito() ){
            Json.solicitarMsgError(CheckInResponse.getHacerCheckInTiendaResult(), Json.ERROR_JSON.CHECK_OUT);
            response =  CheckInResponse.getHacerCheckInTiendaResult();
            visita.setCheckInNotificado(RespuestaBinaria.NO);
            visita.setEstatusVisita(EstatusVisita.CHECK_OUT_REQUEST);
            this.actualizarVisitaActual();
        } else {
            visita.setFechaCheckIn(Util.getDateTimeFromMilis(new Date().getTime()));
            visita.setCheckInNotificado(RespuestaBinaria.SI);
            this.visitaDao.updateVisita(visita, Integer.parseInt(this.rutaActual.getIdRuta()));
            this.rutaActual = this.rutaService.refrescarRutaDesdeBase( this.rutaActual );

        }

        return response;
    }

    private CheckInTienda armarCheckIn( Visita visita ){
        Promotor promotor = PromotorServiceImpl.getSingleton().getPromotorActual();
        CheckInTienda checkIn = new CheckInTienda();

        LogUtil.printLog( CLASSNAME , "realizarCheckIn visita:" + visita + ", Promotor:" + promotor );

        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            visita.setFechaCheckIn( Util.getDateTimeFromMilis( new Date().getTime() ) )   ;
            visita.setEstatusVisita( EstatusVisita.CHECK_IN );
        }else{
            checkIn.setIdTienda( Integer.parseInt( visita.getTienda().getIdTienda() ) );
            checkIn.setIdVisita(Integer.parseInt(visita.getIdVisita()));
            checkIn.setClavePromotor( promotor.getClavePromotor() );
            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );
            checkIn.setFechaCreacion( Util.getDateTimeFromMilis( new Date().getTime() ) );

            Location locationActual = this.locationService.getLocation(); //Se recupera solo para pintar el toast
            checkIn.setLatitud( "" + this.locationService.getLatitude() );
            checkIn.setLongitud("" + this.locationService.getLongitude());

            visita.setEstatusVisita(EstatusVisita.CHECK_IN);

            LogUtil.printLog(CLASSNAME, "checkIn:" + checkIn);

        }
        return checkIn;
    }

    private void limpiarDatosEnMemoriaDeLaVisita( Visita visita) {
        visita.setRevisionFoto( new RevisionFoto[0]);
        visita.setFirmaGerente( new byte[0]);
        visita.setEncuestaPersonas( new EncuestaPersona[0]);
        Runtime.getRuntime().gc();
    }

    private void eliminarRegistrosEnTablasDeLaVisita( ){
        int idVisitaActual = Integer.parseInt( this.visitaActual.getIdVisita() );
        this.encuestaDao.deleteEncuestaByIdVisita( idVisitaActual );
        this.productoDao.deleteProductoByIdVisita( idVisitaActual );
        this.fotografiaDao.deleteFotoByIdVisita( idVisitaActual );
        //this.visitaDao.deleteVisitaById( idVisitaActual );
    }

    private VisitaTienda armarVisitaTienda(Visita visita) {
        VisitaTienda vt = new VisitaTienda();
        vt.setComentarios( visita.getComentarios() );
        vt.setIdTienda(Integer.parseInt(visita.getTienda().getIdTienda()));
        vt.setIdVisita( Integer.parseInt(visita.getIdVisita()) );
        vt.setNombreJefeDepartamento( visita.getGerente().getNombre() );
        vt.setFirmaJefeDepartamento(ViewUtil.obtenerStringBase64 (visita.getFirmaGerente() ) );
        
        List<EntrevistaEncuesta> entrevista = this.armaListaEntrevistaEncuesta( visita );
        vt.setEntrevistasEncuesta( entrevista );

        //Se deshabilita la carga de las fotos
        List<String> fotos = this.armarListFotosCapturadas( visita );
        vt.setFotosTienda( fotos );

        List<ProductoTienda> listaProductos = this.armarListaProductos( visita );
        vt.setProductosTienda( listaProductos );

        //INI MAMM motivo retiro
        vt.setIdMotivoRetiro( visita.getIdMotivoRetiro() );
        vt.setDescripcionMotivoRetiro( visita.getDescripcionMotivoRetiro() );
        //END MAMM motivo retiro

        return vt;
    }

    private List<ProductoTienda> armarListaProductos(Visita visita) {
        List<ProductoTienda> listaProductos = new ArrayList<ProductoTienda>();
        RevisionProducto[] revProductoArray = visita.getRevisionProductos();

        for( RevisionProducto itemRevProd : revProductoArray ){
            ProductoTienda pt = new ProductoTienda();
            pt.setCodigo( itemRevProd.getProducto().getCodigo() );
            pt.setDescripcion( itemRevProd.getProducto().getDescripcion() );
            pt.setPrecioBase( itemRevProd.getProducto().getPrecioBase() );
            pt.setExhibicionAdicional(itemRevProd.getExhibicionAdicional().isBoolRespuesta() );
            pt.setExistencia( itemRevProd.getExistencia() );
            pt.setNumeroFrente( itemRevProd.getNumeroFrente() );
            pt.setPrecioTienda( itemRevProd.getPrecioEnTienda());
            listaProductos.add( pt );
        }
        return listaProductos;
    }

    private List<String> armarListFotosCapturadas(Visita visita) {
        List<String> fotos = new ArrayList<String>();
        RevisionFoto[] revfotosArray = visita.getRevisionFoto();
        for( RevisionFoto itemFoto : revfotosArray ){
            fotos.add( ViewUtil.obtenerStringBase64( itemFoto.getFoto() ) );
        }
        return fotos;
    }



    private List<EntrevistaEncuesta> armaListaEntrevistaEncuesta(Visita visita ) {
        EncuestaPersona[] encuestaPersona = visita.getEncuestaPersonas();
        List<EntrevistaEncuesta> eeList = new ArrayList<EntrevistaEncuesta>();
        for(EncuestaPersona itemEncuestaPersona : encuestaPersona ){
            EntrevistaEncuesta ee = new EntrevistaEncuesta();
            ee.setIdEncuesta( Integer.parseInt( visita.getIdEncuesta() )  );
            PreguntaRespuesta[] pregRespArray = itemEncuestaPersona.getPreguntaRespuesta();
            List<DetalleRespuesta> detRespList = new ArrayList<DetalleRespuesta>();
            for( PreguntaRespuesta itemPregResp : pregRespArray){
                DetalleRespuesta detResp = new DetalleRespuesta();
                detResp.setIdPregunta( Integer.parseInt( itemPregResp.getPregunta().getIdPregunta() ) );
                detResp.setIdRespuestaSeleccionada(Integer.parseInt(itemPregResp.getRespuestaElegida().getIdRespuesta()));
                detRespList.add( detResp );
            }
            ee.setDetalleRespuestas( detRespList );
            eeList.add( ee );
        }
        return eeList;
    }


    public Visita recuperarVisitaPorIdVisita(String idVisita){
        Visita[] visitas = this.rutaActual.getVisitas();
        Visita visitaBuscada = null;
        for( Visita item : visitas ){
            if(item.getIdVisita().equals( idVisita )  ){
                visitaBuscada = item;
            }
        }
        LogUtil.printLog( CLASSNAME , "recuperarVisitaPorIdVisita: idVisita:" + idVisita + ", visita:" +  visitaBuscada );
        return visitaBuscada;
    }

    public Set<Cadena> getCadenasEnRuta(){
        return this.cadenasAplicadasEnRuta;
    }

//    public List<MotivoRetiro> getCatalogoMotivoRetiro(){
//        return this.catalogoMotivoRetiro;
//    }


    public void agregarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual){
        int  idVisita = Integer.parseInt(visitaActual.getIdVisita()) ;
        this.productoDao.insertProducto( revisionProductoActual , idVisita );
        visitaActual.setRevisionProductos( this.productoDao.getProductosByIdVisita( idVisita ) );
    }

    @Override
    public Visita getVisitaPorPosicionEnLista(int posicion) {
        Visita visitaMemoria = this.visitaService.getRutaActual().getVisitas()[ posicion ];
        visitaActual =  this.visitaDao.getVisitaById( Integer.parseInt( visitaMemoria.getIdVisita() ) );
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
        visitaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisita( idVisita ) );
        visitaActual.setEncuestaPersonas(this.encuestaDao.getEncuestasByIdVisita(idVisita));
        return visitaActual;
    }


    public Visita getVisitaActual() {
        return visitaActual;
    }


    @Override
    public void actualizarVisitaActual( ){
        int idRutaActual = Integer.parseInt( rutaActual.getIdRuta() );
        this.visitaDao.updateVisita( visitaActual , idRutaActual );
    }


    @Override
    public void eliminarRevisionProductoDeVisita(RevisionProducto revisionProductoActual) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.productoDao.deleteProductoById( revisionProductoActual.getProducto().getCodigo() , idVisita );
        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
    }

    public void actualizarRevisionProductoAVisitaActual( RevisionProducto revisionProductoActual ){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.productoDao.updateProducto( revisionProductoActual , idVisita );
        visitaActual.setRevisionProductos(this.productoDao.getProductosByIdVisita(idVisita)) ;
    }

    public void guardarRevisionFoto(RevisionFoto revFoto){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.fotografiaDao.insertFotografia( revFoto , idVisita );
        visitaActual.setRevisionFoto(this.fotografiaDao.getRevisionFotoByIdVisita( idVisita));
    }

    public void guardarEncuesta( EncuestaPersona encuestaPersona){
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.encuestaDao.insertEncuesta( encuestaPersona , idVisita );
        this.visitaActual.setEncuestaPersonas( this.encuestaDao.getEncuestasByIdVisita( idVisita ) );
    }


    @Override
    public void eliminarRevisionFotografia(  String idRevisionFoto  ) {
        int idVisita = Integer.parseInt(visitaActual.getIdVisita());
        this.fotografiaDao.deleteFotoById( idRevisionFoto  , idVisita );
        visitaActual.setRevisionFoto( this.fotografiaDao.getRevisionFotoByIdVisita(idVisita) ); ;
    }

    private void testDaoMock(Ruta ruta) {
        this.rutaDao.insertRuta( ruta );
        Ruta rutaTemp = this.rutaDao.getRutaById(Integer.parseInt(ruta.getIdRuta()));
        LogUtil.printLog( CLASSNAME , "Se recupera ruta de base:" + rutaTemp );
        Visita[] visitas = ruta.getVisitas();
        for(Visita itemVisita : visitas ){
            this.visitaDao.insertVisita( itemVisita , Integer.parseInt( ruta.getIdRuta() ) );
        }
        Visita[] visitasArray = this.visitaDao.getVisitasByIdRuta( Integer.parseInt( ruta.getIdRuta() ) );
        LogUtil.printLog( CLASSNAME , "Se recupera visitasArray de base:" + visitasArray );

        Visita currentVisita = visitas[1];
        RevisionProducto[] rp = currentVisita.getRevisionProductos();
        for( RevisionProducto itemrp : rp ){
            this.productoDao.insertProducto( itemrp , Integer.parseInt( currentVisita.getIdVisita() ) );
        }
        RevisionProducto[] rpArray = this.productoDao.getProductosByIdVisita( Integer.parseInt( currentVisita.getIdVisita() ) );
        currentVisita.setRevisionProductos( rpArray );
        LogUtil.printLog( CLASSNAME , "Se recupera RevisionProducto de base:" + rpArray );

        RevisionFoto[] rf = currentVisita.getRevisionFoto();
        for( RevisionFoto itemRF : rf ){
            this.fotografiaDao.insertFotografia(itemRF, Integer.parseInt(currentVisita.getIdVisita()));
        }
        RevisionFoto[] rfArray = this.fotografiaDao.getRevisionFotoByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
        currentVisita.setRevisionFoto(rfArray);
        LogUtil.printLog( CLASSNAME , "Se recupera RevisionFoto de base:" + rfArray );

        EncuestaPersona[] ep = currentVisita.getEncuestaPersonas();
        for( EncuestaPersona itemEP : ep ){
            this.encuestaDao.insertEncuesta(itemEP, Integer.parseInt(currentVisita.getIdVisita()));
        }
        EncuestaPersona[] epArray = this.encuestaDao.getEncuestasByIdVisita(Integer.parseInt(currentVisita.getIdVisita()));
        LogUtil.printLog( CLASSNAME , "Se recupera EncuestaPersona de base:" + epArray );

        //*** Prueba Catálogos Productos
        RevisionProducto[] productosMock = currentVisita.getRevisionProductos();
        int idCadena = Integer.parseInt( currentVisita.getCadena().getIdCadena() );
        for( RevisionProducto itemrp : productosMock ){
            this.catalogosDao.insertarCatalogoProductos( itemrp.getProducto(), idCadena );
            LogUtil.printLog(CLASSNAME, "Productos:" + itemrp + " idCadena " + idCadena );
        }
        List<Producto> productosTestMock = this.catalogosDao.recuperarCatalogoProductosPorIdCadena( idCadena );
        LogUtil.printLog(CLASSNAME, " idTienda " + productosTestMock);

        List<Integer> tiendasTestMock = this.catalogosDao.recuperarListaDeIdCadenasEnCatalogoDeProductos();
        LogUtil.printLog(CLASSNAME, " Tiendas " + tiendasTestMock);

        long productosDeleteMock = this.catalogosDao.eliminarCatalogoProductos();
        LogUtil.printLog( CLASSNAME , "Se eliminan Productos de Catálogo:" + productosDeleteMock );

        //*** Prueba Catálogos Preguntas
        String idEncuestaMock = "1000";
        Encuesta encuesta = this.catalogosService.recuperarEncuestaPorId(idEncuestaMock);
        Pregunta[] preguntas = encuesta.getPreguntasEncuesta();

        for( Pregunta itempregunta : preguntas ){
                this.catalogosDao.insertarCatalogoPreguntas(itempregunta, Integer.parseInt( idEncuestaMock ) );
                LogUtil.printLog(CLASSNAME, "Preguntas:" + itempregunta + " idEncuesta " + idEncuestaMock);
                Respuesta[] respuestas = itempregunta.getRespuestasPregunta();
                for( Respuesta itemRespuesta : respuestas ){
                    this.catalogosDao.insertarCatalogoRespuesta( itemRespuesta, Integer.parseInt( itempregunta.getIdPregunta() ) );
                    LogUtil.printLog(CLASSNAME, "Respuestas:" + itemRespuesta + " idPregunta " + itempregunta.getIdPregunta() );
                }

        }
        List<Pregunta> getPreguntas = this.catalogosDao.recuperarCatalogoPreguntasPorIdEncuesta( Integer.parseInt( idEncuestaMock ) );
        LogUtil.printLog(CLASSNAME, "Preguntas:" + getPreguntas + " id " + idEncuestaMock);
        for( Pregunta itemPregunta : getPreguntas){
            List<Respuesta> respuestas = this.catalogosDao.recuperarCatalogoRespuestaPorIdPregunta(Integer.parseInt(itemPregunta.getIdPregunta()));
            itemPregunta.setRespuestasPregunta( respuestas.toArray( new Respuesta[0] ) );
        }
        LogUtil.printLog(CLASSNAME, "Preguntas completas:" + getPreguntas + " id " + idEncuestaMock);

        this.catalogosDao.eliminarCatalogoPreguntasRespuestas();
        List<Pregunta> getPreguntasDespueseLimpiar = this.catalogosDao.recuperarCatalogoPreguntasPorIdEncuesta( Integer.parseInt( idEncuestaMock ) );

        //*** Pruebas Catálogo Motivos retiro

        //MotivoUbicado
//        List<MotivoRetiro> motivoRetiroTest = this.visitaService.getCatalogoMotivoRetiro();
//
//        for(MotivoRetiro itemMotRet : motivoRetiroTest ){
//            this.catalogosDao.insertarMotivosRetiro(itemMotRet);
//        }
//
//        List<MotivoRetiro> motivoRetiroGetTest = this.catalogosDao.recuperarTodosLosMotivosDeRetiro();
//        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Retiro de Catálogo:" + motivoRetiroGetTest );
//
//        long motivoRetiroResultTest = this.catalogosDao.eliminarTodosLosMotivosDeRetiro();
//        LogUtil.printLog( CLASSNAME , "Se eliminan Motivo Descartado de Catálogo:" + motivoRetiroResultTest );
//
//
//        List<MotivoRetiro> motivoRetiroDeleteTest = this.catalogosDao.recuperarTodosLosMotivosDeRetiro();
//        LogUtil.printLog( CLASSNAME , "Se recupera Motivo Retiro de Catálogo:" + motivoRetiroDeleteTest );

    }

    private void testDaoTablaErrores() {
        NotificacionError notificacionError = this.crearMensajeErrorMock();
        LogUtil.printLog( CLASSNAME , "Antes notificacionError:" + notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        LogUtil.printLog( CLASSNAME , "Despues notificacionError:" + notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        this.notificacionErroresDao.insertarErrores( notificacionError );
        List<NotificacionError> erroresNoEnviados =  this.notificacionErroresDao.recuperarErroresNoEnviados();
        LogUtil.printLog( CLASSNAME , "erroresNoEnviados size:" + erroresNoEnviados.size() );

        List<Integer> idErroresActualizar = new ArrayList<Integer>();
        for(NotificacionError itemError : erroresNoEnviados ){
            idErroresActualizar.add( itemError.getIdError() );
        }

        LogUtil.printLog( CLASSNAME , "Se actualizaran los ids:" + idErroresActualizar );
        this.notificacionErroresDao.actualizarErroresEnviados( idErroresActualizar );

        List<NotificacionError> erroresNoEnviadosNuevos =  this.notificacionErroresDao.recuperarErroresNoEnviados();
        LogUtil.printLog( CLASSNAME , "erroresNoEnviadosNuevos size:" + erroresNoEnviadosNuevos.size() );




    }

    private NotificacionError crearMensajeErrorMock() {
        NotificacionError notificacionError = new NotificacionError();
        notificacionError.setAplicacion( Const.Aplicacion.PRO.name() );
        notificacionError.setVersion( Const.VersionAPK.getUltimaVersion().version );
        notificacionError.setFechaHora( Util.getDateTimeFromMilis( new Date().getTime() ) );
        notificacionError.setUsuario( "Usuario.prueba" );
        notificacionError.setTraza( "Mensaje Largo \n Muy largo , bien pinche largo" );
        return notificacionError;
    }

    public void recuperarRutaDesdeBase( String user , String pass ){
        Ruta rutaEnBase = this.rutaDao.getRutaPorClaveYPasswordDePromotor( user, pass );
        this.rutaActual = this.rutaService.refrescarRutaDesdeBase( rutaEnBase );
    }


}
