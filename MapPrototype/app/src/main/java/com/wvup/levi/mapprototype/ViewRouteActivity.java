package com.wvup.levi.mapprototype;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

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
import com.wvup.levi.mapprototype.models.RoutePoint;
import com.wvup.levi.mapprototype.room.RouteRepository;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Activity for viewing a route's Path using Google's Maps<br/>
 * The route displayed will be the routeId that is passed in, which is done when starting this activity, pass
 * in the key of "RouteId" in the extras and the actual routeId to accompany the key.
 *
 * @author Levi Butcher
 */
public class ViewRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int routeId;
    private RouteRepository repo;
    private List<RoutePoint> points;
    private List<PlaceOfInterest> places;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        Bundle extras = getIntent().getExtras();
        repo = new RouteRepository(this.getApplication());
        if(extras != null){
            routeId = extras.getInt("RouteId");
            points = repo.getRoutePoints(routeId);
            places = repo.getPlaces(routeId);
        }
        else{
            finish();
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
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }
        traceRoute();
        addOnPlaces();
        centerOnPathStart();
    }

    /**
     * Centers the Camera and zooms in at the start of the Route. Also, a Marker
     * is added to the beginning and end of the Route
     *
     */
    public void centerOnPathStart(){
        if(points.size() > 0){
            RoutePoint first = points.get(0);
            RoutePoint last = points.get(points.size() -1 );
            LatLng firstLatLng = new LatLng(first.getLatitude(), first.getLongitude());
            LatLng lastLatLng = new LatLng(last.getLatitude(), last.getLongitude());
            MarkerOptions placeMarker = new MarkerOptions().position(firstLatLng).title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            MarkerOptions lastMarker = new MarkerOptions().position(lastLatLng).title("End")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            mMap.addMarker(placeMarker);
            mMap.addMarker(lastMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 15.5f));
        }
    }

    /**
     * Builds out a PolyLine for all the RoutePoints for this route
     */
    private void traceRoute(){
        ArrayList<LatLng> latLngPoints = new ArrayList<>();
        for(RoutePoint point : points){
            latLngPoints.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        Polyline route = mMap.addPolyline(new PolylineOptions());
        route.setPoints(latLngPoints);
    }

    /**
     * Adds on all the markers for the PlaceOfInterests onto the map
     */
    private void addOnPlaces(){
        for(PlaceOfInterest place : places){
            LatLng newLatLong = new LatLng(place.getLatitude(), place.getLongitude());
            final MarkerOptions placeMarker = new MarkerOptions().position(newLatLong).title(place.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).zIndex(10.0f);
            if(place.getPicture() != null){
                int height = 100;
                int width = 100;
                Bitmap picture = ByteConvertor.convertToBitmap(place.getPicture());
                BitmapDrawable d = new BitmapDrawable(getResources(), picture);
                Bitmap smallMarker = Bitmap.createScaledBitmap(d.getBitmap(), width, height, false);

                placeMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            }
            mMap.addMarker(placeMarker).setTag(place);
            final Activity currentActivity = this;
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(marker.getTag() != null){
                        Intent viewPlace = new Intent(currentActivity ,ViewLocationActivity.class);
                        viewPlace.putExtra("placeId", ((PlaceOfInterest)marker.getTag()).getId());
                        startActivity(viewPlace);
                    }
                    //Should send up toast here
                    return false;
                }
            });
        }
    }
}
