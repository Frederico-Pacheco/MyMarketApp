package br.com.mymarket.navegacao;

import android.app.Fragment;
import br.com.mymarket.R;
import br.com.mymarket.activities.MainActivity;
import br.com.mymarket.fragments.MainFragment;
import br.com.mymarket.fragments.OauthFragment;
import br.com.mymarket.fragments.ProgressFragment;
import br.com.mymarket.utils.FragmentUtils;

public enum EstadoMainActivity {

    INICIO{
        public void executa(MainActivity activity){
        	activity.buscarMeuPerfil();
        	activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PERFIL);
        }

    },AGUARDANDO_PERFIL{
        public void executa(MainActivity activity){
        	FragmentUtils.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,ProgressFragment.class,false);
        }
    },OAUTH{
        public void executa(MainActivity activity){
            this.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,OauthFragment.class,false);
        }	
    },PERFIL{
        public void executa(MainActivity activity){
            this.colocaOuBuscaFragmentNaTela(activity,R.id.fragment_principal,MainFragment.class,false);
        }
    };

    public void executa(MainActivity activity){

    }

    public Fragment colocaOuBuscaFragmentNaTela(MainActivity activity,int id,Class<? extends Fragment> fragmentClass,boolean backStack){
       return FragmentUtils.colocaOuBuscaFragmentNaTela(activity, id, fragmentClass, backStack);
    }
}
