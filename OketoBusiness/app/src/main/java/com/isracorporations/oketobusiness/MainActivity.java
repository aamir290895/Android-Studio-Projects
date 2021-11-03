package com.isracorporations.oketobusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser  user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, Index.class);
            startActivity(intent);

        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);

                    }

                }
            };
            thread.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}