package mx.com.algroup.promotormovil.controller.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.business.utils.EstatusVisita;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by MAMM on 20/04/15.
 */
public class TiendasListAdapter extends BaseAdapter {
    private static final String CLASSNAME = TiendasListAdapter.class.getSimpleName();

    private Visita[] visitas;
    private Context context;


    public TiendasListAdapter( Visita[] visitas , Context context ) {
        LogUtil.printLog( CLASSNAME , ".TiendasListAdapter() visitas:" + visitas );
        this.visitas = visitas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return visitas.length;
    }

    @Override
    public Object getItem(int position) {
        return visitas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.printLog( CLASSNAME , ".getView position:" + position );
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE );

        View rowView = inflater.inflate( R.layout.contenedor_tiendas_lista_layout , parent,
                false );
        Visita itemVisita = this.visitas[ position ];

        TextView nombreTiendaTextView = (TextView) rowView.findViewById( R.id.nombreTiendaTextView );
        nombreTiendaTextView.setText( itemVisita.getTienda().getNombreTienda() );

        TextView nombreCadenaTextView = (TextView) rowView.findViewById( R.id.nombreCadenaTextView );
        nombreCadenaTextView.setText( itemVisita.getCadena().getNombreCadena() ) ;


        TextView estatusVisitaTextView = (TextView) rowView.findViewById( R.id.estatusVisitaTextView );
       estatusVisitaTextView.setText( itemVisita.getEstatusVisita().getNombreEstatus() + "     -     Visita:" + itemVisita.getIdVisita());

        LinearLayout celdaTiendaLinearLayout = (LinearLayout)rowView.findViewById( R.id.celdaTiendaLinearLayout );
        celdaTiendaLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() );
        celdaTiendaLinearLayout.setBackgroundColor( itemVisita.getEstatusVisita().getColor() - 50);

        return rowView;
    }


}
