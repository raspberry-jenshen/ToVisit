package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Aspect implements Parcelable {

    public static final Parcelable.Creator<Aspect> CREATOR = new Parcelable.Creator<Aspect>() {
        @Override
        public Aspect createFromParcel(Parcel source) {
            return new Aspect(source);
        }

        @Override
        public Aspect[] newArray(int size) {
            return new Aspect[size];
        }
    };
    @SerializedName("rating")
    private Integer rating;
    @SerializedName("type")
    private String type;

    public Aspect() {
    }

    protected Aspect(Parcel in) {
        this.rating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
    }

    public Integer getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.rating);
        dest.writeString(this.type);
    }
}