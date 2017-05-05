package mx.com.algroup.promotormovil.controller.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mx.com.algroup.promotormovil.R;
import mx.com.algroup.promotormovil.business.RevisionProducto;
import mx.com.algroup.promotormovil.business.Visita;
import mx.com.algroup.promotormovil.controller.DetalleProductoActivity;
import mx.com.algroup.promotormovil.utils.Const;
import mx.com.algroup.promotormovil.utils.LogUtil;

/**
 * Created by MAMM on 20/04/15.
 */
public class RevisionProductoListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String CLASSNAME = RevisionProductoListAdapter.class.getSimpleName();

    private RevisionProducto[] revisionProductos;
    private Visita visita;
    private Context context;


    public RevisionProductoListAdapter(RevisionProducto[] revisionProductos, Context context , Visita visita ) {
        LogUtil.printLog( CLASSNAME , ".RevisionProductoListAdapter() revisionProductos:" + revisionProductos );
        this.revisionProductos = revisionProductos;
        this.visita = visita;
        this.context = context;
    }

    @Override
    public int getCount() {
        return revisionProductos.length;
    }

    @Override
    public Object getItem(int position) {
        return revisionProductos[position];
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

        View rowView = inflater.inflate( R.layout.contenedor_productos_lista_layout , parent, false );
        RevisionProducto itemRevisionProducto = this.revisionProductos[ position ];

        TextView nombreProductoTextView = (TextView) rowView.findViewById( R.id.nombreProductoTextView );
        nombreProductoTextView.setText( itemRevisionProducto.getProducto().getDescripcion() );
        TextView codigoProductoVisitaTextView = (TextView) rowView.findViewById( R.id.codigoProductoVisitaTextView );
        codigoProductoVisitaTextView.setText( itemRevisionProducto.getProducto().getCodigo() );

        rowView.setTag( "" + position );
        rowView.setOnClickListener( this );
        return rowView;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent( this.context, DetalleProductoActivity.class );
        int idPosición = Integer.parseInt( v.getTag().toString() ) ;
        intent.putExtra( Const.ParametrosIntent.ID_VISITA_ACTUAL.getNombre() , this.visita.getIdVisita() );
        intent.putExtra( Const.ParametrosIntent.POSICION_PRODUCTO.getNombre() , idPosición );
        this.context.startActivity( intent );
    }
}
