package com.example.myapplication.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class ConstomDialog extends Dialog {
    /**取消按钮*/
    private Button button_cancel;

    /**确认按钮*/
    private Button button_exit;

    /**标题文字*/
    private EditText tv;
    MyCallBack myCallBack;
    //构造方法
    public ConstomDialog(Context context) {
        super(context, R.style.mdialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_layout, null);

        tv = (EditText) view.findViewById(R.id.title);
        button_cancel = (Button) view.findViewById(R.id.btn_cancel);
        button_exit = (Button) view.findViewById(R.id.btn_exit);
        tv.setText(tv.getText().toString());
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
            public void onClick(View v) {
                String s = tv.getText().toString();
                if (s.equals("")){
                    Toast.makeText(context, "请输入昵称", Toast.LENGTH_SHORT).show();
                }else {
                    myCallBack.call(s);
                }

            }
        });
    }

    public  interface  MyCallBack{
        void  call(String connect);
    }
    public void setMyCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }
}
