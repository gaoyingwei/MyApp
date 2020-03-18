package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class TopWindowUtils {
    private static PopupWindow popupWindow;

    public static void show(final Activity activity, final CharSequence text) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = new LinearLayout(activity);
            View viewContent = inflater.inflate(R.layout.item, linearLayout);
            popupWindow.setContentView(viewContent);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.PopupTopAnim);
        }
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);

        final TextView tv = ((TextView) popupWindow.getContentView().findViewById(R.id.tv));
        tv.setText(text);
        popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, ""+tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1, 5000);
    }

    public static void dismiss(){
        popupWindow.dismiss();
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popupWindow.dismiss();
        }
    };

    /**
     * 从dp单位转换为px
     *
     * @param dp dp值
     * @return 返回转换后的px值
     */
    private static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
