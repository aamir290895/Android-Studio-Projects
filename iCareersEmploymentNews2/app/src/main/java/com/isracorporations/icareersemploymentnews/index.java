package com.isracorporations.icareersemploymentnews;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class index extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    IndexAdapter indexAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.about_us);

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("loading");

        recyclerView = findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("upsc");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {

                            sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                            progressDialog.dismiss();

                        }

                    }
                };
                thread.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<list> options = new FirebaseRecyclerOptions.Builder<list>()
                .setQuery(ref, list.class)
                .build();
          indexAdapter = new IndexAdapter(options);

           recyclerView.setAdapter(indexAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        indexAdapter.startListening();
    }



    @Override
    protected void onStop() {
        super.onStop();
        indexAdapter.stopListening();
    }
}
