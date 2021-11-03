package com.greenomungerthree.greenomunger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class One extends AppCompatActivity {
    WebView webView;
    String link;
    NetworkInfo wifi,mobile;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

            ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            webView = findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);


            webView.getSettings().setDisplayZoomControls(false);

            webView.setWebViewClient(new callback());
        if (wifi.isConnected()) {
            webView.setVisibility(View.VISIBLE);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("greeno");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model model = snapshot.getValue(Model.class);

                    webView.loadUrl(model.getLink());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else if(mobile.isConnected()){
            webView.setVisibility(View.VISIBLE);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("greeno");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model model = snapshot.getValue(Model.class);

                    webView.loadUrl(model.getLink());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            webView.setVisibility(View.INVISIBLE);

            Dialog dialog = new Dialog(One.this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.retry);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }

    }






    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
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

    private class callback extends WebViewClient {

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {

            if (url.startsWith("tel:") || url.startsWith("whatsapp:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

                return true;
            }

            return false;
        }
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webView.loadUrl("");

        }

    }
}