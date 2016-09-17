package com.jenshen.tovisit.manager.api;

import com.jenshen.tovisit.api.entity.Place;

import java.util.List;

import io.reactivex.Observable;

public interface IApiManager {

    Observable<List<Place>> getPlaces(double latitude, double longitude, String types);
}
