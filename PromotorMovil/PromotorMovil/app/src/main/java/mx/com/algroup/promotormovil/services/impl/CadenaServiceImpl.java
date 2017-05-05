package mx.com.algroup.promotormovil.services.impl;

import android.content.Context;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.Cadena;
import mx.com.algroup.promotormovil.business.Tienda;
import mx.com.algroup.promotormovil.services.CadenaService;
import mx.com.algroup.promotormovil.utils.Const;

/**
 * Created by devmac03 on 21/04/15.
 */
public class CadenaServiceImpl implements CadenaService {
    private static final String CLASSNAME = CadenaServiceImpl.class.getSimpleName();


    private static CadenaService cadenaService;
    private Context context;

    private CadenaServiceImpl( Context context ){
        this.context = context;
    }

    public static CadenaService getSingleton(){
        if( cadenaService == null){
            cadenaService = new CadenaServiceImpl( PromotorMovilApp.getPromotorMovilApp() );
        }
        return cadenaService;
    }


    @Override
    public Cadena recuperarCadenaApartirDeTienda(Tienda tienda) {
        Cadena cadena = null;
        if( Const.Enviroment.currentEnviroment == Const.Enviroment.MOCK ){
            cadena = new Cadena();
            cadena.setIdCadena( "Cad_01" );
            cadena.setNombreCadena( "Grupo Comercial Mexicana" );
        }else{
            throw new RuntimeException( CLASSNAME + " Pendiente de implementacion" );
        }
        return cadena;
    }

    @Override
    public Cadena recuperarCadenaPorIdCadena(String idCadena) {
        //TOD MAMM no se utilizo el servicio de cadena
        return null;
    }
}
