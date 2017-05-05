package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Producto;
import mx.com.algroup.promotormovil.business.rest.Get.LoginResponse;
import mx.com.algroup.promotormovil.business.rest.Get.ProductosCadenaResponse;
import mx.com.algroup.promotormovil.business.utils.Json;
import mx.com.algroup.promotormovil.services.JsonService;
import mx.com.algroup.promotormovil.services.ProductoService;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by devmac03 on 21/04/15.
 */
public class ProductoServiceImpl implements ProductoService {
    private static final String CLASSNAME = ProductoServiceImpl.class.getSimpleName();


    private static ProductoService productoService;
    private static JsonService jsonService;
    private Context context;
    Map< Cadena , Map<String , Producto> > mapaProductosPorCadena;

    private ProductoServiceImpl( Context context ){
        this.context = context;
        this.jsonService = JsonServiceImpl.getSingleton();
        this.mapaProductosPorCadena = new HashMap< Cadena , Map<String , Producto> >();

    }

    public static ProductoService getSingleton(){
        if( productoService == null){
            productoService = new ProductoServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return productoService;
    }

//    @Override
//    public boolean esValidoProductoEnCadena(String codigoProducto, Cadena cadena) {
//        boolean esValido = false;
//        LogUtil.printLog(CLASSNAME, "esValidoProductoEnCadenao: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            if( codigoProducto.contains( "1" )){
//                esValido = true;
//            }else{
//                esValido = false;
//            }
//        }else{
//            Producto productoEncontrado= this.mapaProductosPorCadena.get( cadena ).get(codigoProducto);
//            if( productoEncontrado != null ){
//                esValido = true;
//            }
//        }
//        return esValido;
//    }

//    @Override
//    public Producto recuperarProducto(String codigoProducto, Cadena cadena) {
//        LogUtil.printLog(CLASSNAME, "recuperarProducto: cadena:" + cadena + " , codigoProducto:" + codigoProducto);
//        Producto productoEncontrado = null;
//        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
//            productoEncontrado = new Producto();
//            productoEncontrado.setPrecioBase(1050);
//            productoEncontrado.setDescripcion( "Producto básico " + codigoProducto );
//            productoEncontrado.setCodigo(codigoProducto);
//        }else{
//            productoEncontrado= this.mapaProductosPorCadena.get( cadena ).get(codigoProducto);
//        }
//
//        return productoEncontrado;
//    }

    @Override
    public  void actualizarCatalogoProductos(Set<Cadena> cadenasEnRuta) {
        LogUtil.printLog(CLASSNAME, "actualizarCatalogoProductos ..");
        this.mapaProductosPorCadena = new HashMap<Cadena , Map<String , Producto> >();
        for( Cadena itemCadena : cadenasEnRuta){
            ProductosCadenaResponse response = jsonService.realizarPeticionProductosCadena(Integer.parseInt(itemCadena.getIdCadena()));
            if( !response.isSeEjecutoConExito() ){
                Json.solicitarMsgError(response, Json.ERROR_JSON.LOGIN);
                return;
            }else{
                List<Producto> productoResponse = response.getProductos();
                Map< String , Producto > mapaProducto = new HashMap< String , Producto >();
                for( Producto itemProducto :productoResponse ){
                    mapaProducto.put( itemProducto.getCodigo() , itemProducto );
                }
                this.mapaProductosPorCadena.put( itemCadena , mapaProducto );
                LogUtil.printLog(CLASSNAME, "Elemento en Catalog de producto: cadena:" + itemCadena + " , productos:" + productoResponse);
            }

        }
        LogUtil.printLog(CLASSNAME, "finaliza actualizarCatalogoProductos ");
    }

}
