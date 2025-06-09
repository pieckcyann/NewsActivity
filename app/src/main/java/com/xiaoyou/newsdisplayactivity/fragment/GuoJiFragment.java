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

public class GuoJiFragment extends Fragment {
    RecyclerView guoJiRecyclerView;
    RecyclerViewAdapter guoJiMyAdapter;
    List<NewsItem> guoJiNewsList;

    SmartRefreshLayout guoJiSmartRefresh;

    public GuoJiFragment() {
        super(R.layout.fragment_guoji);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guoJiRecyclerView = view.findViewById(R.id.fragment_guoji);
        guoJiNewsList = JsonUtils.parseNewsJsonByRequest("guonei");

        guoJiMyAdapter = new RecyclerViewAdapter(guoJiNewsList, getContext());
        guoJiRecyclerView.setAdapter(guoJiMyAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        guoJiRecyclerView.setLayoutManager(layoutManager);

        guoJiSmartRefresh = view.findViewById(R.id.guoji_smart_refresh_layout);

        guoJiSmartRefresh.setOnRefreshListener(refreshLayout -> {
            List<NewsItem> latestNews = JsonUtils.parseNewsJsonByRequest("guoji");

            if (latestNews != null && !latestNews.isEmpty()) {
                guoJiNewsList.clear();
                guoJiNewsList.addAll(latestNews);
                guoJiMyAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "刷新失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            refreshLayout.finishRefresh(true);
        });

        guoJiSmartRefresh.setOnLoadMoreListener(refreshLayout -> {
            Toast.makeText(getContext(), "加载中...", Toast.LENGTH_SHORT).show();

            refreshLayout.getLayout().postDelayed(() -> {
                refreshLayout.finishLoadMore();
                Toast.makeText(getContext(), "加载完毕！", Toast.LENGTH_SHORT).show();
            }, 1000);
        });
    }

}
