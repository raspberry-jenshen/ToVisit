package com.jenshen.tovisit.manager.api;

import android.support.annotation.Nullable;

import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IApiManager {

    Observable<NearByResponse> getPlaces(double latitude, double longitude,
                                         @Nullable List<String> names,
                                         @Nullable String pageToken);

    Single<PlaceDetailsResponse> getPlace(String id);
}
