package com.xiaoyou.newsdisplayactivity.bean;

public class NewsResponse {
    private String reason;
    private NewsResult result;

    private Integer ErrorCode;

    public String getReason() {
        return reason;
    }

    public NewsResult getResult() {
        return result;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }
}

