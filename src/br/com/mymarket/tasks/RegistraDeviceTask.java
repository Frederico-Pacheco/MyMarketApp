package br.com.mymarket.tasks;

import android.os.AsyncTask;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.infra.MyLog;

public class RegistraDeviceTask extends AsyncTask<Void, Void, String> {

	private MyMarketApplication app;

	public RegistraDeviceTask(MyMarketApplication app) {
		MyLog.i("REGISTRADEVICETASK() " + app.getPackageName());
		this.app = app;
	}

	//FIXME CRIAR CONTA NO GCM E ALTERAR CONSTANTE E DEPENDENCIAS
	@Override
	protected String doInBackground(Void... params) {
		return null;
	}

	/****
	@Override
	protected String doInBackground(Void... params) {
		String registrationId = null;
		try {
			GoogleCloudMessaging gcm = GoogleCloudMessaging
					.getInstance(this.app);
			MyLog.i("RECUPERAR INSTANCIA GCM ");
			registrationId = gcm.register(Constants.GCM_SERVER_ID);
			MyLog.i("Meu device registrado com o ID: " + registrationId);
			String email = InformacoesDoUsuario.getEmail(this.app);
			MyLog.i("URL POST : " + "device/register/" + email + "/"
					+ registrationId);
			new WebClient("device/register/" + email + "/" + registrationId,
					this.app).post();
		} catch (Exception e) {
			MyLog.e("Problema no registro do device : " + e.getMessage());
		}
		return registrationId;
	}*/

	@Override
	protected void onPostExecute(String result) {
		app.lidaComRespostaDoRegistroNoServidor(result);
	}
}
