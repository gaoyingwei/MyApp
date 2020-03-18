package com.example.myapplication.two;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class LeftAdapter extends SimpleRecyclerAdapter<SortBean>  {
    private int mSelectedPosition;

    @Override
    public SimpleViewHolder<SortBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_search_sort_left, parent, false), this);
    }

    public void setSelectedPosition(int position) {
        mListData.get(mSelectedPosition).isSelected = false;
        notifyItemChanged(mSelectedPosition);
        mListData.get(position).isSelected = true;
        notifyItemChanged(position);
        mSelectedPosition = position;
    }
}
