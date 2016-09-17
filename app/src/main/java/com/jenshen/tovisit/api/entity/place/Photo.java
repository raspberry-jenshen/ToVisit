package com.jenshen.tovisit.api.entity.place;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("height")
    public Integer height;
    @SerializedName("width")
    public Integer width;
    @SerializedName("html_attributions")
    public List<Object> htmlAttributions;
    @SerializedName("photo_reference")
    public String photoReference;

}