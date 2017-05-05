package mx.com.algroup.promotormovil.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.utils.StringUtils;
import mx.com.algroup.promotormovil.utils.ValidadorGenerico;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;

/**
 * Created by devmac03 on 22/04/15.
 */
public class ComentariosValidator {
    private static ComentariosValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;

    private ComentariosValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static ComentariosValidator getSingleton( ){
        if( singleton == null ){
            singleton = new ComentariosValidator(PromotorMovilApp.getPromotorMovilApp());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarComentario( EditText comentarioEditText ){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        String existenciaText = comentarioEditText.getText().toString().trim();
        if( existenciaText.length() == 0){
            validator.setMensaje( "Favor de indicar el comentario" );
            validator.setCorrecto( false );
            return validator;
        }
        return validator;
    }

}
