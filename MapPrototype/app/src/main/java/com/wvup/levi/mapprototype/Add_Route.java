package com.wvup.levi.mapprototype;

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
import com.wvup.levi.mapprototype.room.RouteRepository;
import java.util.ArrayList;

/**
 * Add Route Activity
 * Used for a Form for adding the name, and description for a route.<br/>
 * DB inserts all happen within this activity. pass in extra intent data for a List of LatLngPoints,
 * and a list of PointOfInterest. All info for the route is then inserted at once on Submission of the form.
 *
 * @author Levi Butcher
 */
public class Add_Route extends AppCompatActivity {

    RouteRepository repo;
    private static String TAG = "ADD_ROUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__route);
        repo = new RouteRepository(this.getApplication());
    }

    /**
     * OnClick event handler for submission.
     * Starts adding to the DB and if that is successful
     * finishes the activity going back to the activity that started this activity.
     * @param v View that was clicked
     */
    public void Submit(View v){
        if(addRouteToDB())
            finish();
        else{
//            Toast toast = new Toast(this);
//            toast.makeText(this, "DB malfunction", Toast.LENGTH_SHORT);
//            toast.show();
        }
    }

    /**
     * Gets the name and description of the route from the form and insert the new Route to
     * the DB. <br/> If succesful then it will start the methods to add the PointOfInterests
     * and RoutePoints
     * @return true if all DB inserts go successfully
     */
    private boolean addRouteToDB(){
        EditText titleET = findViewById(R.id.title);
        EditText descriptionET = findViewById(R.id.description);
        if(titleET.getText().toString() != "" && descriptionET.getText().toString() != ""){
            Route route = new Route();
            route.setTitle(titleET.getText().toString());
            route.setDescription(descriptionET.getText().toString());
            int id = repo.insertRoute(route);
            //TODO insert location and path to db
            if(id == -1){
                Log.d(TAG, "error within addRouteToDB");
                finish();
            }
            addLocationsToDB(id);
            addPathToDB(id);
            return true;
        }
        else{
            Toast toast = new Toast(this);
            toast.makeText(this, "All fields must be entered", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    /**
     * Adds all the PlaceOfInterests to the DB
     *
     * @param routeId The route's Id that these Places are for
     */
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

    /**
     * Adds all the RoutePoints to the DB
     *
     * @param routeId The route's Id that these RoutePoints are for
     */
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
