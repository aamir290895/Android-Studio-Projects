package com.isracorporations.iacademy.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.isracorporations.iacademy.camera.databinding.ActivityResultBinding;

public class Result extends AppCompatActivity {
    ActivityResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView2.setImageURI(getIntent().getData());

    }
}