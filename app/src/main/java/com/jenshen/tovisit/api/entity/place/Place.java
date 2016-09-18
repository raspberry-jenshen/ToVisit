package com.jenshen.tovisit.api.entity.place;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Place implements Parcelable {

    @SerializedName("geometry")
    private Geometry geometry;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("opening_hours")
    private OpeningHours openingHours;
    @SerializedName("photos")
    private List<Photo> photos;
    @SerializedName("place_id")
    private String placeId;
    @SerializedName("scope")
    private String scope;
    @SerializedName("alt_ids")
    private List<AltId> altIds;
    @SerializedName("reference")
    private String reference;
    @SerializedName("types")
    private List<String> types;
    @SerializedName("vicinity")
    private String vicinity;
    @SerializedName("rating")
    private String rating;

    @Nullable
    private transient Bitmap bitmap;

    public Place(Geometry geometry, String icon, String id, String name, OpeningHours openingHours,
                 List<Photo> photos, String placeId, String scope, List<AltId> altIds,
                 String reference, List<String> types, String vicinity, String rating) {
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.openingHours = openingHours;
        this.photos = photos;
        this.placeId = placeId;
        this.scope = scope;
        this.altIds = altIds;
        this.reference = reference;
        this.types = types;
        this.vicinity = vicinity;
        this.rating = rating;
    }

    public void setBitmap(@Nullable Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Nullable
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Nullable
    public String getRating() {
        return rating;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getScope() {
        return scope;
    }

    public List<AltId> getAltIds() {
        return altIds;
    }

    public String getReference() {
        return reference;
    }

    public List<String> getTypes() {
        return types;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.geometry, flags);
        dest.writeString(this.icon);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.openingHours, flags);
        dest.writeList(this.photos);
        dest.writeString(this.placeId);
        dest.writeString(this.scope);
        dest.writeList(this.altIds);
        dest.writeString(this.reference);
        dest.writeStringList(this.types);
        dest.writeString(this.vicinity);
        dest.writeString(this.rating);
    }

    protected Place(Parcel in) {
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.icon = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
        this.placeId = in.readString();
        this.scope = in.readString();
        this.altIds = new ArrayList<AltId>();
        in.readList(this.altIds, AltId.class.getClassLoader());
        this.reference = in.readString();
        this.types = in.createStringArrayList();
        this.vicinity = in.readString();
        this.rating = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}