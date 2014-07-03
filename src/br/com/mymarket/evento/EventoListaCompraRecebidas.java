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
import br.com.mymarket.delegates.EventoDelegate;
import br.com.mymarket.exception.MyMarketException;
import br.com.mymarket.model.ListaCompra;

public class EventoListaCompraRecebidas extends BroadcastReceiver implements EventoDelegate{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_LISTACOMPRAS = "resultadoListaCompras";
    public static final String LISTACOMPRAS_RECEBIDOS = "Lista de Compras Recebidas";
    public static final String LISTA_COMPRAS_PARAM = "listaCompra";    

    public EventoListaCompraRecebidas registraObservador(BuscaInformacaoDelegate delegate){
    	EventoListaCompraRecebidas receiver = new EventoListaCompraRecebidas();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(LISTACOMPRAS_RECEBIDOS));
        return receiver;
    }

    public void processaResultado(Context context, Object obj, boolean sucesso){
    	List<ListaCompra> resultado = (List<ListaCompra>) obj;
        Intent intent = new Intent(LISTACOMPRAS_RECEBIDOS);
        intent.putExtra(RESULTADO_LISTACOMPRAS,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra(Constants.SUCESSO,false) == true){
        	delegate.processaResultado((List<ListaCompra>) intent.getSerializableExtra(RESULTADO_LISTACOMPRAS));
        }else{
        	delegate.processarException(new MyMarketException());
        }
    }
}