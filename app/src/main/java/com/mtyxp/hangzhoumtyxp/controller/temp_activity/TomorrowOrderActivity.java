package com.mtyxp.hangzhoumtyxp.controller.temp_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.activity.OperateActivity;
import com.mtyxp.hangzhoumtyxp.utils.Constant;

public class TomorrowOrderActivity extends AppCompatActivity {

    private Button tomorrow_lanzhou,tomorrow_hangzhou,tomorrow_back,go_back;
    private TextView tomorrow_text_title;
    private WebView tomorrow_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow_order);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tomorrow_webview.canGoBack()){
                    tomorrow_webview.goBack();
                }
            }
        });
    }

    private void initData() {

        tomorrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TomorrowOrderActivity.this, OperateActivity.class));
                finish();
            }
        });

        tomorrow_hangzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tomorrow_webview.loadUrl(Constant.TOMORROW_ORDER+"Hangzhou");
                        WebSettings settings = tomorrow_webview.getSettings();
                        settings.setUseWideViewPort(true);
                        settings.setLoadWithOverviewMode(true);
                        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

                        tomorrow_webview.setWebChromeClient(new WebChromeClient(){
                            @Override
                            public void onReceivedTitle(WebView view, String title) {
                                tomorrow_text_title.setText(title);
                                super.onReceivedTitle(view, title);
                            }
                        });

                        tomorrow_webview.setWebViewClient(new WebViewClient(){
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return super.shouldOverrideUrlLoading(view, url);
                            }
                        });

                        settings.setDisplayZoomControls(true);
                    }
                });
            }
        });

        tomorrow_lanzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tomorrow_webview.loadUrl(Constant.TOMORROW_ORDER+"Lanzhou");
                        WebSettings settings = tomorrow_webview.getSettings();
                        settings.setUseWideViewPort(true);
                        settings.setLoadWithOverviewMode(true);
                        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

                        tomorrow_webview.setWebChromeClient(new WebChromeClient(){
                            @Override
                            public void onReceivedTitle(WebView view, String title) {
                                tomorrow_text_title.setText(title);
                                super.onReceivedTitle(view, title);
                            }
                        });

                        tomorrow_webview.setWebViewClient(new WebViewClient(){
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return super.shouldOverrideUrlLoading(view, url);
                            }
                        });

                        settings.setDisplayZoomControls(true);
                    }
                });
            }
        });


    }

    private void initView() {

        tomorrow_back = (Button) findViewById(R.id.tomorrow_back);
        tomorrow_hangzhou = (Button) findViewById(R.id.tomorrow_hangzhou);
        tomorrow_lanzhou = (Button) findViewById(R.id.tomorrow_lanzhou);
        go_back = (Button) findViewById(R.id.go_back);

        tomorrow_text_title = (TextView) findViewById(R.id.tomorrow_text_title);
        tomorrow_webview = (WebView) findViewById(R.id.tomorrow_webview);

    }
}
