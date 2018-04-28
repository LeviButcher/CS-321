package com.wvup.levi.mapprototype.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "RoutePoint", indices = {@Index(value = {"routeId"})},
    foreignKeys = @ForeignKey(entity = Route.class, parentColumns = "id",
        childColumns = "routeId", onDelete = CASCADE))
public class RoutePoint {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int routeId;
    private double latitude;
    private double longitude;

    public RoutePoint(){

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
