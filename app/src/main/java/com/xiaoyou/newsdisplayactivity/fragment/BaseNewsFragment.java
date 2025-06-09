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

public class BaseNewsFragment extends Fragment {
    private static final String ARG_NEWS_TYPE = "news_type";

    private String newsType;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter; // 接收返回的适配器，用于之后更新列表视图
    private List<NewsItem> newsList;
    private int pageStap = 3;
    private int currentPage = 0; // 当前加载的页数
    private SmartRefreshLayout smartRefreshLayout;

    public BaseNewsFragment() {
        super(R.layout.fragment_common); // 通用布局
    }

    // 创建新实例
    public static BaseNewsFragment newInstance(String type) {
        BaseNewsFragment fragment = new BaseNewsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_NEWS_TYPE, type);
        fragment.setArguments(args);

        return fragment; // 返回带参数的 fragment 实例
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取传入的类型参数
        if (getArguments() != null) {
            newsType = getArguments().getString(ARG_NEWS_TYPE, "top"); // 获取 setArguments 值
        }

        recyclerView = view.findViewById(R.id.fragment_recycler_view_common);
        smartRefreshLayout = view.findViewById(R.id.fragment_smart_refresh_layout_common);

        // 初始加载新闻
        loadInitialNews();

        // 下拉刷新 监听器
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            loadNewData(smartRefreshLayout);
        });

        // 上拉加载 监听器
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            loadMoreData(smartRefreshLayout);
        });
    }

    private void loadInitialNews() {
        new Thread(() -> {
            List<NewsItem> currentNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_06.json", 0, pageStap);

            requireActivity().runOnUiThread(() -> {
                if (currentNews != null && !currentNews.isEmpty()) {
                    newsList = currentNews;

                    // 设置 RecyclerView 适配器
                    adapter = new RecyclerViewAdapter(newsList, getContext());
                    recyclerView.setAdapter(adapter);
                    // 设置 RecyclerView 管理器
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void loadNewData(SmartRefreshLayout refreshLayout) {
        new Thread(() -> {
            List<NewsItem> latestNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_06.json", 0, currentPage + pageStap);

            requireActivity().runOnUiThread(() -> {
                if (!latestNews.isEmpty()) {
                    newsList.clear();  // 清空现有数据
                    newsList.addAll(latestNews);  // 添加最新的新闻数据
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "刷新失败。。。", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishRefresh(true);
            });
        }).start();
    }

    private void loadMoreData(SmartRefreshLayout refreshLayout) {
        new Thread(() -> {
            List<NewsItem> moreNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_06.json", currentPage += 3, pageStap);

            requireActivity().runOnUiThread(() -> {
                if (!moreNews.isEmpty()) {
                    newsList.addAll(moreNews);  // 追加数据到已有列表
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "加载成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "没有更多新闻了！", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishLoadMore(true);
                // refreshLayout.finishLoadMoreWithNoMoreData();
            });
        }).start();
    }
}
