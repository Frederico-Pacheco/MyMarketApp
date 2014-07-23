package br.com.mymarket.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.mocks.ListaGrupoMock;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.webservice.Pagina;

public class BuscarGrupoTask extends AsyncTask<Pagina, Void, List<Grupo>> {

    private Exception erro;
    //private BuscaMaisPostsDelegate delegate;
    private MyMarketApplication application;
    private ReceiverDelegate event;
    
    public BuscarGrupoTask(MyMarketApplication application,ReceiverDelegate event){
        this.application = application;
        this.application.registra(this);
        this.event = event;
    }
	
    @Override
    protected List<Grupo> doInBackground(Pagina... paginas) {
        try {
            //FIXME FAZER PARTE SERVIDOR.
//			Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();        	
//          String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar,this.application).get();
//          List<ListaCompra> listasRecebidas = new ListaCompraConverter().convert(jsonDeResposta);
            return ListaGrupoMock.get();
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Grupo> retorno) {
        MyLog.i("RETORNO OBTIDO LISTA GRUPO!");

        if (retorno!=null) {
        	this.event.processaResultado(this.application,retorno,true);
        } else {
        	this.event.processaResultado(this.application,retorno,false);
        }

        this.application.desregistra(this);
    }
}
