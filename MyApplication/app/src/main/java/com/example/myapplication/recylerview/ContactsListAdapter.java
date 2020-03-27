package com.example.myapplication.recylerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class ContactsListAdapter extends RecyclerView.Adapter {
    private ArrayList<ShareContactsBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnContactsBeanClickListener mOnClickListener;

    public ContactsListAdapter(LayoutInflater layoutInflater, ArrayList<ShareContactsBean> list) {
        this.mList = list;
        mLayoutInflater = layoutInflater;
    }

    public void setOnContactsBeanClickListener(OnContactsBeanClickListener listener) {
        mOnClickListener = listener;
    }

    private ShareContactsBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactsViewHolder(
                mLayoutInflater.inflate(R.layout.listitem_share_contact_content, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShareContactsBean target = getItem(position);
        if (holder instanceof ContactsViewHolder) {
            ((ContactsViewHolder) holder).bindBean(target);
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ContactsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTx;
        private TextView phoneTv;

        ContactsViewHolder(View itemView) {
            super(itemView);
            nameTx = (TextView) itemView.findViewById(R.id.list_item_contact_name);
            phoneTv = (TextView) itemView.findViewById(R.id.list_item_contact_number);
        }

        void bindBean(final ShareContactsBean bean) {
            nameTx.setText(bean.getName());
            phoneTv.setText(bean.getPhone());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onContactsBeanClicked(bean);
                }
            });
        }
    }

    interface OnContactsBeanClickListener {
        void onContactsBeanClicked(ShareContactsBean bean);
    }
}
