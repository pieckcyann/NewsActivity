package com.xiaoyou.newsdisplayactivity.dto;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsItem {
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;
    private String is_content;

    // 无参构造函数
    public NewsItem() {
    }

    // 带 JSONObject 构造函数
    public NewsItem(JSONObject newsJson) {
        this.uniquekey = newsJson.optString("uniquekey");
        this.title = newsJson.optString("title");
        this.date = newsJson.optString("date");
        this.category = newsJson.optString("category");
        this.author_name = newsJson.optString("author_name");
        this.url = newsJson.optString("url");
        this.thumbnail_pic_s = newsJson.optString("thumbnail_pic_s");
        this.thumbnail_pic_s02 = newsJson.optString("thumbnail_pic_s02");
        this.thumbnail_pic_s03 = newsJson.optString("thumbnail_pic_s03");
        this.is_content = newsJson.optString("is_content");
    }

    public List<String> getImageList() {
        List<String> imageList = new ArrayList<>();
        if (thumbnail_pic_s != null && !thumbnail_pic_s.isEmpty()) {
            imageList.add(thumbnail_pic_s);
        }
        if (thumbnail_pic_s02 != null && !thumbnail_pic_s02.isEmpty()) {
            imageList.add(thumbnail_pic_s02);
        }
        if (thumbnail_pic_s03 != null && !thumbnail_pic_s03.isEmpty()) {
            imageList.add(thumbnail_pic_s03);
        }
        return imageList;
    }

    // 提供必要的 getter 方法
    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    public String getIs_content() {
        return is_content;
    }

    public void setIs_content(String is_content) {
        this.is_content = is_content;
    }
}
