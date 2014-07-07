package br.com.mymarket.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;

public class FormGrupoContatoAdapter extends BaseAdapter {
	private Context context;
    private final List<Pessoa> listas;
    
    public FormGrupoContatoAdapter(Context mContext, List<Pessoa> listas) {
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
        int layout = R.layout.listview_contatos_form_grupo;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha!!!");
        }

        Pessoa pessoa = (Pessoa) getItem(position);
        viewHolder.nome.setText(pessoa.getNome());
        Drawable res = context.getResources().getDrawable(android.R.drawable.ic_delete);
        viewHolder.imageView.setImageDrawable(res);
        return convertView;
    }

    
	public void add(Pessoa pessoa) {
		if(!this.listas.contains(pessoa)){
			this.listas.add(pessoa);	
			notifyDataSetChanged();
		}
	}
    
	public void remove(Pessoa pessoa) {
		this.listas.remove(pessoa);
		notifyDataSetChanged();
	}
	
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    

    class ViewHolder {
        TextView nome;
        ImageView imageView;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.nome);
            this.imageView = (ImageView) view.findViewById(R.id.image);
        }
    }
}
