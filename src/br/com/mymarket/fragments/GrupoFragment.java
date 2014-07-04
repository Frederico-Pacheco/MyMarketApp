package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.mymarket.R;
import br.com.mymarket.activities.GrupoActivity;
import br.com.mymarket.adapters.GrupoAdapter;
import br.com.mymarket.model.Grupo;

public class GrupoFragment extends Fragment {

	private ListView grupoList;
    private GrupoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.grupoList = (ListView) inflater.inflate(R.layout.fragment_listview, container, false);
        final GrupoActivity activity = ((GrupoActivity)this.getActivity());
		activity.registerForContextMenu(this.grupoList);
		this.grupoList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,int posicao, long id) {
				activity.setItemSelecionado((Grupo)adapter.getItemAtPosition(posicao));
				return false;
			}
		});
        
        this.adapter = new GrupoAdapter(activity, activity.getListaGrupo());
        this.grupoList.setAdapter(this.adapter);
        return this.grupoList;
    }
}
