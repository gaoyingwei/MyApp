package com.example.myapplication.util;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestBaseActivity extends AppCompatActivity {
    private NetworkStatusLayout mNetworkStatusLayout;
    private NetBroadcastReceiver receiver;
    private boolean checkNetworkStatusChangeListenerEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test_base);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout mRootLayout = findViewById(R.id.root_linear_layout);
        //将网络状态view添加到根视图
        mNetworkStatusLayout = new NetworkStatusLayout(this);
        //默认隐藏状态
        mNetworkStatusLayout.setVisibility(View.GONE);
        mRootLayout.addView(mNetworkStatusLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //将子类的layout，添加到根目录
        View mContentView = LayoutInflater.from(this).inflate(layoutResID, null);
        mRootLayout.addView(mContentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkNetworkStatusChangeListenerEnable) {
            return;
        }
        receiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setCheckNetworkStatusChangeListenerEnable(boolean checkNetworkStatusChangeListener) {
        this.checkNetworkStatusChangeListenerEnable = checkNetworkStatusChangeListener;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getType().equals("广播网络变化")) {
            if (event.getContect().equals("网络已断开")) {
                if (mNetworkStatusLayout.getVisibility() == View.GONE) {
                    mNetworkStatusLayout.setVisibility(View.VISIBLE);
                }
            } else {
                if (mNetworkStatusLayout.getVisibility() == View.VISIBLE) {
                    mNetworkStatusLayout.setVisibility(View.GONE);
                }
            }
        }
    }

}
