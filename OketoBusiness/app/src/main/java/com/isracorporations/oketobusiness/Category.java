package com.isracorporations.oketobusiness;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category extends AppCompatActivity {
    Spinner group,subGroup;
    Button button,buttonEdit;
    ArrayList<String> groups;
    ArrayAdapter<String> adapterGroup;
    ArrayList<String> grocery,fruits,dairy,bakery,healthAndCare,mensFashion,stationary,womensfashion,computer_mobile ;
    ArrayAdapter<String> adapterSubGroup;
    FirebaseAuth auth;
    DatabaseReference reference;
    String pinCode;
    Toolbar toolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_pin:
                 setPin();
                 break;

            case R.id.action_logout:
                auth.signOut();
                FirebaseUser user_x = auth.getCurrentUser();
                if (user_x == null){
                    startActivity(new Intent(Category.this,Login.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setPin() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Loading");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        Dialog dialog = new Dialog(Category.this);
        dialog.setContentView(R.layout.pin_select);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        Spinner s = dialog.findViewById(R.id.change_pin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Category.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.pin_code1));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(arrayAdapter);
        dialog.show();


        Button set = dialog.findViewById(R.id.set_new_pin);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
                Map<String,Object> map = new HashMap<>();
                map.put("pin",s.getSelectedItem().toString());
                reference.child("s1").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        pd.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        toolbar=  findViewById(R.id.toolbar_one);
        toolbar.setTitle("Category");
        toolbar.setTitleTextColor(Color.WHITE);

        setActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_pin);

        group= findViewById(R.id.cc_group);
        subGroup= findViewById(R.id.cc_sub_group);
        button= findViewById(R.id.btn_category);
        buttonEdit=findViewById(R.id.btn_edit);
        auth=FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid()).child("s1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Modal model = snapshot.getValue(Modal.class);
                pinCode = model.getPin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        groups = new ArrayList<>();
        groups.add("Groceries");
        groups.add("Dairy");
        groups.add("Bakery");
        groups.add("Fruits & Vegetables");
        groups.add("Health & Care");



        grocery= new ArrayList<>();
        grocery.add("Atta|Flour");
        grocery.add("Rice|Rice Products");
        grocery.add("Cooking Oils");
        grocery.add("Dals|Pulses");
        grocery.add("Organic Staples|Seeds");
        grocery.add("Raw Spices");
        grocery.add("Spices|Masalas");
        grocery.add("Sugar|Salt|Jaggery");
        grocery.add("Dry Fruits and Nuts");
        grocery.add("Sauces");
        grocery.add("Soaps");
        grocery.add("Detergents");
        grocery.add("Chocolates");
        grocery.add("Biscuits|Snacks|Toast");
        grocery.add("Dishwash & Cleaners");
        grocery.add("Noodles and more");
        grocery.add("Tea and Coffee");
        grocery.add("Oral Care");
        grocery.add("Shampoos");
        grocery.add("Body lotion");
        grocery.add("Make up|Perfumes|talc");
        grocery.add("Hair Oils|Hair Dies");
        grocery.add("Pickles");
        grocery.add("Hand Wash & Sanitizers");
        grocery.add("Air Freshners");
        grocery.add("Agarbatti|Dhoop");
        grocery.add("Insects Killer");



        womensfashion=new ArrayList<>();
        womensfashion.add("Moisturizers");
        womensfashion.add("Creams");
        womensfashion.add("Face Wash");
        womensfashion.add("Face Pack");
        womensfashion.add("Face Scrub");
        womensfashion.add("Night Creams");
        womensfashion.add("Eye Liners");
        womensfashion.add("Kajal");
        womensfashion.add("Makeup Brushes");
        womensfashion.add("BB Cream");
        womensfashion.add("CC Cream");
        womensfashion.add("Foundation");
        womensfashion.add("Face Powder");
        womensfashion.add("Lipstick");
        womensfashion.add("Lip Liner");
        womensfashion.add("Highlighters");
        womensfashion.add("Blusher");
        womensfashion.add("Shimmer Eye Shadow");
        womensfashion.add("Concealer");
        womensfashion.add("Lip gloss");
        womensfashion.add("Glitter Eye Liner");
        womensfashion.add("Makeup Kit");
        womensfashion.add("Setting Spray");
        womensfashion.add("Facial Fit");
        womensfashion.add("Contour");
        womensfashion.add("Face Serum");
        womensfashion.add("Bleach");
        womensfashion.add("Primer");
        womensfashion.add("Nail Polish");
        womensfashion.add("Compact Powder");
        womensfashion.add("Micellar Cleansing Water");
        womensfashion.add("Setting Powder");
        womensfashion.add("Bindi");
        womensfashion.add("Sindur");
        womensfashion.add("Eyebrow Pencils");



        fruits=new ArrayList<>();
        fruits.add("Fruits");
        fruits.add("Vegetables");

        dairy=new ArrayList<>();
        dairy.add("Milk|Milk Products");
        dairy.add("Sweets");
        dairy.add("Ghee|Butter|Cheese");
        dairy.add("Cold Drinks|Juices");


        bakery= new ArrayList<>();
        bakery.add("Cakes");
        bakery.add("Donuts");
        bakery.add("Muffins|Cupcakes");
        bakery.add("Truffle");
        bakery.add("Breads");
        bakery.add("Toast");
        bakery.add("Baked Cookies");

        healthAndCare= new ArrayList<>();
        healthAndCare.add("Baby Personal Care");
        healthAndCare.add("Oral Care");
        healthAndCare.add("Feminine Hygiene");
        healthAndCare.add("Mom Care");
        healthAndCare.add("Masks & Sanitizers");
        healthAndCare.add("Sexual Wellness");
        healthAndCare.add("Pain Relief");



        adapterGroup = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,groups);
        group.setAdapter(adapterGroup);

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,grocery);
                }
                if(position == 1){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,dairy);
                }
                if(position == 2){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,bakery);
                }
                if(position == 3){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,fruits);
                }
                if(position == 4){
                    adapterSubGroup = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,healthAndCare);
                }
                subGroup.setAdapter(adapterSubGroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, addProduct.class);
                i.putExtra("kit" ,subGroup.getSelectedItem().toString());
                i.putExtra("group" , group.getSelectedItem().toString());
                i.putExtra("pincode", pinCode);
                startActivity(i);
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Category.this, ProductsDetails.class);
                i.putExtra("kit" ,subGroup.getSelectedItem().toString());
                i.putExtra("group" , group.getSelectedItem().toString());
                i.putExtra("pincode", pinCode);
                startActivity(i);
            }
        });
    }



}