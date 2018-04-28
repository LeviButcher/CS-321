package com.wvup.levi.mapprototype.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wvup.levi.mapprototype.models.Route;

import java.util.List;

@Dao
public interface RouteDAO {

    @Insert
    long insert(Route route);

    @Query("Select * from Route order by id desc limit 1")
    Route getLast();

    @Query("Select * from Route")
    List<Route> getAll();

    @Update
    void update(Route route);

    @Delete
    void delete(Route route);
}
