package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {
    private XBanner xbanner_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_fragment1,container,false);
        //初始化控件
        xbanner_view = view.findViewById(R.id.xbanner_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //图片集合,模拟一下数据
        final List<String> imgesUrl = new ArrayList<>();
        imgesUrl.add("http://image14.m1905.cn/uploadfile/2018/0907/thumb_1_1380_460_20180907013518839623.jpg");
        imgesUrl.add("http://image14.m1905.cn/uploadfile/2018/0906/thumb_1_1380_460_20180906040153529630.jpg");
        imgesUrl.add("http://image13.m1905.cn/uploadfile/2018/0907/thumb_1_1380_460_20180907114844929630.jpg");
        //数据集合导入banner里
        final List<Integer>xlist=new ArrayList<>();
        xlist.add(R.drawable.f);
        xlist.add(R.drawable.h);
        xlist.add(R.drawable.timg);
        xbanner_view.setData(xlist,null);
        // XBanner适配数据
        xbanner_view.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
               Glide.with(getActivity()).load(xlist.get(position)).into((ImageView) view);
               // Picasso.with(getContext()) .load(imgesUrl.get(position)) .into((ImageView) view);
            }
        });
        xbanner_view.setPageTransformer(Transformer.Depth);  //本页左移，下页从后面出来
        // 设置XBanner页面切换的时间，即动画时长
        xbanner_view.setPageChangeDuration(1000);
    }
}
