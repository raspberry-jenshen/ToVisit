package com.jenshen.tovisit.ui.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Place;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.name_textView)
    protected TextView name_textView;

    @BindView(R.id.imageView)
    protected ImageView imageView;

    @BindView(R.id.isOpen_textView)
    protected TextView isOpen_textView;

    @BindView(R.id.vicinity_textView)
    protected TextView vicinity_textView;

    @BindView(R.id.rating_textView)
    protected TextView rating_textView;

    public PlaceHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setInfo(Place place) {
        name_textView.setText(place.getName());
        if (place.getIcon() != null) {
            vicinity_textView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(place.getIcon())
                    .asBitmap()
                    .centerCrop()
                    .into(imageView);
        } else {
            vicinity_textView.setVisibility(View.GONE);
        }

        if (place.getOpeningHours() != null && place.getOpeningHours().isOpenNow()) {
            isOpen_textView.setVisibility(View.VISIBLE);
        } else {
            isOpen_textView.setVisibility(View.GONE);
        }

        if (place.getVicinity() != null) {
            vicinity_textView.setVisibility(View.VISIBLE);
            vicinity_textView.setText(place.getVicinity());
        } else {
            vicinity_textView.setVisibility(View.GONE);
        }

        if (place.getRating() != null) {
            rating_textView.setVisibility(View.VISIBLE);
            rating_textView.setText(place.getRating());
        } else {
            rating_textView.setVisibility(View.GONE);
        }
    }
}
