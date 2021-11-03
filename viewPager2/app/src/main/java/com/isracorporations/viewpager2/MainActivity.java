package com.isracorporations.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager2;
    int[] images = {R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
            R.drawable.one ,
            R.drawable. two ,
            R.drawable. one ,
            R.drawable. two ,



    };
    MainAdapter adapter;
    List<Activity> activities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list();
        setContentView(R.layout.activity_main);
        pager2 = findViewById(R.id.pager);
        adapter= new MainAdapter(activities);
        pager2.setAdapter(adapter);

    }
    private void list(){
        activities = new ArrayList<>();
        activities.add(new one());
        activities.add(new two());

    }
}