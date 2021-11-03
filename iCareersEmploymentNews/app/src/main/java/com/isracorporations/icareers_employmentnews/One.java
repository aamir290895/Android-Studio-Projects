package com.isracorporations.icareers_employmentnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class One extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterOne adapter1;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        context = One.this;
        recyclerView = findViewById(R.id.rv1);


        FirebaseDatabase  database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FirebaseRecyclerOptions<list> options = new FirebaseRecyclerOptions.Builder<list>()
                .setQuery(ref, list.class)
                .build();

        adapter1 = new AdapterOne(options);
        recyclerView.setAdapter(adapter1);


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
}