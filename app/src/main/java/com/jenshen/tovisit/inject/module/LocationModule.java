package com.jenshen.tovisit.inject.module;

import com.jenshen.tovisit.manager.location.ILocationManager;
import com.jenshen.tovisit.manager.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    @Singleton
    public ILocationManager providesILocationManager() {
        return new LocationManager();
    }
}
