package com.isracorporations.oketomart.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isracorporations.oketomart.DeliveryDetails;
import com.isracorporations.oketomart.MainActivity;
import com.isracorporations.oketomart.PaymentMethods;
import com.isracorporations.oketomart.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button goToPay;
    TextView total,weight;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter adapter;
    FirebaseAuth auth;
    String totalAdd,totalSub,weightAdd,weightSub,cp;
    int i=0 ;
    int j =0;
    int k=0;
    int check=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView= findViewById(R.id.cart_item);
        goToPay= findViewById(R.id.go_to_pay);
        goToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check != 0 ) {
                    Intent i = new Intent(CartActivity.this, DeliveryDetails.class);
                    i.putExtra("total", total.getText().toString());
                    i.putExtra("weight", weight.getText().toString());
                    i.putExtra("cost", cp);
                    startActivity(i);
                }else {
                    Dialog dialog= new Dialog(CartActivity.this);
                    dialog.setContentView(R.layout.dialog_empty_cart);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                    dialog.show();
                    Button back = dialog.findViewById(R.id.dialog_ec_back);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(CartActivity.this, MainActivity.class));
                            dialog.dismiss();
                        }
                    });
                }
            }
        });


        total= findViewById(R.id.total_amount);
        weight= findViewById(R.id.total_weight);
        layoutManager= new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        auth= FirebaseAuth.getInstance();
        String user = auth.getCurrentUser().getUid();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("cart_list").child(user);
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(cartListRef, CartModel.class)
                .build();

        adapter= new CartAdapter(options);
        recyclerView.setAdapter(adapter);

        cartListRef.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              CartModel model = snapshot.getValue(CartModel.class);
              j += Integer.parseInt(model.getWeight());
              k += Integer.parseInt(model.getCostPrice());
              cp = String.valueOf(k);
              weightAdd=String.valueOf(j/1000);
              i +=  Integer.parseInt(model.getTotalPrice());
              totalAdd = String.valueOf(i);
              total.setText(  "Total Amount: Rs "  + totalAdd + "/-");
              weight.setText(  "Total Weight  =  "  + weightAdd + "kg");
              check += 1;

          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {



          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot snapshot) {
              CartModel model = snapshot.getValue(CartModel.class);
              j -= Integer.parseInt(model.getWeight());
              weightSub=String.valueOf(j/1000);
              k -=  Integer.parseInt(model.getCostPrice());
              cp =String.valueOf(k);
              i -=  Integer.parseInt(model.getTotalPrice());
              totalAdd = String.valueOf(i);
              total.setText( "Total Amount: Rs "+ totalAdd + "/-");
              weight.setText(  "Total Weight  =  "  + weightSub + "kg");
              check -= 1;
          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

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