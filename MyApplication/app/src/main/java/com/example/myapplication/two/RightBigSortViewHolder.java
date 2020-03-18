package com.example.myapplication.two;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class RightBigSortViewHolder  extends SimpleViewHolder<SortItem>{
    TextView tvTitle;

    public RightBigSortViewHolder(View itemView, @Nullable SimpleRecyclerAdapter<SortItem> adapter) {
        super(itemView, adapter);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }

    @Override
    protected void refreshView(SortItem data) {
        tvTitle.setText(data.name);
    }

}
