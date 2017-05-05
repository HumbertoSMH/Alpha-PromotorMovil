package mx.com.algroup.promotormovil.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import mx.com.algroup.promotormovil.R;

/**
 * Created by devmac03 on 22/04/15.
 */
public class UtilsMock {

    public static byte[] getImageMock( Context context ){
        Drawable imageMock = context.getResources().getDrawable(R.drawable.image_pequena_mock);
        Bitmap bitmap = ((BitmapDrawable)imageMock).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
}
