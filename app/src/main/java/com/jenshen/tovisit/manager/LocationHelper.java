package com.jenshen.tovisit.manager;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.annimon.stream.function.FunctionalInterface;

public class LocationHelper implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 sec
    private final LocationManager locationManager;
    // flag for GPS status
    private boolean canGetLocation = false;

    @Nullable
    private Location location;
    private double latitude;
    private double longitude;
    @Nullable
    private OnLocationReceived onLocationReceived;

    public LocationHelper(Context context) {
        this.locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setOnLocationReceived(@Nullable OnLocationReceived onLocationReceived) {
        this.onLocationReceived = onLocationReceived;
        if (onLocationReceived != null && location != null) {
            onLocationReceived.onReceived(location);
        }
    }

    @SuppressWarnings("MissingPermission")
    public void loadLocation() {

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        }

        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                }
            }
        }

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            this.canGetLocation = true;
        }

        if (onLocationReceived != null) {
            onLocationReceived.onReceived(location);
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    @SuppressWarnings("MissingPermission")
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationHelper.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (onLocationReceived != null) {
            onLocationReceived.onReceived(location);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @FunctionalInterface
    public interface OnLocationReceived {
        void onReceived(Location location);
    }

}