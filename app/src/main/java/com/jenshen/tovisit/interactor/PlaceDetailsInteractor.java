package com.jenshen.tovisit.interactor;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;
import com.jenshen.tovisit.api.entity.place.PlaceDetails;
import com.jenshen.tovisit.manager.PreferenceManager;
import com.jenshen.tovisit.manager.api.IApiManager;

import javax.inject.Inject;

import io.reactivex.Single;

public class PlaceDetailsInteractor {

    private final Context context;
    private final IApiManager apiManager;
    private final PreferenceManager preferenceManager;

    @Inject
    public PlaceDetailsInteractor(Context context, IApiManager apiManager, PreferenceManager preferenceManager) {
        this.context = context;
        this.apiManager = apiManager;
        this.preferenceManager = preferenceManager;
    }
    
    public Single<PlaceDetails> getPlace(String id) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return apiManager.getPlace(id)
                .map(PlaceDetailsResponse::getPlace)
                .doOnSuccess(placeDetails -> placeDetails.bindPhotosUrl(size.x, preferenceManager.getWebApiKey()));
    }
}
