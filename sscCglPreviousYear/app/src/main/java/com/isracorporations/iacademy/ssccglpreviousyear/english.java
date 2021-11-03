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

public class english extends AppCompatActivity {
    private Context context;
    ArrayList<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        context=english.this;
        array();
        AdView mAdView = findViewById(R.id.adViewEnglish);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView)findViewById(R.id.rvEnglish);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        englishAdapter  indexAdapter = new englishAdapter(context, list, new ItemClicker() {
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
        list.add(IndexMaterials.E1);
        list.add(IndexMaterials.E2);
        list.add(IndexMaterials.E3);
        list.add(IndexMaterials.E4);
        list.add(IndexMaterials.E5);
        list.add(IndexMaterials.E6);
        list.add(IndexMaterials.E7);
        list.add(IndexMaterials.E8);
        list.add(IndexMaterials.E9);
        list.add(IndexMaterials.E10);
        list.add(IndexMaterials.E11);
        list.add(IndexMaterials.E12);
        list.add(IndexMaterials.E13);
        list.add(IndexMaterials.E14);
        list.add(IndexMaterials.E15);
        list.add(IndexMaterials.E16);
        list.add(IndexMaterials.E17);
        list.add(IndexMaterials.E18);
       


    }
}