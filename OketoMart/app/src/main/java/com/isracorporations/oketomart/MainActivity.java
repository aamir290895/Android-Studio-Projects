package com.isracorporations.oketomart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.cart.CartActivity;
import com.isracorporations.oketomart.cart.CartAdapter;
import com.isracorporations.oketomart.shopOne.DailyNeeds;
import com.isracorporations.oketomart.ui.home.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amazon.Amazon;
import amazon.FlipCart;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle toggle;

    DatabaseReference ref1;
    DrawerLayout mDrawerLayout;

    TextView userName;
    FirebaseAuth auth;
    ImageButton cart;
    TextView count;
    String number;

    int k = 0;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this,LoginOtp.class));

        }else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Oketo");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_table_rows_24);

        setSupportActionBar(toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


      

        auth= FirebaseAuth.getInstance();

        Paper.init(this);
        ConnectivityManager connectivityManager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.retry);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }

        user();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_amazon)
//                .setDrawerLayout(mDrawerLayout)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.user_prof_name);


    }




    private void user(){

        ref1 = FirebaseDatabase.getInstance().getReference().child("user_info").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Model model = snapshot.getValue(Model.class);
                String name = model.getName();
                userName.setText( "Hello ,"+ name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void count(){

        DatabaseReference r3 = FirebaseDatabase.getInstance().getReference().child("cart_list").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        r3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 k += 1 ;
                 count.setText(String.valueOf(k));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                k -= 1 ;
                count.setText(String.valueOf(k));
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
    public void onBackPressed() {

        AlertDialog.Builder  dialog2 = new AlertDialog.Builder(MainActivity.this);
        dialog2.setTitle("Exit Application");
        dialog2.setMessage("Are you sure want to exit");
        dialog2.setCancelable(false);
        dialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

           int id=item.getItemId();
           switch (id){
//               case R.id.action_cart:
//                   startActivity(new Intent(MainActivity.this,CartActivity.class));
//                   break;

               case R.id.action_contact_us:
                   Dialog dialog = new Dialog(MainActivity.this);
                   dialog.setContentView(R.layout.query_by_user);
                   dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                   dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                   dialog.show();
                   EditText ww = dialog.findViewById(R.id.query_contact);
                   Button cc = dialog.findViewById(R.id.btn_contact_us);
                   Button back = dialog.findViewById(R.id.btn_contact_us_back);
                   back.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   cc.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           Intent i = new Intent(Intent.ACTION_VIEW);
                           String url = null;
                           try {
                               url = "https://api.whatsapp.com/send?phone="+ "+918224849403" +"&text=" + URLEncoder.encode(ww.getText().toString() , "UTF-8");
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           i.setPackage("com.whatsapp");
                           i.setData(Uri.parse(url));
                           startActivity(i);
                       }
                   });
                   break;
           }




        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem cartMenu = menu.findItem(R.id.action_cart);
        View actionView = cartMenu.getActionView();
        if(actionView != null){
             cart = actionView.findViewById(R.id.new_cart_one);
             count = actionView.findViewById(R.id.count_cart);
        }
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });

        count();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
      switch (id){


          case R.id.nav_amazon:
              startActivity(new Intent(MainActivity.this, MapsActivity.class));
              break;
          case R.id.nav_orders:

              startActivity(new Intent(MainActivity.this,OrdersDetails.class));
              break;

          case R.id.nav_categories:
              startActivity(new Intent(MainActivity.this,Category.class));
              break;
          case R.id.nav_cart:
              startActivity(new Intent(MainActivity.this,CartActivity.class));
              break;
          case R.id.nav_logout:
              FirebaseAuth.getInstance().signOut();
              FirebaseUser user_x = FirebaseAuth.getInstance().getCurrentUser();
              if (user_x == null){
                  startActivity(new Intent(MainActivity.this,LoginOtp.class));
              }
              break;
          case R.id.nav_pin_change:
              Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.pin_select);
              dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
              dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
              Spinner s = dialog.findViewById(R.id.change_pin);
              ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.pin_code_2));
              arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              s.setAdapter(arrayAdapter);
              dialog.show();


              Button set = dialog.findViewById(R.id.set_new_pin);
              set.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
                      Map<String,Object> map = new HashMap<>();
                      map.put("pin",s.getSelectedItem().toString());
                      reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                             dialog.dismiss();
                          }
                      });
                  }
              });
              break;

      }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}