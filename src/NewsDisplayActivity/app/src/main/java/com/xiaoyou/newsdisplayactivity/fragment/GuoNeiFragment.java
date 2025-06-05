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

public class GuoNeiFragment extends Fragment {
    RecyclerView guoNeiRecyclerView;
    RecyclerViewAdapter guoNeiMyAdapter;
    List<NewsItem> guoNeiNewsList;
    SmartRefreshLayout guoNeiSmartRefresh;

    public GuoNeiFragment() {
        super(R.layout.fragment_guonei);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guoNeiRecyclerView = view.findViewById(R.id.fragment_guonei);
        guoNeiNewsList = JsonUtils.parseNewsJson(getContext(), "guonei_2025_06_04.json");

        guoNeiMyAdapter = new RecyclerViewAdapter(guoNeiNewsList, getContext());
        guoNeiRecyclerView.setAdapter(guoNeiMyAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        guoNeiRecyclerView.setLayoutManager(layoutManager);

        guoNeiSmartRefresh = view.findViewById(R.id.guonei_smart_refresh_layout);

        guoNeiSmartRefresh.setOnRefreshListener(refreshLayout -> {
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
