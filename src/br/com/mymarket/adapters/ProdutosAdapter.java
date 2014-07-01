package br.com.mymarket.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Produto;

public class ProdutosAdapter extends BaseAdapter {
	
	private Context context;
    private final List<Produto> listas;
    private SparseBooleanArray mSelectedItemsIds;

    public ProdutosAdapter(Context mContext, List<Produto> listas) {
        this.context = mContext;
        this.listas = listas;
        mSelectedItemsIds = new SparseBooleanArray();
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
        int layout = R.layout.listview_lista_produto;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha!!!");
        }

        Produto produto = (Produto) getItem(position);

        convertView.setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4	: Color.TRANSPARENT);
        viewHolder.nome.setText(produto.getNome());
        
//		mudarCor(position,convertView);
        
        return convertView;
    }
    
    
    public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
    
    public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}
	
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    
    public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	private void mudarCor(int posicao, View view) {
		if(posicao % 2 == 0 ){
			view.setBackgroundColor(context.getResources().getColor(R.color.linha_par));
		}
	}
	
    class ViewHolder {
        TextView nome;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.lista_produto_nome);
        }
    }
    

}
