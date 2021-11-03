package com.isracorporations.oketobusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Index extends AppCompatActivity {
    Button add,orders,queries;
    String pincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        add = findViewById(R.id.btn_category_class);
        orders = findViewById(R.id.btn_orders_check);
        queries = findViewById(R.id.btn_index_complains);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("s1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Modal model = snapshot.getValue(Modal.class);
                pincode = model.getPin();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Index.this,Category.class));
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://adept-stage-306721.web.app/"));
                startActivity(i);

            }
        });

        queries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Index.this,Category.class));
            }
        });
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder  dialog2 = new AlertDialog.Builder(Index.this);
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
}