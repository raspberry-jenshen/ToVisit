package com.jenshen.tovisit.ui.activity.places.near;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.app.ToVisitApp;
import com.jenshen.tovisit.base.view.impl.mvp.component.BaseDIMvpActivity;
import com.jenshen.tovisit.inject.component.PlacesSubcomponent;
import com.jenshen.tovisit.manager.LocationManager;
import com.jenshen.tovisit.ui.activity.SettingsActivity;
import com.jenshen.tovisit.ui.activity.places.datails.PlaceDetailsActivity;
import com.jenshen.tovisit.ui.activity.places.near.mvp.PlacesView;
import com.jenshen.tovisit.ui.activity.places.near.mvp.presenter.PlacesPresenter;
import com.jenshen.tovisit.ui.adapter.PlacesAdapter;

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
        PlacesAdapter.OnItemClickListener<Place>, EasyPermissions.PermissionCallbacks  {

    private static final int RC_LOCATION_PERM = 123;
    private static final int RC_SETTINGS_SCREEN = 125;

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

        // Setup recycler view
        adapter = new PlacesAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        startFetchLocation();
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
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
         PlaceDetailsActivity.launch(item, this);
    }


    /* permissions */

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void startFetchLocation() {
        startFetchLocation(false);
    }

    public void startFetchLocation(boolean pullToRefresh) {
        String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
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
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            startFetchLocation(false);
        }
    }
}
