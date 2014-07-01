package br.com.mymarket.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.evento.EventoListaCompraRecebidas;
import br.com.mymarket.evento.EventoProdutoRecebido;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.mocks.ProdutoMock;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.model.Produto;
import br.com.mymarket.webservice.Pagina;

public class BuscarProdutosTask extends AsyncTask<Pagina, Void, List<Produto>> {

    private Exception erro;
    private MyMarketApplication application;
    private ListaCompra listaCompra;
    
    public BuscarProdutosTask(MyMarketApplication application,ListaCompra listaCompra){
        this.application = application;
        this.listaCompra = listaCompra;
        this.application.registra(this);
    }
	
    @Override
    protected List<Produto> doInBackground(Pagina... paginas) {
        try {
            //FIXME FAZER PARTE SERVIDOR.
//			Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();        	
//          String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar,this.application).get();
//          List<Produto> listasRecebidas = new ListaCompraConverter().convert(jsonDeResposta);
            return ProdutoMock.get();
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Produto> retorno) {
        MyLog.i("RETORNO OBTIDO LISTA PRODUTOS!");
        if (retorno!=null) {
            EventoProdutoRecebido.processaResultado(this.application,retorno,true);
        } else {
        	EventoProdutoRecebido.processaResultado(this.application,retorno,false);
        }
        this.application.desregistra(this);
    }

}
