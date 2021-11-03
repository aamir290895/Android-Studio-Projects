package com.isracorporations.iacademy.upsccivilservicespreviousyear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class index extends AppCompatActivity {

    private Context context;
    ArrayList<String> list;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context = index.this;
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        array();
        recyclerView = (RecyclerView)findViewById(R.id.rvi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        indexAdapter mIndexAdapter = new indexAdapter(context,list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {

                final Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(context, GeneralStudiesI.class);
                        break;


                    default:
                        intent = new Intent(context,GeneralStudiesII.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(mIndexAdapter);

    }



    private void array() {
        list= new ArrayList<>();
        list.add(IndexMaterials.qq);


    }
}

