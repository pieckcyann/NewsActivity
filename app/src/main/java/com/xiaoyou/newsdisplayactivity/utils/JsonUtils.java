package com.xiaoyou.newsdisplayactivity.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.xiaoyou.newsdisplayactivity.dto.NewsItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtils {

    // 将这个Android工具类方法中使用gson的部分改为使用原生的Java来解析
    // public static List<NewsItem> parseNewsJsonByLocal(Context context, String fileName) {
    //     try {
    //         // 获取资源文件对象
    //         AssetManager assetManager = context.getAssets();
    //
    //         // 获取 JSON 内容
    //         InputStream inputStream = assetManager.open(fileName);
    //
    //         // 1. Read 类型
    //         InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    //
    //         Gson gson = new Gson();
    //
    //         // 2. Type 类型
    //         Type type = new TypeToken<NewsResponse>() {
    //         }.getType();
    //
    //         // JSON -> NewsResponse
    //         NewsResponse response = gson.fromJson(reader, type);
    //         reader.close();
    //
    //         if (response != null && response.getResult() != null) {
    //             return response.getResult().getData();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //
    //     return Collections.emptyList();
    // }


    public static List<NewsItem> parseNewsJsonByLocal(Context context, String fileName, int start, int count) {
        try {
            // 获取资源文件对象
            AssetManager assetManager = context.getAssets();

            // 获取 JSON 内容
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            StringBuilder jsonBuilder = new StringBuilder();

            int character;
            while ((character = reader.read()) != -1) {
                jsonBuilder.append((char) character);
            }
            reader.close();

            // 将 JSON 内容转为 JSONObject
            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());

            // 提取数据部分
            JSONArray resultArray = jsonObject.getJSONObject("result").getJSONArray("data");
            List<NewsItem> newsItemList = new ArrayList<>();

            // 获取分页数据
            int toIndex = Math.min(start + count, resultArray.length());
            for (int i = start; i < toIndex; i++) {
                JSONObject newsObject = resultArray.getJSONObject(i);

                // 创建 NewsItem 并设置数据
                NewsItem newsItem = new NewsItem();
                newsItem.setUniquekey(newsObject.optString("uniquekey"));
                newsItem.setTitle(newsObject.optString("title"));
                newsItem.setDate(newsObject.optString("date"));
                newsItem.setCategory(newsObject.optString("category"));
                newsItem.setAuthor_name(newsObject.optString("author_name"));
                newsItem.setUrl(newsObject.optString("url"));
                newsItem.setThumbnail_pic_s(newsObject.optString("thumbnail_pic_s"));
                newsItem.setThumbnail_pic_s02(newsObject.optString("thumbnail_pic_s02"));
                newsItem.setThumbnail_pic_s03(newsObject.optString("thumbnail_pic_s03"));
                newsItem.setIs_content(newsObject.optString("is_content"));

                // 添加到返回的列表中
                newsItemList.add(newsItem);
            }

            return newsItemList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // 如果出错，返回空列表
    }


    // public static List<NewsItem> parseNewsJsonByRequest(String newsType) {
    //     String apiUrl = "http://v.juhe.cn/toutiao/index"
    //             + "?type=" + newsType
    //             + "&page=1&page_size=7&is_filter=0"
    //             + "&key=cb53bd41a74ef445a2d1b7fcfebe6fa0";
    //
    //     OkHttpClient client = new OkHttpClient();
    //
    //     Request request = new Request.Builder()
    //             .url(apiUrl)
    //             .build();
    //
    //     try (Response response = client.newCall(request).execute()) {
    //
    //         if (response.isSuccessful() && response.body() != null) {
    //             String json = response.body().string();
    //
    //             Gson gson = new Gson();
    //             Type type = new TypeToken<NewsResponse>() {
    //             }.getType();
    //             NewsResponse newsResponse = gson.fromJson(json, type);
    //
    //             if (newsResponse != null && newsResponse.getResult() != null) {
    //                 return newsResponse.getResult().getData();
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         System.out.println(e);
    //     }
    //
    //     return Collections.emptyList();
    // }
}
