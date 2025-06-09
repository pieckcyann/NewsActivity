package com.xiaoyou.newsdisplayactivity.dto;

import java.util.List;

public class NewsResult {
    private String stat;
    private List<NewsItem> data;

    public String getStat() {
        return stat;
    }

    public List<NewsItem> getData() {
        return data;
    }
}
