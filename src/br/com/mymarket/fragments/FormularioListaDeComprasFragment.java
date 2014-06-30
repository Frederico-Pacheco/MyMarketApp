package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.mymarket.R;
import br.com.mymarket.activities.ListaComprasActivity;
import br.com.mymarket.helpers.FormularioListaCompraHelper;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.navegacao.EstadoListaComprasActivity;

public class FormularioListaDeComprasFragment extends Fragment {
	
	private FormularioListaCompraHelper formularioListaCompraHelper;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_form_list_compras, container, false);
    	final ListaComprasActivity activity = ((ListaComprasActivity)this.getActivity());
    	formularioListaCompraHelper = new FormularioListaCompraHelper(view);
    	formularioListaCompraHelper.colocarListaComprasNoFormulario(activity.getItemSelecionado());
    	Button button = (Button)view.findViewById(R.id.btn_form_lista_compras);
    	button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ListaCompra listaCompra = formularioListaCompraHelper.recuperarListaCompra();
				//TODO PERSIST OR POST OR PUT.
				activity.persiste(listaCompra);
				activity.alteraEstadoEExecuta(EstadoListaComprasActivity.LISTAS_RECEBIDAS);
			}
		});
        return view;
    }
}
