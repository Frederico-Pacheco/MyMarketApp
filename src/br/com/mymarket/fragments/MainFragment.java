package br.com.mymarket.fragments;

import java.text.ParseException;
import java.util.Calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.com.mymarket.R;
import br.com.mymarket.activities.MainActivity;
import br.com.mymarket.infra.CheckConnectivity;
import br.com.mymarket.model.Lembrete;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.utils.DateUtils;

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
        
	    Calendar atual = Calendar.getInstance();
	    atual.setTimeInMillis(System.currentTimeMillis());
        final EditText teste = (EditText) view.findViewById(R.id.teste_lembrete);
        teste.setText(DateUtils.formatDate(atual.getTime()) + " " + DateUtils.formatHour(atual.getTime()));
        
    	Button button = (Button)view.findViewById(R.id.botao_lembrete);
    	button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					activity.getMyMarketApplication().criarLembrete(new Lembrete("teste", DateUtils.toDate(teste.getText().toString())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        
        return view;
    }

	private void setarInformacoesDoPerfil(Pessoa perfil, View view) {
		TextView info = (TextView) view.findViewById(R.id.info);
		info.setText(perfil.getNome());
	}
	
}
