package com.jenshen.tovisit.ui.fragment.places;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.Place;
import com.jenshen.tovisit.app.ToVisitApp;
import com.jenshen.tovisit.base.view.impl.mvp.component.BaseDIMvpFragment;
import com.jenshen.tovisit.inject.component.PlaceSubcomponent;
import com.jenshen.tovisit.ui.adapter.PlacesAdapter;
import com.jenshen.tovisit.ui.fragment.places.mvp.PlacesView;
import com.jenshen.tovisit.ui.fragment.places.mvp.presenter.PlacesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlacesFragment extends BaseDIMvpFragment<
        PlaceSubcomponent,
        SwipeRefreshLayout,
        List<Place>,
        PlacesView,
        PlacesPresenter>
        implements PlacesView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private Unbinder unbinder ;

    /* Creator */

    public static PlacesFragment create() {
        return new PlacesFragment();
    }


    /* inject */

    @Override
    public PlaceSubcomponent createComponent() {
        return ToVisitApp.getApplication().getAppComponent().plus();
    }

    @Override
    public void inject(PlaceSubcomponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    /* lifecycle */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        unbinder = ButterKnife.bind(this, view);
        // Setup contentView == SwipeRefreshView
        contentView.setOnRefreshListener(this);

        // Setup recycler view
        adapter = new PlacesAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadData(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void setData(List<Place> data) {
        adapter.setPlaces(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadPlaces(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
