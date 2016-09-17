package com.jenshen.tovisit.interactor;

import com.jenshen.tovisit.api.entity.Place;
import com.jenshen.tovisit.manager.api.IApiManager;
import com.jenshen.tovisit.manager.location.ILocationManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PlacesInteractor {

    private final IApiManager apiManager;
    private final  ILocationManager locationManager;

    @Inject
    public PlacesInteractor(IApiManager apiManager, ILocationManager locationManager) {
        this.apiManager = apiManager;
        this.locationManager = locationManager;
    }
    
    public Observable<List<Place>> getPlaces(List<String> poi) {
        //// TODO: 9/17/2016
        return apiManager.getPlaces(locationManager.getLatitude(), locationManager.getLongitude(), "atm");
    }
}
