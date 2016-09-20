package com.jenshen.tovisit.ui.activity.places.near.mvp.presenter;


import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.api.entity.place.PlaceLocation;
import com.jenshen.tovisit.api.model.PlaceType;
import com.jenshen.tovisit.api.model.RankBy;
import com.jenshen.tovisit.base.presenter.MvpLceRxPresenter;
import com.jenshen.tovisit.interactor.PlacesInteractor;
import com.jenshen.tovisit.manager.LocationManager;
import com.jenshen.tovisit.manager.PreferenceManager;
import com.jenshen.tovisit.ui.activity.places.near.mvp.PlacesView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.subscribers.single.ConsumerSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PlacesPresenter extends MvpLceRxPresenter<List<Place>, PlacesView> implements OnMapReadyCallback {

    private final PreferenceManager preferenceManager;
    private final PlacesInteractor placesInteractor;
    private final LocationManager locationManager;
    @Nullable
    private GoogleMap map;
    @Nullable
    private List<Place> data;

    @Inject
    public PlacesPresenter(PreferenceManager preferenceManager, PlacesInteractor placesInteractor, LocationManager locationManager) {
        this.preferenceManager = preferenceManager;
        this.placesInteractor = placesInteractor;
        this.locationManager = locationManager;
    }

    /* lifecycle */

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        locationManager.setOnLocationReceived(null);
    }


    /* public methods */

    @SuppressWarnings("MissingPermission")
    public void onHasLocationPermission(boolean pullToRefresh) {
        locationManager.searchLocation();
        locationManager.setOnLocationReceived(location -> loadPlaces(location, pullToRefresh));
    }

    public void setRankBy(RankBy item) {
        preferenceManager.setRankBy(item);
    }

    public void setPlaceTypes(@Nullable List<PlaceType> types) {
        preferenceManager.setPlaceTypes(types);
    }

    @Nullable
    public List<PlaceType> getSelectedTypes() {
        return preferenceManager.getPlaceTypes();
    }


    /* callbacks */

    @Override
    protected void onNext(List<Place> data) {
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

    private void loadPlaces(Location location, boolean pullToRefresh) {
        subscribeOnModel(placesInteractor.getPlaces(location), pullToRefresh);
    }

    @SuppressWarnings("ConstantConditions")
    private void setMarkers(@NonNull GoogleMap googleMap, @NonNull List<Place> data) {
        if (!isViewAttached() || data.isEmpty()) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        Single<LatLngBounds> single = Observable.fromIterable(data)
                .filter(place -> place.getGeometry() != null)
                .flatMap(place -> Observable.fromCallable(() -> getBitmap(place.getIcon()))
                        .map(bitmap -> {
                            final PlaceLocation location = place.getGeometry().getPlaceLocation();
                            LatLng latLng = new LatLng(location.getLat(), location.getLng());
                            final MarkerOptions markerOptions = new MarkerOptions()
                                    .position(latLng)
                                    .title(place.getName());

                            if (bitmap != null) {
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                            }
                            return markerOptions;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(googleMap::addMarker)
                        .observeOn(Schedulers.computation())
                        .doOnNext(marker -> builder.include(marker.getPosition()))
                        .map(marker -> builder.build()))
                .toSingle();

        subscribe(Schedulers.computation(), single, new ConsumerSingleObserver<>(latLngBounds -> {
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(latLngBounds, padding);
            googleMap.moveCamera(cu);
        }, throwable -> getView().handleError(throwable)));
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
