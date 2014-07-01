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
import br.com.mymarket.model.Produto;
import br.com.mymarket.utils.DateUtils;

public class ProdutosAdapter extends BaseAdapter {
	
	private Context context;
    private final List<Produto> listas;

    public ProdutosAdapter(Context mContext, List<Produto> listas) {
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

        Produto produto = (Produto) getItem(position);

        viewHolder.nome.setText(produto.getNome());
        
//		mudarCor(position,convertView);
        
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

	private void mudarCor(int posicao, View view) {
		if(posicao % 2 == 0 ){
			view.setBackgroundColor(context.getResources().getColor(R.color.linha_par));
		}
	}
	
    class ViewHolder {
        TextView nome;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.lista_compra_nome);
        }
    }


}
