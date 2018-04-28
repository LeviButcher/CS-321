package com.wvup.levi.mapprototype;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.models.Route;
import com.wvup.levi.mapprototype.models.RoutePoint;
import com.wvup.levi.mapprototype.room.RouteDatabase;
import com.wvup.levi.mapprototype.room.RouteRepository;

import java.util.ArrayList;
import java.util.List;

public class Add_Route extends AppCompatActivity {

    RouteRepository repo;
    private static String TAG = "ADD_ROUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__route);
        repo = new RouteRepository(this.getApplication());
    }

    public void Submit(View v){
        if(addRouteToDB())
            finish();
        else{
//            Toast toast = new Toast(this);
//            toast.makeText(this, "DB malfunction", Toast.LENGTH_SHORT);
//            toast.show();
        }
    }

    private boolean addRouteToDB(){
        EditText titleET = findViewById(R.id.title);
        EditText descriptionET = findViewById(R.id.description);
        if(titleET.getText().toString() != "" && descriptionET.getText().toString() != ""){
            Route route = new Route();
            route.setTitle(titleET.getText().toString());
            route.setDescription(descriptionET.getText().toString());
            repo.insertRoute(route);
            //TODO insert location and path to db
            Route justAdded = repo.getMostRecentRoute();
            Log.d(TAG, "id is" + justAdded.getId());
            if(justAdded.getId() > 0){
                addLocationsToDB(justAdded.getId());
                addPathToDB(justAdded.getId());
                return true;
            }
        }
        else{
            Toast toast = new Toast(this);
            toast.makeText(this, "All fields must be entered", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    private void addLocationsToDB(int routeId){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            ArrayList places = extras.getParcelableArrayList("places");

            for(int i = 0; i < places.size(); i++){
                PlaceOfInterest place = (PlaceOfInterest) places.get(i);
                place.setRouteId(routeId);
                repo.insertPlace(place);
            }
        }
    }

    private void addPathToDB(int routeId){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            ArrayList points = extras.getParcelableArrayList("routePoints");

            for(int i = 0; i < points.size(); i++){
                LatLng point = (LatLng) points.get(i);
                RoutePoint routePoint = new RoutePoint();
                routePoint.setLatitude(point.latitude);
                routePoint.setLongitude(point.longitude);
                routePoint.setRouteId(routeId);
                repo.insertPoint(routePoint);
            }
        }
    }
}
