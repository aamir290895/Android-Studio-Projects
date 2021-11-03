package com.isracorporations.indexmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    ArrayList<String> list;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        array();
        mContext = MainActivity.this;
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        IndexAdapter indexAdapter = new IndexAdapter(mContext,list, new ItemClicker() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(mContext, intent.class);
                        break;

                    case 1:
                        intent =  new Intent(mContext, Intent2.class);
                        break;
                    default:
                        intent =  new Intent(mContext, MainActivity2.class);
                        break;
                }
                mContext.startActivity(intent);

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


    }
}