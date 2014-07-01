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
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;

public class EventoListaCompraRecebidas extends BroadcastReceiver{

    private BuscaInformacaoDelegate delegate;

    public static EventoListaCompraRecebidas registraObservador(BuscaInformacaoDelegate delegate){
    	EventoListaCompraRecebidas receiver = new EventoListaCompraRecebidas();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(Constants.LISTACOMPRAS_RECEBIDOS));
        return receiver;
    }

    public  static void processaResultado(Context context, List<ListaCompra> resultado, boolean sucesso){
        Intent intent = new Intent(Constants.LISTACOMPRAS_RECEBIDOS);
        intent.putExtra(Constants.RESULTADO_LISTACOMPRAS,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("RECEBI O EVENTO! DEU CERTO?" + intent.getBooleanExtra(Constants.SUCESSO,false));
        delegate.processaResultado((List<ListaCompra>) intent.getSerializableExtra(Constants.RESULTADO_LISTACOMPRAS));
    }
}