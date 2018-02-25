package com.example.chenguang.doudou.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.MySection;

import java.util.List;

/**
 * Created by chenguang on 2018/2/3.
 */

public class MultiAddressAdapter extends SectioningAdapter {
    List<MySection> sections;
    Context mContext;
    public static final int DEFAULT_USER_HEADER_TYPE = 0;

    public static final int USER_ITEM_TYPE_GPS = 0;
    public static final int USER_ITEM_TYPE_HOT_CITY = 1;
    public static final int USER_ITEM_TYPE_ADDRESS = 2;

    public class ItemViewHolder0 extends SectioningAdapter.ItemViewHolder {
        TextView tv_gps;

        public ItemViewHolder0(View itemView) {
            super(itemView);
            tv_gps = itemView.findViewById(R.id.tv_gps);
        }
    }

    public class ItemViewHolder1 extends SectioningAdapter.ItemViewHolder {
        RecyclerView rv_hot_city;

        public ItemViewHolder1(View itemView) {
            super(itemView);
            rv_hot_city = itemView.findViewById(R.id.rv_hot_city);
        }
    }

    public class ItemViewHolder2 extends SectioningAdapter.ItemViewHolder {
        TextView tv_address;

        public ItemViewHolder2(View itemView) {
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

    public MultiAddressAdapter(Context context, List<MySection> sections) {
        this.mContext = context;
        this.sections = sections;
    }

    public void setNewData(List<MySection> sections) {
        this.sections = sections;
        notifyAllSectionsDataSetChanged();
    }

    public List<MySection> getData() {
        return sections;
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).items.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public int getSectionHeaderUserType(int sectionIndex) {
        return sections.get(sectionIndex).type;
    }

    @Override
    public int getSectionItemUserType(int sectionIndex, int itemIndex) {
        MySection section = sections.get(sectionIndex);
        return section.items.get(itemIndex).type;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (itemType) {
            case USER_ITEM_TYPE_GPS:
                ItemViewHolder0 itemViewHolder0 = new ItemViewHolder0(inflater.inflate(R.layout
                        .item_section_gps, parent, false));
                return itemViewHolder0;
            case USER_ITEM_TYPE_HOT_CITY:
                return new ItemViewHolder1(inflater.inflate(R.layout.item_section_hot_city,
                        parent, false));
            case USER_ITEM_TYPE_ADDRESS:
                return new ItemViewHolder2(inflater.inflate(R.layout.item_section_address,
                        parent, false));
        }

        throw new IllegalArgumentException("Unrecognized itemType: " + itemType);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new MyHeaderViewHolder(inflater.inflate(R.layout.header_section_letter, parent,
                false));
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int
            sectionIndex, final int itemIndex, int itemType) {
        final MySection s = sections.get(sectionIndex);

        switch (itemType) {
            case USER_ITEM_TYPE_GPS: {
                ((ItemViewHolder0) viewHolder).tv_gps.setText(s.items.get(itemIndex).title);
                ((ItemViewHolder0) viewHolder).tv_gps.setOnClickListener(new View.OnClickListener
                        () {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onClick(s.items.get(itemIndex).title);
                        }
                    }
                });
                break;
            }
            case USER_ITEM_TYPE_HOT_CITY: {
                ItemViewHolder1 ivh1 = (ItemViewHolder1) viewHolder;
                ivh1.rv_hot_city.setLayoutManager(new GridLayoutManager(mContext, 3));
                HotCityAdapter hotCityAdapter;
                ivh1.rv_hot_city.setAdapter(hotCityAdapter = new HotCityAdapter(s.items.get
                        (itemIndex).titles));
                hotCityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mListener != null) {
                            mListener.onClick((String) adapter.getData().get(position));
                        }
                    }
                });

                break;
            }
            case USER_ITEM_TYPE_ADDRESS: {
                ((ItemViewHolder2) viewHolder).tv_address.setText(s.items.get(itemIndex).title);
                ((ItemViewHolder2) viewHolder).tv_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onClick(s.items.get(itemIndex).title);
                        }
                    }
                });
                break;
            }

            default:
                throw new IllegalArgumentException("Unrecognized itemType: " + itemType);
        }

    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int
            headerUserType) {
        ((MyHeaderViewHolder) viewHolder).tv_letter.setText(sections.get
                (sectionIndex).title);
    }

    private OnItemViewClickListener mListener;

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemViewClickListener {
        void onClick(String address);
    }
}
