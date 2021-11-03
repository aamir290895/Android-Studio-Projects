package com.isracorporations.ihubemploymentnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class News extends AppCompatActivity {
    RecyclerView recyclerView;
    newsAdapter  adapter;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        progressDialog = new ProgressDialog(News.this);

        recyclerView = (RecyclerView)findViewById(R.id.rView);
        FirebaseDatabase  database=FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<list> options = new FirebaseRecyclerOptions.Builder<list>()
                .setQuery(ref, list.class)
                .build();
        adapter= new newsAdapter(options);
        recyclerView.setAdapter(adapter);


    }
    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}