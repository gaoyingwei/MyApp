package com.example.myapplication.shop;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void makeText(Context mContext, String text) {

        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        mHandler.postDelayed(r, 2000);

        mToast.show();
    }
}
