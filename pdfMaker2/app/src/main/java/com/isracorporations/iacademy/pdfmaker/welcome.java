package com.isracorporations.iacademy.pdfmaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

public class welcome extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = welcome.this;
        AdRequest adRequest = new AdRequest.Builder().build();




        Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);

                    }

                }
            };
            thread.start();
        }



    @Override
    protected void onPause() {
        super.onPause();
      finish();
    }
    }
