package com.example.myapplication.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabFragmentAdapter;
import com.example.myapplication.ui.SearcherActivity;
import com.example.myapplication.ui.TopWindowUtils;
import com.example.myapplication.util.LoadingDialog;
import com.example.myapplication.video.VideoAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class Message_Fragment extends Fragment {
    private ViewPager main_frag_1_main_ViewPager;
    private TabLayout TopNavTabLayout;
    TextView textView_type;
    private String[] TabTitle = {"热门推荐", "体育", "新闻", "爱情", "小说", "军事", "政治"};
    private List<Fragment> TabFragmentList = new ArrayList<>();
    private List<String> TabTitleList = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;
    EditText editText;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(getActivity(),"加载中...",R.mipmap.ic_dialog_loading);
        loadingDialog.show();
        loadingDialog.setCanceledOnTouchOutside(true);
        for (int i = 0; i < TabTitle.length; i++) {
            TabTitleList.add(TabTitle[i]);
        }
        TabFragmentList.add(new TabFragment1());
        TabFragmentList.add(new TabFragment2());
        TabFragmentList.add(new TabFragment3());
        TabFragmentList.add(new TabFragment4());
        TabFragmentList.add(new TabFragment5());
        TabFragmentList.add(new TabFragment6());
        TabFragmentList.add(new TabFragment7());

        tabFragmentAdapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager(), TabFragmentList, TabTitleList);
        main_frag_1_main_ViewPager.setAdapter(tabFragmentAdapter);
        TopNavTabLayout.setupWithViewPager(main_frag_1_main_ViewPager);
        TopNavTabLayout.setTabIndicatorFullWidth(false);
        TopNavTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#3D87FB"));
        TopNavTabLayout.setTabTextColors(Color.GRAY,Color.parseColor("#3D87FB"));
        TopNavTabLayout.setTabRippleColor(ColorStateList.valueOf(getContext().getResources().getColor(R.color.qq_white)));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearcherActivity.class));
            }
        });
        textView_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopWindowUtils.show(getActivity(),"个任务下载完成");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, null);
        main_frag_1_main_ViewPager = view.findViewById(R.id.main_frag_1_main);
        TopNavTabLayout = view.findViewById(R.id.TopNav);
        editText=view.findViewById(R.id.et_searchs);
        textView_type=view.findViewById(R.id.text_search);
        return view;

    }

}
