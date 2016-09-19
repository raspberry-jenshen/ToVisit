package com.jenshen.tovisit.api.entity.place;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.app.ToVisitApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Place implements Parcelable {

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
    @SerializedName("geometry")
    protected Geometry geometry;
    @SerializedName("icon")
    protected String icon;
    @SerializedName("id")
    protected String id;
    @SerializedName("name")
    protected String name;
    @SerializedName("opening_hours")
    protected OpeningHours openingHours;
    @SerializedName("photos")
    protected List<Photo> photos;
    @SerializedName("place_id")
    protected String placeId;
    @SerializedName("scope")
    protected String scope;
    @SerializedName("alt_ids")
    protected List<AltId> altIds;
    @SerializedName("reference")
    protected String reference;
    @SerializedName("types")
    protected List<String> types;
    @SerializedName("vicinity")
    protected String vicinity;
    @SerializedName("rating")
    protected String rating;

    @Nullable
    private transient Bitmap bitmap;

    private transient List<String> photoUrl;

    public Place() {
    }

    protected Place(Parcel in) {
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.icon = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        this.photos = in.createTypedArrayList(Photo.CREATOR);
        this.placeId = in.readString();
        this.scope = in.readString();
        this.altIds = new ArrayList<AltId>();
        in.readList(this.altIds, AltId.class.getClassLoader());
        this.reference = in.readString();
        this.types = in.createStringArrayList();
        this.vicinity = in.readString();
        this.rating = in.readString();
    }

    public String getVicinity() {
        return vicinity;
    }

    public List<String> getPhotoUrl() {
        return photoUrl;
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

    @Nullable
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

    public void bindPhotosUrl(final int maxwidth, String apiKey) {
        List<Photo> photos = getPhotos();
        if (photos == null) {
            this.photos = Collections.emptyList();
        } else {
            this.photoUrl = Stream.of(photos).map(photo -> ToVisitApp.BASE_URL
                    + "maps/api/place/photo?maxwidth=" + String.valueOf(maxwidth)
                    + "&photoreference=" + photo.getPhotoReference()
                    + "&key=" + apiKey).collect(Collectors.toList());
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void bindBitmap(Context context) {
        if (getIcon() != null) {
            try {
                bitmap = Glide.with(context).load(getIcon()).asBitmap().into(-1, -1).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
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
        dest.writeTypedList(this.photos);
        dest.writeString(this.placeId);
        dest.writeString(this.scope);
        dest.writeList(this.altIds);
        dest.writeString(this.reference);
        dest.writeStringList(this.types);
        dest.writeString(this.vicinity);
        dest.writeString(this.rating);
    }
}