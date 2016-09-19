package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressComponent implements Parcelable {

    public static final Parcelable.Creator<AddressComponent> CREATOR = new Parcelable.Creator<AddressComponent>() {
        @Override
        public AddressComponent createFromParcel(Parcel source) {
            return new AddressComponent(source);
        }

        @Override
        public AddressComponent[] newArray(int size) {
            return new AddressComponent[size];
        }
    };
    @SerializedName("long_name")
    private String longName;
    @SerializedName("short_name")
    private String shortName;
    @SerializedName("types")
    private List<String> types;

    public AddressComponent() {
    }

    protected AddressComponent(Parcel in) {
        this.longName = in.readString();
        this.shortName = in.readString();
        this.types = in.createStringArrayList();
    }

    public List<String> getTypes() {
        return types;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.longName);
        dest.writeString(this.shortName);
        dest.writeStringList(this.types);
    }
}