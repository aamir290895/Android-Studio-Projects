
package com.isracorporations.modernhistory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ActivityOne extends AppCompatActivity {
    ViewPager2 pager2;
    int[] images = {R.drawable.oneone,
            R.drawable.two ,
            R.drawable.ty ,
    };
    AdapterOne adapter;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);


        pager2 = findViewById(R.id.pager);
        adapter= new AdapterOne(images);
        pager2.setAdapter(adapter);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }
}
