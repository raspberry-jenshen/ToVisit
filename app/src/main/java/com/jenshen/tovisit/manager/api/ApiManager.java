package com.jenshen.tovisit.manager.api;


import android.support.annotation.Nullable;

import com.jenshen.tovisit.api.Api;
import com.jenshen.tovisit.api.QueryList;
import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;
import com.jenshen.tovisit.manager.PreferenceManager;

import java.util.Locale;

import io.reactivex.Observable;

public class ApiManager implements IApiManager {

    private final Api api;
    private final PreferenceManager preferenceManager;

    public ApiManager(Api api, PreferenceManager preferenceManager) {
        this.api = api;
        this.preferenceManager = preferenceManager;
    }

    @Override
    public Observable<NearByResponse> getPlaces(double latitude, double longitude,
                                                @Nullable Api.RankBy rankBy,
                                                @Nullable QueryList<Api.Type> types,
                                                @Nullable QueryList<String> names,
                                                @Nullable String pageToken) {
        return api.getPlaces(latitude + "," + longitude,
                preferenceManager.getRadiusForSearch(),
                rankBy,
                Locale.getDefault().getDisplayLanguage(),
                types != null? types.toString() : null,
                names != null? names.toString() : null,
                pageToken,
                preferenceManager.getWebApiKey());
    }

    @Override
    public Observable<PlaceDetailsResponse> getPlace(String id) {
        return api.getPlace(id,
                Locale.getDefault().getDisplayLanguage(),
                preferenceManager.getWebApiKey());
    }
}
