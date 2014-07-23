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

public class OauthReceiver extends BroadcastReceiver implements ReceiverDelegate{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_OAUTH = "resultadoOauth";
    public static final String OAUTH_RECEBIDO = "Oauth Recebido";
    public static final String OAUTH_PARAM = "oauth";

    public OauthReceiver registraObservador(BuscaInformacaoDelegate delegate){
    	OauthReceiver receiver = new OauthReceiver();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(OAUTH_RECEBIDO));
        return receiver;
    }

    public void processaResultado(Context context, Object obj, boolean sucesso){
    	String resultado = (String) obj;
        Intent intent = new Intent(OAUTH_RECEBIDO);
        intent.putExtra(RESULTADO_OAUTH,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra(Constants.SUCESSO,false) == true){
        	delegate.processaResultado((String) intent.getSerializableExtra(RESULTADO_OAUTH));
        }else{
        	delegate.processarException(new MyMarketException());
        }
    }
}