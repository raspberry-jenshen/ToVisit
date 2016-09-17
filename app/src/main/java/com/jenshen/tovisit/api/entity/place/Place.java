package com.jenshen.tovisit.api.entity.place;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place {

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

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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

    public String getVicinity() {
        return vicinity;
    }
}