package com.wvup.levi.mapprototype;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, LocationListener{

    private GoogleMap mMap;
    public static LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_REQUEST = 4;
    private static final int REQUEST_LOC_PERMISSION = 1;
    private static final int MIN_DISTANCE = 25;
    private ArrayList<LatLng> routePoints;
    private boolean trackingRoute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        routePoints = new ArrayList<>();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    public void eventStartTrackingRoute(View v){
        trackingRoute = true;
        //mount up tracking listeners
        startTrackingRoute();
        //grab button to switch text and onClickListener
        changeStartRouteButton(trackingRoute);
    }

    private void changeStartRouteButton(final boolean startTracking){
        Button startRoute = findViewById(R.id.trackRoute);
        if(startTracking){
            startRoute.setText("Stop Tracking");
        }
        else{
            startRoute.setText("Start Route");
        }
        startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startTracking){
                    eventStopTrackingRoute(view);
                }
                else{
                    eventStartTrackingRoute(view);
                }
            }
        });
    }

    public void eventStopTrackingRoute(View v){
        if(trackingRoute){
            trackingRoute = false;
            //Unmount tracking listeners
            locationManager.removeUpdates(this);
            changeStartRouteButton(trackingRoute);
        }
    }

    private void startTrackingRoute(){
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //Starts up alert dialog for getting permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOC_PERMISSION);
        }
        else{
            //Start the location updater listener
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, MIN_DISTANCE, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
            }
        }
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
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            //Adds a center on me button to map
            mMap.setMyLocationEnabled(true);
    }

    //Gets location returned from Add_Location
    //https://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == LOCATION_REQUEST && resultCode == RESULT_OK){
            PlaceOfInterest fromAdd = (PlaceOfInterest) data.getSerializableExtra("Place");
            LatLng newLatLong = new LatLng(fromAdd.getLatitude(), fromAdd.getLongitude());
            MarkerOptions placeMarker = new MarkerOptions().position(newLatLong).title(fromAdd.getName());
            if(fromAdd.getPicture() != null){
                Log.d(TAG, "getPicture was not null");
                int height = 100;
                int width = 100;
                Bitmap picture = ByteConvertor.convertToBitmap(fromAdd.getPicture());
                BitmapDrawable d = new BitmapDrawable(getResources(), picture);
                Bitmap smallMarker = Bitmap.createScaledBitmap(d.getBitmap(), width, height, false);

                placeMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            }
            mMap.addMarker(placeMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLong));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG,"Map clicked at " + latLng);
        //mMap.addMarker(new MarkerOptions().position(latLng));
    }



    @Override
    public boolean onMarkerClick(final Marker marker) {
        return true;
    }


    public void addLocation(View v){
        Intent AddLocation = new Intent(this, Add_Location.class);
        startActivityForResult(AddLocation, LOCATION_REQUEST);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "My location is" + location);
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        routePoints.add(currentLocation);
        if(routePoints.size() > 1){
            Polyline route = mMap.addPolyline(new PolylineOptions());
            route.setPoints(routePoints);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
