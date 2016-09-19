package com.jenshen.tovisit.interactor;


import android.content.Context;
import android.location.Location;

import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.manager.api.IApiManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class PlacesInteractor {

    private final IApiManager apiManager;
    private final Context context;

    @Inject
    public PlacesInteractor(IApiManager apiManager, Context context) {
        this.apiManager = apiManager;
        this.context = context;
    }
    
    public Observable<List<Place>> getPlaces(Location location) {
        return apiManager.getPlaces(location.getLatitude(), location.getLongitude(), null, null)
                .map(NearByResponse::getPlaces)
                .observeOn(Schedulers.computation())
                .flatMap(Observable::fromIterable)
                .doOnNext(place -> place.bindBitmap(context))
                .toList();
    }
}
