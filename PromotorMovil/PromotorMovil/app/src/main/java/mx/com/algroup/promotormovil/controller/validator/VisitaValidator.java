package mx.com.algroup.promotormovil.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.utils.ValidadorGenerico;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;

/**
 * Created by MAMM on 17/04/15.
 */
public class VisitaValidator {

    private static VisitaValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private VisitaValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static VisitaValidator getSingleton( ){
        if( singleton == null ){
            singleton = new VisitaValidator(PromotorMovilApp.getPromotorMovilApp());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarNombreJefe( EditText nombreJefeEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String nombreJefe = nombreJefeEditText.getText().toString();
        if( nombreJefe.trim().length() == 0){
            validator.setMensaje( "Capture el nombre" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

}
