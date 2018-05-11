package com.mtyxp.hangzhoumtyxp.controller.fragment.statistic_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.utils.Constant;

/**
 * Created by CaoZhF on 2018-05-07.
 */

public class StatisticOrderFragment extends Fragment {

    private View view;
    private WebView statistic_order_wb;
    private TextView statistic_order_url;
    private Button html_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistic_fg_order,container,false);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        html_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statistic_order_wb.canGoBack()){
                    statistic_order_wb.goBack();
                }
            }
        });
    }

    private void initData() {

        statistic_order_wb.loadUrl(Constant.ORDER_CHECK);

        WebSettings settings = statistic_order_wb.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        statistic_order_wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                statistic_order_url.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

        statistic_order_wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        settings.setDisplayZoomControls(true);

    }

    private void initView() {
        statistic_order_wb = view.findViewById(R.id.statistic_order_wb);
        statistic_order_url = view.findViewById(R.id.statistic_order_url);
        html_back = view.findViewById(R.id.html_order_back);
    }

}
