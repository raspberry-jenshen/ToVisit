package com.jenshen.tovisit.manager.api;


import com.jenshen.tovisit.api.Api;
import com.jenshen.tovisit.api.entity.Place;
import com.jenshen.tovisit.manager.PreferenceManager;

import java.util.List;

import io.reactivex.Observable;

public class ApiManager implements IApiManager {

    private final Api api;
    private final PreferenceManager preferenceManager;

    public ApiManager(Api api, PreferenceManager preferenceManager) {
        this.api = api;
        this.preferenceManager = preferenceManager;
    }

    @Override
    public Observable<List<Place>> getPlaces(double latitude, double longitude, String types) {
        return api.getPlaces(latitude + "," + latitude, preferenceManager.getRadiusForSearch(), true, types, preferenceManager.getApiKey());
    }
}
