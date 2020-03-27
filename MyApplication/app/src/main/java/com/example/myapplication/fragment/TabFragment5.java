package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.weight.RefreshAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabFragment5 extends Fragment {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<String> mDatas = new ArrayList<>();
    private RefreshAdapter mRefreshAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_fragment5,container,false);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }
    private void initView() {

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GRAY);

    }

    private void initData() {

        for (int i = 0; i < 10; i++) {

            mDatas.add("人生若只如初见"+i);
        }

        initRecylerView();
    }

    @SuppressLint("WrongConstant")
    private void initRecylerView() {

        mRefreshAdapter = new RefreshAdapter(getActivity(),mDatas);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);



        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRefreshAdapter);
    }

    private void initListener() {

        initPullRefresh();

        initLoadMoreListener();

    }



    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> headDatas = new ArrayList<String>();
                        for (int i = 20; i <30 ; i++) {

                            headDatas.add("我愿意停留在昨天"+i);
                        }
                        mRefreshAdapter.AddHeaderItem(headDatas);

                        //刷新完成
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "更新了 "+headDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                    }

                }, 3000);

            }
        });
    }

    private void initLoadMoreListener() {

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==mRefreshAdapter.getItemCount()){

                    //设置正在加载更多
                    mRefreshAdapter.changeMoreStatus(mRefreshAdapter.LOADING_MORE);

                    //改为网络请求
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //
                            List<String> footerDatas = new ArrayList<String>();
                            for (int i = 0; i< 10; i++) {

                                footerDatas.add("十步杀一人,千里不留行" + i);
                            }
                            mRefreshAdapter.AddFooterItem(footerDatas);
                            //设置回到上拉加载更多
                            mRefreshAdapter.changeMoreStatus(mRefreshAdapter.PULLUP_LOAD_MORE);
                            //没有加载更多了
                            //mRefreshAdapter.changeMoreStatus(mRefreshAdapter.NO_LOAD_MORE);
                            Toast.makeText(getActivity(), "更新了 "+footerDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);


                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });

    }
}
