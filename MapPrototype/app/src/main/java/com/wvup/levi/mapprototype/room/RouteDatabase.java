package com.wvup.levi.mapprototype.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.models.Route;
import com.wvup.levi.mapprototype.models.RoutePoint;

@Database(entities = {Route.class, RoutePoint.class, PlaceOfInterest.class}, version = 2)
public abstract class RouteDatabase extends RoomDatabase{

    public abstract RouteDAO routeDAO();
    public abstract PlaceOfInterestDAO placeOfInterestDAO();
    public abstract RoutePointDAO routePointDAO();

    private static RouteDatabase INSTANCE;

    public static RouteDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RouteDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RouteDatabase.class, "route_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
