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

import java.util.List;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView mTitleTv;
    TextView mTitleAuthor;
    TextView mTitleDate;

    // 单张图
    ImageView one_image1;

    // 两张图容器和ImageView
    LinearLayout two_image_container;
    ImageView two_image1, two_image2;

    // 三张图容器和ImageView
    LinearLayout tree_image_container;
    ImageView three_image1, three_image2, three_image3;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTv = itemView.findViewById(R.id.news_title);
        mTitleAuthor = itemView.findViewById(R.id.news_author);
        mTitleDate = itemView.findViewById(R.id.news_date);

        one_image1 = itemView.findViewById(R.id.one_image1);

        two_image_container = itemView.findViewById(R.id.two_image_container);
        two_image1 = two_image_container.findViewById(R.id.two_image1);
        two_image2 = two_image_container.findViewById(R.id.two_image2);

        tree_image_container = itemView.findViewById(R.id.tree_image_container);
        three_image1 = tree_image_container.findViewById(R.id.tree_image1);
        three_image2 = tree_image_container.findViewById(R.id.tree_image2);
        three_image3 = tree_image_container.findViewById(R.id.tree_image3);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void bindImageData(List<String> images, Context context) {
        // 清空之前的图片状态（建议你在 resetImageData 方法中完成）

        int count = images.size();

        if (count >= 3) {
            Glide.with(context).load(images.get(0)).into(three_image1);
            Glide.with(context).load(images.get(1)).into(three_image2);
            Glide.with(context).load(images.get(2)).into(three_image3);

            tree_image_container.setVisibility(View.VISIBLE);

            three_image1.setVisibility(View.VISIBLE);
            three_image2.setVisibility(View.VISIBLE);
            three_image3.setVisibility(View.VISIBLE);

            // image1.setOnClickListener(v -> System.out.println("三张图的第一张：" + images.get(0)));
            // image2.setOnClickListener(v -> System.out.println("三张图的第二张：" + images.get(1)));
            // image3.setOnClickListener(v -> System.out.println("三张图的第三张：" + images.get(2)));

        } else if (count == 2) {
            two_image1 = two_image_container.findViewById(R.id.two_image1);
            two_image2 = two_image_container.findViewById(R.id.two_image2);

            Glide.with(context).load(images.get(0)).into(two_image1);
            Glide.with(context).load(images.get(1)).into(two_image2);

            two_image_container.setVisibility(View.VISIBLE);

            two_image1.setVisibility(View.VISIBLE);
            two_image2.setVisibility(View.VISIBLE);

            // image1.setOnClickListener(v -> System.out.println("两张图的第一张：" + images.get(0)));
            // image2.setOnClickListener(v -> System.out.println("两张图的第二张：" + images.get(1)));

        } else if (count == 1) {
            one_image1 = itemView.findViewById(R.id.one_image1);

            Glide.with(context).load(images.get(0)).into(one_image1);

            one_image1.setVisibility(View.VISIBLE);

            one_image1.setOnClickListener(v -> System.out.println("一张图：" + images.get(0)));
        }
    }

    public void resetImageData() {
        if (one_image1 != null) {
            one_image1.setVisibility(View.GONE);
            one_image1.setImageDrawable(null);
        }
        if (two_image1 != null) {
            two_image1.setVisibility(View.GONE);
            two_image1.setImageDrawable(null);
        }
        if (two_image2 != null) {
            two_image2.setVisibility(View.GONE);
            two_image2.setImageDrawable(null);
        }
        if (three_image1 != null) {
            three_image1.setVisibility(View.GONE);
            three_image1.setImageDrawable(null);
        }
        if (three_image2 != null) {
            three_image2.setVisibility(View.GONE);
            three_image2.setImageDrawable(null);
        }
        if (three_image3 != null) {
            three_image3.setVisibility(View.GONE);
            three_image3.setImageDrawable(null);
        }

        if (two_image_container != null)
            two_image_container.setVisibility(View.GONE);
        if (tree_image_container != null)
            tree_image_container.setVisibility(View.GONE);
    }


    // 解决 RecyclerView 多次滚动多次绑定数据 (导致布局混乱)
    // public void resetImageData(List<String> images) {
    //     // 避免错误地删除多图控件中的第一张图片
    //     // if (multi_image_container.getVisibility() == View.GONE && image1 != null) {
    //     // if (image3 == null && image1 != null) {
    //
    //     // 隐藏容器
    //
    //     int count = images.size();
    //     if(count == 3){
    //         multi_image_container.setVisibility(View.GONE);
    //         tree_image_container.setVisibility(View.GONE);
    //     } else if (count==2) {
    //         multi_image_container.setVisibility(View.GONE);
    //         two_image_container.setVisibility(View.GONE);
    //     } else if (count == 1) {
    //         one_image1.setVisibility(View.GONE);
    //     }
    //
    //
    //     if (image1 != null) image1.setImageDrawable(null);
    //     if (image2 != null) image2.setImageDrawable(null);
    //     if (image3 != null) image3.setImageDrawable(null);
    //     if (one_image1 != null) one_image1.setImageDrawable(null);
    //
    //     image1 = null;
    //     image2 = null;
    //     image3 = null;
    //     one_image1 = null;
    //
    //     // // 清空所有 ImageView 的内容并隐藏
    //     // ImageView one = itemView.findViewById(R.id.one_image1);
    //     // ImageView two1 = itemView.findViewById(R.id.two_image1);
    //     // ImageView two2 = itemView.findViewById(R.id.two_image2);
    //     // ImageView three1 = itemView.findViewById(R.id.tree_image1);
    //     // ImageView three2 = itemView.findViewById(R.id.tree_image2);
    //     // ImageView three3 = itemView.findViewById(R.id.tree_image3);
    //     //
    //     // for (ImageView img : new ImageView[]{one, two1, two2, three1, three2, three3}) {
    //     //     if (img != null) {
    //     //         img.setImageDrawable(null);
    //     //         img.setVisibility(View.GONE);
    //     //         img.setOnClickListener(null); // 可选，避免旧的点击事件残留
    //     //     }
    //     // }
    // }

}
