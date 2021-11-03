package com.isracorporations.oketobusiness;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProductsDetails extends AppCompatActivity {

    AdapterOne one;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);
        rv=(RecyclerView) findViewById(R.id.rv5);
        String group = getIntent().getStringExtra("group");
        String  subGroup = getIntent().getStringExtra("kit");
        String pinCode = getIntent().getStringExtra("pincode");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(group).child(subGroup).child(pinCode);

        FirebaseRecyclerOptions<Modal> options = new FirebaseRecyclerOptions.Builder<Modal>()
                .setQuery(ref, Modal.class)
                .build();
        one= new AdapterOne(options,pinCode,group,subGroup);
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