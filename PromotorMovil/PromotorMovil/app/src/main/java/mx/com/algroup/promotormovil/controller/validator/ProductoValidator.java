package mx.com.algroup.promotormovil.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import java.text.NumberFormat;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.ValidadorGenerico;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;

/**
 * Created by MAMM on 17/04/15.
 */
public class ProductoValidator {

    private static ProductoValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private ProductoValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static ProductoValidator getSingleton( ){
        if( singleton == null ){
            singleton = new ProductoValidator(PromotorMovilApp.getPromotorMovilApp());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarExistencia( EditText existenciaEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String existenciaText = existenciaEditText.getText().toString().trim();
        if( existenciaText.length() == 0){
            validator.setMensaje( "Favor de indicar la existencia" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isOnlyIntegerPositive(existenciaText) == false ){
            validator.setMensaje( "Valor inválido" );
            validator.setCorrecto( false );
            return validator;
        }

        return validator;
    }


    public ValidadorUIMensajes validarPrecio( EditText passwordEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String precioText = passwordEditText.getText().toString().trim();
        if( precioText.length() == 0){
            validator.setMensaje( "El precio no puede estar vacio" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isDecimal(precioText) == false ){
            validator.setMensaje( "El precio no es válido" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

    public ValidadorUIMensajes validarNumeroFrente( EditText numeroFrenteEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String numeroFrenteText = numeroFrenteEditText.getText().toString().trim();
        if( numeroFrenteText.length() == 0){
            validator.setMensaje( "Favor de indicar el numero de frente" );
            validator.setCorrecto( false );
            return validator;
        }else
        if( StringUtils.isDecimal( numeroFrenteText ) == false ){
            validator.setMensaje( "Valor inválido" );
            validator.setCorrecto( false );
            return validator;
        }

        return validator;
    }

    public ValidadorUIMensajes validarNombreProducto( EditText nombreProductoEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String nombreText = nombreProductoEditText.getText().toString().trim();
        if( nombreText.length() == 0){
            validator.setMensaje( "Favor de indicar el nombre del producto" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }
}
