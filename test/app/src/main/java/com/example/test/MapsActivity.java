package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private int ACCESS_LOCATION_REQUEST_CODE = 1001;
    private static final String TAG = "MapsActivity";
    LocationServices getLocationUpdates;

    LocationRequest locationRequest;
    GeofencingClient geofencingClient;
    Marker userLocationMarker;

    FusedLocationProviderClient fusedLocationProviderClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            geocoder = new Geocoder(this);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(4000);
            locationRequest.setFastestInterval(2000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



    }
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();

        }
        protected void onStop () {
            super.onStop();
            stopLocationUpdates();
        }


        private void startLocationUpdates () {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        }
        private void stopLocationUpdates () {

            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);
            mMap.setOnMarkerDragListener((GoogleMap.OnMarkerDragListener) this);



            // Add a marker in Sydney and move the camera
            //LatLng rewa = new LatLng(a,b);
            //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        public void onLocationResult (LocationResult locationResult){
            super.onLocationResult(locationResult);
            Log.d(TAG, "onLocationResult:" + locationResult.getLastLocation())
            if (mMap != null) {
                setUserLocationMarker(locationResult.getLastLocation());
            }

        }
        
        }

        private void GetLocationUpdates () {

            LocationServices.getFusedLocationProviderClient(this);

        }
        private void setUserLocationMarker (Location location){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (userLocationMarker == null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                userLocationMarker = mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            } else {
                userLocationMarker.setPosition(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));


            }



