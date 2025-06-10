package com.xiaoyou.newsdisplayactivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.activity_web_view);
        String url = getIntent().getStringExtra("url");

        if (url != null) {
            webView.loadUrl(url);
        }

        findViewById(R.id.floating_action_btn)
                .setOnClickListener(v -> {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish(); // 关闭
                        }
                );
    }

}