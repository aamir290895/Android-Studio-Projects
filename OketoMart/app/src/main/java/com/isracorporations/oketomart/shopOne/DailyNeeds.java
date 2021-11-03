
package com.isracorporations.oketomart.shopOne;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.isracorporations.oketomart.Category;
import com.isracorporations.oketomart.MainActivity;
import com.isracorporations.oketomart.Orders;
import com.isracorporations.oketomart.OrdersDetails;
import com.isracorporations.oketomart.R;
import com.isracorporations.oketomart.addDeliveryDetails;
import com.isracorporations.oketomart.cart.CartActivity;

public class DailyNeeds extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    BottomNavigationView navigationView;
    DailyNeedsAdapter adapter;
    SearchView searchView;
    String intent;
    String group;
    String subgroup,pincode;
    DatabaseReference reference2;
    FirebaseAuth auth;
    String uid;
    Context context;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_daily_needs, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_info:
                startActivity(new Intent(DailyNeeds.this,MainActivity.class));

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_needs);
        toolbar= findViewById(R.id.toolbar_daily_needs);
        toolbar.setTitleTextColor(Color.WHITE);
        context = DailyNeeds.this;
        setSupportActionBar(toolbar);
        auth= FirebaseAuth.getInstance();
        uid= auth.getCurrentUser().getUid();


        searchView = findViewById(R.id.search_daily_needs);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItem(newText);
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_daily_needs);
        navigationView=findViewById(R.id.bottom_nav_daily_needs);
        recyclerView= findViewById(R.id.rv_daily_needs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DailyNeeds.this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        subgroup = getIntent().getStringExtra("kit");
        group =getIntent().getStringExtra("group");
        pincode=getIntent().getStringExtra("pincode");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(group).child(subgroup).child(pincode);
        FirebaseRecyclerOptions<ModelDn> options = new FirebaseRecyclerOptions.Builder<ModelDn>()
                .setQuery(reference, ModelDn.class)
                .build();
        adapter= new DailyNeedsAdapter(options);
        recyclerView.setAdapter(adapter);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.nav_dn_orders:
                        startActivity(new Intent(context, OrdersDetails.class));
                        break;

                    case R.id.nav_dn_home:
                        startActivity(new Intent(context, MainActivity.class));

                        break;
                    case R.id.nav_dn_cart:
                        startActivity(new Intent(context, CartActivity.class));

                        break;
                    case R.id.nav_dn_categories:
                        startActivity(new Intent(context, Category.class));

                        break;

                }

                return true;
            }
        });


    }


    private void searchItem(String newText) {

        Query reference =  FirebaseDatabase.getInstance().getReference().child(group).child(subgroup).child(pincode).orderByChild("item").startAt(newText).endAt(newText + "\uf8ff");
        FirebaseRecyclerOptions<ModelDn> options = new FirebaseRecyclerOptions.Builder<ModelDn>()
                .setQuery(reference, ModelDn.class)
                .build();
        adapter= new DailyNeedsAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

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