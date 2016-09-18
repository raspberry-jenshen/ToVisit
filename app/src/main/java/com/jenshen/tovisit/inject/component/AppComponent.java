package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.inject.module.ApiModule;
import com.jenshen.tovisit.inject.module.AppModule;
import com.jenshen.tovisit.inject.module.LocationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, LocationModule.class})
public interface AppComponent {

    PlacesSubcomponent plusPlaces();

    PlaceDetailsSubcomponent plusPlaceDetails();
}
