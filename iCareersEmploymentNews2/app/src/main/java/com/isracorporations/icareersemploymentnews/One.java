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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class One extends AppCompatActivity {
    RecyclerView recyclerView;
    OneAdapter adapter;
    Bundle bundle;
    ProgressDialog progressDialog;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
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
            context = One.this;
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("loading");






            recyclerView = (RecyclerView) findViewById(R.id.rv1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            bundle = getIntent().getExtras();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child(bundle.getString("indx"));
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
            adapter = new OneAdapter(options);
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

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.dismiss();
    }
}