package com.wvup.levi.mapprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.room.RouteRepository;

/**
 * Activity for viewing the details of a PlaceOfInterest
 * expected any calling activity to pass in the ID of the PlaceOfInterest,
 * key to use is "placeId", then db is queried to find that PlaceOfInterest.<br/>
 * If no PlaceOfInterest is found the activity exits, if not then PlaceOfInterest is displayed in the view
 *
 * @author Levi Butcher
 */
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

    @Override
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

    /**
     * Sets up the views to have the correct information dislayed.
     * Gets the Name, descriptions, and Image of the PlaceOfInterest
     * and displays it in the view
     */
    public void setUpView()
    {
        locName.setText(viewLocation.getName());
        locDescription.setText(viewLocation.getDescription());
        try{
            if(viewLocation.getPicture() != null && viewLocation.getPicture().length > 0){
                locPic.setImageBitmap(ByteConvertor.convertToBitmap(viewLocation.getPicture()));
            }
        }
        catch(Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
