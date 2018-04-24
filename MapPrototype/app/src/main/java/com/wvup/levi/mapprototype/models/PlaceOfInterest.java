package com.wvup.levi.mapprototype.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class PlaceOfInterest implements Serializable {

    private String name;
    private double longitude;
    private double latitude;
    private Bitmap picture;

    public PlaceOfInterest(){

    }

    public PlaceOfInterest(String name, double longitude, double latitude){
        this.setName(name);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String toString(){
        return String.format("Location: name = %s;", name);
    }







}
