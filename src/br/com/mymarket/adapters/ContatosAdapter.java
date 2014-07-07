package br.com.mymarket.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;

public class ContatosAdapter extends ArrayAdapter<Pessoa> {
	
	private Context context;
    private final List<Pessoa> listas;

    public ContatosAdapter(Context context,int resource, List<Pessoa> listas) {
    	super(context, resource, listas);
        this.context = context;
        this.listas = listas;
    }

    @Override
    public int getCount() {
        return listas.size();
    }

    @Override
    public Pessoa getItem(int i) {
        return listas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int layout = R.layout.listview_contatos;

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
        viewHolder.telefone.setText(pessoa.getNumber());
        return convertView;
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }

	
    class ViewHolder {
        TextView nome;
        TextView telefone;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.nome);
            this.telefone = (TextView) view.findViewById(R.id.telefone);
        }
    }


	public void remove(Pessoa pessoa) {
		listas.remove(pessoa);
		notifyDataSetChanged();
	}

	public void add(Pessoa pessoa, int posicao) {
		listas.add(posicao, pessoa);
		notifyDataSetChanged();
	}
    
}
