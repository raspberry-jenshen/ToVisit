package com.jenshen.tovisit.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jenshen.tovisit.R;

import java.util.Collections;
import java.util.List;

public class ItemPagerAdapter extends PagerAdapter {

    public static final String EMPTY_PHOTO = "EMPTY_PHOTO";
    
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<String> items;

    public ItemPagerAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItems(List<String> items) {
        this.items = items;
        if (items.isEmpty()) {
            this.items = Collections.singletonList(EMPTY_PHOTO);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.item_photo, container, false);
        setViewHolder(itemView, position);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    /* private methods */

    private void setViewHolder(View container, int position) {
        ImageView imageView = (ImageView) container.findViewById(R.id.imageView);
        String item = items.get(position);
        if (!item.equals(EMPTY_PHOTO)) {
            Glide.with(context)
                    .load(item)
                    .asBitmap()
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_filter);
        }
    }
}
