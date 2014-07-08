package br.com.mymarket.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.utils.DateUtils;

public class ListaCompraAdapter extends BaseAdapter {
	
	private Context context;
    private final List<ListaCompra> listas;

    public ListaCompraAdapter(Context mContext, List<ListaCompra> listas) {
        this.context = mContext;
        this.listas = listas;
    }

    @Override
    public int getCount() {
        return listas.size();
    }

    @Override
    public Object getItem(int i) {
        return listas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int layout = R.layout.listview_lista_compra;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha!!!");
        }

        ListaCompra listaCompra = (ListaCompra) getItem(position);

        viewHolder.nome.setText(listaCompra.getNome());
        viewHolder.data.setText(DateUtils.formatDate(listaCompra.getData()));
        
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class ViewHolder {
        TextView nome;
        TextView data;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.lista_compra_nome);
            this.data = (TextView) view.findViewById(R.id.lista_compra_data);
        }
    }


}
