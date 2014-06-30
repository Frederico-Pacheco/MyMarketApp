package br.com.mymarket.infra;

import android.app.Application;
import br.com.mymarket.R;

public class MyServer {
	
    private String uri;

    public MyServer(Application application){
        this.uri = application.getResources().getString(R.string.server_uri);
    }

    public String uriFor(String value) {
        return String.format(this.uri, value);
    }

}
