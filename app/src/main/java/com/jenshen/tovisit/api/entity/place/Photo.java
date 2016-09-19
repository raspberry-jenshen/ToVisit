package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photo implements Parcelable {

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
    @SerializedName("height")
    private Integer height;
    @SerializedName("width")
    private Integer width;
    @SerializedName("html_attributions")
    private List<Object> htmlAttributions;
    @SerializedName("photo_reference")
    private String photoReference;

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.htmlAttributions = new ArrayList<Object>();
        in.readList(this.htmlAttributions, Object.class.getClassLoader());
        this.photoReference = in.readString();
    }

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

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }
}