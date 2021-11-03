package com.isracorporations.snapcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class start extends AppCompatActivity {
   FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ViewPager viewPager =findViewById(R.id.viewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter{



        public MyPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return ChatFragment.newInstance();
                case 1:
                    return StoryFragment.newInstance();
                case 2:

            }
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}