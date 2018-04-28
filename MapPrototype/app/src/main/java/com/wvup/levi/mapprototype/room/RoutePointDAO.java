package com.wvup.levi.mapprototype.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wvup.levi.mapprototype.models.RoutePoint;

import java.util.List;

@Dao
public interface RoutePointDAO {

    @Insert
    void insert(RoutePoint routePoint);

    @Insert
    void insert(List<RoutePoint> routePoints);

    @Update
    void update(RoutePoint routePoint);

    @Query("Select * from RoutePoint where routeId == :routeId")
    List<RoutePoint> getAllByRoute(int routeId);
}
