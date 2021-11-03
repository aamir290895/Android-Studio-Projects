package com.example.cab;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    private static final String TAG = "";
    private int TIME = 1000;
    private int DIST = 1;
    Button callingBtn;
    private int REQ_CODE = 101;
    private int CO = 1100;
    FirebaseUser user;



    DatabaseReference databaseReference;
    Marker userLocationMarker;
    Circle mcircleOptions;
    boolean b = false;
    ImageView im ;
    GeoFire geoFire;
    AutocompleteSupportFragment autocompleteFragment;
    EditText  write;
    TextView placeShow;
    SupportMapFragment mapFragment;
    PlacesClient placesClient;
    Place place;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private  SinchClient sinchClient;





    



    List<Marker>markerList = new ArrayList<Marker>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete);
        mapFragment.getMapAsync(this);
        android.content.Context context = this.getApplicationContext();
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        callingBtn = (Button) findViewById(R.id.calling);



        String apiKey = getString(R.string.google_api_key);
      if(!Places.isInitialized()) {
          Places.initialize(getApplicationContext(), apiKey);
      }

        placesClient = Places.createClient(MapsActivity.this);

        android.content.Context pok = this.getApplicationContext();



//
        write = (EditText) findViewById(R.id.loc);

        im = (ImageView)findViewById(R.id.imageView);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);

        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        locationCallback = new LocationCallback() {


            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult != null && locationResult.getLocations() != null) {
                    Location location = locationResult.getLastLocation();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    if (mMap != null) {
                        setUserLocationMarker(locationResult.getLastLocation());
                    }

                        DatabaseReference driverLocation = FirebaseDatabase.getInstance().getReference();
                        GeoFire geoFire = new GeoFire(driverLocation);

                        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latitude, longitude), 100);

                        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                            @Override
                            public void onKeyEntered(String key, GeoLocation location) {

                                for(Marker makerIt : markerList) {
                                    if (makerIt.getTag().equals(key))
                                        return;
                                }
                                    LatLng driverLatLng = new LatLng(location.latitude, location.longitude);
                                    Marker driverMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng));
                                    driverMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cr));

                                    driverMarker.setTag(key);
                                    markerList.add(driverMarker);
                                    im.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });
                                }


                            @Override
                            public void onKeyExited(String key) {

                                for (Marker makerIt : markerList) {
                                    if (makerIt.getTag().equals(key)){
                                        makerIt.remove();
                                        markerList.remove(makerIt);
                                        return;
                                    }

                                }
                            }

                            @Override
                            public void onKeyMoved(String key, GeoLocation location) {

                            }

                            @Override
                            public void onGeoQueryReady() {

                            }

                            @Override
                            public void onGeoQueryError(DatabaseError error) {

                            }
                        });

                    }
                }



        };

    }
  


    private void setUserLocationMarker(Location location){
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        if(userLocationMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    // Sets the center of the map to
                    .zoom(60)                   // Sets the zoom
                    .bearing(90) // Sets the orientation of the camera to east
                    .tilt(45)    // Sets the tilt of the camera to 30 degrees
                    .build();    // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    cameraPosition));
            
          //    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.p1));
          //   markerOptions.rotation(location.getBearing());
          //  markerOptions.anchor((float)0.5,(float)0.5);

        //    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
            userLocationMarker = mMap.addMarker(markerOptions);

        }else{
            userLocationMarker.setPosition(latLng);
            userLocationMarker.setRotation(location.getBearing());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        }
        if(mcircleOptions == null){
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.strokeWidth(4);
            circleOptions.strokeColor(Color.argb(32, 255, 0, 0));
            circleOptions.fillColor(Color.argb(32, 255, 0, 0));
            mcircleOptions = mMap.addCircle(circleOptions);
        }else {
            mcircleOptions.setCenter(latLng);
            mcircleOptions.setRadius(location.getAccuracy());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1  && resultCode == RESULT_OK){
            place = Autocomplete.getPlaceFromIntent(data);

            String address = place.getAddress();
            
            write.setText(address);


        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        check();
        callingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
call();
            }
        });


        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field>fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(MapsActivity.this);
                startActivityForResult(intent,1);

            }
        });




        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        

                        Toast.makeText(MapsActivity.this,"Hiii",Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
    private  void getLocation(){
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            mMap.setMyLocationEnabled(true);
        }else {
            check();
        }
    }
    private void check() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(MapsActivity.this).setTitle("permission need").setMessage("yes")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100 );

        }
    }

    @Override
    public void  onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode== 100) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }else{
                    check();
                }
        }

    }


   private  void call (){
       CallClient callClient = sinchClient.getCallClient();
     //   Call call = callClient.callUserVideo("<remote user id>");
       Call call = callClient.callUser("9340952682");
      call.addCallListener(new CallListener() {
                               @Override
                               public void onCallProgressing(Call call) {

                               }

                               @Override
                               public void onCallEstablished(Call call) {

                               }

                               @Override
                               public void onCallEnded(Call call) {

                               }

                               @Override
                               public void onShouldSendPushNotification(Call call, List<PushPair> list) {

                               }
                           });

// Or for video call: Call call = callClient.callUserVideo("<remote user id>");

   }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if(fusedLocationClient != null){
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference avail = database.getReference();
        GeoFire geoFire = new GeoFire(avail);
        geoFire.removeLocation(userId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
    }
}