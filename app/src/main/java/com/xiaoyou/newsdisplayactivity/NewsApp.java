package com.xiaoyou.newsdisplayactivity;

import android.app.Application;

import java.util.Arrays;
import java.util.LinkedList;

public class NewsApp extends Application {

    private final LinkedList<String> myTabs = new LinkedList<>(Arrays.asList("头条", "国内", "国际", "娱乐"));
    private final LinkedList<String> otherTabs = new LinkedList<>(Arrays.asList("体育", "军事", "科技"));

    public LinkedList<String> getMyTabsState() {
        return myTabs;
    }

    public int sizeMyTabsState() {
        return myTabs.size();
    }

    public String getMyTabsState(int position) {
        return myTabs.get(position);
    }

    public boolean addMyTabsState(String s) {
        return myTabs.add(s);
    }

    public void removeMyTabsState(int position) {
        myTabs.remove(position);
    }

    //
    public LinkedList<String> getOhterTabsState() {
        return otherTabs;
    }

    public int sizeOtherTabsState() {
        return otherTabs.size();
    }


    public String getOtherTabsState(int position) {
        return otherTabs.get(position);
    }

    public boolean addOtherTabsState(String s) {
        return otherTabs.add(s);
    }

    public void removeOtherTabsState(int position) {
        otherTabs.remove(position);
    }

}