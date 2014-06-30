package br.com.mymarket.activities;

import br.com.mymarket.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class AppBaseActivity extends Activity {
	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "br.com.mymarket.activities.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";

	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();

	public static final IntentFilter INTENT_FILTER = createIntentFilter();

	private static IntentFilter createIntentFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}

	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}

	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
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
}
