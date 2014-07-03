package br.com.mymarket.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.R;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.infra.MyLog;

public abstract class AppBaseActivity extends Activity {
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "br.com.mymarket.activities.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	protected ReceiverDelegate evento;

	private static IntentFilter createIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	
	
	
	protected void set(){
		AdRequest request = new AdRequest.Builder();
		request.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        request.build();
		AdView adView = (AdView) findViewById(R.id.ad);
		adView.loadAd(request);
	}
	

	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}

	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	
	
    @Override
    public void onDestroy(){
        super.onDestroy();
        MyLog.i("DESTRUIU O PICO");
        this.evento.desregistra(getMyMarketApplication());
        unRegisterBaseActivityReceiver();
    }

	public class BaseActivityReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)) {
				finish();
			}
		}
	}

	protected void closeAllActivities() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.setTitle((String)getString(R.string.app_name));
		alertDialog.setMessage((String)getString(R.string.comum_sair_app));
		alertDialog.setPositiveButton((String)getString(R.string.comum_sim), new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
			}
		});
		alertDialog.setNegativeButton((String)getString(R.string.comum_nao), null);
		alertDialog.show();
	}
	
    public MyMarketApplication getMyMarketApplication(){
        return (MyMarketApplication) getApplication();
    }
    
	public void processarException(Exception e) {
		Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();	
	}
}
