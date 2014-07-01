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
import br.com.mymarket.activities.ProdutosActivity;
import br.com.mymarket.adapters.ProdutosAdapter;
import br.com.mymarket.model.Produto;

public class ProdutosFragment extends Fragment{
	private ListView listview;
    private ProdutosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.listview = (ListView) inflater.inflate(R.layout.fragment_listview, container, false);
        final ProdutosActivity activity = ((ProdutosActivity)this.getActivity());
		activity.registerForContextMenu(this.listview);
		this.listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,int posicao, long id) {
				activity.setItemSelecionado((Produto)adapter.getItemAtPosition(posicao));
				return false;
			}
		});
        
        this.adapter = new ProdutosAdapter(activity, activity.getProdutos());
        this.listview.setAdapter(this.adapter);
        return this.listview;
    }
}
