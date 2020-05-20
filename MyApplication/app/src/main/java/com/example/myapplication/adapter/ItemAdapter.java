package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder>{

    OnClickListener onClickListener;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        //点击事件里面调用click(position);
       // Fragment里面调用的时候直接用itemAdapter.setOnClickListener()就出来了。
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder{

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public  interface OnClickListener{
        void click(int postion);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
