package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.activities.ListaComprasActivity;

public class InformacaoComprasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_info_compras, container, false);
    	final ListaComprasActivity activity = ((ListaComprasActivity)this.getActivity());
    	EditText titulo = (EditText) view.findViewById(R.id.lista_compra_nome);
    	titulo.setText(activity.getItemSelecionado().getNome());
        return view;
    }
	
}
