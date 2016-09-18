package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.base.component.PresenterComponent;
import com.jenshen.tovisit.inject.scope.ActivityScope;
import com.jenshen.tovisit.ui.activity.places.datails.PlaceDetailsActivity;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.PlaceDetailsView;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter.PlaceDetailsPresenter;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface PlaceDetailsSubcomponent extends PresenterComponent<Place, PlaceDetailsView, PlaceDetailsPresenter> {
    void inject(PlaceDetailsActivity activity);
}
