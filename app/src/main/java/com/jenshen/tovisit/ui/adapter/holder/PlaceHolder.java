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

    public PlaceHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setInfo(Place place) {
        name_textView.setText(place.getName());
        Glide.with(context)
                .load(place.getIcon())
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }
}
