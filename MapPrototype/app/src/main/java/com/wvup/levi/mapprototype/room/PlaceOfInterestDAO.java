package com.wvup.levi.mapprototype.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wvup.levi.mapprototype.models.PlaceOfInterest;

import java.util.List;

@Dao
public interface PlaceOfInterestDAO {

    @Insert
    void insert(PlaceOfInterest placeOfInterest);

    @Insert
    void insert(List<PlaceOfInterest> placeOfInterest);

    @Delete
    void delete(PlaceOfInterest placeOfInterest);

    @Update
    void update(PlaceOfInterest placeOfInterest);

    @Query("Select * from PlaceOfInterest where routeId == :routeId")
    List<PlaceOfInterest> getAllByRoute(int routeId);

    @Query("Select * from PlaceOfInterest where id == :id")
    PlaceOfInterest get(int id);
}
