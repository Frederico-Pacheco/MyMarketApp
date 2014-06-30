package br.com.mymarket.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.evento.EventoListaCompraRecebidas;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.mocks.ListaComprasMocks;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.webservice.Pagina;

public class BuscarMaisListaCompraTask extends AsyncTask<Pagina, Void, List<ListaCompra>> {

    private Exception erro;
    //private BuscaMaisPostsDelegate delegate;
    private MyMarketApplication application;
    
    public BuscarMaisListaCompraTask(MyMarketApplication application){
        this.application = application;
        this.application.registra(this);
    }
	
    @Override
    protected List<ListaCompra> doInBackground(Pagina... paginas) {
        try {
            //FIXME FAZER PARTE SERVIDOR.
//			Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();        	
//          String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar,this.application).get();
//          List<ListaCompra> listasRecebidas = new ListaCompraConverter().convert(jsonDeResposta);
            return ListaComprasMocks.get();
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<ListaCompra> retorno) {
        MyLog.i("RETORNO OBTIDO LISTA COMPRAS!");

        if (retorno!=null) {
            EventoListaCompraRecebidas.processaResultado(this.application,retorno,true);
        } else {
        	EventoListaCompraRecebidas.processaResultado(this.application,retorno,false);
        }

        this.application.desregistra(this);
    }

}
