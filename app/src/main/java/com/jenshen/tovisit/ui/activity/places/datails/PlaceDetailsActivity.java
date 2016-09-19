package com.jenshen.tovisit.ui.activity.places.datails;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.api.entity.place.PlaceDetails;
import com.jenshen.tovisit.app.ToVisitApp;
import com.jenshen.tovisit.base.view.impl.mvp.component.BaseDIMvpActivity;
import com.jenshen.tovisit.inject.component.PlaceDetailsSubcomponent;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.PlaceDetailsView;
import com.jenshen.tovisit.ui.activity.places.datails.mvp.presenter.PlaceDetailsPresenter;
import com.jenshen.tovisit.ui.adapter.ItemPagerAdapter;
import com.jenshen.tovisit.ui.adapter.ReviewsAdapter;
import com.jenshen.tovisit.ui.behavior.BottomSheetBehaviorGoogleMapsLike;
import com.jenshen.tovisit.ui.behavior.MergedAppBarLayoutBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceDetailsActivity extends BaseDIMvpActivity<
        PlaceDetailsSubcomponent,
        View,
        PlaceDetails,
        PlaceDetailsView,
        PlaceDetailsPresenter>
        implements PlaceDetailsView {

    public static final String TRANSITION_KEY_MAP = "TRANSITION_KEY_MAP";
    private static final String PLACE_EXTRA_TAG = "PLACE_EXTRA_TAG";

    @BindView(R.id.bottomSheetView)
    protected View bottomSheetView;
    @BindView(R.id.merged_appBarLayout)
    protected AppBarLayout merged_appBarLayout;
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.title_textView)
    protected TextView title_textView;
    @BindView(R.id.address_textView)
    protected TextView address_textView;
    @BindView(R.id.webSite_textView)
    protected TextView webSite_textView;
    @BindView(R.id.rating_textView)
    protected TextView rating_textView;
    @BindView(R.id.reviews_recyclerView)
    protected RecyclerView reviews_recyclerView;

    private Place place;
    private ItemPagerAdapter pagerAdapter;
    private ReviewsAdapter reviewsAdapter;
    private MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;


    /* launcher */

    public static void launch(Place place, View view, Activity activity) {
        Intent intent = new Intent(activity, PlaceDetailsActivity.class);
        intent.putExtra(PLACE_EXTRA_TAG, place);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, TRANSITION_KEY_MAP);
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
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
        ButterKnife.bind(this);
        ViewCompat.setTransitionName(findViewById(R.id.map_container), PlaceDetailsActivity.TRANSITION_KEY_MAP);
        place = getIntent().getExtras().getParcelable(PLACE_EXTRA_TAG);

        //set action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
            actionBar.setTitle(R.string.Place_details);
        }

        //set map
        if (savedInstanceState == null) {
            MapFragment mapFragment = MapFragment.newInstance();
            mapFragment.getMapAsync(presenter);
            getFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }

        //set view pager
        pagerAdapter = new ItemPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //set reviews view
        reviewsAdapter = new ReviewsAdapter(this, null);
        reviews_recyclerView.setAdapter(reviewsAdapter);

        //set behaviors
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheetView);
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(merged_appBarLayout);
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(v -> behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED));

        loadData(false);
    }


    /* LCE */

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return getErrorMessage(e);
    }

    @Override
    public void setData(PlaceDetails data) {
        mergedAppBarLayoutBehavior.setToolbarTitle(data.getName());
        title_textView.setText(data.getName());
        address_textView.setText(data.getVicinity());
        rating_textView.setText(data.getRating());
        if (data.getWebsite() != null) {
            webSite_textView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getWebsite()));
                startActivity(browserIntent);
            });
            webSite_textView.setVisibility(View.VISIBLE);
        } else {
            webSite_textView.setVisibility(View.GONE);
        }
        if (data.getReviews() != null) {
            reviews_recyclerView.setVisibility(View.VISIBLE);
            reviewsAdapter.setReviews(data.getReviews());
        } else {
            reviews_recyclerView.setVisibility(View.GONE);
        }

        pagerAdapter.setItems(data.getPhotoUrl());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadData(place.getPlaceId(), pullToRefresh);
    }
}