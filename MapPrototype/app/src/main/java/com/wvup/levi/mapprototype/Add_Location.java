package com.wvup.levi.mapprototype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.wvup.levi.mapprototype.models.PlaceOfInterest;

/**
 * Add Location Activity
 * used for a form where a user is enterering information about a <br/>
 * Locations name, description, and Picture. On submittion this activity retrieves that info
 * from the view and sends that newly created PlaceOfInterest back to the activity that start this activity.
 *
 * @author Levi Butcher
 */
public class Add_Location extends AppCompatActivity {
    private final String TAG = "Add_LocationActivity";
    private final int REQUEST_FILE = 6;
    private PlaceOfInterest newLocation;
    private EditText nameEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__location);
        newLocation = new PlaceOfInterest();
    }

    @Override
    protected void onStart(){
        super.onStart();
        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
    }

    /**
     * OnClick event for a View within the xml View<br/>
     * Starts a intent for a user to find and select a image for the
     * location being added
     * @param v View that fired the onClick event
     */
    public void addLocationPic(View v){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_FILE);
    }

    //Convert file to byte - byte[] byteFiles = Files.readAllBytes(newFile.toPath());

    /*
    *  requestCodes are defined as static ints at the top of the class.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_FILE && resultCode == RESULT_OK){
            Uri path = data.getData();
            if(path.getPath() != null){
                try{
                    Bitmap picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                    Log.d(TAG, "after assigning location image is " + picture.toString());
                    ImageView imageView = findViewById(R.id.placePic);
                    imageView.setImageBitmap(picture);
                    newLocation.setPicture(ByteConvertor.convertToByteArr(picture));
                }
                catch(Exception e){
                    Log.d(TAG, "Image could not be resolved:");
                    e.printStackTrace();
                }

            }
        }
    }


    //

    /**
     * onClick event handler for submitting the form data. <br/>
     * Gets all form data, creating a PlaceOfInterest and sending it back to the activity
     * that start this activity. Key value for the extra data stored in the intent is "Place"
     *
     * <br/>Very useful for return data
     * https://stackoverflow.com/questions/14785806/android-how-to-make-an-activity-return-results-to-the-activity-which-calls-it
     * @param v View that fired event when clicked
     */
    public void Submit(View v){

        newLocation.setName(nameEditText.getText().toString());
        newLocation.setDescription(nameEditText.getText().toString());
        Log.d(TAG, "onSubmit image was " + newLocation.getPicture());
        Log.d(TAG,"location is" + newLocation.toString());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            double longitude = extras.getDouble("longitude");
            double latitude = extras.getDouble("latitude");
            newLocation.setLatitude(latitude);
            newLocation.setLongitude(longitude);
            Intent returnData = new Intent();
            returnData.putExtra("Place", newLocation);
            setResult(RESULT_OK, returnData);
            finish();
        }
        else{
            Toast toast = new Toast(this);
            toast.setText("Lat and Long was not set, go back to map");
            toast.show();
        }
    }
}
