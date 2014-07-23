package br.com.mymarket.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class FragmentUtils {
    public static Fragment colocaOuBuscaFragmentNaTela(Activity activity,int id,Class<? extends Fragment> fragmentClass,boolean backStack){
        Fragment naTela = activity.getFragmentManager().findFragmentByTag(fragmentClass.getCanonicalName());
        if(naTela != null)
            return naTela;
        else{
            try{
                Fragment novoFragment = fragmentClass.newInstance();
                FragmentTransaction tx = activity.getFragmentManager().beginTransaction();
                tx.replace(id,novoFragment);
                if(backStack)
                    tx.addToBackStack(null);
                tx.commit();
                return novoFragment;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
