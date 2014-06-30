package br.com.mymarket.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.activities.MainActivity;
import br.com.mymarket.infra.MyLog;

public class OauthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_oauth, null);
        final MainActivity activity = ((MainActivity)this.getActivity());
        TelephonyManager tMgr = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        String myPhoneNumber = tMgr.getLine1Number();
    	((EditText)view.findViewById(R.id.oat_cellphone)).setText(myPhoneNumber);
        return view;
    }
	
    public void acessarApk(View view) {
    	MyLog.i("CLICK FRAGMENT OAUTH");
    	((MainActivity)this.getActivity()).acessarApk(view);
	}
    
}
