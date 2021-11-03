package com.isracorporations.icareers_employmentnews;

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

public class Index extends AppCompatActivity {
    private  RecyclerView recyclerView;
    private Context context;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context=Index.this;
        array();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        IndexAdapter indexAdapter = new IndexAdapter(context,list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {

                final Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(context,One.class);
                        break;
                    case 1:
                        intent =  new Intent(context,Two.class);
                        break;
                    case 2:
                        intent =  new Intent(context,Three.class);
                        break;
                    case 3:
                        intent =  new Intent(context,Four.class);
                        break;
                    case 4:
                        intent =  new Intent(context,Five.class);
                        break;
                    case 5:
                        intent =  new Intent(context,Six.class);
                        break;
                    case 6:
                        intent =  new Intent(context,Seven.class);
                        break;
                    case 7:
                        intent =  new Intent(context,Eight.class);
                        break;

                    default:
                        intent = new Intent(context,Nine.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(indexAdapter);



    }
    private void array() {
        list= new ArrayList<>();
        list.add(IndexMaterials.one);
        list.add(IndexMaterials.two);
        list.add(IndexMaterials.three);

        list.add(IndexMaterials.four);
        list.add(IndexMaterials.five);
        list.add(IndexMaterials.six);
        list.add(IndexMaterials.seven);
        list.add(IndexMaterials.Eight);

        list.add(IndexMaterials.nine);


    }
}