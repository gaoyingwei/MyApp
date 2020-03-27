package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.myapplication.R;
import com.example.myapplication.two.ItemType;
import com.example.myapplication.two.LeftAdapter;
import com.example.myapplication.two.MyUtils;
import com.example.myapplication.two.RightAdapter;
import com.example.myapplication.two.SimpleRecyclerAdapter;
import com.example.myapplication.two.SortBean;
import com.example.myapplication.two.SortItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabFragment2 extends Fragment {
    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;

    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private final List<SortBean> mLeftList = new ArrayList<>();

    private final List<SortItem> mRightList = new ArrayList<>();

    private final Map<Integer, Integer> indexMap = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_fragment2,container,false);
        leftRecyclerView = (RecyclerView)view. findViewById(R.id.rv_sort_left) ;
        rightRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sort_right);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView() {

        // 左列表
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((SimpleItemAnimator) leftRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        leftAdapter = new LeftAdapter();
        leftAdapter.setListData(mLeftList);
        leftRecyclerView.setAdapter(leftAdapter);
        // 左侧列表的点击事件
        leftAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<SortBean>() {
            @Override
            public void onItemClick(SortBean item, int index) {
                // 左侧选中并滑到中间位置
                leftAdapter.setSelectedPosition(index);
                MyUtils.moveToMiddle(leftRecyclerView, index);
                // 右侧滑到对应位置
                ((GridLayoutManager)rightRecyclerView.getLayoutManager())
                        .scrollToPositionWithOffset(indexMap.get(index),0);
            }
        });
        // 右列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (mRightList.get(position).viewType == ItemType.BIG_SORT) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rightRecyclerView.setLayoutManager(gridLayoutManager);
        rightAdapter = new RightAdapter();
        rightAdapter.setListData(mRightList);
        rightRecyclerView.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new SimpleRecyclerAdapter.OnItemClickListener<SortItem>() {
            @Override
            public void onItemClick(SortItem item, int index) {
                Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
            }
        });
        //右侧列表的滚动事件
        rightRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //获取右侧列表的第一个可见Item的position
                int topPosition = ((GridLayoutManager) rightRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                // 如果此项对应的是左边的大类的index
                if (mRightList.get(topPosition).position != -1) {
                    MyUtils.moveToMiddle(leftRecyclerView, mRightList.get(topPosition).position);
                    leftAdapter.setSelectedPosition(mRightList.get(topPosition).position);
                }

            }
        });
    }

    {

        // 构造点数据，比如整个数据刚刚好就是从json转过来的，一个Bean里面有一个大类，有若干个小类
        // 左侧的adapter就直接用这个构造好的list
        for (int i = 0; i < 30; i++) {
            SortBean bean = new SortBean();
            bean.bigSortId = i;
            bean.bigSortName = "商品" + i;
            List<SortBean.ListBean> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SortBean.ListBean listBean = new SortBean.ListBean();
                listBean.smallSortId = j;
                listBean.smallSortName = "手机" + j;
                list.add(listBean);
            }
            bean.list = list;
            mLeftList.add(bean);
        }
        // 右侧的list是将每一个大类和小类按次序添加，并且标记大类的位置
        for (int i = 0; i < mLeftList.size(); i++) {
            SortItem bigItem = new SortItem();
            bigItem.viewType = ItemType.BIG_SORT;
            bigItem.id = mLeftList.get(i).bigSortId;
            bigItem.name = mLeftList.get(i).bigSortName;
            // 标记大类的位置，所有项的position默认是-1，如果是大类就添加position，让他和左侧位置对应
            bigItem.position = i;
            mRightList.add(bigItem);
            for (int j = 0; j < mLeftList.get(i).list.size(); j++) {
                SortItem smallItem = new SortItem();
                smallItem.viewType = ItemType.SMALL_SORT;
                smallItem.id = mLeftList.get(i).list.get(j).smallSortId;
                smallItem.name = mLeftList.get(i).list.get(j).smallSortName;
                mRightList.add(smallItem);
            }
        }
        // 点击左侧需要知道对应右侧的位置，用map先保存起来
        for (int i = 0; i < mRightList.size(); i++) {
            if (mRightList.get(i).position != -1) {
                indexMap.put(mRightList.get(i).position, i);
            }
        }

    }
}
