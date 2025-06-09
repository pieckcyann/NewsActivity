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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    List<NewsItem> mNewsList;
    Context context;

    public RecyclerViewAdapter(List<NewsItem> mNewsList, Context context) {
        this.mNewsList = mNewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View view = View.inflate(this.context, R.layout.item_news_text, null);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_text, parent, false);

        return new RecyclerViewHolder(view);
    }

    // 注意，多次滑动时会重复调用此方法
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        NewsItem news = this.mNewsList.get(position);
        holder.mTitleTv.setText(news.getTitle());
        holder.mTitleAuthor.setText(news.getAuthor_name());
        holder.mTitleDate.setText(news.getDate());

        holder.bindImageData(news, this.context); // 数据绑定交给 ViewHolder 处理
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}