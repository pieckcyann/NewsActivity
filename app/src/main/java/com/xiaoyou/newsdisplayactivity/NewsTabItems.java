package com.xiaoyou.newsdisplayactivity;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class NewsTabItems {

    private static final String PREFS_NAME = "NewsTabPrefs";
    private static final String MY_TABS_KEY = "myTabs";
    private static final String OTHER_TABS_KEY = "otherTabs";

    private static LinkedList<String> myTabs = new LinkedList<>(Arrays.asList("头条", "国内", "国际", "娱乐"));
    private static LinkedList<String> otherTabs = new LinkedList<>(Arrays.asList("体育", "军事", "科技"));

    // 加载 myTabs
    public static LinkedList<String> loadMyTabState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String myTabsString = prefs.getString(MY_TABS_KEY, null);
        if (myTabsString != null) {
            myTabs = stringToLinkedList(myTabsString);
        }
        return myTabs;  // 无论如何返回非null
    }

    // 加载 otherTabs
    public static LinkedList<String> loadOtherTabState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String otherTabsString = prefs.getString(OTHER_TABS_KEY, null);
        if (otherTabsString != null) {
            otherTabs = stringToLinkedList(otherTabsString);
        }
        return otherTabs;
    }

    // 保存当前的Tabs到SharedPreferences
    public static void saveTabs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 将myTabs和otherTabs转换为字符串并保存到SharedPreferences
        editor.putString(MY_TABS_KEY, linkedListToString(myTabs));
        editor.putString(OTHER_TABS_KEY, linkedListToString(otherTabs));

        editor.apply(); // 异步保存
    }

    /* ******************** myTab ******************** */

    // 返回 myTabs 元素个数
    public static int sizeMyTabsState() {
        return myTabs.size();
    }

    /**
     * 获取 myTabs 指定位置的元素
     *
     * @param position 位置
     * @return 指定位置的列表项
     */
    public static String getMyTabsState(int position) {
        return myTabs.get(position);
    }

    // 添加元素到myTabs
    public static boolean addMyTabsState(String s, Context context) {
        boolean added = myTabs.add(s);
        saveTabs(context); // 保存到SharedPreferences
        return added;
    }

    // 删除myTabs中的元素
    public static void removeMyTabsState(int position, Context context) {
        myTabs.remove(position);
        saveTabs(context); // 保存到SharedPreferences
    }

    /* ******************** otherTab ******************** */

    // 返回 otherTabs 元素个数
    public static int sizeOtherTabsState() {
        return otherTabs.size();
    }

    /**
     * 获取 otherTabs 指定位置的元素
     *
     * @param position 位置
     * @return 指定位置的列表项
     */
    public static String getOtherTabsState(int position) {
        return otherTabs.get(position);
    }

    public static boolean addOtherTabsState(String s, Context context) {
        boolean added = otherTabs.add(s);
        saveTabs(context); // 保存到SharedPreferences
        return added;
    }

    public static void removeOtherTabsState(int position, Context context) {
        otherTabs.remove(position);
        saveTabs(context); // 保存到SharedPreferences
    }

    // 将 LinkedList 转为字符串，使用逗号分隔
    private static String linkedListToString(LinkedList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(",");
        }
        // 移除最后的逗号
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    // 将字符串转换为 LinkedList
    private static LinkedList<String> stringToLinkedList(String str) {
        LinkedList<String> list = new LinkedList<>();
        String[] items = str.split(",");
        Collections.addAll(list, items);
        return list;
    }
}
