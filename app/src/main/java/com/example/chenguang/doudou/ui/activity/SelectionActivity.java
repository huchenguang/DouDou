package com.example.chenguang.doudou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chenguang.doudou.R;
import com.example.chenguang.doudou.adapter.AddressAdapter;
import com.example.chenguang.doudou.adapter.layoutmanager.StickyHeaderLayoutManager;
import com.example.chenguang.doudou.ui.base.BaseActivity;
import com.example.chenguang.doudou.utils.SortUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SelectionActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    ArrayList<String> mCityNames;
    private HashMap<String, List<String>> mMap;
    private AddressAdapter mAdapter;

    public static void startActivity(Context context, ArrayList<String> cityNames) {
        Intent intent = new Intent(context, SelectionActivity.class);
        intent.putStringArrayListExtra("citys", cityNames);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selection;
    }

    @Override
    public void initData() {
        mCityNames = getIntent().getStringArrayListExtra("citys");
        mMap = SortUtil.getHashMap(mCityNames);
    }

    @Override
    public void initView() {
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycler_view.setLayoutManager(new StickyHeaderLayoutManager());
        recycler_view.setAdapter(mAdapter = new AddressAdapter(mMap));
        mAdapter.notifyAllSectionsDataSetChanged();
        mAdapter.setOnItemViewClickListener(new AddressAdapter.OnItemViewClickListener() {
            @Override
            public void onClick(int sectionIndex, int itemIndex) {
                System.out.println("你好");
            }
        });
    }
}
