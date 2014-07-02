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
import android.os.SystemClock;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Lembrete;
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
	
	public void criarLembrete(Lembrete lembrete) {
//		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(context, Alarm.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lembrete.getData());
		
	    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	    Intent intent = new Intent(Constants.LEMBRETE_ACTION);
	    PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	    
	    Calendar teste = Calendar.getInstance();
	    teste.setTimeInMillis(System.currentTimeMillis());
	    
	    MyLog.i(calendar.getTime()+" -- " + teste.getTime());
	    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000*5, alarmIntent); 
	    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , alarmIntent);
	}
	
}
