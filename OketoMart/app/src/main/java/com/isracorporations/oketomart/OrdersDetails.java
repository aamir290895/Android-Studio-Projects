package com.isracorporations.oketomart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class OrdersDetails extends AppCompatActivity {
    RecyclerView rv;
    AdapterOrderDetails adapter;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
        rv = findViewById(R.id.rv_od);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        auth= FirebaseAuth.getInstance();


        Query reference = FirebaseDatabase.getInstance().getReference().child("user_orders").child(auth.getCurrentUser().getUid());
        FirebaseRecyclerOptions<ModelOrderDetails> options = new FirebaseRecyclerOptions.Builder<ModelOrderDetails>()
                .setQuery(reference, ModelOrderDetails.class)
                .build();
        adapter = new AdapterOrderDetails(options);

        rv.setAdapter(adapter);
        AdView mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
            adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

            adapter.stopListening();

    }
}