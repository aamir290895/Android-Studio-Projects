package com.isracorporations.oketobusiness;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

public class EditProductDetails extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,pin;
    Button del,updateDes,copyProduct;
    String pid,pincode;
    FirebaseAuth auth;
    ProgressDialog dialog;
    EditText cp,sp,crossed,quantity,description,newName,weight;

    String group,subgroup;
    ImageButton buttonSp,buttonCp,buttonQuantity,buttonCrossed,buttonName,buttonWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_details);
        imageView = findViewById(R.id.pre_cart_image);
        name = findViewById(R.id.name_pre_cart);
        price = findViewById(R.id.price_pre_cart);
        copyProduct= findViewById(R.id.copy_product);
        buttonName = findViewById(R.id.update_name);
        buttonWeight=findViewById(R.id.update_weight);
        newName= findViewById(R.id.new_name);
        weight= findViewById(R.id.new_weight);

        cp = (EditText)findViewById(R.id.new_cp);
        sp = (EditText)findViewById(R.id.new_sp);
        description=(EditText)findViewById(R.id.new_description);
        crossed = (EditText)findViewById(R.id.new_crossed_price);
        quantity = (EditText)findViewById(R.id.new_quantity);
        del =findViewById(R.id.del_product);

        pid = getIntent().getStringExtra("pid");
        subgroup  = getIntent().getStringExtra("subGroup");
        group    =getIntent().getStringExtra("group");
        pin =findViewById(R.id.pre_cart_pincode);
        auth= FirebaseAuth.getInstance();
        buttonCp=findViewById(R.id.update_cp);
        buttonCrossed= findViewById(R.id.update_crossed_price);
        buttonSp =findViewById(R.id.update_sp);
        buttonQuantity=findViewById(R.id.update_quantity);
        updateDes = findViewById(R.id.save_description);
        copyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy();
            }
        });
        buttonSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getText().toString() != null) {
                    updateSP();
                    dialog.show();
                }
            }
        });
        buttonWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeight();
                dialog.show();
            }
        });
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName();
                dialog.show();
            }
        });
        buttonCp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(cp.getText().toString())) {
                    Toast.makeText(EditProductDetails.this,"Fill cost price",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(sp.getText().toString())) {
                    Toast.makeText(EditProductDetails.this,"Fill selling price",Toast.LENGTH_LONG).show();

                }else if(TextUtils.isEmpty(crossed.getText().toString())) {
                    Toast.makeText(EditProductDetails.this,"Fill crossed price",Toast.LENGTH_LONG).show();
                }else{
                    updateCP();
                    dialog.show();
                }
            }
        });
        buttonQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(quantity.getText().toString())) {
                    Toast.makeText(EditProductDetails.this,"Fill Quantity",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(weight.getText().toString())) {
                    Toast.makeText(EditProductDetails.this,"Fill Weight",Toast.LENGTH_LONG).show();

                }else{
                    updateQuantity();
                    dialog.show();
                }
            }
        });
        buttonCrossed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(crossed.getText().toString() != null){
                    updateCrossed();
                    dialog.show();
                }
            }
        });
        updateDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().toString() != null){
                    updateDescription();
                    dialog.show();
                }
            }
        });


        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait");

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup).child(pincode).child(pid);
                ref1.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                        i.putExtra("kit" ,subgroup);
                        i.putExtra("group" , group);
                        i.putExtra("pincode", pincode);
                        startActivity(i);
                    }
                });
            }
        });
        pincode = getIntent().getStringExtra("pin");
        pin.setText(pincode);
        getProductDetails(pid ,group,subgroup,pincode);


    }

    private void updateWeight() {
        DatabaseReference refWeight = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("weight",weight.getText().toString());

        refWeight.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);

            }
        });
    }

    private void updateName() {
        DatabaseReference refName = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("item",newName.getText().toString());

        refName.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);

            }
        });
    }

    private void copy() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
        String currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        String currentTime= simpleTimeFormat.format(calendar.getTime());
        String randomKey=currentDate +currentTime;

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        Dialog dialog = new Dialog(EditProductDetails.this);
        dialog.setContentView(R.layout.dialog_copy);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        Spinner s = dialog.findViewById(R.id.copy_pin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditProductDetails.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.pin_code1));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arrayAdapter);
        dialog.show();


        Button set = dialog.findViewById(R.id.copy_to_pin);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup).child(pincode).child(pid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelCopy model =snapshot.getValue(ModelCopy.class);
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup).child(s.getSelectedItem().toString()).child(randomKey);
                        Map<String,Object>map =new HashMap<>();
                        map.put("costPrice",model.getCostPrice());
                        map.put("crossed",model.getCrossed());
                        map.put("date",currentDate);
                        map.put("description",model.getDescription());
                        map.put("group",model.getGroup());
                        map.put("image",model.getImage());
                        map.put("item",model.getItem());
                        map.put("key",randomKey);
                        map.put("pin",s.getSelectedItem().toString());
                        map.put("price",model.getPrice());
                        map.put("quantity",model.getQuantity());
                        map.put("subGroup",model.getSubGroup());
                        map.put("time",currentTime);
                        map.put("weight",model.getWeight());
                        ref2.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                pd.dismiss();
                                dialog.dismiss();
                                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                                i.putExtra("kit" ,subgroup);
                                i.putExtra("group" , group);
                                i.putExtra("pincode", pincode);
                                startActivity(i);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    private void updateSP(){
        DatabaseReference refSP = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("price",sp.getText().toString());

        refSP.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);

            }
        });
    }

    private void updateCP(){
        DatabaseReference refCP = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("costPrice",cp.getText().toString());
        cart.put("crossed",crossed.getText().toString());
        cart.put("price",sp.getText().toString());

        refCP.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);


            }
        });
    }
    private void updateCrossed(){
        DatabaseReference refCrossed = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("crossed",crossed.getText().toString());

        refCrossed.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);


            }
        });
    }
    private void updateQuantity(){
        DatabaseReference refQuantity = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("quantity",quantity.getText().toString());
        cart.put("weight",weight.getText().toString());

        refQuantity.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);


            }
        });
    }
    private void updateDescription(){
        DatabaseReference refDescription = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup);
        Map<String,Object> cart = new HashMap<>();
        cart.put("description",description.getText().toString());

        refDescription.child(pincode).child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(EditProductDetails.this, ProductsDetails.class);
                i.putExtra("kit" ,subgroup);
                i.putExtra("group" , group);
                i.putExtra("pincode", pincode);
                startActivity(i);


            }
        });
    }


    private void getProductDetails(String pid,String group ,String subgroup,String pincode) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(group).child(subgroup).child(pincode).child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Modal model = snapshot.getValue(Modal.class);
                    Glide.with(EditProductDetails.this).load(model.getImage()).into(imageView);
                    name.setText(model.getItem());
                    price.setText(model.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}