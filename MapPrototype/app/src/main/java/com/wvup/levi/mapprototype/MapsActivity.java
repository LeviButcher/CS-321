package com.wvup.levi.mapprototype;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * BoilerPlated FragmentActivity provided by Android Studio. Builds out Google Maps then allows functionality for
 * recording a route and adding location to that route. Once Route recording is stop, a new activity will be started
 * for saving all the data collected from this activity.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    public static LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    private static final int LOCATION_REQUEST = 4;
    private static final int REQUEST_LOC_PERMISSION = 1;
    private static final int MIN_DISTANCE = 25;
    private ArrayList<LatLng> routePoints;
    private ArrayList<PlaceOfInterest> places;
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
        places = new ArrayList<>();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setUpTracking();
    }

    /**
     * Sets up Tracking the route. Adds a onClick listener to the button with the id
     * of "trackRoute" that handles starting and stopping tracking routes.
     * Once stopped a new Activity is started for adding in information about the route
     * recorded
     */
    private void setUpTracking(){
        final Button startRoute = findViewById(R.id.trackRoute);

        startRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trackingRoute){
                    eventStopTrackingRoute();
                    startRoute.setText(R.string.startRoute);
                }
                else{
                    startTrackingRoute();
                    startRoute.setText(R.string.stopTracking);
                    trackingRoute = true;
                }
            }
        });
    }

    /**
     * event handler for stopping tracking the route. When called a new activity is started
     * for entering in information about the route but only if tracking was happening when this method is called.
     */
    private void eventStopTrackingRoute(){
        if(trackingRoute){
            trackingRoute = false;
            //Unmount tracking listeners
            locationManager.removeUpdates(this);
            //TODO Send DB stuff to Add_Route
            Intent addRoute = new Intent(this, Add_Route.class);
            addRoute.putExtra("places", places);
            addRoute.putExtra("routePoints", routePoints);
            //Once child finishes this activity will also finish
            finishFromChild(this);
            startActivity(addRoute);
        }
    }


    /**
     * Starts tracking the users current location and changes of locations.
     * If the users hasn't given permission for finding the location then the user is prompted when this method is called<br/>
     */
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,MIN_DISTANCE, this);
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
        mMap.setOnMapLongClickListener(this);
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //Adds a center on me button to map
            mMap.setMyLocationEnabled(true);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                routePoints.add(myLatLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15.5f));
            }
        }
    }

    /**
     * Gets the activity results from starting a activity for add a Location. Add_Location Activity returns back
     * a PointOfInterest to the called. This Maps activity gets the result of that within this method.
     * Resizing of Bitmaps Found at -> https://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == LOCATION_REQUEST && resultCode == RESULT_OK){
            PlaceOfInterest fromAdd = (PlaceOfInterest) data.getSerializableExtra("Place");
            LatLng newLatLong = new LatLng(fromAdd.getLatitude(), fromAdd.getLongitude());
            MarkerOptions placeMarker = new MarkerOptions().position(newLatLong).title(fromAdd.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            if(fromAdd.getPicture() != null){
                Log.d(TAG, "getPicture was not null");
                int height = 100;
                int width = 100;
                Bitmap picture = ByteConvertor.convertToBitmap(fromAdd.getPicture());
                BitmapDrawable d = new BitmapDrawable(getResources(), picture);
                Bitmap smallMarker = Bitmap.createScaledBitmap(d.getBitmap(), width, height, false);

                placeMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            }
            places.add(fromAdd);
            mMap.addMarker(placeMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLong));
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return true;
    }


    /**
     * OnClick event handler for adding a Location. Starts the Add_Location activity and passes in the lastKnow location
     * into the activity for where this location is at. <br/>This method is for dropping a location at the user's current
     * location
     * @param v
     */
    public void addLocation(View v){
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //This may not work
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double latitude = lastLocation.getLatitude();
            double longitude = lastLocation.getLongitude();
            startAddLocation(longitude, latitude);
        }
        else{
            Toast toast = new Toast(this);
            toast.setText("Missing Permissions");
            toast.show();
        }
    }

    /**
     * Starts up the Add_Location Activity
     * passing in Longitude and Latitude is required
     * @param longitude
     * @param latitude
     */
    private void startAddLocation(double longitude, double latitude){
        Intent AddLocation = new Intent(this, Add_Location.class);
        AddLocation.putExtra("latitude", latitude);
        AddLocation.putExtra("longitude", longitude);
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

    @Override
    public void onMapLongClick(LatLng latLng) {
        startAddLocation(latLng.longitude, latLng.latitude);
    }
}
