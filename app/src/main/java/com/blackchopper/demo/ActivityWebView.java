package com.blackchopper.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import com.bumptech.glide.Glide;

import com.blackchopper.iplayer.PlayerUtils;
import com.blackchopper.iplayer.Video;
import com.blackchopper.iplayer.Iplayer;
import com.blackchopper.demo.R;

/**
 * Created by Nathen on 16/10/13.
 */

public class ActivityWebView extends AppCompatActivity {
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("WebView");
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JZCallBack(), "jzvdStd");
        mWebView.loadUrl("file:///android_asset/jzvdStd.html");
    }

    @Override
    public void onBackPressed() {
        if (Video.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Video.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class JZCallBack {

        @JavascriptInterface
        public void adViewJiaoZiVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        Iplayer webVieo = new Iplayer(ActivityWebView.this);
                        webVieo.setUp(VideoConstant.videoUrlList[1], "饺子骑大马",
                                Video.SCREEN_WINDOW_LIST);
                        Glide.with(ActivityWebView.this)
                                .load(VideoConstant.videoThumbList[1])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = PlayerUtils.dip2px(ActivityWebView.this, top);
                        layoutParams.x = PlayerUtils.dip2px(ActivityWebView.this, left);
                        layoutParams.height = PlayerUtils.dip2px(ActivityWebView.this, height);
                        layoutParams.width = PlayerUtils.dip2px(ActivityWebView.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    } else {
                        Iplayer webVieo = new Iplayer(ActivityWebView.this);
                        webVieo.setUp(VideoConstant.videoUrlList[2], "饺子失态了",
                                Video.SCREEN_WINDOW_LIST);
                        Glide.with(ActivityWebView.this)
                                .load(VideoConstant.videoThumbList[2])
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = PlayerUtils.dip2px(ActivityWebView.this, top);
                        layoutParams.x = PlayerUtils.dip2px(ActivityWebView.this, left);
                        layoutParams.height = PlayerUtils.dip2px(ActivityWebView.this, height);
                        layoutParams.width = PlayerUtils.dip2px(ActivityWebView.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    }

                }
            });

        }
    }
}
