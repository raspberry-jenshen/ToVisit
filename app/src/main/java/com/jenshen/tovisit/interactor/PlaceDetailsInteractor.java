package com.jenshen.tovisit.interactor;


import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.manager.api.IApiManager;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PlaceDetailsInteractor {

    private final IApiManager apiManager;

    @Inject
    public PlaceDetailsInteractor(IApiManager apiManager) {
        this.apiManager = apiManager;
    }
    
    public Observable<Place> getPlace(String id) {
        return apiManager.getPlace(id)
                .map(PlaceDetailsResponse::getPlace);
    }
}
