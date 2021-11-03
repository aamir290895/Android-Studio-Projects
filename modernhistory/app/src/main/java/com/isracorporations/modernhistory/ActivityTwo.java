package com.isracorporations.modernhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

public class ActivityTwo extends AppCompatActivity {
    Context context;
    Bundle bundle;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        context = ActivityTwo.this;
        webView = findViewById(R.id.webView);
        bundle = getIntent().getExtras();
        String extras = bundle.getString("tit");
        String url = "file:///android_asset/" +extras +".html" ;
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);


    }
}