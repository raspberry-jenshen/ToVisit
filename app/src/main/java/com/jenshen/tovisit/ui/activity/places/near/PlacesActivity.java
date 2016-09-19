package com.jenshen.tovisit.ui.activity.places.near;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.maps.MapFragment;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.api.model.PlaceType;
import com.jenshen.tovisit.app.ToVisitApp;
import com.jenshen.tovisit.base.view.impl.mvp.component.BaseDIMvpActivity;
import com.jenshen.tovisit.inject.component.PlacesSubcomponent;
import com.jenshen.tovisit.manager.LocationManager;
import com.jenshen.tovisit.ui.activity.SettingsActivity;
import com.jenshen.tovisit.ui.activity.places.datails.PlaceDetailsActivity;
import com.jenshen.tovisit.ui.activity.places.near.mvp.PlacesView;
import com.jenshen.tovisit.ui.activity.places.near.mvp.presenter.PlacesPresenter;
import com.jenshen.tovisit.ui.adapter.PlacesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PlacesActivity extends BaseDIMvpActivity<
        PlacesSubcomponent,
        SwipeRefreshLayout,
        List<Place>,
        PlacesView,
        PlacesPresenter>
        implements PlacesView, SwipeRefreshLayout.OnRefreshListener,
        PlacesAdapter.OnItemClickListener<Place>, EasyPermissions.PermissionCallbacks {

    private static final int RC_LOCATION_PERM = 123;
    private static final int RC_ANDROID_SETTINGS_SCREEN = 125;
    private static final int RC_SETTINGS_SCREEN = 126;

    @Inject
    protected LocationManager locationManager;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @BindView(R.id.filter_button)
    protected FloatingActionButton filter_button;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.map_container)
    protected View map_container;

    private PlacesAdapter adapter;


    /* inject */

    @Override
    public PlacesSubcomponent createComponent() {
        return ToVisitApp.getApplication().getAppComponent().plusPlaces();
    }

    @Override
    public void inject(PlacesSubcomponent activityComponent) {
        activityComponent.inject(this);
    }


    /* lifecycle */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);
        ButterKnife.bind(this);
        ViewCompat.setTransitionName(map_container, PlaceDetailsActivity.TRANSITION_KEY_MAP);
        setSupportActionBar(toolbar);
        collapsing_toolbar.setTitle(getString(R.string.app_name));
        if (savedInstanceState == null) {
            MapFragment mapFragment = MapFragment.newInstance();
            mapFragment.getMapAsync(presenter);
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }

        contentView.setOnRefreshListener(this);

        //todo implement sort
        /*sorted_button.setOnClickListener(v -> {
            final RankBy[] values = RankBy.values();
            final List<String> stringList = Stream.of(values)
                    .map(value -> value.getName(this))
                    .collect(Collectors.toList());
            String[] array = stringList.toArray(new String[stringList.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.places_choose_filter);
            builder.setItems(array, (dialog, item) -> presenter.setRankBy(values[item]));
            AlertDialog alert = builder.create();
            alert.show();
        });*/

        filter_button.setOnClickListener(v -> showSelectPlaceTypesDialog(presenter.getSelectedTypes()));

        // Setup recycler view
        adapter = new PlacesAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        startFetchLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (filter_button.getVisibility() == View.GONE) {
            filter_button.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_item_menu:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, RC_SETTINGS_SCREEN);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ANDROID_SETTINGS_SCREEN || requestCode == RC_SETTINGS_SCREEN) {
            startFetchLocation();
        }
    }


    /* Loading-Content-Error */

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getErrorMessage(e);
    }

    @Override
    public void setData(List<Place> data) {
        adapter.setPlaces(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        startFetchLocation(pullToRefresh);
    }


    /* callbacks */

    @Override
    public void onRefresh() {
        startFetchLocation(true);
    }

    @Override
    public void onItemClick(@NonNull Place item) {
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(filter_button, View.SCALE_X, 1, 0);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(filter_button, View.SCALE_Y, 1, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorScaleX, animatorScaleY);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                filter_button.setVisibility(View.GONE);
                PlaceDetailsActivity.launch(item, map_container, PlacesActivity.this);
            }
        });
        animatorSet.start();
    }


    /* permissions */

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void startFetchLocation() {
        startFetchLocation(false);
    }

    public void startFetchLocation(boolean pullToRefresh) {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            presenter.onHasLocationPermission(pullToRefresh);
        } else {
            if (pullToRefresh) {
                contentView.setRefreshing(false);
            }
            // Ask for both permissions
            EasyPermissions.requestPermissions(this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // Have permissions, do the thing!
        presenter.onHasLocationPermission(false);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showError(new RuntimeException(), false);
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.settings))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC_ANDROID_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }


    /* private methods */

    private void showSelectPlaceTypesDialog(@Nullable List<PlaceType> selectedTypes) {
        final PlaceType[] values = PlaceType.values();
        final List<String> typesList = Stream.of(values).map(PlaceType::getName).collect(Collectors.toList());
        String[] types = typesList.toArray(new String[typesList.size()]);
        boolean[] checkedTypes = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            checkedTypes[i] = selectedTypes != null && selectedTypes.contains(values[i]);
        }

        new AlertDialog.Builder(PlacesActivity.this)
                .setMultiChoiceItems(types, checkedTypes,
                        (dialog, which, isChecked) -> checkedTypes[which] = isChecked)
                .setCancelable(false)
                .setTitle(R.string.places_choose_filter)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    List<PlaceType> newSelectedTypes = new ArrayList<>();
                    for (int i = 0; i < values.length; i++) {
                        boolean checked = checkedTypes[i];
                        if (checked) {
                            newSelectedTypes.add(values[i]);
                        }
                    }
                    presenter.setPlaceTypes(newSelectedTypes);
                    startFetchLocation();
                }).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .setNeutralButton(R.string.reset, (dialog, which) -> presenter.setPlaceTypes(null))
                .show();
    }

}
