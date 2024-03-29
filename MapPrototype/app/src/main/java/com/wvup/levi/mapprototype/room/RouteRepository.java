package com.wvup.levi.mapprototype.room;

import android.app.Application;
import android.os.AsyncTask;

import com.wvup.levi.mapprototype.models.PlaceOfInterest;
import com.wvup.levi.mapprototype.models.Route;
import com.wvup.levi.mapprototype.models.RoutePoint;

import java.util.List;

public class RouteRepository {

    private RouteDAO routeDAO;
    private PlaceOfInterestDAO placeOfInterestDAO;
    private RoutePointDAO routePointDAO;

    public RouteRepository(Application application){
        RouteDatabase db = RouteDatabase.getDatabase(application);
        routeDAO = db.routeDAO();
        placeOfInterestDAO = db.placeOfInterestDAO();
        routePointDAO = db.routePointDAO();
    }

    public Route getMostRecentRoute(){
        return routeDAO.getLast();
    }

    public List<Route> getRoutes(){
        return routeDAO.getAll();
    }

    public void wipeRoutes(){
        routeDAO.wipe();
    }

    public int insertRoute(Route route){
        try{
            return (int) new insertAsyncTaskRoute(routeDAO).execute(route).get().longValue();
        }
        catch(Exception e){
            return -1;
        }
    }

    public List<RoutePoint> getRoutePoints(int routeId) {
        return routePointDAO.getAllByRoute(routeId);
    }

    public List<PlaceOfInterest> getPlaces(int routeId){
        return placeOfInterestDAO.getAllByRoute(routeId);
    }

    public void insertPlace(PlaceOfInterest place){
        new insertAsyncTaskPlace(placeOfInterestDAO).execute(place);
    }

    public PlaceOfInterest getPlace(int placeId){
        return placeOfInterestDAO.get(placeId);
    }

    public void insertPoint(RoutePoint routePoint){
        new insertAsyncTaskRoutePoint(routePointDAO).execute(routePoint);
    }

    private static class insertAsyncTaskRoute extends AsyncTask<Route, Void, Long> {

        private RouteDAO asyncRouteDAO;

        insertAsyncTaskRoute(RouteDAO routeDAO){
            asyncRouteDAO = routeDAO;
        }

        @Override
        protected Long doInBackground(Route... routes) {
            return asyncRouteDAO.insert(routes[0]);
        }
    }

    private static class insertAsyncTaskPlace extends AsyncTask<PlaceOfInterest, Void, Void> {

        private PlaceOfInterestDAO asyncPlaceDAO;

        insertAsyncTaskPlace(PlaceOfInterestDAO placeDAO){
            asyncPlaceDAO = placeDAO;
        }

        @Override
        protected Void doInBackground(PlaceOfInterest... places) {
            asyncPlaceDAO.insert(places[0]);
            return null;
        }
    }

    private static class insertAsyncTaskRoutePoint extends AsyncTask<RoutePoint, Void, Void> {

        private RoutePointDAO asyncPointDAO;

        insertAsyncTaskRoutePoint(RoutePointDAO pointDAO){
            asyncPointDAO = pointDAO;
        }

        @Override
        protected Void doInBackground(RoutePoint... points) {
            asyncPointDAO.insert(points[0]);
            return null;
        }
    }
}
