package com.xiaoyou.newsdisplayactivity.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoyou.newsdisplayactivity.R;
import com.xiaoyou.newsdisplayactivity.dto.NewsItem;

import java.util.List;

// 用于将数据绑定到 RecyclerView 上
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    List<NewsItem> mNewsList;
    Context context;

    public RecyclerViewAdapter(List<NewsItem> mNewsList, Context context) {
        this.mNewsList = mNewsList;
        this.context = context;
    }

    // 创建并返回每一项的视图(ViewHolder)
    // RecyclerView 依赖此方法来构造每个条目的视图，避免每次滑动都新建视图，提升性能
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建单个 item 布局的视图
        // View view = View.inflate(this.context, R.layout.item_news_text, null);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_text, parent, false);

        return new RecyclerViewHolder(view); // 返回自定义 ViewHolder
    }

   // RecyclerView 滑动或刷新时调用此方法更新内容
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        NewsItem news = this.mNewsList.get(position);

        List<String> images = news.getImageList();

        // Step 1: 清除旧视图状态，避免残影
        holder.resetImageData();

        // Step 2: 绑定基本文本数据
        holder.mTitleTv.setText(news.getTitle());
        holder.mTitleAuthor.setText(news.getAuthor_name());
        holder.mTitleDate.setText(news.getDate());

        // Step 3: 绑定图片数据
        holder.bindImageData(images, context);
    }

    // RecyclerView 需要这个函数来判断列表长度
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}