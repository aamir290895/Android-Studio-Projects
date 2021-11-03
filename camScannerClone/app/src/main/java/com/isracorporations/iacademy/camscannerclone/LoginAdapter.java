package com.isracorporations.iacademy.camscannerclone;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int tabs;
    public LoginAdapter(@NonNull FragmentManager fm,Context context , int tabs) {
        super(fm, tabs);
        this.tabs = tabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                loginTabFragment log = new loginTabFragment();
                return log;
            case 1:
                signupTabFragment sig = new signupTabFragment();
                return sig;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabs;
    }
}
