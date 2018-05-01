package com.wvup.levi.mapprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.room.RouteRepository;

public class ViewLocationActivity extends AppCompatActivity {

    private RouteRepository repo;
    private static String TAG = "ADD_ROUTE";
    private TextView locName;
    private TextView locDescription;
    private ImageView locPic;
    private PlaceOfInterest viewLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        repo = new RouteRepository(this.getApplication());
    }

    protected void onStart(){
        super.onStart();
        locName = findViewById(R.id.viewLocName);
        locDescription = findViewById(R.id.viewLocDescription);
        locPic = findViewById(R.id.viewLocPic);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int id = extras .getInt("placeId");
            viewLocation = repo.getPlace(id);
            setUpView();
        }
        else{
            finish();
        }
    }

    public void setUpView()
    {
        locName.setText(viewLocation.getName());
        locDescription.setText(viewLocation.getDescription());

    }
}
