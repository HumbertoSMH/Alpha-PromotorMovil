package mx.com.algroup.promotormovil.controller.validator;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.Spinner;

import mx.com.algroup.promotormovil.PromotorMovilApp;
import mx.com.algroup.promotormovil.business.utils.MotivoRetiro;
import mx.com.algroup.promotormovil.services.impl.CatalogosServiceImpl;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.ValidadorGenerico;
import mx.com.algroup.promotormovil.utils.ValidadorUIMensajes;

/**
 * Created by devmac03 on 22/04/15.
 */
public class CheckOutValidator {
    private static CheckOutValidator singleton;
    private static ValidadorGenerico validadorGenerico;

    private Resources resources;
    private Context mContext;



    private CheckOutValidator(Context context) {
        resources = context.getResources();
        mContext = context;
        validadorGenerico = ValidadorGenerico.getSingleton( context );
    }

    public static CheckOutValidator getSingleton( ){
        if( singleton == null ){
            singleton = new CheckOutValidator(PromotorMovilApp.getPromotorMovilApp());
        }
        return singleton;
    }

    public ValidadorUIMensajes validarMotivoCheckOut( Spinner motivosCheckOutSpinner , EditText motivoRetiroEditText){
        ValidadorUIMensajes validator = new ValidadorUIMensajes();
        int index = motivosCheckOutSpinner.getSelectedItemPosition();
        if( index == -1 ){
            validator.setCorrecto( false );
            validator.setMensaje("Seleccione el motivo por el que se retira");
        }
        else{
            MotivoRetiro motRet = CatalogosServiceImpl.getSingleton().getCatalogoMotivoRetiro().get( index );
            if( motRet.getIdMotivoRetiro() == Const.ID_MOTIVO_RETIRO_OTRO &&
                    motivoRetiroEditText.getText().toString().trim().length() == 0 ){
                validator.setCorrecto( false );
                validator.setMensaje("Favor de escribir el motivo del retiro");
            }

        }

      return validator;
    }

}
