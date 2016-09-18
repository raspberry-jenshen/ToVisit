package com.jenshen.tovisit.manager;

import android.content.Context;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationManager {

    private android.location.LocationManager locationManager;
    private OnLocationReceived onLocationReceived;
    @Nullable
    private Location location;

    public LocationManager(Context context) {
        locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setOnLocationReceived(@Nullable OnLocationReceived onLocationReceived) {
        this.onLocationReceived = onLocationReceived;
        if (location != null && onLocationReceived != null) {
            onLocationReceived.onReceived(location);
        }
    }

    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    @SuppressWarnings("MissingPermission")
    public void searchLocation() {
        location = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
        if (location != null && onLocationReceived != null) {
            onLocationReceived.onReceived(location);
        }
    }
    
    /* inner types */

    @FunctionalInterface
    public interface OnLocationReceived {
        void onReceived(Location location);
    }
}
