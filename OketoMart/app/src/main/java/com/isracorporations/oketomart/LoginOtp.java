package com.isracorporations.oketomart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginOtp extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100 ;

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    SignInButton signInButton;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        Button reqOtp = (Button)findViewById(R.id.sendOtp);

        auth =FirebaseAuth.getInstance();
        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait.....");
        dialog.setCancelable(false);

        signInButton= findViewById(R.id.login_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        reqOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  signInAnamyous();
                  dialog.show();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
         signInButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               signIn();
               dialog.show();
           }
       });
    }
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
                            Map<String,Object> pin = new HashMap<>();
                            pin.put("pin","484001");
                            pin.put("name","User");
                            reference.updateChildren(pin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(LoginOtp.this,UserInformation.class));
                                    dialog.dismiss();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });
    }
    private void signInAnamyous(){
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
                            Map<String,Object> pin = new HashMap<>();
                            pin.put("pin","484001");
                            pin.put("name","User");
                            reference2.updateChildren(pin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(LoginOtp.this,UserInformation.class));
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(LoginOtp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}