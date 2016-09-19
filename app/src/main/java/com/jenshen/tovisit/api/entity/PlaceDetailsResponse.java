package com.jenshen.tovisit.api.entity;


import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.api.entity.place.PlaceDetails;

import java.util.List;

public class PlaceDetailsResponse {

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;
    @SerializedName("result")
    private PlaceDetails place;

    public PlaceDetailsResponse(PlaceDetails place, List<String> htmlAttributions) {
        this.place = place;
        this.htmlAttributions = htmlAttributions;
    }

    public PlaceDetails getPlace() {
        return place;
    }
}
