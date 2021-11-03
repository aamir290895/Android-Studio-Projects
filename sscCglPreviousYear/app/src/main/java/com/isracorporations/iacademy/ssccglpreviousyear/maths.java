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

public class maths extends AppCompatActivity {
    private Context context;
    ArrayList<String> list;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);
        context = maths.this;
        array();
        AdView mAdView = findViewById(R.id.adViewMaths);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView)findViewById(R.id.rvMaths);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mathsAdapter indexAdapter = new mathsAdapter(context, list, new ItemClicker() {
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

        list.add(IndexMaterials.M1);


    }
}