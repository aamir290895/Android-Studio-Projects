package com.isracorporations.okrydes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gps extends AppCompatActivity {

    Button enable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        enable = (Button) findViewById(R.id.gps);
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             permissions();
            }
        });

    }

    private void permissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(gps.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(gps.this).setTitle("permission need").setMessage("yes")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(gps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);

                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(gps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PackageManager.PERMISSION_GRANTED:
                if (ActivityCompat.checkSelfPermission(gps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(gps.this, maps.class);
                    startActivity(intent);
                } else {
                    permissions();

                }
        }
    }
}
