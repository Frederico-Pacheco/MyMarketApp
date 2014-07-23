package br.com.mymarket.infra;

import android.util.Log;

public class MyLog {
    public static void i(Object o) {
        Log.i("[MyMarket]", o.toString());
    }

    public static void e(Object o) {
        Log.e("[MyMarket]", o.toString());
    }
}
