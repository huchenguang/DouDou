package com.example.chenguang.doudou.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.utils.SortUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chenguang on 2018/1/9.
 */

public class AddressAdapter extends SectioningAdapter {


    private HashMap<String, List<String>> mData;
    private List<String> keys = new ArrayList<>();

    public AddressAdapter(HashMap<String, List<String>> data) {
        this.mData = data;
        for (String s : mData.keySet()) {
            keys.add(s);
        }
        keys = SortUtil.sortByChineseName(keys);
    }


    public void setData(HashMap<String, List<String>> data) {
        this.mData = data;
        keys.clear();
        for (String s : mData.keySet()) {
            keys.add(s);
        }
        keys = SortUtil.sortByChineseName(keys);
    }


    public void clearAll() {
        this.mData.clear();
    }

    public HashMap<String, List<String>> getData() {
        return this.mData;
    }

    public List<String> getKeys() {
        return keys;
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        TextView tv_address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }

    public class MyHeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        TextView tv_letter;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            tv_letter = itemView.findViewById(R.id.tv_letter);
        }
    }


    @Override
    public int getNumberOfSections() {
        return keys.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mData.get(keys.get(sectionIndex)).size();
    }


    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public SectioningAdapter.ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int
            itemUserType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_address,
                null);
        return new ItemViewHolder(view);
    }

    @Override
    public SectioningAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int
            headerUserType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_section_letter, null);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int
            headerUserType) {
        ((MyHeaderViewHolder) viewHolder).tv_letter.setText(keys.get(sectionIndex));

    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, final int
            sectionIndex, final int itemIndex, int itemUserType) {
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClick(sectionIndex, itemIndex);
                }
            }
        });
        System.out.println(keys.get(sectionIndex));
        ((ItemViewHolder) viewHolder).tv_address.setText(mData.get(keys.get(sectionIndex)).get
                (itemIndex));
    }

    private OnItemViewClickListener mListener;

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemViewClickListener {
        void onClick(int sectionIndex, int itemIndex);
    }
}
