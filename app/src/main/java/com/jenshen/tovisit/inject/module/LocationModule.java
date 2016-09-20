package com.jenshen.tovisit.inject.module;

import android.content.Context;

import com.jenshen.tovisit.manager.LocationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    @Singleton
    public LocationHelper providesILocationManager(Context context) {
        return new LocationHelper(context);
    }
}
