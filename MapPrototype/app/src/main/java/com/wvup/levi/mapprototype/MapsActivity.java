package com.wvup.levi.mapprototype;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    public static FusedLocationProviderClient flpc;
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        flpc = LocationServices.getFusedLocationProviderClient(this);
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
        mMap.setOnMapClickListener(this);
    }

    public void onClickCenter(View v){
        centerOnDevicesLocation();
    }


    public void centerOnDevicesLocation(){
        Log.d(TAG, "Entered getDevicesLocation");
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            flpc.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        Log.d(TAG, "location is" + location);
                        LatLng latLngOfLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        CircleOptions circle = new CircleOptions()
                                .center(latLngOfLoc).radius(200)
                                .strokeWidth(10.0f).strokeColor(0xFFFF0000);
                        mMap.addCircle(circle);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfLoc, 15.5f));
                    }
                }
            });
        }

        return;
    }

    /*
        Plan is to make Add_Location return back the Location object, then add it to the map

     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == LOCATION_REQUEST && resultCode == RESULT_OK){
            PlaceOfInterest fromAdd = (PlaceOfInterest) data.getSerializableExtra("Place");
            LatLng newLatLong = new LatLng(fromAdd.getLatitude(), fromAdd.getLongitude());
            mMap.addMarker(new MarkerOptions().position(newLatLong).title(fromAdd.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLong));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng));
    }



    @Override
    public boolean onMarkerClick(final Marker marker) {
        return true;
    }


    public void addLocation(View v){
        Intent AddLocation = new Intent(this, Add_Location.class);
        startActivityForResult(AddLocation, LOCATION_REQUEST);
    }
}
