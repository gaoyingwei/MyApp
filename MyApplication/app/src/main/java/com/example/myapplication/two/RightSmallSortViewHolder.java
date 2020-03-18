package com.example.myapplication.two;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class RightSmallSortViewHolder extends SimpleViewHolder<SortItem>{
    private TextView textView;

    public RightSmallSortViewHolder(View itemView, @Nullable SimpleRecyclerAdapter<SortItem> adapter) {
        super(itemView, adapter);
        textView = itemView.findViewById(R.id.tv_small);
    }

    @Override
    protected void refreshView(SortItem data) {
        textView.setText(data.name);
    }
}
