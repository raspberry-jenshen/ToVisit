package com.jenshen.tovisit.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.function.FunctionalInterface;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.entity.place.Review;
import com.jenshen.tovisit.ui.adapter.holder.ReviewHolder;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private final OnItemClickListener<Review> onItemClick;
    private final Context context;
    private List<Review> reviews;

    public ReviewsAdapter(Context context, OnItemClickListener<Review> onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(context,
                LayoutInflater.from(context)
                        .inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        final Review item = reviews.get(position);
        holder.setInfo(item);
        setListener(item, holder);
    }
    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }


    private void setListener(Review item, RecyclerView.ViewHolder holder) {
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
