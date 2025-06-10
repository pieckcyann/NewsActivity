package com.xiaoyou.newsdisplayactivity.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String makeHttpRequest(String apiUrl) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder jsonBuilder = new StringBuilder();

        try {
            // 创建 URL 对象并打开连接
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");       // 设置请求方法
            connection.setConnectTimeout(8000);       // 设置连接超时时间(毫秒)
            connection.setReadTimeout(8000);          // 设置读取超时时间(毫秒)

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 是否成功
                // 使用 BufferedReader 读取返回的数据
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                // 按行读取 JSON 字符串
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                return jsonBuilder.toString(); // 返回 JSON 字符串
            } else {
                return null; // 响应失败，返回 null
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null; // 出现异常，返回 null
        } finally {
            // 关闭资源，避免内存泄漏
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception ignored) {
            }
        }
    }

    public static void loadUrlImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Drawable>() {
                    private boolean retried = false;

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (!retried && url.contains("dftoutiao")) {
                            retried = true;
                            String newUrl = url.replace("dftoutiao", "dfxwdc");
                            // System.out.println(newUrl);
                            Glide.with(context)
                                    .load(newUrl)
                                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .into(imageView);
                        }
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

}
