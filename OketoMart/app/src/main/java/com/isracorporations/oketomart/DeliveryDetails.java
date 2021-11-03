package com.isracorporations.oketomart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveryDetails extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    AdapterDeliveryDetails adapter;
    FirebaseAuth auth;
    String address,total,weight,cost;
    Button addDD;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        Toolbar toolbar = findViewById(R.id.toolbar12);
        toolbar.setTitle("Delivery Details");
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar_dd);
        recyclerView= findViewById(R.id.rv_dd);
        layoutManager= new LinearLayoutManager(this);
        addDD =findViewById(R.id.add_dd);
        auth= FirebaseAuth.getInstance();
        String  user = auth.getCurrentUser().getUid();
        total = getIntent().getStringExtra("total");
        weight = getIntent().getStringExtra("weight");
        cost=getIntent().getStringExtra("cost");

        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference cart = FirebaseDatabase.getInstance().getReference().child("Address_Delivery").child(auth.getCurrentUser().getUid());
        FirebaseRecyclerOptions<DeliveryModel> options = new FirebaseRecyclerOptions.Builder<DeliveryModel>()
                .setQuery(cart, DeliveryModel.class)
                .build();
        adapter= new AdapterDeliveryDetails(options,total,weight,cost);
        recyclerView.setAdapter(adapter);


        addDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i  =new Intent(DeliveryDetails.this,addDeliveryDetails.class);
               i.putExtra("total",total);
                i.putExtra("weight",weight);
                i.putExtra("cost",cost);
                startActivity(i);
            }
        });

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