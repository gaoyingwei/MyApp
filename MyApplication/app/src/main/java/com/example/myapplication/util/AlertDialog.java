package com.example.myapplication.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class AlertDialog extends Dialog {
    /**取消按钮*/
    private Button button_cancel;

    /**确认按钮*/
    private Button button_exit;
    //构造方法
    public AlertDialog(Context context) {
        super(context, R.style.mdialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.view_alertdialog, null);

        button_cancel = (Button) view.findViewById(R.id.btn_neg);
        button_exit = (Button) view.findViewById(R.id.btn_pos);
        //设置显示的视图
        setContentView(view);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {dismiss();

            }
        });
    }
}
