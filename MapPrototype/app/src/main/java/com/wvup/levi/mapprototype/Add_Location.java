package com.wvup.levi.mapprototype;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Add_Location extends AppCompatActivity {
    private final String TAG = "Add_LocationActivity";
    private final int REQUEST_FILE = 6;
    private PlaceOfInterest newLocation;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__location);

    }

    protected void onStart(){
        super.onStart();
        newLocation = new PlaceOfInterest();
        nameEditText = findViewById(R.id.name);

    }

    public void AddFiles(View v){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_FILE && resultCode == RESULT_OK){
            Uri file = data.getData();

            Log.d(TAG, "file data is " + file);
        }
    }


    //Very useful for return data
    //https://stackoverflow.com/questions/14785806/android-how-to-make-an-activity-return-results-to-the-activity-which-calls-it
    public void Submit(View v){

        newLocation.setName(nameEditText.getText().toString());

        Log.d(TAG,"location is" + newLocation.toString());
        setToDeviceLocation();

    }

    private void setToDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            MapsActivity.flpc.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(android.location.Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        newLocation.setLongitude(location.getLongitude());
                        newLocation.setLatitude(location.getLatitude());

                        Intent returnData = new Intent();
                        returnData.putExtra("Place", newLocation);
                        setResult(RESULT_OK, returnData);
                        finish();
                    }
                }
            });
        }
    }
}
