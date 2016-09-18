package com.jenshen.tovisit.api.entity;


import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.api.entity.place.Place;

import java.util.List;

public class PlaceDetailsResponse {

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;
    @SerializedName("results")
    private Place place;

    public PlaceDetailsResponse(Place place, List<String> htmlAttributions) {
        this.place = place;
        this.htmlAttributions = htmlAttributions;
    }

    public Place getPlace() {
        return place;
    }
}
