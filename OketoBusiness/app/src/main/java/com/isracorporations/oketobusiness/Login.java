package com.isracorporations.oketobusiness;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG ="done" ;

    FirebaseAuth auth;
    String i1,i2;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth= FirebaseAuth.getInstance();

        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        EditText userName = (EditText)findViewById(R.id.userName);
        EditText pass = (EditText)findViewById(R.id.password);

        Button login = (Button)findViewById(R.id.sendOtp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1 = userName.getText().toString();
                i2 = pass.getText().toString();
                dialog.show();

                auth.signInWithEmailAndPassword(i1,i2)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid()).child("s1");
                                    Map<String,Object>map = new HashMap<>();
                                    map.put("pin","484001");
                                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(Login.this,Index.class));
                                            dialog.dismiss();
                                        }
                                    });


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
}