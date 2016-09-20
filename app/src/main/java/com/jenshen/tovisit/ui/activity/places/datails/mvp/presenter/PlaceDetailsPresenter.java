package com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
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

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.internal.subscribers.single.ConsumerSingleObserver;
import io.reactivex.schedulers.Schedulers;

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
        subscribeOnModel(placeDetailsInteractor.getPlace(id).toObservable(), pullToRefresh);
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

    @SuppressWarnings("ConstantConditions")
    private void setMarkers(@NonNull GoogleMap googleMap, @NonNull Place place) {
        if (!isViewAttached()) {
            return;
        }

        subscribe(Schedulers.computation(),
                Single.fromCallable(() -> getBitmap(place.getIcon())),
                new ConsumerSingleObserver<>(
                        bitmap -> {
                            final PlaceLocation location = place.getGeometry().getPlaceLocation();
                            LatLng latLng = new LatLng(location.getLat(), location.getLng());
                            final MarkerOptions markerOptions = new MarkerOptions()
                                    .position(latLng)
                                    .title(place.getName());
                            if (bitmap != null) {
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                            }
                            CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                            googleMap.addMarker(markerOptions);
                            googleMap.moveCamera(center);
                            googleMap.animateCamera(zoom);
                        },
                        throwable -> getView().handleError(throwable)));
    }

    @Nullable
    @SuppressWarnings("ConstantConditions")
    private Bitmap getBitmap(String iconUrl) {
        if (iconUrl != null) {
            try {
                return Glide.with(getView().getContext()).load(iconUrl).asBitmap().into(-1, -1).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
