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
    private final int pageStep = 3;
    private String newsType;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter; // 接收返回的适配器，用于之后更新列表视图
    private List<NewsItem> newsList;
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
        loadNewsData("loadInitial", null);

        // 下拉刷新 监听器
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            loadNewsData("loadNew", smartRefreshLayout);
        });

        // 上拉加载 监听器
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            loadNewsData("loadMore", smartRefreshLayout);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadNewsData(String actionType, SmartRefreshLayout refreshLayout) {
        // 创建子线程用于执行耗时操作，避免堵塞主线程
        new Thread(() -> {
            List<NewsItem> resultNews = null;

            // 处理不同的 actionType
            if (actionType.equals("loadInitial")) {
                // resultNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_05.json", 0, pageStep);
                resultNews = JsonUtils.parseNewsJsonByRequest(newsType, 0, pageStep);
            } else if (actionType.equals("loadNew")) {
                currentPage = 0;
                // resultNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_05.json", 0, pageStep);
                resultNews = JsonUtils.parseNewsJsonByRequest(newsType, 0, pageStep);
            } else if (actionType.equals("loadMore")) {
                currentPage += pageStep;
                // resultNews = JsonUtils.parseNewsJsonByLocal(getContext(), newsType + "_2025_06_05.json", currentPage, pageStep);
                resultNews = JsonUtils.parseNewsJsonByRequest(newsType, currentPage += pageStep, pageStep);
            }

            // For lambda
            List<NewsItem> finalResultNews = resultNews;

            // 请求数据的子线程执行完要更新 UI 时，必须要返回到主线程来更新
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // () -> {
                    if (finalResultNews != null && !finalResultNews.isEmpty()) {
                        if (actionType.equals("loadInitial")) {
                            newsList = finalResultNews;  // 初始加载

                            // 添加适配器，负责新闻项的渲染绑定
                            adapter = new RecyclerViewAdapter(newsList, getContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        } else if (actionType.equals("loadNew")) {
                            newsList.clear();  // 清空现有数据
                            newsList.addAll(finalResultNews);  // 添加新数据
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "刷新成功！", Toast.LENGTH_SHORT).show();
                        } else { // "loadMore"
                            newsList.addAll(finalResultNews);  // 追加新数据
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "加载成功！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (actionType.equals("loadNew")) {
                            Toast.makeText(getContext(), "刷新失败。。。", Toast.LENGTH_SHORT).show();
                        } else if (actionType.equals("loadMore")) {
                            Toast.makeText(getContext(), "没有更多新闻了！", Toast.LENGTH_SHORT).show();
                        } else { // "loadInitial"
                            Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (refreshLayout != null) {
                        if (actionType.equals("loadNew")) {
                            refreshLayout.finishRefresh(true);
                        } else if (actionType.equals("loadMore")) {
                            refreshLayout.finishLoadMore(true);
                        }
                    }
                    // }
                }
            });
        }).start();
    }

}
