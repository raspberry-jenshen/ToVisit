package com.jenshen.tovisit.api.entity.place;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Geometry implements Parcelable {

    @SerializedName("location")
    private PlaceLocation placeLocation;

    public Geometry(PlaceLocation placeLocation) {
        this.placeLocation = placeLocation;
    }

    public PlaceLocation getPlaceLocation() {
        return placeLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.placeLocation, flags);
    }

    protected Geometry(Parcel in) {
        this.placeLocation = in.readParcelable(PlaceLocation.class.getClassLoader());
    }

    public static final Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}