package com.isracorporations.iacademy.camscannerclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signupTabFragment extends Fragment {
    EditText name,phone,email,password;
    Button signUp;
    FirebaseAuth auth;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.sign_up_tab_fragment, container, false);
        auth= FirebaseAuth.getInstance();
        name=root.findViewById(R.id.s1);
        phone=root.findViewById(R.id.s2);
        email=root.findViewById(R.id.s3);
        password=root.findViewById(R.id.s4);
        signUp = root.findViewById(R.id.sign_up);
        progressBar = root.findViewById(R.id.progress_bar_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users");
                        Map<String ,Object> details = new HashMap<>();
                        details.put( "name" ,name.getText().toString());
                        details.put( "phone" ,phone.getText().toString());
                        details.put( "email" ,email.getText().toString());
                        details.put( "password" ,password.getText().toString());
                        reference.setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
            }
        });

        return root;
    }


}
