package com.isracorporations.iacademy.ssccglpreviousyear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class generalAwareness extends AppCompatActivity {
    private Context context;
    ArrayList<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_awareness);
        context = generalAwareness.this;
        array();
        AdView mAdView = findViewById(R.id.adViewGen);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        recyclerView = (RecyclerView)findViewById(R.id.rvGeneral);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        generalAwarenessAdapter  indexAdapter = new generalAwarenessAdapter(context, list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(context,Papers.class);
                i.putExtra("tit",list.get(position));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(indexAdapter);
    }
    private void array() {
        list= new ArrayList<>();
        list.add(IndexMaterials.G1);
        list.add(IndexMaterials.G2);
        list.add(IndexMaterials.G3);
        list.add(IndexMaterials.G4);
        list.add(IndexMaterials.G5);
        list.add(IndexMaterials.G6);
        list.add(IndexMaterials.G7);
        list.add(IndexMaterials.G8);
        list.add(IndexMaterials.G9);



    }
}