package com.greenomunger.greenomunger;

import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class callback extends WebViewClient {
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return false;
    }
}