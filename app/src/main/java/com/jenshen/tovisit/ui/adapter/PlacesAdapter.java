package com.jenshen.tovisit.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.function.FunctionalInterface;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;
import com.jenshen.tovisit.ui.adapter.holder.PlaceHolder;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceHolder> {

    private final OnItemClickListener<Place> onItemClick;
    private final Context context;
    private List<Place> places;

    public PlacesAdapter(Context context, OnItemClickListener<Place> onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlaceHolder(context,
                LayoutInflater.from(context)
                        .inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        final Place place = places.get(position);
        holder.setInfo(place);
        setListener(place, holder);
    }
    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
    }


    private void setListener(Place item, RecyclerView.ViewHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null)
                onItemClick.onItemClick(item);
        });
    }

    /* inner types */

    @FunctionalInterface
    public interface OnItemClickListener<T> {
        void onItemClick(@NonNull T item);
    }
}
