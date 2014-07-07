package br.com.mymarket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.constants.Extras;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Lembrete;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.tasks.RecuperarContatosTask;
import br.com.mymarket.tasks.RegistraDeviceTask;

public class MyMarketApplication extends Application {
	
	private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

	private SharedPreferences preferences;
	
	private List<Pessoa> contato = new ArrayList<Pessoa>();
	
	public void registra(AsyncTask<?, ?, ?> task) {
		tasks.add(task);
	}

	public void desregistra(AsyncTask<?, ?, ?> task) {
		tasks.remove(task);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);
		initializeGCM();
		getContacts();
	}
	
	private void getContacts() {
		new RecuperarContatosTask(this).execute();
	}

	@Override
	public void onTerminate() {
		for (AsyncTask<?, ?, ?> task : tasks) {
			task.cancel(true);
		}
	}
	
	public void initializeGCM() {
		if (!usuarioRegistrado()) {
			new RegistraDeviceTask(this).execute();
		} else {
			MyLog.i("Device ja registrado " + preferences.getString(Constants.REGISTRATION_ID, null));
		}
	}

	private boolean usuarioRegistrado() {
		return preferences.getBoolean(Constants.REGISTERED, false);
	}

	public void lidaComRespostaDoRegistroNoServidor(String registro) {
		if (registro != null) {
			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean(Constants.REGISTERED, true);
			editor.putString(Constants.REGISTRATION_ID, registro);
			editor.commit();
		}
	}
	
	public void criarLembrete(Lembrete lembrete) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lembrete.getData());
	    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	    Intent intent = new Intent(Constants.LEMBRETE_ACTION);
	    intent.putExtra(Extras.EXTRA_LEMBRETE, lembrete);
	    final int _id = (int) lembrete.getDataCriacao().getTimeInMillis();
	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, _id, intent, 0);
	    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , alarmIntent);
	}
	
	public List<Pessoa> getContato(){
		return contato;
	}

	public void adicionaContatos(List<Pessoa> retorno) {
		this.contato.clear();
		this.contato.addAll(retorno);
	}
	
}
