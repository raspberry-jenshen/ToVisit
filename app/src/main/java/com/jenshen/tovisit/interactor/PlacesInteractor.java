package com.jenshen.tovisit.interactor;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.manager.api.IApiManager;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
    
    public Observable<List<Place>> getPlaces(List<String> poi, Location location) {
        //// TODO: 9/17/2016
        return apiManager.getPlaces(location.getLatitude(), location.getLongitude(), null, null, null, null)
                .map(NearByResponse::getPlaces)
                .observeOn(Schedulers.computation())
                .flatMap(Observable::fromIterable)
                .doOnNext(place -> place.setBitmap(getIcon(place.getIcon())))
                .toList();
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    private Bitmap getIcon(@Nullable String url) {
        if (url != null) {
            try {
                return Glide.with(context).load(url).asBitmap().into(-1, -1).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
