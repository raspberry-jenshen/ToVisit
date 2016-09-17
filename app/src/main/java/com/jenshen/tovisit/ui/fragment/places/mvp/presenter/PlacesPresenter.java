package com.jenshen.tovisit.ui.fragment.places.mvp.presenter;

import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.base.presenter.MvpLceRxPresenter;
import com.jenshen.tovisit.interactor.PlacesInteractor;
import com.jenshen.tovisit.ui.fragment.places.mvp.PlacesView;

import java.util.List;

import javax.inject.Inject;

public class PlacesPresenter extends MvpLceRxPresenter<List<Place>, PlacesView> {

    private final PlacesInteractor placesInteractor;

    @Inject
    public PlacesPresenter(PlacesInteractor placesInteractor) {
        this.placesInteractor = placesInteractor;
    }

    public void loadPlaces(boolean pullToRefresh) {
        subscribe(placesInteractor.getPlaces(null)
                .map(NearByResponse::getPlaces), pullToRefresh);
    }
}
