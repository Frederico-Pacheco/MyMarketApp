package br.com.mymarket.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.activities.GrupoActivity;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Grupo;

public class GrupoAdapter extends BaseAdapter {
	
	private Context context;
    private final List<Grupo> lista;
    
	public GrupoAdapter(GrupoActivity context, List<Grupo> lista) {
        this.context = context;
        this.lista = lista;
	}

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        int layout = R.layout.listview_lista_grupo;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,viewGroup,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha!!!");
        }

        Grupo grupo = (Grupo) getItem(position);
        viewHolder.nome.setText(grupo.getNome());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class ViewHolder {
        TextView nome;

        ViewHolder(View view) {
            this.nome = (TextView) view.findViewById(R.id.lista_grupo_nome);
        }
    }



}
