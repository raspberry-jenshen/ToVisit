package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaceLocation implements Parcelable {

    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;

    public PlaceLocation(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
    }

    protected PlaceLocation(Parcel in) {
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<PlaceLocation> CREATOR = new Creator<PlaceLocation>() {
        @Override
        public PlaceLocation createFromParcel(Parcel source) {
            return new PlaceLocation(source);
        }

        @Override
        public PlaceLocation[] newArray(int size) {
            return new PlaceLocation[size];
        }
    };
}