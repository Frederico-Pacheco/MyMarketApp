package br.com.mymarket.evento;

import java.io.Serializable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;

public class EventoPerfilRecebido extends BroadcastReceiver{

    private BuscaInformacaoDelegate delegate;

    public static EventoPerfilRecebido registraObservador(BuscaInformacaoDelegate delegate){
    	EventoPerfilRecebido receiver = new EventoPerfilRecebido();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(Constants.PERFIL_RECEBIDO));
        return receiver;
    }

    public  static void processaResultado(Context context, Pessoa resultado, boolean sucesso){
        Intent intent = new Intent(Constants.PERFIL_RECEBIDO);
        intent.putExtra(Constants.RESULTADO_PERFIL,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("RECEBI O EVENTO! DEU CERTO?" + intent.getBooleanExtra(Constants.SUCESSO,false));
        delegate.processaResultado((Pessoa) intent.getSerializableExtra(Constants.RESULTADO_PERFIL));
    }
}