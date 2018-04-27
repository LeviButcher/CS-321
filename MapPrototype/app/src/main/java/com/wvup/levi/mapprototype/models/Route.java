package com.wvup.levi.mapprototype.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable{

    private String title;
    private String description;
    private ArrayList<LatLng> path;
    private ArrayList<PlaceOfInterest> Places;


}
