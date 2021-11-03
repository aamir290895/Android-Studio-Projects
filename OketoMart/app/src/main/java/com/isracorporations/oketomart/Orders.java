package com.isracorporations.oketomart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Orders extends AppCompatActivity {
    String currentDate,currentTime,randomKey;
    FirebaseAuth auth;
    TextView showTotal ,showPayment,showTransId;
    String address,mobile,name,total,paymentMethod,delivery,totalBefore,cost;
    String transactionId,refId,user,id,pin;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
        showTotal = findViewById(R.id.orders_total);
        showPayment = findViewById(R.id.orders_ps);
        Button button= findViewById(R.id.btn_cnfrm_order);
        address = getIntent().getStringExtra("address");
        mobile = getIntent().getStringExtra("mobile");
        name = getIntent().getStringExtra("name");
        total=getIntent().getStringExtra("totalCost");
        paymentMethod= getIntent().getStringExtra("paymentMethod");
        delivery= getIntent().getStringExtra("delivery");
        totalBefore= getIntent().getStringExtra("total");
        cost=getIntent().getStringExtra("cost");
        pin=getIntent().getStringExtra("pincode");
        transactionId=getIntent().getStringExtra("transactionId");
        refId=getIntent().getStringExtra("refId");
        showTotal.setText("Rs"+" " + total + "/-");
        showPayment.setText(paymentMethod);
        showTransId = findViewById(R.id.trans_id);
        showTransId.setText(transactionId);

        dialog = new ProgressDialog(Orders.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFirebaseData();
                date();
                dialog.show();
            }
        });

    }



    private void date() {
        DatabaseReference date = FirebaseDatabase.getInstance().getReference().child("orders").child(pin).child(randomKey);
        Map<String,Object> dat =new HashMap<>();
        dat.put("date",randomKey);
        date.updateChildren(dat);
    }


    public void copyFirebaseData() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
        currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime= simpleTimeFormat.format(calendar.getTime());
        randomKey=currentDate +currentTime;
        DatabaseReference questionNodes = FirebaseDatabase.getInstance().getReference().child("cart_list").child(auth.getCurrentUser().getUid());
        DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("user_orders").child(auth.getCurrentUser().getUid()).child(randomKey);
        Map<String,Object> ss =new HashMap<>();
        ss.put("date",randomKey);
        ss.put("orderId",randomKey.replaceAll("\\D+",""));
        ss.put("transactionId",transactionId);
        ss.put("payment" ,paymentMethod);
        ss.put("status" , "order placed");
        userData.updateChildren(ss);

        DatabaseReference toUsersQuestions = FirebaseDatabase.getInstance().getReference().child("orders").child(pin).child(randomKey).child("details");


        questionNodes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             toUsersQuestions.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     questionNodes.removeValue();
                     DatabaseReference date = FirebaseDatabase.getInstance().getReference().child("orders").child(pin).child(randomKey).child("main");

                     Map<String,Object> d = new HashMap<>();
                     d.put("GrandTotal",total);
                     d.put("deliveryCharges",delivery);
                     d.put("address" ,address);
                     d.put("mobile" ,mobile);
                     d.put("name" ,name);
                     d.put("date",currentDate);
                     d.put("total",totalBefore);
                     d.put("orderId",randomKey.replaceAll("\\D+",""));
                     d.put("transactionId",transactionId);
                     d.put("refId",refId);
                     d.put("paymentMethod" ,paymentMethod);
                     d.put("cost",cost);
                     d.put("status","Order Placed");

                     date.updateChildren(d).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(Orders.this,MainActivity.class));
                            dialog.dismiss();
                         }
                     });
                 }
             });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
        String currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        String currentTime= simpleTimeFormat.format(calendar.getTime());
        String randomKey=currentDate +currentTime;

        AlertDialog.Builder  dialog2 = new AlertDialog.Builder(Orders.this);
        dialog2.setTitle("Cancel Order");
        dialog2.setMessage("Are you sure want to cancel order");
        dialog2.setCancelable(false);
        dialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog pd = new ProgressDialog(Orders.this);
                pd.setTitle("Loading");
                pd.setMessage("Please wait....");
                pd.setCancelable(false);
                pd.show();

               DatabaseReference ref_transaction = FirebaseDatabase.getInstance().getReference().child("failed_transactions").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
               Map<String,Object> m =new HashMap<>();
               m.put("tansactionId",transactionId);
               m.put("status","backed before confirm order");
               ref_transaction.updateChildren(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       DatabaseReference user_order = FirebaseDatabase.getInstance().getReference().child("user_orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomKey);
                       Map<String,Object> pp =new HashMap<>();
                       pp.put("date",randomKey);
                       pp.put("orderId",randomKey.replaceAll("\\D+",""));
                       pp.put("transactionId",transactionId);
                       pp.put("payment" ,paymentMethod);
                       pp.put("status" , "Order Cancelled");
                       user_order.updateChildren(pp).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               startActivity(new Intent(Orders.this,MainActivity.class));
                               pd.dismiss();
                           }
                       });
                   }
               });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dialog2.create();
        alertDialog.show();
    }



}