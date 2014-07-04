package br.com.mymarket.receivers;

import java.io.Serializable;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.exception.MyMarketException;
import br.com.mymarket.model.Grupo;

public class GrupoReceiver extends BroadcastReceiver implements ReceiverDelegate{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_LISTAGRUPO = "resultadoListaGrupo";
    public static final String LISTAGRUPO_RECEBIDOS = "Lista de Grupo Recebidas";
    public static final String LISTA_GRUPO_PARAM = "listaGrupo";   
	
	@Override
	public ReceiverDelegate registraObservador(BuscaInformacaoDelegate delegate) {
		GrupoReceiver receiver = new GrupoReceiver();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(LISTAGRUPO_RECEBIDOS));
        return receiver;
	}

	@Override
	public void processaResultado(Context context, Object obj,boolean sucesso) {
    	List<Grupo> resultado = (List<Grupo>) obj;
        Intent intent = new Intent(LISTAGRUPO_RECEBIDOS);
        intent.putExtra(RESULTADO_LISTAGRUPO,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra(Constants.SUCESSO,false) == true){
        	delegate.processaResultado((List<Grupo>) intent.getSerializableExtra(RESULTADO_LISTAGRUPO));
        }else{
        	delegate.processarException(new MyMarketException());
        }
    }

}
