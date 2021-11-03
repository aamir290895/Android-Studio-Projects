package com.isracorporations.iacademy.ssccglpreviousyear;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        IndexAdapter indexAdapter = new IndexAdapter(context,list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {

                final Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(context, reasoning.class);
                        break;

                    case 1:
                        intent =  new Intent(context, maths.class);

                        break;
                    case 2:
                        intent = new Intent(context,english.class);
                        break;

                    default:
                        intent = new Intent(context,generalAwareness.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(indexAdapter);

    }



    private void array() {
        list= new ArrayList<>();
        list.add(IndexMaterials.qq);
        list.add(IndexMaterials.qr);
        list.add(IndexMaterials.three);
        list.add(IndexMaterials.four);


      }
    }
