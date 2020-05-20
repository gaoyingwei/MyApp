package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.example.myapplication.R;
import com.example.myapplication.util.TestBaseActivity;

import java.util.ArrayList;

public class SearcherActivity extends TestBaseActivity {
    AutoFlowLayout autoFlowLayout;
    TextView textView_delete;
    EditText editText;
    Button button_clear;
    TextView textView;
    private ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setCheckNetworkStatusChangeListenerEnable(true);
        editText=findViewById(R.id.et_search);
        button_clear=findViewById(R.id.bt_clear);
        autoFlowLayout=findViewById(R.id.flowLayout);
        textView_delete=findViewById(R.id.delete);
        textView=findViewById(R.id.text_search);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_delete.setVisibility(View.VISIBLE);
                String s = editText.getText().toString().trim();
                list.add(s);
                addData(list);
                editText.setText("");
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    // TODO: 2018/6/27 0027 进行相应的搜索操作
                    textView_delete.setVisibility(View.VISIBLE);
                    String s = editText.getText().toString().trim();
                    list.add(s);
                    addData(list);
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });
        textView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_delete.setVisibility(View.GONE);
                editText.getText().clear();
                list.clear();
                autoFlowLayout.removeAllViews();

            }
        });
    }
    private void addData(final ArrayList<String> list) {
        //流式布局适配器
        autoFlowLayout.setAdapter(new FlowAdapter(list) {
            @Override
            public View getView(int i) {
                //引入视图
                View inflate = LayoutInflater.from(SearcherActivity.this).inflate(R.layout.item_flowlayout, null, false);
                //获取视图控件
                TextView auto_tv = inflate.findViewById(R.id.auto_tv);
                //修改值
                auto_tv.setText(list.get(i));
                //清空当前集合
                list.clear();
                //返回视图
                return inflate;
            }
        });
    }
}
