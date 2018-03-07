package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.bean.MovieBasicData;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.ui.contract.MovieDetailContract;
import com.example.chenguang.doudou.ui.presenter.MovieDetailPresenter;
import com.example.chenguang.doudou.utils.BitmapUtils;
import com.example.chenguang.doudou.utils.StatusBarUtils;
import com.example.chenguang.doudou.widget.ExpandTextView;

import java.util.List;

import butterknife.BindView;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {
    @BindView(R.id.scroll_view)
    NestedScrollView scroll_view;
    @BindView(R.id.ll_title)
    LinearLayout ll_title;
    @BindView(R.id.status_bar_bg)
    View status_bar_bg;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_bar_title)
    TextView tv_bar_title;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    /**
     * movieBasicData相关
     */
    @BindView(R.id.rl_cover)
    RelativeLayout rl_cover;
    @BindView(R.id.iv_cover)
    ImageView iv_cover;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_pub_time)
    TextView tv_pub_time;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    @BindView(R.id.tv_rate)
    TextView tv_rate;
    @BindView(R.id.rating_bar)
    RatingBar rating_bar;
    @BindView(R.id.tv_rating_num)
    TextView tv_rating_num;
    @BindView(R.id.tv_intro)
    ExpandTextView tv_intro;
    @BindView(R.id.tv_want_to_see)
    TextView tv_want_to_see;
    @BindView(R.id.ll_read)
    LinearLayout tv_read;
    /**
     * 相关影人和剧照
     */
    @BindView(R.id.tv_actor)
    TextView tv_actor;
    @BindView(R.id.rv_actor)
    RecyclerView rv_actor;
    @BindView(R.id.tv_photos)
    TextView tv_photos;
    @BindView(R.id.rv_photos)
    RecyclerView rv_photos;

    MovieDetailPresenter mPresenter;

    private String mId;
    private Bitmap mTitleBitmap;
    private String mTitle;

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setStatusBarColor() {

    }

    @Override
    public void initData() {
        mId = getIntent().getStringExtra("id");
        mPresenter = new MovieDetailPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView() {
        initStatusBar();
        initListener();
        this.showDialog();
        mPresenter.getMovieData(mId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void complete() {

    }

    @Override
    public void error(String e) {

    }

    private void initStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        int statusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
        LinearLayout.LayoutParams status_bar_params = (LinearLayout.LayoutParams) status_bar_bg
                .getLayoutParams();
        status_bar_params.height = statusBarHeight;
        status_bar_bg.setLayoutParams(status_bar_params);

        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) ll_title
                .getLayoutParams();
        //将Title布局往上移动状态栏的高度
        title_params.setMargins(0, -statusBarHeight, 0, 0);
        ll_title.setLayoutParams(title_params);

        RelativeLayout.LayoutParams sv_params = (RelativeLayout.LayoutParams) scroll_view
                .getLayoutParams();
        sv_params.setMargins(0, -statusBarHeight, 0, 0);
        scroll_view.setLayoutParams(sv_params);

    }

    private void initListener() {

        scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
                    oldScrollX, int oldScrollY) {
                float scale = (float) scrollY / (rl_cover.getHeight() - ll_title.getHeight());

                //变化范围0-100 表示从透明到纯色背景
                int alpha = scale * 100 >= 100 ? 100 : (int) (scale * 100);
                if (mTitleBitmap != null) {
                    BitmapDrawable bd = new BitmapDrawable(BitmapUtils.getTransparentBitmap
                            (mTitleBitmap, alpha));
                    ll_title.setBackgroundDrawable(bd);
                }
                if (alpha >= 100) {
                    tv_bar_title.setText(mTitle);
                } else {
                    tv_bar_title.setText("电影");
                }
            }
        });
    }

    @Override
    public void showMovieBasicData(MovieBasicData data) {
        //设置title
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("分享");
            }
        });
        //设置电影的基本信息
        setInfoMovie(data);
        //设置相关影人
        setRelActors(data);
        //设置相关剧照
        setStagePhotos(data);
    }

    private void setInfoMovie(final MovieBasicData data) {
        Glide.with(this)
                .load(data.coverImg)
                .into(iv_cover);
        mTitle = data.title;
        tv_title.setText(mTitle);
        //2017 / 剧情 / 犯罪
        String type = data.age + " / ";
        for (int i = 0; i < data.types.size(); i++) {
            if (i == data.types.size() - 1) {
                type += data.types.get(i);
                break;
            }
            type += data.types.get(i) + " / ";
        }
        tv_type.setText(type);
        tv_pub_time.setText(String.format("上映时间：%s", data.age));
        tv_duration.setText(String.format("片长：%d分钟", data.duration));
        tv_rate.setText(data.rate + "");
        rating_bar.setRating(data.rate / 2);
        tv_rating_num.setText(String.format("%d人", data.ratingNum));
        //设置简介
        tv_intro.setText(data.introduction);
        tv_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_intro.setText(data.introduction, true);
            }
        });
        mPresenter.getImageBitmapColor(data.coverImg);
    }


    private void setStagePhotos(MovieBasicData data) {
        if (data.stagePhotos.get(0) != null) {
            tv_photos.setText("预告片 / 剧照");
        } else {
            tv_photos.setText("剧照");
        }
        rv_photos.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .HORIZONTAL, false));
        BaseQuickAdapter photoAdapter;
        rv_photos.setAdapter(photoAdapter = new BaseQuickAdapter<MovieBasicData.Stage,
                BaseViewHolder>(R.layout
                .item_movie_detail_stages, data.stagePhotos) {

            @Override
            protected void convert(BaseViewHolder helper, MovieBasicData.Stage item) {
                if (item.isPreview) {
                    helper.getView(R.id.iv_play).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.iv_play).setVisibility(View.INVISIBLE);
                }
                Glide.with(mContext)
                        .load(item.imgUrl)
                        .into((ImageView) helper.getView(R.id.iv_stage));
            }

            @Override
            public int addFooterView(View footer) {
                return addFooterView(footer, 0, LinearLayoutManager.HORIZONTAL);
            }
        });
        View footerView = getLayoutInflater().inflate(R.layout.item_movie_detail_footer,
                (ViewGroup) rv_actor.getParent(), false);
        photoAdapter.addFooterView(footerView);
        photoAdapter.setFooterViewAsFlow(true);
        ((TextView) footerView.findViewById(R.id.tv_all)).setText("全部剧照");
        ((TextView) footerView.findViewById(R.id.tv_all_num)).setText(data.stageNum + "张");
        photoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieBasicData.Stage stage = (MovieBasicData.Stage) adapter.getData().get(position);
                if (stage.isPreview) {
                    //是预告片
                    PreviewDetailActivity.startActivity(mContext, stage.id);
                } else {

                }
            }
        });
    }


    private void setRelActors(MovieBasicData data) {
        tv_actor.setText("影人");
        rv_actor.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .HORIZONTAL, false));
        BaseQuickAdapter actorAdapter;
        rv_actor.setAdapter(actorAdapter = new BaseQuickAdapter<MovieBasicData.ActorBean,
                BaseViewHolder>(R.layout.item_movie_detail_actor, data.actors) {
            @Override
            protected void convert(BaseViewHolder helper, MovieBasicData.ActorBean item) {
                Glide.with(mContext)
                        .load(item.imgUrl)
                        .into((ImageView) helper.getView(R.id.iv_actor));
                helper.setText(R.id.tv_name, item.name);
                helper.setText(R.id.tv_role, item.role);
            }

            @Override
            public int addFooterView(View footer) {
                return addFooterView(footer, 0, LinearLayoutManager.HORIZONTAL);
            }
        });
        View footerView = getLayoutInflater().inflate(R.layout.item_movie_detail_footer,
                (ViewGroup) rv_actor.getParent(), false);
        actorAdapter.addFooterView(footerView);
        actorAdapter.setFooterViewAsFlow(true);
        ((TextView) footerView.findViewById(R.id.tv_all)).setText("全部");
        ((TextView) footerView.findViewById(R.id.tv_all_num)).setText(data.actorNums + "人");
    }

    @Override
    public void showActorPhotos() {

    }

    @Override
    public void showStill(List<String> imgList) {

    }

    @Override
    public void showBackground(Bitmap bitmap) {
        mTitleBitmap = bitmap;
        rl_cover.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }
}
