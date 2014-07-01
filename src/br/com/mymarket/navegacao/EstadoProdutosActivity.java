package br.com.mymarket.navegacao;

import br.com.mymarket.R;
import br.com.mymarket.activities.ProdutosActivity;
import br.com.mymarket.fragments.ProdutosFragment;
import br.com.mymarket.fragments.ProgressFragment;
import br.com.mymarket.utils.FragmentUtils;

public enum EstadoProdutosActivity {
	
	INICIO{
        public void executa(ProdutosActivity activity){
        	activity.buscarProdutos();
        	activity.alteraEstadoEExecuta(EstadoProdutosActivity.AGUARDANDO);
        }
    },AGUARDANDO{
        public void executa(ProdutosActivity activity){
        	FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,ProgressFragment.class,false);
        }
    },LISTAGEM{
        public void executa(ProdutosActivity activity){
        	activity.setarInformacoesAdapter();
        	FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,ProdutosFragment.class,false);
        }
    };

    public void executa(ProdutosActivity activity){

    }

}
