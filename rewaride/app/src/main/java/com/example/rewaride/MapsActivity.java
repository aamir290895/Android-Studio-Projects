package com.example.rewaride;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.content.Context;
import android.location.Location;
import android.location.Geocoder;
import java.util.List;
import java.util.Locale;
import java.io.IOException;
import android.text.TextUtils;

/**
 * Created by jessicathornsby on 06/12/2017.
 */

