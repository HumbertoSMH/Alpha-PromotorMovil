package mx.com.algroup.promotormovil.services;

import mx.com.algroup.promotormovil.business.rest.Get.CatalogoMotivoRetiroResponse;
import mx.com.algroup.promotormovil.business.rest.Get.CatalogoProductosResponse;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestaVisitaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.EncuestasRutaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.LoginResponse;
import mx.com.algroup.promotormovil.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovil.business.rest.Get.RutaPromotorResponse;
import mx.com.algroup.promotormovil.business.rest.Post.CheckInTienda;
import mx.com.algroup.promotormovil.business.rest.Post.CheckInTiendaResponse;
import mx.com.algroup.promotormovil.business.rest.Post.CheckOutTienda;
import mx.com.algroup.promotormovil.business.rest.Post.CheckOutTiendaResponse;
import mx.com.algroup.promotormovil.business.rest.Post.GuardarErroresResponse;
import mx.com.algroup.promotormovil.business.rest.Post.GuardarImagenResponse;
import mx.com.algroup.promotormovil.business.rest.Post.ImagenVisita;
import mx.com.algroup.promotormovil.business.rest.Post.NotificacionErrorRest;

/**
 * Created by MAMM on 28/04/2015.
 */
public interface JsonService {

    public CatalogoProductosResponse realizarPeticionCatalogoProductos();
    public ProductosCadenaResponse realizarPeticionProductosCadena(int idCadena);
    public RutaPromotorResponse realizarPeticionRutaPromotor(String usuario);
    public EncuestaVisitaResponse realizarPeticionEncuestaVisita(int idVisita);
    public EncuestasRutaResponse realizarPeticionEncuestasRuta(int idRuta);
    public LoginResponse realizarPeticionLoginGet(String usuario, String password);
    public CatalogoMotivoRetiroResponse realizarPeticionCatalogoMotivoRetiro();
    public CheckInTiendaResponse realizarCheckinPost(CheckInTienda checkInTienda);
    public CheckOutTiendaResponse realizarCheckOutPost(CheckOutTienda checkOutTienda);

    public GuardarImagenResponse realizarPeticionGuardarImagen( ImagenVisita imagenVisita );



    //AJUSTE 20150811
    //Se agrega notificación de errores al Servicio Web
    public GuardarErroresResponse realizarPeticionGuardarErrores( NotificacionErrorRest notificacionErrorRest) ;

}
