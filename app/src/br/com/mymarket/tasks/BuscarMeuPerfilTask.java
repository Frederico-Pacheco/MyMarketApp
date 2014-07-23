package br.com.mymarket.tasks;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.mocks.PessoasMock;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.webservice.Pagina;

public class BuscarMeuPerfilTask extends AsyncTask<Pagina, Void, Pessoa> {

    private Exception erro;
    private MyMarketApplication application;
    private String myPhoneNumber;
    private ReceiverDelegate evento;
    
    public BuscarMeuPerfilTask(MyMarketApplication application, String myPhoneNumber,ReceiverDelegate evento){
        this.application = application;
        this.myPhoneNumber = myPhoneNumber;
        this.evento = evento;
        this.application.registra(this);
    }
	
    @Override
    protected Pessoa doInBackground(Pagina... paginas) {
        try {
            //FIXME FAZER PARTE SERVIDOR.
//			Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();        	
//          String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar +"&myphone="+this.myPhoneNumber,this.application).get();
//          PessoasMock pessoa = new ListaCompraConverter().convert(jsonDeResposta);
            return PessoasMock.get();
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Pessoa retorno) {
        MyLog.i("RETORNO OBTIDO MEU Pefil!");

        if (retorno!=null) {
        	evento.processaResultado(this.application,retorno,true);
        } else {
        	evento.processaResultado(this.application,retorno,false);
        }
        this.application.desregistra(this);
    }

}
