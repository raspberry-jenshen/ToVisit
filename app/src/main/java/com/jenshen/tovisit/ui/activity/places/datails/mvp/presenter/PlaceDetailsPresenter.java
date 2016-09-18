package com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter;


import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.base.presenter.MvpLceRxPresenter;
import com.jenshen.tovisit.interactor.PlaceDetailsInteractor;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.PlaceDetailsView;

import javax.inject.Inject;

public class PlaceDetailsPresenter extends MvpLceRxPresenter<Place, PlaceDetailsView> implements OnMapReadyCallback {

    private final PlaceDetailsInteractor placeDetailsInteractor;
    @Nullable
    private GoogleMap map;
    @Nullable
    private Place data;

    @Inject
    public PlaceDetailsPresenter(PlaceDetailsInteractor placeDetailsInteractor) {
        this.placeDetailsInteractor = placeDetailsInteractor;
    }


    /* public methods */

    public void loadData(String id, boolean pullToRefresh) {
        subscribe(placeDetailsInteractor.getPlace(id), pullToRefresh);
    }

    /* callbacks */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }


    /* private methods */
}
