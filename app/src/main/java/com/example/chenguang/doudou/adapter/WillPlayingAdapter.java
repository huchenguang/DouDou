package com.example.chenguang.doudou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.MoviePlaying;
import com.example.chenguang.doudou.bean.WillPlayingSection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguang on 2018/2/9.
 */

public class WillPlayingAdapter extends SectioningAdapter {
    private List<WillPlayingSection> mData = new ArrayList<>();

    private Context mContext;
    private final DecimalFormat df;

    public WillPlayingAdapter(Context context) {
        this.mContext = context;
        df = new DecimalFormat("0.0万人");
    }

    public void addNewData(List<WillPlayingSection> data) {
        this.mData = data;
        this.notifyAllSectionsDataSetChanged();
    }

    public List<WillPlayingSection> getData() {
        return mData;
    }

    class MyItemViewHolder extends ItemViewHolder {

        ImageView iv_cover;
        TextView tv_title;
        TextView tv_director;
        TextView tv_actors;
        TextView tv_buy;
        TextView tv_num;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            iv_cover = itemView.findViewById(R.id.iv_cover);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_director = itemView.findViewById(R.id.tv_director);
            tv_actors = itemView.findViewById(R.id.tv_actors);
            tv_buy = itemView.findViewById(R.id.tv_buy);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }

    class MyHeaderViewHolder extends HeaderViewHolder {

        private final TextView tv_play_time;

        public MyHeaderViewHolder(View itemView) {
            super(itemView);
            tv_play_time = itemView.findViewById(R.id.tv_play_time);
        }
    }

    @Override
    public int getNumberOfSections() {
        return mData.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mData.get(sectionIndex).items.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        View view = View.inflate(parent.getContext(), R.layout.item_will_playing, null);
        return new MyItemViewHolder(view);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        View view = View.inflate(parent.getContext(), R.layout.item_will_playing_header, null);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex,
                                     int itemUserType) {
        final MoviePlaying item = mData.get(sectionIndex).items.get(itemIndex);
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
        Glide.with(mContext)
                .load(item.coverImg)
                .placeholder(R.mipmap.movie_default_medium)
                .into(holder.iv_cover);
        holder.tv_title.setText(item.title);
        holder.tv_director.setText(String.format("类型：%s", item.type));

        holder.tv_actors.setText(String.format("所属国家：%s", item.region));

        holder.tv_buy.setText("想看");
        String str_num;
        if (item.wantToSeeNum >= 10000) {
            float num = item.wantToSeeNum / 10000.0f;
            str_num = df.format(num);
        } else {
            str_num = item.wantToSeeNum + "人";
        }
        holder.tv_num.setText(String.format("%s想看", str_num));
        //添加监听事件
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(item);
                }
            }
        });
        holder.tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemChildClickListener != null) {
                    mItemChildClickListener.OnItemClick(item);
                }
            }
        });
    }


    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int
            headerUserType) {
        String s_date = mData.get(sectionIndex).HeadTitle;
        /*try {
            //格式化日期
            Date date = fDate.parse(s_date);
            if (date.getYear() == new Date().getYear()) {
                s_date = s_date.substring(s_date.indexOf("年") + 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        ((MyHeaderViewHolder) viewHolder).tv_play_time.setText(s_date);
    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(MoviePlaying item);
    }

    private OnItemChildClickListener mItemChildClickListener;

    public interface OnItemChildClickListener {
        void OnItemClick(MoviePlaying item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.mItemChildClickListener = listener;
    }
}
