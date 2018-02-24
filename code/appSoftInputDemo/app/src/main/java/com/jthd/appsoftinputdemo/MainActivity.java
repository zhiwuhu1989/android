package com.jthd.appsoftinputdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //AndroidBug5497Workaround.assistActivity(this);
    }

    private void initView() {
        String url = "https://www.awc0008.com/";
        webView = (WebView) findViewById(R.id.webview);

        //Web view
        webView.setInitialScale(1);
        //webView.setOnKeyListener(this);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        //缩放
        webSettings.setUseWideViewPort(true);        //设定支持viewport
        webSettings.setLoadWithOverviewMode(true);   //自适应屏幕
        webSettings.setBuiltInZoomControls(true);
        //webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);           //设定支持缩放

        //JS支持
        webSettings.setJavaScriptEnabled(true);
        //缓存
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();   //缓存数据的存储地址
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setAppCacheEnabled(true);               //开启缓存功能
        //webSettings.setCacheMode(LOAD_CACHE_ELSE_NETWORK);  //缓存模式
        //其他
        //webSettings.setUserAgentString(USER_AGENT);
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setDatabaseEnabled(true);        //开启数据库形式存储
        webSettings.setDomStorageEnabled(true);      //开启DOM形式存储

        //Web view 设置
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //mLogger.e("shouldOverrideUrlLoading======>url==" + url);
                view.loadUrl(url);
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }
        });

        webView.loadUrl(url);
    }

    //改写物理按键的返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
