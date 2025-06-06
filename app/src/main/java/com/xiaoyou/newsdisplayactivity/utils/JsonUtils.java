package com.xiaoyou.newsdisplayactivity.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoyou.newsdisplayactivity.bean.NewsItem;
import com.xiaoyou.newsdisplayactivity.bean.NewsResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class JsonUtils {

    public static List<NewsItem> parseNewsJson(Context context, String fileName) {
        try {
            // 获取资源文件对象
            AssetManager assetManager = context.getAssets();

            // 获取 JSON 内容
            InputStream inputStream = assetManager.open(fileName);

            // 1. Read 类型
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            Gson gson = new Gson();

            // 2. Type 类型
            Type type = new TypeToken<NewsResponse>() {
            }.getType();

            // JSON -> NewsResponse
            NewsResponse response = gson.fromJson(reader, type);
            reader.close();

            if (response != null && response.getResult() != null) {
                return response.getResult().getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
