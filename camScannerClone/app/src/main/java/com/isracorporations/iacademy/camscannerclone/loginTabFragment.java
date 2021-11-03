package com.isracorporations.iacademy.camscannerclone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginTabFragment extends Fragment {

    EditText email,password;
    TextView forgetPassword;
    Button login;
    FirebaseAuth auth;
    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.login_tab_fragment, container, false);
        email= root.findViewById(R.id.l1);
        password= root.findViewById(R.id.l2);
        forgetPassword = root.findViewById(R.id.forget_password);
        login= root.findViewById(R.id.b1);
        auth= FirebaseAuth.getInstance();
        progressBar = root.findViewById(R.id.progress_bar_signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                letsLogin();
            }
        });


        return root;
    }

    private void letsLogin() {

      String  i1 = email.getText().toString();
      String  i2 = password.getText().toString();

        auth.signInWithEmailAndPassword(i1,i2)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = auth.getCurrentUser();
                            if(user.isEmailVerified()){
                                startActivity(new Intent(getContext(),MainActivity.class));
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(requireContext()
                                        ,"Verify Email with link " ,Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(requireContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }



}
