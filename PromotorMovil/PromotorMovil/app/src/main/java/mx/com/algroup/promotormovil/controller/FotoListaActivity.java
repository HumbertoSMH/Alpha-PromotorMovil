package mx.com.algroup.promotormovil.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.controller.Adapter.RevisionFotoListAdapter;
import mx.com.algroup.promotormovil.services.VisitaService;
import mx.com.algroup.promotormovil.services.impl.VisitaServiceImpl;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.CustomUncaughtExceptionHandler;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.Util;
import mx.com.algroup.promotormovil.utils.ViewUtil;

import mx.com.algroup.promotormovil.utils.storage.AlbumStorageDirFactory;
import mx.com.algroup.promotormovil.utils.storage.BaseAlbumDirFactory;
import mx.com.algroup.promotormovil.utils.storage.FroyoAlbumDirFactory;

public class FotoListaActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    private final static String CLASSNAME = FotoListaActivity.class.getSimpleName();
   //Service
    private VisitaService visitaService;

    //Business
    private Visita visita;


    //UI Elements
    private TextView tiendaTexView;
    private ListView fotografiasListView;
    private Button capturarFotoButton;
    private Button cancelarFotoButton;
    private Button guardarFotoButton;
    private int posicionFotoAuxiliar = -1;
    private String fotoAEliminarId = null;

    //Elementos para la foto
    private static final int ACTION_TAKE_PHOTO_B = 1;  //Foto GRANDE
    private static final int ACTION_TAKE_PHOTO_S = 2;  //foto pequeña

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView;
    private Bitmap mImageBitmap;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_PROM_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler(this));
        this.visitaService = VisitaServiceImpl.getSingleton();

        Intent intent = this.getIntent();
        //INI MAMM Se remmplaza la busqueda en memoria de la visita por el guardado en base de datos
        //String idVisita = intent.getStringExtra(Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() );
        //this.visita = this.visitaService.recuperarVisitaPorIdVisita( idVisita );
        this.visita = this.visitaService.getVisitaActual();
        //END MAMM

        this.prepararPantalla();
    }

    private void prepararPantalla() {
        if( this.visita == null){
            ViewUtil.redireccionarALogin(this);
            return;
        }
        setContentView(R.layout.foto_lista_layout);
        this.tiendaTexView = (TextView)findViewById( R.id.tiendaTexView );
        this.tiendaTexView.setText( this.visita.getTienda().getNombreTienda() );
        this.cancelarFotoButton = (Button)findViewById( R.id.cancelarFotoButton );
        this.guardarFotoButton = (Button)findViewById( R.id.guardarFotoButton );
        this.capturarFotoButton = (Button) findViewById(R.id.capturarFotoButton);

        this.fotografiasListView = (ListView)findViewById( R.id.fotografiasListView );
        this.fotografiasListView.setAdapter( new RevisionFotoListAdapter( this.visita.getRevisionFoto() , this , this.visita ) );
        this.fotografiasListView.setOnItemLongClickListener( this );


        this.cancelarFotoButton.setOnClickListener( this );
        this.guardarFotoButton.setOnClickListener( this );
        this.capturarFotoButton.setOnClickListener( this );
        this.prepararImagenYStorage();
    }

    private void prepararImagenYStorage() {
        //mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageBitmap = null;

        //this.capturarFotoButton = (Button) findViewById(R.id.capturarFotoButton);
//        setBtnListenerOrDisable(
//                this.capturarFotoButton,
//                mTakePicSOnClickListener,
//                MediaStore.ACTION_IMAGE_CAPTURE
//        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foto_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if( v == cancelarFotoButton ){
            this.finish();
            return;
        }else
        if( v == this.capturarFotoButton ){
            //dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);  //Genera fotografia tamaño chico
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

            return;
        }else
        if( v == guardarFotoButton ){
            if( this.visita.getRevisionFoto().length < Const.CANTIDAD_MINIMA_FOTOS ){
                String message = this.getResources().getString( R.string.minimo_fotos_no_alcanzado );
                ViewUtil.mostrarMensajeRapido( this , message );
                return;
            }
            intent = new Intent( this, ComentariosActivity.class);
        }
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        startActivity( intent );
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return  "promotoria_fotos" ;
    }

    //TODO MAMM Volver metodo utilitario
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //BLOQUE 20150729
        switch(actionCode) {
            case ACTION_TAKE_PHOTO_B:
            File f = null;

            try {
                f = setUpPhotoFile();
                mCurrentPhotoPath = f.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            } catch (IOException e) {
                e.printStackTrace();
                f = null;
                mCurrentPhotoPath = null;
            }
            break;

            default:
            break;
        } // switch
        //END BLOQUE

        startActivityForResult(takePictureIntent, actionCode);
    }


    private void guardaFotoEnVisita(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmapCapturado = (Bitmap) extras.get("data");
        byte[] imagenbyte = Util.getArrayByteDeBitMap( mImageBitmapCapturado );
        RevisionFoto revFoto = this.creaFotoRevision(imagenbyte);
        this.guardarRevisionFotoEnVisita( revFoto );
    }

    private RevisionFoto creaFotoRevision(byte[] imagenbyte) {
        RevisionFoto revFoto = new RevisionFoto();
        revFoto.setFoto( imagenbyte );
        revFoto.setFechaCaptura( Util.getDateTimeFromMilis( new Date().getTime() ));
        revFoto.setIdFoto( Util.getDateTimeFromMilis_hastaSegundos( new Date().getTime() ) );
        return revFoto;
    }

    private void guardarRevisionFotoEnVisita( RevisionFoto revFoto ) {
        //INI MAMM Se guarda por base de datos
        this.visitaService.guardarRevisionFoto( revFoto );
//        //Ajustar el Arreglo de Revision de productos
//        RevisionFoto[] revFotos = this.visita.getRevisionFoto();
//        revFotos = Arrays.copyOf(revFotos, revFotos.length + 1);
//        revFotos[ revFotos.length -1 ] = revFoto;
//        this.visita.setRevisionFoto(revFotos);
        //END MMM
    }

    Button.OnClickListener mTakePicSOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
                }
            };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {   //Procesar fotografia GRANDES
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
//                    RevisionFotoListAdapter revisionFotoListAdapter = (RevisionFotoListAdapter) (this.fotografiasListView.getAdapter());
//                    revisionFotoListAdapter.setRevisionFotos( this.visita.getRevisionFoto() );
//                    revisionFotoListAdapter.notifyDataSetChanged();
                }
                break;
            } // ACTION_TAKE_PHOTO_B
            case ACTION_TAKE_PHOTO_S: { //Procesar fotografia peuqeñas
                if (resultCode == RESULT_OK) {
                    guardaFotoEnVisita(data);
//                    RevisionFotoListAdapter revisionFotoListAdapter = (RevisionFotoListAdapter) (this.fotografiasListView.getAdapter());
//                    revisionFotoListAdapter.setRevisionFotos( this.visita.getRevisionFoto() );
//                    revisionFotoListAdapter.notifyDataSetChanged();

                }
                break;
            } // ACTION_TAKE_PHOTO_S
        } // switch
        RevisionFotoListAdapter revisionFotoListAdapter = (RevisionFotoListAdapter) (this.fotografiasListView.getAdapter());
        revisionFotoListAdapter.setRevisionFotos( this.visita.getRevisionFoto() );
        revisionFotoListAdapter.notifyDataSetChanged();
    }

    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
        //mImageView.setImageBitmap(mImageBitmap);
        //mImageView.setVisibility(
        //        savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ?
        //                ImageView.VISIBLE : ImageView.INVISIBLE
        //);
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @param action The Intent action to check for availability.
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        this.posicionFotoAuxiliar = position;
        fotoAEliminarId = view.getTag().toString();
        LogUtil.printLog( CLASSNAME , "Se recupera el ID a Eliminar:" +  fotoAEliminarId );
        ViewUtil.mostrarAlertaAceptarCancelar( "¿Eliminar esta fotografía?" , this , this );
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        LogUtil.printLog(CLASSNAME, "AlertClicked: dialog:" + dialog + " , wich:" + which + ", posicion seleccionada:" + posicionFotoAuxiliar);
        if( which == AlertDialog.BUTTON_POSITIVE ){
            //INI MAMM Se procede a eliminar por base de datos
            this.visitaService.eliminarRevisionFotografia( fotoAEliminarId );
            ((RevisionFotoListAdapter)this.fotografiasListView.getAdapter()).setRevisionFotos( this.visita.getRevisionFoto() );
            ((RevisionFotoListAdapter)this.fotografiasListView.getAdapter()).notifyDataSetChanged();

//            RevisionFoto[] fotos = this.visita.getRevisionFoto();
//            int tamano = fotos.length;
//            List<RevisionFoto> fotosListAux = new ArrayList<RevisionFoto>();
//            for( int j = 0 ; j < tamano ; j++){
//                if( j != this.posicionFotoAuxiliar ){
//                    fotosListAux.add( fotos[j] );
//                }
//            }
//            RevisionFoto[] fotosActuales  = fotosListAux.toArray( new RevisionFoto[0] );
//            this.visita.setRevisionFoto( fotosActuales );
//            ((RevisionFotoListAdapter)this.fotografiasListView.getAdapter()).setRevisionFotos( fotosActuales );
//            ((RevisionFotoListAdapter)this.fotografiasListView.getAdapter()).notifyDataSetChanged();
            //END MAMM

            ViewUtil.mostrarMensajeRapido(  this , "La fotografia se ha eliminado" );
            System.gc();
        }else {
            //No se realiza nada
        }
    }




    /*
    * BLOQUE DE METODOS PARA SOPORTAR IMAGENES GRANDES
    * */


    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            Bitmap imagenCapturada = setPic();
            guardaFotoGrandeEnVisita( imagenCapturada );
            //galleryAddPic();
            mCurrentPhotoPath = null;
        }
    }

    private Bitmap setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */
        int targetH = 0;   //mImageView.getHeight();
        int targetW = 0;     //mImageView.getWidth();


		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        if( photoH > photoW ){
        /*Se definen las dimensiones de la imagen que se desea generar*/
             targetH = Const.MedidasReduccionImagen.PEQUENA_PORTRAIT.heigh;     // 640;
             targetW = Const.MedidasReduccionImagen.PEQUENA_PORTRAIT.width;     //480;
        }else{
             targetH = Const.MedidasReduccionImagen.PEQUENA_LANDSCAPE.heigh;    //480;
             targetW = Const.MedidasReduccionImagen.PEQUENA_LANDSCAPE.width;    //640;
        }

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 0;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        return bitmap;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void guardaFotoGrandeEnVisita( Bitmap mImageBitmapCapturado) {
        LogUtil.printLog( CLASSNAME , "guardaFotoGrandeEnVisita" );
        byte[] imagenbyte = Util.getArrayByteDeBitMap( mImageBitmapCapturado );
        RevisionFoto revFoto = this.creaFotoRevision(imagenbyte);
        this.guardarRevisionFotoEnVisita( revFoto );
    }


}
