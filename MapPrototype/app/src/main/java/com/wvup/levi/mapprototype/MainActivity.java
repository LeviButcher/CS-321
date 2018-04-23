package com.wvup.levi.mapprototype;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 100;
    public static GoogleApiClient gac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    //OnClick that starts the map up
    public void startMap(View v)
    {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        if(gac != null){
            startActivity(mapIntent);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.w(TAG, "connected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG,"connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        if(connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this, REQUEST_CODE);
            }
            catch(IntentSender.SendIntentException sie){
                //Intent cancelled exit app
                finish();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == resultCode && resultCode == RESULT_OK){
            gac.connect();
        }
    }

    protected void onStart(){
        super.onStart();
        if(gac != null){
            gac.connect();
        }
    }
}
