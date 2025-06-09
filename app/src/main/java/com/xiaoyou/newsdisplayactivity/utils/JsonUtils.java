package com.xiaoyou.newsdisplayactivity.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaoyou.newsdisplayactivity.dto.NewsItem;
import com.xiaoyou.newsdisplayactivity.dto.NewsResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonUtils {

    public static List<NewsItem> parseNewsJsonByLocal(Context context, String fileName) {
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

    public static List<NewsItem> parseNewsJsonByRequest(String newsType) {
        String apiUrl = "http://v.juhe.cn/toutiao/index"
                + "?type=" + newsType
                + "&page=1&page_size=7&is_filter=0"
                + "&key=cb53bd41a74ef445a2d1b7fcfebe6fa0";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();

                Gson gson = new Gson();
                Type type = new TypeToken<NewsResponse>() {
                }.getType();
                NewsResponse newsResponse = gson.fromJson(json, type);

                if (newsResponse != null && newsResponse.getResult() != null) {
                    return newsResponse.getResult().getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return Collections.emptyList();
    }

}
