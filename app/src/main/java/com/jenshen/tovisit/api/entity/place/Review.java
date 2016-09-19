package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Review implements Parcelable {

    @SerializedName("aspects")
    private List<Aspect> aspects;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_url")
    private String authorUrl;
    @SerializedName("language")
    private String language;
    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;
    @SerializedName("rating")
    private Integer rating;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private Date time;

    public List<Aspect> getAspects() {
        return aspects;
    }

    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    @Nullable
    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.aspects);
        dest.writeString(this.authorName);
        dest.writeString(this.authorUrl);
        dest.writeString(this.language);
        dest.writeString(this.profilePhotoUrl);
        dest.writeValue(this.rating);
        dest.writeString(this.text);
        dest.writeLong(this.time != null ? this.time.getTime() : -1);
    }

    public Review() {
    }

    protected Review(Parcel in) {
        this.aspects = in.createTypedArrayList(Aspect.CREATOR);
        this.authorName = in.readString();
        this.authorUrl = in.readString();
        this.language = in.readString();
        this.profilePhotoUrl = in.readString();
        this.rating = (Integer) in.readValue(Integer.class.getClassLoader());
        this.text = in.readString();
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}