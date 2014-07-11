package br.com.mymarket.tasks;

import android.os.AsyncTask;
import android.telephony.PhoneNumberUtils;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.converters.TokenConverter;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.webservice.WebClient;

public class OauthTask extends AsyncTask<String, Void, String> {
	
	private MyMarketApplication application;
    private ReceiverDelegate evento;
	
	public OauthTask(MyMarketApplication application, ReceiverDelegate evento) {
		MyLog.i("OauthTask() " + application.getPackageName());
        this.application = application;
        this.evento = evento;
        this.application.registra(this);
    }

	@Override
	protected String doInBackground(String... celular) {
		if(PhoneNumberUtils.isWellFormedSmsAddress(celular[0]))
		{
			String json = new WebClient("pessoa/",this.application).post(celular[0]);
			return new TokenConverter().convert(json);
		}else{
			return null;
		}
	}
	
    @Override
    protected void onPostExecute(String token) {
        MyLog.i("OBTIDO MEU TOKEN!");
        if (token!=null) {
        	evento.processaResultado(this.application,token,true);
        } else {
        	evento.processaResultado(this.application,token,false);
        }
        this.application.desregistra(this);
    }
}
