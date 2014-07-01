package br.com.mymarket.evento;

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
import br.com.mymarket.exception.MyMarketException;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.model.Pessoa;

public class EventoPerfilRecebido extends BroadcastReceiver{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_PERFIL = "resultadoPerfil";
    public static final String PERFIL_RECEBIDO = "Perfil Recebido";
    public static final String PERFIL_PARAM = "perfil";

    public static EventoPerfilRecebido registraObservador(BuscaInformacaoDelegate delegate){
    	EventoPerfilRecebido receiver = new EventoPerfilRecebido();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(PERFIL_RECEBIDO));
        return receiver;
    }

    public  static void processaResultado(Context context, Pessoa resultado, boolean sucesso){
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