package com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.api.entity.place.PlaceDetails;
import com.jenshen.tovisit.api.entity.place.PlaceLocation;
import com.jenshen.tovisit.base.presenter.MvpLceRxPresenter;
import com.jenshen.tovisit.interactor.PlaceDetailsInteractor;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.PlaceDetailsView;

import javax.inject.Inject;

public class PlaceDetailsPresenter extends MvpLceRxPresenter<PlaceDetails, PlaceDetailsView> implements OnMapReadyCallback {

    private final PlaceDetailsInteractor placeDetailsInteractor;
    @Nullable
    private GoogleMap map;
    @Nullable
    private PlaceDetails data;

    @Inject
    public PlaceDetailsPresenter(PlaceDetailsInteractor placeDetailsInteractor) {
        this.placeDetailsInteractor = placeDetailsInteractor;
    }


    /* public methods */

    public void loadData(String id, boolean pullToRefresh) {
        subscribe(placeDetailsInteractor.getPlace(id).toObservable(), pullToRefresh);
    }


    /* callbacks */

    @Override
    protected void onNext(PlaceDetails data) {
        super.onNext(data);
        if (map != null) {
            setMarkers(map, data);
        } else {
            this.data = data;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (data != null) {
            setMarkers(googleMap, data);
        }
    }


    /* private methods */

    private void setMarkers(@NonNull GoogleMap googleMap, @NonNull Place place) {
        final PlaceLocation location = place.getGeometry().getPlaceLocation();
        LatLng latLng = new LatLng(location.getLat(), location.getLng());
        final MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(place.getName());
        if (place.getBitmap() != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(place.getBitmap()));
        }
        CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }
}
