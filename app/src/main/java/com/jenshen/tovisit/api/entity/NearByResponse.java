package com.jenshen.tovisit.api.entity;


import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.api.entity.place.Place;

import java.util.List;

public class NearByResponse {

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("results")
    private List<Place> places;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<String> getHtmlAttributions() {

        return htmlAttributions;
    }
}
