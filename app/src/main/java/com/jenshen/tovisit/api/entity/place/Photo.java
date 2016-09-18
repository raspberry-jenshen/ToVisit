package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photo implements Parcelable {

    @SerializedName("height")
    public Integer height;
    @SerializedName("width")
    public Integer width;
    @SerializedName("html_attributions")
    public List<Object> htmlAttributions;
    @SerializedName("photo_reference")
    public String photoReference;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.height);
        dest.writeValue(this.width);
        dest.writeList(this.htmlAttributions);
        dest.writeString(this.photoReference);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.htmlAttributions = new ArrayList<Object>();
        in.readList(this.htmlAttributions, Object.class.getClassLoader());
        this.photoReference = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}