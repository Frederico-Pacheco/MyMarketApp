package br.com.mymarket.navegacao;

import br.com.mymarket.R;
import br.com.mymarket.activities.ListaComprasActivity;
import br.com.mymarket.fragments.FormularioListaDeComprasFragment;
import br.com.mymarket.fragments.InformacaoComprasFragment;
import br.com.mymarket.fragments.ListaCompraFragment;
import br.com.mymarket.fragments.ProgressFragment;
import br.com.mymarket.utils.FragmentUtils;

public enum EstadoListaComprasActivity {

    INICIO{
        public void executa(ListaComprasActivity activity){
        	activity.buscarListasDeCompras();
        	activity.alteraEstadoEExecuta(EstadoListaComprasActivity.AGUARDANDO_LISTAGEM);
        }
    },AGUARDANDO_LISTAGEM{
        public void executa(ListaComprasActivity activity){
        	FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,ProgressFragment.class,false);
        }
    },LISTAS_RECEBIDAS{
    	public void executa(ListaComprasActivity activity){
    		FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,ListaCompraFragment.class,false);
    	}
    },CADASTRAR_LISTA{
    	public void executa(ListaComprasActivity activity){
    		FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,FormularioListaDeComprasFragment.class,false);
    	}
    },INFORMACOES_COMPRAS{
    	public void executa(ListaComprasActivity activity){
    		FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,InformacaoComprasFragment.class,false);
    	}
    };

    public void executa(ListaComprasActivity activity){

    } 
}
