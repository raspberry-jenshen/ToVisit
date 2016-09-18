package com.jenshen.tovisit.ui.activity.places.datails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.app.ToVisitApp;
import com.jenshen.tovisit.base.view.impl.mvp.component.BaseDIMvpActivity;
import com.jenshen.tovisit.inject.component.PlaceDetailsSubcomponent;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.PlaceDetailsView;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter.PlaceDetailsPresenter;
import com.jenshen.tovisit.ui.adapter.ItemPagerAdapter;
import com.jenshen.tovisit.ui.behavior.BottomSheetBehaviorGoogleMapsLike;
import com.jenshen.tovisit.ui.behavior.MergedAppBarLayoutBehavior;


public class PlaceDetailsActivity extends BaseDIMvpActivity<
        PlaceDetailsSubcomponent,
        View,
        Place,
        PlaceDetailsView,
        PlaceDetailsPresenter>
        implements PlaceDetailsView {

    private static final String PLACE_EXTRA_TAG = "PLACE_EXTRA_TAG";
    int[] mDrawables = {
            R.drawable.places_ic_clear,
            R.drawable.places_ic_clear,
            R.drawable.places_ic_clear,
            R.drawable.places_ic_clear,
            R.drawable.places_ic_clear,
            R.drawable.places_ic_clear
    };

    TextView bottomSheetTextView;
    private Place place;

    /* launcher */

    public static void launch(Place place, Context context) {
        Intent intent = new Intent(context, PlaceDetailsActivity.class);
        intent.putExtra(PLACE_EXTRA_TAG, place);
        context.startActivity(intent);
    }


    /* inject */

    @Override
    public PlaceDetailsSubcomponent createComponent() {
        return ToVisitApp.getApplication().getAppComponent().plusPlaceDetails();
    }

    @Override
    public void inject(PlaceDetailsSubcomponent activityComponent) {
        activityComponent.inject(this);
    }


    /* lifecycle */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PLACE_EXTRA_TAG, place);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        place = savedInstanceState.getParcelable(PLACE_EXTRA_TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        place = getIntent().getExtras().getParcelable(PLACE_EXTRA_TAG);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
            actionBar.setTitle("");
        }

        if (savedInstanceState == null) {
            MapFragment mapFragment = MapFragment.newInstance();
            mapFragment.getMapAsync(presenter);
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }

        /**
         * If we want to listen for states callback
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.nestedScrollView);
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);

        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appBarLayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Title Dummy");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.bottom_sheet_title);
        ItemPagerAdapter adapter = new ItemPagerAdapter(this, mDrawables);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        loadData(false);
    }


    /* LCE */

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getErrorMessage(e);
    }

    @Override
    public void setData(Place data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadData(place.getId(), pullToRefresh);
    }
}