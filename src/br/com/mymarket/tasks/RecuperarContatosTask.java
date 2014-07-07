package br.com.mymarket.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.infra.Contacts;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.webservice.Pagina;

public class RecuperarContatosTask extends AsyncTask<Pagina, Void, List<Pessoa>> {

	private MyMarketApplication application;
	
	public RecuperarContatosTask(MyMarketApplication application) {
		MyLog.i("RecuperarContatosTask() " + application.getPackageName());
		this.application = application;
		this.application.registra(this);
	}

	@Override
	protected List<Pessoa> doInBackground(Pagina... paginas) {
		Contacts contact = new Contacts(this.application);
		contact.setContatos();
		MyLog.i("CONTATOS " + contact.getContacts().size());
		return contact.getContacts();
	}
	
    @Override
    protected void onPostExecute(List<Pessoa> retorno) {
        MyLog.i("RETORNO OBTIDO LISTA PESSOAS!");
        this.application.adicionaContatos(retorno);
        this.application.desregistra(this);
    }
}
