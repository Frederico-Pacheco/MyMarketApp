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
import br.com.mymarket.activities.ListaComprasActivity;
import br.com.mymarket.adapters.ListaCompraAdapter;
import br.com.mymarket.model.ListaCompra;

public class ListaDeComprasFragment extends Fragment {
	
	private ListView listComprasList;
    private ListaCompraAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.listComprasList = (ListView) inflater.inflate(R.layout.fragment_list_compras, container, false);
        final ListaComprasActivity activity = ((ListaComprasActivity)this.getActivity());
		activity.registerForContextMenu(this.listComprasList);
		this.listComprasList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,int posicao, long id) {
				activity.setItemSelecionado((ListaCompra)adapter.getItemAtPosition(posicao));
				return false;
			}
		});
        
        this.adapter = new ListaCompraAdapter(activity, activity.getListaCompras());
        this.listComprasList.setAdapter(this.adapter);
        return this.listComprasList;
    }
}
