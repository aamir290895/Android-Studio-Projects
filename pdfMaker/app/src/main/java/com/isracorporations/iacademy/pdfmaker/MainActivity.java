package com.isracorporations.iacademy.pdfmaker;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import android.content.ClipData;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;

    private int GO = 45;
    Button gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        gallery=(Button)findViewById(R.id.gallery);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent,GO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GO  && resultCode==RESULT_OK){
            ArrayList<Uri> arrayList = new ArrayList<>();

            ClipData clipData = data.getClipData();
            if(clipData != null){
                for(int i =0; i <clipData.getItemCount();i++){
                    Uri multi = clipData.getItemAt(i).getUri();
                    arrayList.add(multi);
                    Intent intent = new Intent(context,savePdf.class);
                    intent.putParcelableArrayListExtra("multipleImage", arrayList);

                    startActivity(intent);

                }
            }
        }
    }


}