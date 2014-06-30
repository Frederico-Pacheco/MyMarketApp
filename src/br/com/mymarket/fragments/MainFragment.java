package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.activities.MainActivity;
import br.com.mymarket.infra.CheckConnectivity;
import br.com.mymarket.model.Pessoa;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        final MainActivity activity = ((MainActivity)this.getActivity());
        boolean status = new CheckConnectivity().checkNetworkStatus(activity.getMyMarketApplication());
        TextView status_conexao = (TextView) view.findViewById(R.id.status_conexao);
        Pessoa perfil = activity.getPerfil();
        if(perfil != null){
        	setarInformacoesDoPerfil(perfil,view);	
        }
        if(status){
        	status_conexao.setText(R.string.status_online);
        }else{
        	status_conexao.setText(R.string.status_offline);
        }
        return view;
    }

	private void setarInformacoesDoPerfil(Pessoa perfil, View view) {
		TextView info = (TextView) view.findViewById(R.id.info);
		info.setText(perfil.getNome());
	}
	
}
