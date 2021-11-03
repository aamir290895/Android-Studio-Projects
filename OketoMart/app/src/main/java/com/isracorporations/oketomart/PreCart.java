
package com.isracorporations.oketomart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.shopOne.DailyNeeds;
import com.isracorporations.oketomart.ui.home.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PreCart extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,pin,quantityX;
    TextView description;
    ElegantNumberButton quantity;
    Button add;
    String pid,pincode;
    String currentDate,currentTime,randomKey,weight;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference reference2;
    String im;
    String group,subgroup,costPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_cart);
        imageView = findViewById(R.id.pre_cart_image);
        name = findViewById(R.id.name_pre_cart);
        price = findViewById(R.id.price_pre_cart);
        quantityX = findViewById(R.id.quantity_pre_cart);
        quantity = findViewById(R.id.multiple_pre_cart);
        add = findViewById(R.id.add_to_final_cart);
        pid = getIntent().getStringExtra("pid");
        subgroup  = getIntent().getStringExtra("subGroup");
        group    =getIntent().getStringExtra("group");
        pin =findViewById(R.id.pre_cart_pincode);
        auth= FirebaseAuth.getInstance();
        description =findViewById(R.id.see_description);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(PreCart.this);
                dialog.setContentView(R.layout.description_view);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

                dialog.show();
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
                reference1.child(group).child(subgroup).child(pincode).child(pid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Model model = snapshot.getValue(Model.class);
                            Glide.with(PreCart.this).load(model.getImage()).into(imageView);

                            String des = model.getDescription();
                            TextView tv = dialog.findViewById(R.id.splash_des);
                            tv.setText(des);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
                dialog.show();
            }
        });

        pincode = getIntent().getStringExtra("pin");
        pin.setText("Delivery Pin Code :" + pincode);
        getProductDetails(pid ,group,subgroup,pincode);
        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    private void addToCart() {
        String user= auth.getCurrentUser().getUid();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
        currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime= simpleTimeFormat.format(calendar.getTime());
        randomKey=currentDate +currentTime;
        int  totalPrice =Integer.parseInt( price.getText().toString().replaceAll("\\D+",""))  *Integer.parseInt (quantity.getNumber());
        int  totalWeight =Integer.parseInt( weight.replaceAll("\\D+",""))  *Integer.parseInt (quantity.getNumber());
        int cp = Integer.parseInt(costPrice.replaceAll("\\D+","")) * Integer.parseInt(quantity.getNumber());



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cart_list").child(user);
        Map<String,Object> cart = new HashMap<>();
        cart.put("pid",pid);
        cart.put("name",name.getText().toString());
        cart.put("price",price.getText().toString());
        cart.put("multiple",quantity.getNumber());
        cart.put("totalPrice",String.valueOf(totalPrice));
        cart.put("weight",String.valueOf(totalWeight));
        cart.put("perCostPrice",costPrice);
        cart.put("costPrice" ,String.valueOf(cp));




        ref.child(pid).updateChildren(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Intent i = new Intent(PreCart.this,DailyNeeds.class);
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
                    Model model = snapshot.getValue(Model.class);
                    Glide.with(PreCart.this).load(model.getImage()).into(imageView);
                    name.setText(model.getItem());
                    price.setText( "Rs" + " " + model.getPrice() + "/-");
                    quantityX.setText(model.getQuantity());
                    weight = model.getWeight();
                    costPrice = model.getCostPrice();
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