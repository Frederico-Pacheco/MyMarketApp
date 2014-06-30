package br.com.mymarket;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.tasks.RegistraDeviceTask;

public class MyMarketApplication extends Application {
	
	private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

	private SharedPreferences preferences;
	
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
}
