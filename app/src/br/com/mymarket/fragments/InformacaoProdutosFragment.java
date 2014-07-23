package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.mymarket.R;
import br.com.mymarket.activities.ProdutosActivity;
import br.com.mymarket.helpers.InformacaoProdutoHelper;

public class InformacaoProdutosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_info_produtos, container, false);
    	final ProdutosActivity activity = ((ProdutosActivity)this.getActivity());
    	InformacaoProdutoHelper informacaoProdutoHelper = new InformacaoProdutoHelper(view);
    	informacaoProdutoHelper.colocarProdutoNoFormulario(activity.getItemSelecionado());
        return view;
    }
	
}
