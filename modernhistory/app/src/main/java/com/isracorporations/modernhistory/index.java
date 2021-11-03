package com.isracorporations.modernhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class index extends AppCompatActivity {

    private Context mContext;
    ArrayList<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        array();
        mContext = index.this;
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        IndexAdapter indexAdapter = new IndexAdapter(mContext,list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(mContext,ActivityTwo.class);
                i.putExtra("tit",list.get(position));
                startActivity(i);
//                final Intent intent;
//                switch (position){
//                    case 0:
//                        intent =  new Intent(mContext, ActivityOne.class);
//                        break;
//
//
//                    case 1:
//                        intent =  new Intent(mContext, ActivityTwo.class);
//                        intent.putExtra("tit", list.get(position));
//
//                        break;
//                    default:
//                        intent = new Intent(mContext,ActivityTwo.class);
//                }
//                mContext.startActivity(intent);

            }
        });
        recyclerView.setAdapter(indexAdapter);

    }

    private void array() {
        list= new ArrayList<>();
        list.add(IndexMaterials.one);
        list.add(IndexMaterials.TWO);
        list.add(IndexMaterials.three);
        list.add(IndexMaterials.four);
        list.add(IndexMaterials.five);
        list.add(IndexMaterials.six);

      }
    }
