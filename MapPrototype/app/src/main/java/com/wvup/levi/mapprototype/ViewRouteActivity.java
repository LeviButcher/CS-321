package com.wvup.levi.mapprototype;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.models.RoutePoint;
import com.wvup.levi.mapprototype.room.RouteRepository;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

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

    public void centerOnPathStart(){
        if(points.size() > 0){
            RoutePoint first = points.get(0);
            LatLng firstLatLng =new LatLng(first.getLatitude(), first.getLongitude());
            MarkerOptions placeMarker = new MarkerOptions().position(firstLatLng).title("Start");
            mMap.addMarker(placeMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 15.5f));
        }
    }

    private void traceRoute(){
        ArrayList<LatLng> latLngPoints = new ArrayList<>();
        for(RoutePoint point : points){
            latLngPoints.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        Polyline route = mMap.addPolyline(new PolylineOptions());
        route.setPoints(latLngPoints);
    }

    private void addOnPlaces(){
        for(PlaceOfInterest place : places){
            LatLng newLatLong = new LatLng(place.getLatitude(), place.getLongitude());
            MarkerOptions placeMarker = new MarkerOptions().position(newLatLong).title(place.getName());
            if(place.getPicture() != null){
                int height = 100;
                int width = 100;
                Bitmap picture = ByteConvertor.convertToBitmap(place.getPicture());
                BitmapDrawable d = new BitmapDrawable(getResources(), picture);
                Bitmap smallMarker = Bitmap.createScaledBitmap(d.getBitmap(), width, height, false);

                placeMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            }
            mMap.addMarker(placeMarker);
        }
    }
}
