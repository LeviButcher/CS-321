package com.wvup.levi.mapprototype;

import android.app.Application;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.wvup.levi.mapprototype.models.Route;
import com.wvup.levi.mapprototype.room.RouteRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RouteRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new RouteRepository(this.getApplication());
    }

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
    public void startMap(View v)
    {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }

    public void updateList(){
        ScrollView sv = findViewById(R.id.container);
        sv.removeAllViews();
        sv.addView(buildOutRouteList());
    }

    private LinearLayout buildOutRouteList(){
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 20);
        ll.setOrientation(LinearLayout.VERTICAL);
        List<Route> routes = repo.getRoutes();

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
        return ll;
    }

    public void viewRoute(int routeId){
        Intent viewRoute = new Intent(this, ViewRouteActivity.class);
        //pass route Id here
        viewRoute.putExtra("RouteId", routeId);
        startActivity(viewRoute);
    }
}
