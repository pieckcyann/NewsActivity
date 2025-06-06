package com.xiaoyou.newsdisplayactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xiaoyou.newsdisplayactivity.fragment.GuoJiFragment;
import com.xiaoyou.newsdisplayactivity.fragment.GuoNeiFragment;
import com.xiaoyou.newsdisplayactivity.fragment.JunShiFragment;
import com.xiaoyou.newsdisplayactivity.fragment.KeJiFragment;
import com.xiaoyou.newsdisplayactivity.fragment.TiYuFragment;
import com.xiaoyou.newsdisplayactivity.fragment.TopFragment;
import com.xiaoyou.newsdisplayactivity.fragment.YuLeFragment;

import java.util.Arrays;
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

    // Fragment 列表
    private final List<Fragment> fragmentList = Arrays.asList(
            new TopFragment(),
            new GuoNeiFragment(),
            new GuoJiFragment(),
            new YuLeFragment(),
            new TiYuFragment(),
            new JunShiFragment(),
            new KeJiFragment()
    );
    // 标题内容列表
    private final List<String> tabTitles = Arrays.asList("头条", "国内", "国际", "娱乐", "体育", "军事", "科技");
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ImageButton moreTagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.info_viewpager2);

        findViewById(R.id.btn_more_tags).setOnClickListener(v -> {
            Intent intent = new Intent(this, MoreTabsActivity.class);
            startActivity(intent);
        });

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
        // 中介会同步 ViewPager2 的位置与选中的 TabLayout 项
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

    }

}
