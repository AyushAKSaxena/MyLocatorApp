package com.example.ayush.mylocator;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String from, to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent searchActivity = getIntent();
        from = searchActivity.getStringExtra("from");
        to = searchActivity.getStringExtra("to");
        Log.i("from and to",from+ "and" +to);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder addressDecoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> fromAddress, toAddress;
        LatLng fromLatLng = null;
        LatLng toLatLng = null;

        try {
            fromAddress = addressDecoder.getFromLocationName(from,1);
            toAddress = addressDecoder.getFromLocationName(to,1);
            if (fromAddress == null || toAddress == null) {
                //For incorrect addresses
            }
            Address fromLocation = fromAddress.get(0);
            fromLocation.getLatitude();
            fromLocation.getLongitude();
            Address toLocation = toAddress.get(0);
            toLocation.getLatitude();
            toLocation.getLongitude();
            fromLatLng = new LatLng( fromLocation.getLatitude() * 1E6, fromLocation.getLongitude() * 1E6);
            toLatLng = new LatLng( toLocation.getLatitude() * 1E6, toLocation.getLongitude() * 1E6);
            //Log.e("lat lng values",""+fromLatLng +" , " +toLatLng);
            //String RequestLink = "https://maps.googleapis.com/maps/api/directions/json?origin=" +fromLatLng +""
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

