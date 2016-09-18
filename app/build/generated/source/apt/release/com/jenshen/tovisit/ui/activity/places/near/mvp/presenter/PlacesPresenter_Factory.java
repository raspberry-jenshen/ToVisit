package com.jenshen.tovisit.ui.activity.places.near.mvp.presenter;

import com.jenshen.tovisit.interactor.PlacesInteractor;
import com.jenshen.tovisit.manager.LocationManager;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PlacesPresenter_Factory implements Factory<PlacesPresenter> {
  private final MembersInjector<PlacesPresenter> placesPresenterMembersInjector;

  private final Provider<PlacesInteractor> placesInteractorProvider;

  private final Provider<LocationManager> locationManagerProvider;

  public PlacesPresenter_Factory(
      MembersInjector<PlacesPresenter> placesPresenterMembersInjector,
      Provider<PlacesInteractor> placesInteractorProvider,
      Provider<LocationManager> locationManagerProvider) {
    assert placesPresenterMembersInjector != null;
    this.placesPresenterMembersInjector = placesPresenterMembersInjector;
    assert placesInteractorProvider != null;
    this.placesInteractorProvider = placesInteractorProvider;
    assert locationManagerProvider != null;
    this.locationManagerProvider = locationManagerProvider;
  }

  @Override
  public PlacesPresenter get() {
    return MembersInjectors.injectMembers(
        placesPresenterMembersInjector,
        new PlacesPresenter(placesInteractorProvider.get(), locationManagerProvider.get()));
  }

  public static Factory<PlacesPresenter> create(
      MembersInjector<PlacesPresenter> placesPresenterMembersInjector,
      Provider<PlacesInteractor> placesInteractorProvider,
      Provider<LocationManager> locationManagerProvider) {
    return new PlacesPresenter_Factory(
        placesPresenterMembersInjector, placesInteractorProvider, locationManagerProvider);
  }
}
