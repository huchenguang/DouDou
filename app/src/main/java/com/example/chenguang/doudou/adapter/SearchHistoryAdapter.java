package com.example.chenguang.doudou.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chenguang.doudou.R;

import java.util.List;

/**
 * Created by chenguang on 2018/1/15.
 */

public class SearchHistoryAdapter extends BaseAdapter implements View.OnClickListener {
    List<String> mData;

    public SearchHistoryAdapter(List<String> data) {
        this.mData = data;
    }

    public void setData(List<String> data) {
        this.mData = data;
    }

    public List<String> getData() {
        return this.mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_search_history, null);
            TextView tv_history = convertView.findViewById(R.id.tv_history);
            tv_history.setOnClickListener(this);
            convertView.setTag(tv_history);
        }
        ((TextView) convertView.getTag()).setText(getItem(position));

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            String history = ((TextView) v).getText().toString();
            mListener.onItemClick(history);
        }
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String history);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
