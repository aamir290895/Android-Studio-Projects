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

public class reasoning extends AppCompatActivity {
    private Context context;
    ArrayList<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasoning);
        context=reasoning.this;
        array();
        AdView mAdView = findViewById(R.id.adViewReasoning);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView)findViewById(R.id.rvReasoning);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reasoningAdapter  indexAdapter = new reasoningAdapter(context, list, new ItemClicker() {
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

        list.add(IndexMaterials.R1);
        list.add(IndexMaterials.R2);
        list.add(IndexMaterials.R3);
        list.add(IndexMaterials.R4);
        list.add(IndexMaterials.R5);
        list.add(IndexMaterials.R6);
        list.add(IndexMaterials.R7);

        list.add(IndexMaterials.R17);
        list.add(IndexMaterials.R18);

    }
}