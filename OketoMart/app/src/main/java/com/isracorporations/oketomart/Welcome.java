package com.isracorporations.oketomart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Thread thread1 = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(Welcome.this,MainActivity.class));

                    }

                }
            };
            thread1.start();

        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        Intent intent = new Intent(Welcome.this, LoginOtp.class);
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