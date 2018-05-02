package com.wvup.levi.mapprototype.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Represents a Landmark or some place of interesting quality. a place has a name,
 * description, and a Picture of the place. Class is used for sqlLite by room.
 */
@Entity(tableName = "PlaceOfInterest", indices = {@Index(value = {"name", "routeId"})},
        foreignKeys = @ForeignKey(entity = Route.class, parentColumns = "id",
        childColumns = "routeId", onDelete = CASCADE))
public class PlaceOfInterest implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int routeId;
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private byte[] picture;

    public PlaceOfInterest(){

    }

    @Ignore
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String toString(){
        return String.format("Location: name = %s;", name);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
