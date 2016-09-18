package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.base.component.PresenterComponent;
import com.jenshen.tovisit.inject.scope.ActivityScope;
import com.jenshen.tovisit.ui.activity.places.near.PlacesActivity;
import com.jenshen.tovisit.ui.activity.places.near.mvp.PlacesView;
import com.jenshen.tovisit.ui.activity.places.near.mvp.presenter.PlacesPresenter;

import java.util.List;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface PlacesSubcomponent extends PresenterComponent<List<Place>, PlacesView, PlacesPresenter> {
    void inject(PlacesActivity activity);
}
