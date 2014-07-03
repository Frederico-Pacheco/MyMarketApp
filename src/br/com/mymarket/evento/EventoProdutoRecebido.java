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
import br.com.mymarket.model.Produto;

public class EventoProdutoRecebido extends BroadcastReceiver implements EventoDelegate{

    private BuscaInformacaoDelegate delegate;
    
    public static final String RESULTADO_PRODUTO = "resultadoProduto";
    public static final String PRODUTO_RECEBIDO = "Produtos Recebido";
    public static final String PRODUTO_PARAM = "produtos";    

    public EventoProdutoRecebido registraObservador(BuscaInformacaoDelegate delegate){
    	EventoProdutoRecebido receiver = new EventoProdutoRecebido();
        receiver.delegate = delegate;
        LocalBroadcastManager.getInstance(delegate.getMyMarketApplication()).registerReceiver(receiver,new IntentFilter(PRODUTO_RECEBIDO));
        return receiver;
    }

    public void processaResultado(Context context,Object obj, boolean sucesso){
    	List<Produto> resultado = (List<Produto>) obj;
        Intent intent = new Intent(PRODUTO_RECEBIDO);
        intent.putExtra(RESULTADO_PRODUTO,(Serializable) resultado);
        intent.putExtra(Constants.SUCESSO,sucesso);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(MyMarketApplication application){
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra(Constants.SUCESSO,false) == true){
        	delegate.processaResultado((List<Produto>) intent.getSerializableExtra(RESULTADO_PRODUTO));
        }else{
        	delegate.processarException(new MyMarketException());
        }
    }
}