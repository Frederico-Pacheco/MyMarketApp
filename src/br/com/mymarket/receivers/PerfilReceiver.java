package br.com.mymarket.receivers;

import java.io.Serializable;

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
import br.com.mymarket.model.Pessoa;

public class PerfilReceiver extends BroadcastReceiver implements ReceiverDelegate{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_PERFIL = "resultadoPerfil";
    public static final String PERFIL_RECEBIDO = "Perfil Recebido";
    public static final String PERFIL_PARAM = "perfil";

    public PerfilReceiver registraObservador(BuscaInformacaoDelegate delegate){
    	PerfilReceiver receiver = new PerfilReceiver();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(PERFIL_RECEBIDO));
        return receiver;
    }

    public void processaResultado(Context context, Object obj, boolean sucesso){
    	Pessoa resultado = (Pessoa) obj;
        Intent intent = new Intent(PERFIL_RECEBIDO);
        intent.putExtra(RESULTADO_PERFIL,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra(Constants.SUCESSO,false) == true){
        	delegate.processaResultado((Pessoa) intent.getSerializableExtra(RESULTADO_PERFIL));
        }else{
        	delegate.processarException(new MyMarketException());
        }
    }
}