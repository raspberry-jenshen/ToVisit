package com.jenshen.tovisit.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jenshen.tovisit.api.entity.Place;
import com.jenshen.tovisit.ui.adapter.holder.PlaceHolder;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceHolder> {

    public PlacesAdapter(Context context) {

    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setPlaces(List<Place> places) {
    }
}
