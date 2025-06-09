package com.xiaoyou.newsdisplayactivity.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.xiaoyou.newsdisplayactivity.dto.NewsItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


    // 从本地 assets 中读取 JSON 文件，并按分页返回解析后的 NewsItem 列表
    public static List<NewsItem> parseNewsJsonByLocal(Context context, String fileName, int start, int count) {
        try {
            // 获取资源管理器，用于访问 assets 目录中的文件
            AssetManager assetManager = context.getAssets();

            // 打开指定文件名的 JSON 文件（位于 assets 目录下）
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            StringBuilder jsonBuilder = new StringBuilder();

            // 逐字符读取 JSON 内容并拼接为字符串
            int character;
            while ((character = reader.read()) != -1) {
                jsonBuilder.append((char) character);
            }
            reader.close(); // 关闭读取器

            // 将 JSON 字符串转换为 JSONObject 对象
            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());

            // 解析 "result" 对象中的 "data" 数组，该数组包含新闻数据
            JSONArray resultArray = jsonObject.getJSONObject("result").getJSONArray("data");

            // 用于存储最终解析后的 NewsItem 列表
            List<NewsItem> newsItemList = new ArrayList<>();

            // 计算当前页的结束索引，防止越界
            int toIndex = Math.min(start + count, resultArray.length());

            // 遍历分页范围内的 JSON 项目，转换为 NewsItem 对象
            for (int i = start; i < toIndex; i++) {
                JSONObject newsObject = resultArray.getJSONObject(i);

                // 创建 NewsItem 实例并从 JSON 中提取对应字段设置进去
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

                // 添加到返回列表中
                newsItemList.add(newsItem);
            }

            // 返回分页后的 NewsItem 列表
            return newsItemList;

        } catch (Exception e) {
            // 捕获异常，打印错误日志
            e.printStackTrace();
        }

        // 出现异常时，返回空列表避免崩溃
        return Collections.emptyList();
    }

    public static List<NewsItem> parseNewsJsonByRequest(String newsType) {
        // 创建用于存放新闻数据的列表
        List<NewsItem> newsList = new ArrayList<>();

        // 拼接 API 请求 URL，指定新闻类型、分页等参数
        String apiUrl = "http://v.juhe.cn/toutiao/index"
                + "?type=" + newsType
                + "&page=1&page_size=7&is_filter=0"
                + "&key=cb53bd41a74ef445a2d1b7fcfebe6fa0";

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // 创建 URL 对象并打开连接
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");       // 设置请求方法为 GET
            connection.setConnectTimeout(8000);       // 设置连接超时时间（毫秒）
            connection.setReadTimeout(8000);          // 设置读取超时时间（毫秒）

            // 获取响应码并判断是否成功
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 使用 BufferedReader 读取服务器返回的数据
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;

                // 按行读取 JSON 字符串
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                // 将 JSON 字符串转换为 JSONObject
                String json = jsonBuilder.toString();
                JSONObject rootObject = new JSONObject(json);

                // 判断是否成功获取数据（error_code == 0 表示成功）
                if (rootObject.optInt("error_code") == 0) {
                    // 提取 result 对象
                    JSONObject resultObject = rootObject.optJSONObject("result");
                    if (resultObject != null) {
                        // 提取 data 数组
                        JSONArray dataArray = resultObject.optJSONArray("data");
                        if (dataArray != null) {
                            // 遍历每条新闻数据
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);
                                NewsItem news = new NewsItem();

                                // 提取并设置各个字段
                                news.setTitle(item.optString("title"));
                                news.setAuthor_name(item.optString("author_name"));
                                news.setDate(item.optString("date"));
                                news.setUrl(item.optString("url"));
                                news.setThumbnail_pic_s(item.optString("thumbnail_pic_s"));
                                news.setThumbnail_pic_s02(item.optString("thumbnail_pic_s02"));
                                news.setThumbnail_pic_s03(item.optString("thumbnail_pic_s03"));

                                // 添加到新闻列表中
                                newsList.add(news);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            // 捕获并打印异常
            e.printStackTrace();
        } finally {
            // 关闭资源，避免内存泄漏
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception ignored) {}
        }

        // 返回解析后的新闻列表
        return newsList;
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
