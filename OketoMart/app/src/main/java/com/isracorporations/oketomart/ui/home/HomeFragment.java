package com.isracorporations.oketomart.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    RecyclerView r1,r2,r3,r4,r5,r6,r7,r8,r9;
    private  HomeViewModel one;
    private AdapterFruits fruits;
    private AdapterDairy dairy;
    private AdapterVegitables vegitables;
    private AdapterFive five;


    ImageSlider slider;
    List<SlideModel> list;
    String im1,im2,im3;

   TextView tv;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        r1 = root.findViewById(R.id.rv1);
        r2= root.findViewById(R.id.rv2);
        r3 = root.findViewById(R.id.rv3);
        r4 = root.findViewById(R.id.rv4);
        r5 = root.findViewById(R.id.rv5);
//        r6 = root.findViewById(R.id.rv6);
//        r7 = root.findViewById(R.id.rv7);
//        r8 = root.findViewById(R.id.rv8);
//        r9 = root.findViewById(R.id.rv9);
        tv= root.findViewById(R.id.pin_show_main);
        slider= root.findViewById(R.id.image_slider);
        slider.startSliding(9000);


        DatabaseReference ads = FirebaseDatabase.getInstance().getReference().child("banner_slider");
        ads.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelAds modelAds = snapshot.getValue(ModelAds.class);
                im1= modelAds.getOne();
                im2= modelAds.getTwo();
                im3 = modelAds.getThree();

                list= new ArrayList<>();
                list.add(new SlideModel(im1,ScaleTypes.FIT));
                list.add(new SlideModel(im2,ScaleTypes.FIT));
                list.add(new SlideModel(im3,ScaleTypes.FIT));
                slider.setImageList(list);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference refPin= FirebaseDatabase.getInstance().getReference().child("user_info").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        refPin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              Model model= snapshot.getValue(Model.class);
              tv.setText("Delivery to Pin Code :" + model.getPin());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(),1 ,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager5 = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager6 = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager7 = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager8 = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager9 = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);


        r1.setLayoutManager(gridLayoutManager);
        r2.setLayoutManager(gridLayoutManager2);
        r3.setLayoutManager(gridLayoutManager3);
        r4.setLayoutManager(gridLayoutManager4);
        r5.setLayoutManager(gridLayoutManager5);

        Query reference = FirebaseDatabase.getInstance().getReference().child("Category").child("Groceries");
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference, Model.class)
                .build();
        Query reference2 = FirebaseDatabase.getInstance().getReference().child("Category").child("Dairy");
        FirebaseRecyclerOptions<Model> options2 = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference2, Model.class)
                .build();
        Query reference3 = FirebaseDatabase.getInstance().getReference().child("Category").child("Bakery");
        FirebaseRecyclerOptions<Model> options3 = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference3, Model.class)
                .build();
        Query reference4 = FirebaseDatabase.getInstance().getReference().child("Category").child("Fruits & Vegetables");
        FirebaseRecyclerOptions<Model> options4 = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference4, Model.class)
                .build();

        Query reference5 = FirebaseDatabase.getInstance().getReference().child("Category").child("Health & Care");
        FirebaseRecyclerOptions<Model> options5 = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(reference5, Model.class)
                .build();



        one = new HomeViewModel(options);
        dairy=new AdapterDairy(options2);
        vegitables = new AdapterVegitables(options3);
        fruits= new AdapterFruits(options4);
        five = new AdapterFive(options5);

        r1.setAdapter(one);
        r2.setAdapter(dairy);
        r3.setAdapter(vegitables);
        r4.setAdapter(fruits);
        r5.setAdapter(five);

        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        one.startListening();
        fruits.startListening();
        vegitables.startListening();
        dairy.startListening();
        five.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        one.stopListening();
        fruits.stopListening();
        vegitables.stopListening();
        dairy.stopListening();
        five.stopListening();

    }
}