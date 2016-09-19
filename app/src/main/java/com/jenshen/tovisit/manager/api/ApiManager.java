package com.jenshen.tovisit.manager.api;


import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.jenshen.tovisit.api.Api;
import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;
import com.jenshen.tovisit.api.model.PlaceType;
import com.jenshen.tovisit.manager.PreferenceManager;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ApiManager implements IApiManager {

    private final Api api;
    private final PreferenceManager preferenceManager;

    public ApiManager(Api api, PreferenceManager preferenceManager) {
        this.api = api;
        this.preferenceManager = preferenceManager;
    }

    @Override
    public Observable<NearByResponse> getPlaces(double latitude, double longitude,
                                                @Nullable List<String> names,
                                                @Nullable String pageToken) {
        final List<PlaceType> placeTypes = preferenceManager.getPlaceTypes();
        String types = null;
        if (placeTypes != null) {
            types = Stream.of(placeTypes)
                    .map(PlaceType::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.joining("|"));
        }
        return api.getPlaces(latitude + "," + longitude,
                preferenceManager.getRadiusForSearch(),
                preferenceManager.getRankBy().name().toLowerCase(),
                Locale.getDefault().getDisplayLanguage(),
                types,
                names != null ? names.toString() : null,
                pageToken,
                preferenceManager.getWebApiKey());
    }

    @Override
    public Single<PlaceDetailsResponse> getPlace(String id) {
        return api.getPlace(id, null, preferenceManager.getWebApiKey())
                .toSingle();
    }
}
