package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OpeningHours implements Parcelable {

    @SerializedName("open_now")
    private Boolean openNow;

    public Boolean isOpenNow() {
        return openNow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.openNow);
    }

    public OpeningHours() {
    }

    protected OpeningHours(Parcel in) {
        this.openNow = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<OpeningHours> CREATOR = new Creator<OpeningHours>() {
        @Override
        public OpeningHours createFromParcel(Parcel source) {
            return new OpeningHours(source);
        }

        @Override
        public OpeningHours[] newArray(int size) {
            return new OpeningHours[size];
        }
    };
}