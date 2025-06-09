package com.xiaoyou.newsdisplayactivity.fragment;

import android.annotation.SuppressLint;
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
import com.xiaoyou.newsdisplayactivity.dto.NewsItem;
import com.xiaoyou.newsdisplayactivity.recyclerview.RecyclerViewAdapter;
import com.xiaoyou.newsdisplayactivity.utils.JsonUtils;

import java.util.List;

public class GuoNeiFragment extends Fragment {
    RecyclerView guoNeiRecyclerView;
    RecyclerViewAdapter guoNeiMyAdapter;
    List<NewsItem> guoNeiNewsList;
    SmartRefreshLayout guoNeiSmartRefresh;

    public GuoNeiFragment() {
        super(R.layout.fragment_guonei);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guoNeiRecyclerView = view.findViewById(R.id.fragment_guonei);
        // guoNeiNewsList = JsonUtils.parseNewsJsonByLocal(getContext(), "guonei_2025_06_04.json");
        guoNeiNewsList = JsonUtils.parseNewsJsonByRequest("guonei");

        guoNeiMyAdapter = new RecyclerViewAdapter(guoNeiNewsList, getContext());
        guoNeiRecyclerView.setAdapter(guoNeiMyAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        guoNeiRecyclerView.setLayoutManager(layoutManager);

        guoNeiSmartRefresh = view.findViewById(R.id.guonei_smart_refresh_layout);

        guoNeiSmartRefresh.setOnRefreshListener(refreshLayout -> {
            // List<NewsItem> latestNews = JsonUtils.parseNewsJsonByLocal(getContext(), "top_2025_06_05.json");
            List<NewsItem> latestNews = JsonUtils.parseNewsJsonByRequest("guonei");

            if (latestNews != null && !latestNews.isEmpty()) {
                guoNeiNewsList.clear();
                guoNeiNewsList.addAll(latestNews);
                guoNeiMyAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "刷新失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            refreshLayout.finishRefresh(true);
        });
        guoNeiSmartRefresh.setOnLoadMoreListener(refreshLayout -> {
            Toast.makeText(getContext(), "加载中...", Toast.LENGTH_SHORT).show();

            refreshLayout.getLayout().postDelayed(() -> {
                refreshLayout.finishLoadMore();
                Toast.makeText(getContext(), "加载完毕！", Toast.LENGTH_SHORT).show();
            }, 1000);
        });
    }

}
