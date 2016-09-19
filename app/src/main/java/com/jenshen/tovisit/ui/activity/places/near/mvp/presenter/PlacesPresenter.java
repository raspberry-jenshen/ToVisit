package com.jenshen.tovisit.ui.activity.places.near.mvp.presenter;


import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.api.entity.place.PlaceLocation;
import com.jenshen.tovisit.api.model.RankBy;
import com.jenshen.tovisit.api.model.PlaceType;
import com.jenshen.tovisit.base.presenter.MvpLceRxPresenter;
import com.jenshen.tovisit.interactor.PlacesInteractor;
import com.jenshen.tovisit.manager.LocationManager;
import com.jenshen.tovisit.manager.PreferenceManager;
import com.jenshen.tovisit.ui.activity.places.near.mvp.PlacesView;

import java.util.List;

import javax.inject.Inject;

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
        subscribe(placesInteractor.getPlaces(location), pullToRefresh);
    }

    private void setMarkers(@NonNull GoogleMap googleMap, @NonNull List<Place> data) {
        if (data.isEmpty()) {
            return;
        }
        final List<Marker> markers = Stream.of(data)
                .filter(place -> place.getGeometry() != null)
                .map(place -> {
                    final PlaceLocation location = place.getGeometry().getPlaceLocation();
                    LatLng latLng = new LatLng(location.getLat(), location.getLng());
                    final MarkerOptions markerOptions = new MarkerOptions()
                            .position(latLng)
                            .title(place.getName());
                    if (place.getBitmap() != null) {
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(place.getBitmap()));
                    }
                    return googleMap.addMarker(markerOptions);
                })
                .collect(Collectors.toList());

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }

        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
    }
}
