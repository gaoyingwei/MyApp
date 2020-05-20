package com.example.myapplication.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

public class NetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new MessageEvent("广播网络变化",getNetworkConnectionType(context)));
    }

    public String getNetworkConnectionType(Context context) {
        //获取连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return "网络已断开";
        //获取网络连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI网络";
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "手机数据网络";
            }
        }
        return "网络已断开";
    }
}
