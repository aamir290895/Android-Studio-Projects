package com.isracorporations.indexmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class intent extends AppCompatActivity {
    private Context context;
    private Bundle extras;
    int position = -1;
    ArrayList<String> list;

    ViewPager2 pager2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        pager2 = (ViewPager2) findViewById(R.id.pager);
        context = intent.this;
        extras = getIntent().getExtras();


    }

}