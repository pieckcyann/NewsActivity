package com.xiaoyou.newsdisplayactivity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.xiaoyou.newsdisplayactivity.R;
import com.xiaoyou.newsdisplayactivity.bean.NewsItem;
import com.xiaoyou.newsdisplayactivity.recyclerview.RecyclerViewAdapter;
import com.xiaoyou.newsdisplayactivity.utils.JsonUtils;

import java.util.List;

public class TopFragment extends Fragment {
    RecyclerView topRecyclerView;
    RecyclerViewAdapter topMyAdapter;
    List<NewsItem> topNewsList;

    SmartRefreshLayout topSmartRefresh;

    public TopFragment() {
        super(R.layout.fragment_top);
    }

    // 绘制完成时
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // topRecyclerView = view.findViewById(R.id.fragment_top).findViewById(R.id.recyclear_view_include);
        topRecyclerView = view.findViewById(R.id.fragment_top);
        topNewsList = JsonUtils.parseNewsJson(getContext(), "top_2025_06_03.json");

        // 添加 RecyclerView Adapter
        topMyAdapter = new RecyclerViewAdapter(topNewsList, getContext());
        topRecyclerView.setAdapter(topMyAdapter);

        // 设置 RecyclerView Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        topRecyclerView.setLayoutManager(layoutManager);

        // 设置下拉刷新与上拉加载
        topSmartRefresh = view.findViewById(R.id.top_smart_refresh_layout);

        topSmartRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(true);// 传入false 表示刷新失败
        });

        topSmartRefresh.setOnLoadMoreListener(refreshLayout -> {
            Toast.makeText(getContext(), "加载中...", Toast.LENGTH_SHORT).show();

            refreshLayout.getLayout().postDelayed(() -> {
                refreshLayout.finishLoadMore(); // 本次记载完成，下次仍可继续加载
                // // 关闭底部加载动画 + 设置“不再加载”
                // refreshLayout.finishLoadMoreWithNoMoreData(); // 将不会再触发加载事件
                Toast.makeText(getContext(), "加载完毕！", Toast.LENGTH_SHORT).show();
            }, 1000);
        });

    }

}
