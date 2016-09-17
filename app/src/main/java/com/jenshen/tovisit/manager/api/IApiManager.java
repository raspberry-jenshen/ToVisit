package com.jenshen.tovisit.manager.api;

import android.support.annotation.Nullable;

import com.jenshen.tovisit.api.Api;
import com.jenshen.tovisit.api.QueryList;
import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.place.Place;

import java.util.List;

import io.reactivex.Observable;

public interface IApiManager {

    Observable<NearByResponse> getPlaces(double latitude, double longitude,
                                         @Nullable Api.RankBy rankBy,
                                         @Nullable QueryList<Api.Type> types,
                                         @Nullable QueryList<String> names,
                                         @Nullable String pageToken);
}
