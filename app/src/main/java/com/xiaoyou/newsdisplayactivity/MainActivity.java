package com.xiaoyou.newsdisplayactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xiaoyou.newsdisplayactivity.fragment.BaseNewsFragment;

import java.util.LinkedList;
import java.util.List;

// public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

public class MainActivity extends AppCompatActivity {
    // 标题内容列表
    LinkedList<String> myTabs;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    // 动态地创建 Fragment 列表
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Toast.makeText(this, "xxxxxxxxxxxxxxx", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.info_viewpager2);

        // 从全局状态中获取标题文本列表
        myTabs = ((NewsApp) getApplicationContext()).getMyTabsState();

        // 动态地创建 Fragment 列表
        fragmentList = generateFragments();

        // 点击切换活动
        findViewById(R.id.btn_more_tags).setOnClickListener(v -> {
            Intent intent = new Intent(this, MoreTabsActivity.class);
            startActivity(intent);
        });

        // 添加 ViewPager 适配器
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });

        // 使用中介 TabLayoutMediator 连接 TabLayout 和 ViewPager2
        // 中介会自动同步 ViewPager2 的位置与选中的 TabLayout 项
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(myTabs.get(position)) // 根据 position 设置 tab 的文字
        ).attach();


    }

    // 工厂方法 - 动态创建 Fragment 列表
    private List<Fragment> generateFragments() {
        List<Fragment> fragments = new LinkedList<>();
        for (String tab : myTabs) {
            fragments.add(createFragmentForTab(tab));
        }
        return fragments;
    }

    private Fragment createFragmentForTab(String tabName) {
        switch (tabName) {
            case "头条":
                return BaseNewsFragment.newInstance("top");
            case "国内":
                return BaseNewsFragment.newInstance("guonei");
            case "国际":
                return BaseNewsFragment.newInstance("guoji");
            case "娱乐":
                return BaseNewsFragment.newInstance("yule");
            case "体育":
                return BaseNewsFragment.newInstance("tiyu");
            case "军事":
                return BaseNewsFragment.newInstance("junshi");
            case "科技":
                return BaseNewsFragment.newInstance("keji");
            default:
                return new Fragment(); // 空白 fragment
        }
    }

}
