package com.greenomungerthree.greenomunger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this,One.class));

                }

            }
        };
        thread1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}