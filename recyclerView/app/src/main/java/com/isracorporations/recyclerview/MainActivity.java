package com.isracorporations.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<String>list;
    Context context;
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rView);

        array();
        adapter = new CustomAdapter(context, list, new RecyclerViewClickInterface() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,MainActivity2.class);
                intent.putExtra("title",list.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        }

    private void array() {
        list = new ArrayList<>();
        list.add("one");
        list.add("two");

    }


}



