package com.isracorporations.oketomart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addDeliveryDetails extends AppCompatActivity {
  EditText name,address,phone;
  FirebaseAuth auth;
  Button save;
  String randomKey;
  Spinner pinCode,city;
    ArrayList<String> groups;
    ArrayAdapter<String> adapterGroup;
    ArrayList<String> shahdol,rewa;
    String total,weight,cost;
    ProgressDialog dialog;

    ArrayAdapter<String> adapterSubGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_details);
        name= findViewById(R.id.t_name);
        phone= findViewById(R.id.t_phone);
        address= findViewById(R.id.t_address);
        save = findViewById(R.id.t_save);
        city= (Spinner)findViewById(R.id.add_city);
        pinCode=(Spinner)findViewById(R.id.add_pin_d);
        auth = FirebaseAuth.getInstance();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
         String currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        String currentTime= simpleTimeFormat.format(calendar.getTime());
        randomKey=currentDate +currentTime;
        total= getIntent().getStringExtra("total");
        weight=getIntent().getStringExtra("weight");
        cost = getIntent().getStringExtra("cost");

        groups = new ArrayList<>();
        groups.add("Shahdol");
        groups.add("Rewa");

        shahdol= new ArrayList<>();
        shahdol.add("484001");
        rewa= new ArrayList<>();
        rewa.add("486001");

        dialog= new ProgressDialog(addDeliveryDetails.this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait......");
        dialog.setCancelable(false);

        adapterGroup = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,groups);
        city.setAdapter(adapterGroup);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,shahdol);
                }
                if(position == 1){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,rewa);
                }

                pinCode.setAdapter(adapterSubGroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (TextUtils.isEmpty(name.getText().toString())) {
                   Toast.makeText(addDeliveryDetails.this, "Fill Name", Toast.LENGTH_LONG).show();
               } else if (TextUtils.isEmpty(phone.getText().toString())) {
                   Toast.makeText(addDeliveryDetails.this, "Enter Mobile Number", Toast.LENGTH_LONG).show();

               } else if (TextUtils.isEmpty(address.getText().toString())) {
                   Toast.makeText(addDeliveryDetails.this, "Fill Address Details", Toast.LENGTH_LONG).show();
               } else {
                   dialog.show();
                   DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Address_Delivery").child(auth.getCurrentUser().getUid());
                   Map<String, Object> det = new HashMap<>();
                   det.put("name", name.getText().toString());
                   det.put("phone", phone.getText().toString());
                   det.put("address", address.getText().toString());
                   det.put("pinCode", pinCode.getSelectedItem().toString());
                   det.put("city", city.getSelectedItem().toString());
                   det.put("pid",randomKey);
                   ref.child(randomKey).updateChildren(det).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           Intent i = new Intent(addDeliveryDetails.this, DeliveryDetails.class);
                           i.putExtra("total", total);
                           i.putExtra("weight", weight);
                           i.putExtra("cost", cost);
                           startActivity(i);
                           dialog.dismiss();
                       }
                   });
               }
           }
       });


    }
}