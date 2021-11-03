package com.greenomunger.greenomunger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebView;

import com.greenomungertwo.greenomunger.R;

public class One extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);

        webView.getSettings().setDisplayZoomControls(false);

        webView.setWebViewClient(new callback());

        webView.loadUrl("https://greenomunger.com/");


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder  dialog2 = new AlertDialog.Builder(One.this);
        dialog2.setTitle("Exit Application");
        dialog2.setMessage("Are you sure want to exit");
        dialog2.setCancelable(false);
        dialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialog2.create();
        alertDialog.show();
    }

}