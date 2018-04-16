package com.example.ayush.mylocator;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.SphericalUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    String from, to;
    DirectionsResult drivingResult, walkingResult, transitResult;
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
            drivingResult = getDirections(fromLatLng, toLatLng, TravelMode.DRIVING);
            walkingResult = getDirections(fromLatLng, toLatLng, TravelMode.WALKING);
            transitResult = getDirections(fromLatLng, toLatLng, TravelMode.TRANSIT);
            if(SphericalUtil.computeDistanceBetween(fromLatLng, toLatLng)>3000)
            {
                Toast.makeText(getApplicationContext(),"Distance less then 3km, walking is perferable",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.direction_API_key))
                .setConnectTimeout(3, TimeUnit.SECONDS)
                .setReadTimeout(3, TimeUnit.SECONDS)
                .setWriteTimeout(3, TimeUnit.SECONDS);
    }
    private DirectionsResult getDirections(LatLng fromLatLng, LatLng toLatLng, TravelMode mode) {
        DateTime now = new DateTime();
        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .origin(String.valueOf(fromLatLng))
                    .destination(String.valueOf(toLatLng))
                    .departureTime(now)
                    .await();
            return result;
        } catch (Exception e) {
         return null;
        }
    }
   


}