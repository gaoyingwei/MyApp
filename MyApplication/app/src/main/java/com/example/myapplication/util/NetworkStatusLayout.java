package com.example.myapplication.util;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class NetworkStatusLayout extends LinearLayout {
    public NetworkStatusLayout(Context context) {
        super(context);
        inflate(context, R.layout.layout_network_status, this);
    }
}
