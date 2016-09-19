package com.jenshen.tovisit.ui.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Review;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.name_textView)
    protected TextView name_textView;

    @BindView(R.id.imageView)
    protected ImageView imageView;

    @BindView(R.id.message_textView)
    protected TextView message_textView;

    @BindView(R.id.date_textView)
    protected TextView date_textView;

    @BindView(R.id.rating_textView)
    protected TextView rating_textView;

    @BindView(R.id.link_textView)
    protected TextView link_textView;

    public ReviewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void setInfo(Review review) {
        if (review.getAuthorName() != null) {
            name_textView.setText(review.getAuthorName());
            name_textView.setVisibility(View.VISIBLE);
        } else {
            name_textView.setVisibility(View.GONE);
        }

        if (review.getAuthorUrl() != null) {
            link_textView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getAuthorUrl()));
                context.startActivity(browserIntent);
            });
            link_textView.setVisibility(View.VISIBLE);
        } else {
            link_textView.setVisibility(View.GONE);
        }

        if (review.getRating() != null) {
            rating_textView.setText(String.valueOf(review.getRating()));
            rating_textView.setVisibility(View.VISIBLE);
        } else {
            rating_textView.setVisibility(View.GONE);
        }

        if (review.getAuthorUrl() != null) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(review.getProfilePhotoUrl())
                    .asBitmap()
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        if (review.getText() != null) {
            message_textView.setVisibility(View.VISIBLE);
            message_textView.setText(review.getText());
        } else {
            message_textView.setVisibility(View.GONE);
        }

        if (review.getTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yy hh:mm:ss", Locale.getDefault());
            String strTime = simpleDateFormat.format(review.getTime());
            date_textView.setVisibility(View.VISIBLE);
            date_textView.setText(strTime);
        } else {
            date_textView.setVisibility(View.GONE);
        }
    }
}
