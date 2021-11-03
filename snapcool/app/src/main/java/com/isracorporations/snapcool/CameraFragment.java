
package com.isracorporations.snapcool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.view.OrientationEventListener;
import android.view.Surface;


import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.CameraView;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class CameraFragment extends AppCompatActivity implements CameraXConfig.Provider {
    private static int REQUEST_CODE_PERMISSIONS = 110;

    Button capture;
    CameraView cameraView;
    ImageCapture imageCapture;
    Preview preview;
    PreviewView previewView;
    OrientationEventListener orientationEventListener;
    ProcessCameraProvider cameraProvider;
    ImageAnalysis imageAnalysis;
    ProcessCameraProvider processCameraProvider;
    Camera camera;
    CameraControl cameraControl;
    ExecutorService cameraExecutor;


    CameraSelector cameraSelector;
    private File outputDirectory;


    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"
            , "android.permission.RECORD_AUDIO"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_fragment);
        cameraView = findViewById(R.id.viewFinder);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);


        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                preview = new Preview.Builder().build();
                imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build();
                cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, imageAnalysis, preview);
                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());

                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));


        capture = (Button) findViewById(R.id.camera_capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPhoto();
            }
        });


        cameraControl = camera.getCameraControl();


        orientationEventListener = new OrientationEventListener((Context) this) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation;

                // Monitors orientation values to determine the target rotation value
                if (orientation >= 45 && orientation < 135) {
                    rotation = Surface.ROTATION_270;
                } else if (orientation >= 135 && orientation < 225) {
                    rotation = Surface.ROTATION_180;
                } else if (orientation >= 225 && orientation < 315) {
                    rotation = Surface.ROTATION_90;
                } else {
                    rotation = Surface.ROTATION_0;
                }

                imageCapture.setTargetRotation(rotation);
            }
        };

        orientationEventListener.enable();
        imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .build();


    }
    public boolean allPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        int externalStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordAudio = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if(cameraPermission != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if(externalStorage != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(recordAudio != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_CODE_PERMISSIONS);
            return false;
        }

        return true;
    }

    private void clickPhoto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("xyz", Locale.US);
        final File file = new File(outputDirectory, dateFormat.format(new Date()) + ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(file).build();
        cameraView.setCaptureMode(CameraView.CaptureMode.IMAGE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
            cameraView.bindToLifecycle(this);
            cameraView.takePicture(outputFileOptions, cameraExecutor,
                    new ImageCapture.OnImageSavedCallback(){

                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {

                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {

                        }
                    });
        }



    private void bindPreview(ProcessCameraProvider cameraProvider) {



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if(!allPermissions()){
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.)
            }
        }
         else{

            }


        }

    private void startCamera() {

    }


    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {

        return  Camera2Config.defaultConfig();
    }
}