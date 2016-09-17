package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.api.entity.Place;
import com.jenshen.tovisit.base.component.PresenterComponent;
import com.jenshen.tovisit.inject.scope.FragmentScope;
import com.jenshen.tovisit.ui.fragment.places.PlacesFragment;
import com.jenshen.tovisit.ui.fragment.places.mvp.PlacesView;
import com.jenshen.tovisit.ui.fragment.places.mvp.presenter.PlacesPresenter;

import java.util.List;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface PlaceSubcomponent extends PresenterComponent<List<Place>, PlacesView, PlacesPresenter> {
    void inject(PlacesFragment fragment);
}
