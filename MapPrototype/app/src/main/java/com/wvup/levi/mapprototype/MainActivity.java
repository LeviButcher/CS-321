package com.wvup.levi.mapprototype;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.wvup.levi.mapprototype.models.Route;
import com.wvup.levi.mapprototype.room.RouteRepository;
import java.util.List;

/**
 * Starting activity classes
 * View displays a list of Routes that have been added to the DB.
 * OnClick for each Listitem will start a activity to few said route.
 * event handler method will start another activity for recording routes.
 *
 * @author Levi Butcher
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RouteRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new RouteRepository(this.getApplication());
    }

    @Override
    public void onStart(){
        super.onStart();
        TextView title = findViewById(R.id.title);
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                repo.wipeRoutes();
                updateList();
                return true;
            }
        });
        updateList();
    }

    //OnClick that starts the map up

    /**
     * onClick event handler that will start a activity to
     * record a route
     *
     * @param v View that was clicked
     */
    public void startMap(View v)
    {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }

    /**
     * Updates the list of Routes that are displayed in the view
     * Requeries to get all current routes saved the inserts a view for each into
     * the View with the id of container
     */
    public void updateList(){
        ScrollView sv = findViewById(R.id.container);
        sv.removeAllViews();
        sv.addView(buildOutRouteList());
    }

    /**
     * returns a LinearLayout of all the Routes that have been saved.<br/>
     * LinearLayout will contain a list where each route has it's name and description
     * @return LinearLayout of Routes
     */
    private LinearLayout buildOutRouteList(){
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 20);
        ll.setOrientation(LinearLayout.VERTICAL);
        List<Route> routes = repo.getRoutes();

        if(routes.isEmpty()){
            TextView goAddRoutes = new TextView(this, null, 0, R.style.GoAddRoutes);
            goAddRoutes.setText(R.string.NoRoute);
            ll.addView(goAddRoutes);
        }
        else{
            for(final Route route : routes){
                LinearLayout listItem = new LinearLayout(this, null, 0, R.style.ListItem);
                TextView title = new TextView(this, null, 0, R.style.ListItemTitle);
                TextView description = new TextView(this, null, 0, R.style.ListItemDescription);
                listItem.setOrientation(LinearLayout.VERTICAL);
                title.setText(route.getTitle());
                description.setText(route.getDescription());
                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "id is" + route.getId());
                        //TODO Start intent here to view route
                        viewRoute(route.getId());
                    }
                });
                listItem.addView(title);

                listItem.addView(description);
                ll.addView(listItem, lp);
            }
        }


        return ll;
    }

    /**
     * Starts a new Activity for viewing a route.
     * Route viewed will be the route that has the Id of the int passed into
     * this method
     *
     * @param routeId The Route's Id to view
     */
    public void viewRoute(int routeId){
        Intent viewRoute = new Intent(this, ViewRouteActivity.class);
        //pass route Id here
        viewRoute.putExtra("RouteId", routeId);
        startActivity(viewRoute);
    }
}
