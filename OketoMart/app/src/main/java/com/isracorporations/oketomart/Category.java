package com.isracorporations.oketomart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.shopOne.DailyNeeds;
import com.isracorporations.oketomart.ui.home.Model;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
     Spinner group,subGroup;
     Button button;
     ArrayList<String> groups;
     ArrayAdapter<String> adapterGroup;
     ArrayList<String> grocery,fruits,dairy,bakery,healthAndCare,mensFashion,stationary,computer_mobile,womensfashion ;
    ArrayAdapter<String> adapterSubGroup;
    FirebaseAuth auth;
    DatabaseReference reference;
   String pinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        group= findViewById(R.id.cc_group);
        subGroup= findViewById(R.id.cc_sub_group);
        button= findViewById(R.id.btn_category);
        auth=FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model model = snapshot.getValue(Model.class);
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


        mensFashion= new ArrayList<>();
        mensFashion.add("Shaving Essentials");
        mensFashion.add("Deos");
        mensFashion.add("Mens talc");
        mensFashion.add("Mens Creams");
        mensFashion.add("Gels");
        mensFashion.add("Wax");
        mensFashion.add("Googles");
        mensFashion.add("Belts");


        stationary= new ArrayList<>();
        stationary.add("Pens");
        stationary.add("Papers");
        stationary.add("Colors");
        stationary.add("Sharpeners");
        stationary.add("Erasers");
        stationary.add("Pencils");
        stationary.add("Glues");
        stationary.add("Scissors");
        stationary.add("Sticky Notes");
        stationary.add("Notebooks");
        stationary.add("Writing Pad");
        stationary.add("Spiral Notebooks");
        stationary.add("Project Books");
        stationary.add("Files|Tags");
        stationary.add("Cases|Geometry Boxes");
        stationary.add("Glitter color pens");
        stationary.add("Staple|Stapler Pins");

        computer_mobile = new ArrayList<>();
        computer_mobile.add("Headsets");
        computer_mobile.add("Charges(mobile)");
        computer_mobile.add("Cables|Data Cables");
        computer_mobile.add("Adapters");
        computer_mobile.add("Mobile Batteries");
        computer_mobile.add("Mobile Covers");
        computer_mobile.add("KeyBoards");
        computer_mobile.add("Computer Mouse");
        computer_mobile.add("Speakers");
        computer_mobile.add("Pen Drives|Wifi Adapters");
        computer_mobile.add("Power Banks");
        computer_mobile.add("Screen Protectors");


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
                Intent i = new Intent(Category.this, DailyNeeds.class);
                i.putExtra("kit" ,subGroup.getSelectedItem().toString());
                i.putExtra("group" , group.getSelectedItem().toString());
                i.putExtra("pincode", pinCode);
                startActivity(i);
            }
        });
    }

}