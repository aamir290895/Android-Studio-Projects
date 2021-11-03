package com.oketoshopping.oketodelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
            Sprite doubleBounce = new Wave();
            progressBar.setIndeterminateDrawable(doubleBounce);
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));

                    }

                }
            };
            thread1.start();
        } else {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
            Sprite doubleBounce = new Wave();
            progressBar.setIndeterminateDrawable(doubleBounce);
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(MainActivity.this, Login.class));

                    }

                }
            };
            thread1.start();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}