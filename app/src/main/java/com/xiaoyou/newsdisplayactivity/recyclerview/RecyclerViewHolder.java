package com.xiaoyou.newsdisplayactivity.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xiaoyou.newsdisplayactivity.R;
import com.xiaoyou.newsdisplayactivity.dto.NewsItem;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView mTitleTv;
    TextView mTitleAuthor;
    TextView mTitleDate;
    ImageView image1, image2, image3;
//    Uri imageUri1, imageUri2, imageUri3;

    LinearLayout two_image_container, tree_image_container;
    FrameLayout multi_image_container;

    String imageUrl1, imageUrl2, imageUrl3;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTv = itemView.findViewById(R.id.news_title);
        mTitleAuthor = itemView.findViewById(R.id.news_author);
        mTitleDate = itemView.findViewById(R.id.news_date);

        multi_image_container = itemView.findViewById(R.id.multi_image_container);
        two_image_container = itemView.findViewById(R.id.two_image_container);
        tree_image_container = itemView.findViewById(R.id.tree_image_container);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void bindImageData(NewsItem item, Context context) {
        // 绑定前先清除
        resetImageData();

        mTitleTv.setText(item.getTitle());
        mTitleAuthor.setText(item.getAuthor_name());
        mTitleDate.setText(item.getDate());

        imageUrl1 = item.getThumbnail_pic_s();
        imageUrl2 = item.getThumbnail_pic_s02();
        imageUrl3 = item.getThumbnail_pic_s03();

        // 判空 + 判是否为空字符串，避免崩溃
        if (imageUrl3 != null && !imageUrl3.isEmpty()) {
            image1 = tree_image_container.findViewById(R.id.tree_image1);
            image2 = tree_image_container.findViewById(R.id.tree_image2);
            image3 = tree_image_container.findViewById(R.id.tree_image3);

            Glide.with(context)
                    .load(imageUrl3)  // 网络图片地址
                    .into(image3);
            Glide.with(context)
                    .load(imageUrl2)  // 网络图片地址
                    .into(image2);
            Glide.with(context)
                    .load(imageUrl1)  // 网络图片地址
                    .into(image1);

            multi_image_container.setVisibility(View.VISIBLE);
            tree_image_container.setVisibility(View.VISIBLE);

            image1.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("三张图的第一张：" + imageUrl1);
            });

            image2.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("三张图的第二张：" + imageUrl2);
            });

            image3.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("三张图的第三张：" + imageUrl3);
            });

            return;
        }
        if (imageUrl2 != null && !imageUrl2.isEmpty()) {
            image1 = two_image_container.findViewById(R.id.two_image1);
            image2 = two_image_container.findViewById(R.id.two_image2);

            Glide.with(context)
                    .load(imageUrl2)  // 网络图片地址
                    .into(image2);
            Glide.with(context)
                    .load(imageUrl1)  // 网络图片地址
                    .into(image1);

            multi_image_container.setVisibility(View.VISIBLE);
            two_image_container.setVisibility(View.VISIBLE);

            image1.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("两张图的第一张：" + imageUrl1);
            });

            image2.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("两张图的第二张：" + imageUrl2);
            });

            return;
        }

        if (imageUrl1 != null && !imageUrl1.isEmpty()) {
            System.out.println(!imageUrl1.isEmpty());
            image1 = itemView.findViewById(R.id.one_image1);

            Glide.with(context)
                    .load(imageUrl1)  // 网络图片地址
                    .into(image1);
            // .onLoadFailed(context.getDrawable(R.drawable.image_load_failed));
            image1.setVisibility(View.VISIBLE);

            image1.setOnClickListener(v -> {
                // Toast.makeText(context, imageUrl2, Toast.LENGTH_LONG).show();
                System.out.println("一张图：" + imageUrl1);
            });
            System.out.println(!imageUrl1.isEmpty());
        }
    }

    // 解决 RecyclerView 多次滚动多次绑定数据 (导致布局混乱) 的问题

    public void resetImageData() {
        // 避免错误地删除多图控件中的第一张图片
        // if (multi_image_container.getVisibility() == View.GONE && image1 != null) {
        // if (image3 == null && image1 != null) {
        if (image3 == null && image2 == null && image1 != null) image1.setVisibility(View.GONE);
        if (image3 == null && image2 == null && image1 != null) {
            image1.setVisibility(View.GONE); // !
            image1.setImageDrawable(null);
        }

        multi_image_container.setVisibility(View.GONE);
        two_image_container.setVisibility(View.GONE);
        tree_image_container.setVisibility(View.GONE);


        if (image2 != null) image2.setImageDrawable(null);
        if (image3 != null) image3.setImageDrawable(null);

    }

}
