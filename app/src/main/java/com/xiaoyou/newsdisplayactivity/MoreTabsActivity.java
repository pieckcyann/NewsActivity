package com.xiaoyou.newsdisplayactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class MoreTabsActivity extends AppCompatActivity {
    NewsApp appState;
    LinkedList<String> myTabs;
    LinkedList<String> otherTabs;
    GridView myTabsGridView, otherTabsGridView;
    BaseAdapter myTabsAdapter, otherTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tabs);

        findViewById(R.id.more_tabs_cancel).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        myTabsGridView = findViewById(R.id.my_tabs_grid_view);
        otherTabsGridView = findViewById(R.id.other_tabs_grid_view);

        // 如果不在这里初始化会报 NPE
        appState = (NewsApp) getApplicationContext();
        myTabs = appState.getMyTabsState();
        otherTabs = appState.getOhterTabsState();

        // 创建出动画
        Animation outAnim = AnimationUtils.loadAnimation(MoreTabsActivity.this, R.anim.item_out);
        // 创建入动画
        Animation inAnim = AnimationUtils.loadAnimation(MoreTabsActivity.this, R.anim.item_in);

        // 设置 myTabsGridView 的适配器
        myTabsAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return myTabs.size();
            }

            @Override
            public Object getItem(int position) {
                return myTabs.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_my_tabs, parent, false);
                }
                TextView textView = convertView.findViewById(R.id.my_tabs_text);
                textView.setText(myTabs.get(position));

                // 设置点击事件：从 myTabs 移除并添加到 otherTabs
                convertView.setOnClickListener(v -> {

                    v.startAnimation(outAnim);

                    // 动画结束后再操作数据
                    outAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 保存删除的分类
                            String removedTabItem = appState.getMyTabsState(position);

                            appState.removeMyTabsState(position); // 删除
                            appState.addOtherTabsState(removedTabItem); // 添加

                            // 通知更新
                            myTabsAdapter.notifyDataSetChanged();
                            otherTabsAdapter.notifyDataSetChanged();

                            // 等待 GridView 刷新完成，再播放入动画
                            otherTabsGridView.post(() -> {
                                int lastIndex = appState.sizeOtherTabsState() - 1;
                                View lastView = otherTabsGridView.getChildAt(lastIndex);
                                if (lastView != null) {
                                    lastView.startAnimation(inAnim);
                                }
                            });

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                });

                return convertView;
            }
        };


        myTabsGridView.setAdapter(myTabsAdapter);

        // 设置 otherTabsGridView 的适配器
        otherTabsAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return otherTabs.size();
            }

            @Override
            public Object getItem(int position) {
                return otherTabs.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_other_tabs, parent, false);
                }
                TextView textView = convertView.findViewById(R.id.other_tabs_text);
                textView.setText(otherTabs.get(position));

                // 设置点击事件：从 otherTabs 移除并添加到 myTabs
                convertView.setOnClickListener(v -> {

                    v.startAnimation(outAnim);

                    // 动画结束后再操作数据
                    outAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // 保存删除的分类
                            String removedTabItem = appState.getOtherTabsState(position);


                            appState.removeOtherTabsState(position); // 删除
                            appState.addMyTabsState(removedTabItem); // 添加

                            // 通知更新
                            otherTabsAdapter.notifyDataSetChanged();
                            myTabsAdapter.notifyDataSetChanged();

                            // 等待 GridView 刷新完成，再播放入动画
                            myTabsGridView.post(() -> {
                                int lastIndex = appState.sizeMyTabsState() - 1;
                                View lastView = myTabsGridView.getChildAt(lastIndex);

                                if (lastView != null) {
                                    lastView.startAnimation(inAnim);
                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                });
                return convertView;
            }
        };

        otherTabsGridView.setAdapter(otherTabsAdapter);
    }
}


