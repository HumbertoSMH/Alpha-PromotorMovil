package mx.com.algroup.promotormovil.controller.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.RevisionFoto;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by MAMM on 20/04/15.
 */
public class RevisionFotoListAdapter extends BaseAdapter {
    private static final String CLASSNAME = RevisionFotoListAdapter.class.getSimpleName();

    private RevisionFoto[] revisionFotos;
    private Visita visita;
    private Context context;
    private View[] cells;


    public RevisionFotoListAdapter(RevisionFoto[] revisionFotos, Context context , Visita visita ) {
        LogUtil.printLog( CLASSNAME , ".RevisionFotoListAdapter() revisionFotos:" + revisionFotos );
        this.revisionFotos = revisionFotos;
        this.cells = new View[ this.revisionFotos.length ];
        this.visita = visita;
        this.context = context;
    }

    @Override
    public int getCount() {
        return revisionFotos.length;
    }

    @Override
    public Object getItem(int position) {
        return revisionFotos[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        View rowView = null;
        if( cells[position] != null ){
            rowView = cells[position];
        }else{
            LogUtil.printLog( CLASSNAME , ".create position:" + position );
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE );

            rowView = inflater.inflate( R.layout.contenedor_foto_lista_layout , parent,
                    false );
            RevisionFoto itemRevisionFoto = this.revisionFotos[ position ];
            ImageView fotoImageView = (ImageView) rowView.findViewById( R.id.fotoImageView );
            Bitmap bm = BitmapFactory.decodeByteArray( itemRevisionFoto.getFoto() , 0 , itemRevisionFoto.getFoto().length );
            Drawable image = new BitmapDrawable( this.context.getResources() , bm );
            fotoImageView.setBackground( image );
            cells[position] = rowView;
            rowView.setTag( itemRevisionFoto.getIdFoto() );
        }
        return rowView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setRevisionFotos(RevisionFoto[] revisionFotos) {
        this.revisionFotos = revisionFotos;
        this.cells = new View[ this.revisionFotos.length ];
    }
}
