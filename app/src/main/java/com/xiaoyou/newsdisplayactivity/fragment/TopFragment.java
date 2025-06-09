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

public class TopFragment extends Fragment {
    RecyclerView topRecyclerView;
    RecyclerViewAdapter topMyAdapter;
    List<NewsItem> topNewsList;

    SmartRefreshLayout topSmartRefresh;

    public TopFragment() {
        super(R.layout.fragment_top);
    }

    // 绘制完成时
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topRecyclerView = view.findViewById(R.id.fragment_top);

        // 请求获取数据
        // topNewsList = JsonUtils.parseNewsJsonByLocal(getContext(), "top_2025_06_04.json");
        topNewsList = JsonUtils.parseNewsJsonByRequest("top");

        // 添加 RecyclerView Adapter
        topMyAdapter = new RecyclerViewAdapter(topNewsList, getContext());
        topRecyclerView.setAdapter(topMyAdapter);

        // 设置 RecyclerView Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        topRecyclerView.setLayoutManager(layoutManager);

        // 设置 下拉刷新 与 上拉加载 组件
        topSmartRefresh = view.findViewById(R.id.top_smart_refresh_layout);

        // 下拉
        // topSmartRefresh.setOnRefreshListener(refreshLayout -> {
        //     refreshLayout.finishRefresh(true);// 传入false 表示刷新失败
        // });

        topSmartRefresh.setOnRefreshListener(refreshLayout -> {
            // 再次请求
            // List<NewsItem> latestNews = JsonUtils.parseNewsJsonByLocal(getContext(), "top_2025_06_05.json");
            List<NewsItem> latestNews = JsonUtils.parseNewsJsonByRequest("top");

            if (latestNews != null && !latestNews.isEmpty()) {
                topNewsList.clear(); // 清空旧数据
                topNewsList.addAll(latestNews); // 添加新数据
                topMyAdapter.notifyDataSetChanged(); // 通知适配器刷新

                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "刷新失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            refreshLayout.finishRefresh(true);
        });

        // topSmartRefresh.setOnRefreshListener(refreshLayout -> {
        //     new Thread(() -> {
        //         // 发起网络请求（在子线程中）
        //         List<NewsItem> latestNews = JsonUtils.parseNewsJsonByLocal(getContext(), "top_2025_06_05.json");
        //
        //         // 切换回主线程更新 UI
        //         requireActivity().runOnUiThread(() -> {
        //             if (latestNews != null && !latestNews.isEmpty()) {
        //                 topNewsList.clear(); // 清空旧数据
        //                 topNewsList.addAll(latestNews); // 添加新数据
        //                 topMyAdapter.notifyDataSetChanged(); // 通知适配器刷新
        //
        //                 Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
        //             } else {
        //                 Toast.makeText(getContext(), "刷新失败，请检查网络", Toast.LENGTH_SHORT).show();
        //             }
        //
        //             refreshLayout.finishRefresh(true);
        //         });
        //     }).start();
        // });

        // 上拉
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
