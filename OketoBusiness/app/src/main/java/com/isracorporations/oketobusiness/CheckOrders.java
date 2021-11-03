package com.isracorporations.oketobusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckOrders extends AppCompatActivity {
   RecyclerView rv;
   AdapterCheckOrders one;
   String pincode;
   TextView pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orders);
        rv=(RecyclerView) findViewById(R.id.orders_list);
        pin= findViewById(R.id.tv_pin_check_orders);

        pincode = getIntent().getStringExtra("pin");
        pin.setText("Customer Queries @ :" + pincode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("orders").child(pincode);

        FirebaseRecyclerOptions<ModelOrders> options = new FirebaseRecyclerOptions.Builder<ModelOrders>()
                .setQuery(ref, ModelOrders.class)
                .build();
        one= new AdapterCheckOrders(options);
        rv.setAdapter(one);
    }

    @Override
    protected void onStart() {
        super.onStart();
        one.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        one.stopListening();
    }
}