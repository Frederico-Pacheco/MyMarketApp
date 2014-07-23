package br.com.mymarket.infra;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CheckConnectivity {

	private static final String TAG = "DEBUG >";

	public static boolean checkNetworkStatus(Context context) {
	       ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	       TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	       boolean connected;
	       NetworkInfo info = connMgr.getActiveNetworkInfo();
	       if (info == null) {
	           Log.d(TAG, "checkNetworkStatus() -> No active connection");
	           return false;
	       }
	       int netType = info.getType();
	       connected = info.isConnected();
	       if (netType == ConnectivityManager.TYPE_WIFI) {
	           Log.d(TAG, "checkNetworkStatus() -> WIFI "+ (connected ? "OK" : "NOK"));
	           return connected;
	       } else if (netType == ConnectivityManager.TYPE_MOBILE) {
	           boolean roam = mTelephony.isNetworkRoaming();
	           Log.d(TAG, "checkNetworkStatus() -> 3G "+ (connected ? "OK" : "NOK"));
	           Log.d(TAG, "checkNetworkStatus() -> 3G "+ (roam ? "ROAMING" : "LOCAL"));
	           return connected && !roam;
	       }
	       Log.d(TAG, "checkNetworkStatus() -> Connection type not supported "+ info.getTypeName());
	       return false;
	}

	public static boolean isOffLine(Context context) {
		return checkNetworkStatus(context) == false;
	}
	
}
