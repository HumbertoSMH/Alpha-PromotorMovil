package mx.com.algroup.promotormovil.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.EncuestaPersona;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.utils.LogUtil;
import mx.com.algroup.promotormovil.utils.ViewUtil;

/**
 * Created by MAMM on 20/04/15.
 */
public class EncuestaPersonasListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String CLASSNAME = EncuestaPersonasListAdapter.class.getSimpleName();

    private EncuestaPersona[] encuestaPersonas;
    private Context context;


    public EncuestaPersonasListAdapter(EncuestaPersona[] encuestaPersonas, Context context) {
        LogUtil.printLog( CLASSNAME , ".EncuestaPersonasListAdapter() encuestaPersonas:" + encuestaPersonas );
        this.encuestaPersonas = encuestaPersonas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return encuestaPersonas.length;
    }

    @Override
    public Object getItem(int position) {
        return encuestaPersonas[position];
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

        View rowView = inflater.inflate( R.layout.contenedor_encuesta_lista_layout , parent,
                false );
        EncuestaPersona itemEncuestaPersona = this.encuestaPersonas[ position ];

        TextView encuestaPersonaTextView = (TextView) rowView.findViewById( R.id.encuestadoTextView );
        encuestaPersonaTextView.setText( "Persona " + (position + 1 ) );
        rowView.setOnClickListener( this );
        rowView.setTag( itemEncuestaPersona );
        return rowView;
    }

    @Override
    public void onClick(View v) {
        LogUtil.printLog(CLASSNAME, "Se hace click sobre el item TAG:" + v.getTag());
        String message = this.context.getResources().getString( R.string.no_permitido_detalle_encuesta);
        ViewUtil.mostrarMensajeRapido( this.context, message);

    }
}
