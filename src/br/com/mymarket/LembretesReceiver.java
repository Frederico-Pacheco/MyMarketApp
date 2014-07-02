package br.com.mymarket;

import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import br.com.mymarket.constants.Constants;

public class LembretesReceiver extends BroadcastReceiver {
	
	    @Override
	    public void onReceive(Context context, Intent pIntent) {
	        if(pIntent.getAction().equals(Constants.LEMBRETE_ACTION))
            {
	        	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
	            wl.acquire();
	            
	            Notification notification = new Notification.Builder(context.getApplicationContext())
                .setContentTitle("LEMBRETE")
                .setContentText("PAGUE 50 �")
                .setSmallIcon(R.drawable.ic_launcher).build();
		        NotificationManager manager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		        manager.notify(1, notification);
		        
	            wl.release();
            }
	    }
	    
	    
	    private boolean aplicacaoEstaRodando(final Context context){
	        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
	        if(!tasks.isEmpty()){
	            ComponentName topActivity = tasks.get(0).topActivity;
	            if(!topActivity.getPackageName().equals(context.getPackageName())){
	                return false;
	            }
	        }
	        return true;
	    }
}
