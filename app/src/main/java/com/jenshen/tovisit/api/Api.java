package com.jenshen.tovisit.api;

import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("maps/api/place/nearbysearch/json?")
    Observable<NearByResponse> getPlaces(@Query("location") String location,
                                         @Query("radius") Integer radius,
                                         @Query("rankby") String rankBy,
                                         @Query("language") String language,
                                         @Query("type") String types,
                                         @Query("name") String names,
                                         @Query("pagetoken") String pageToken,
                                         @Query("key") String key);

    @GET("maps/api/place/details/json?")
    Observable<PlaceDetailsResponse> getPlace(@Query("placeid") String location,
                                          @Query("language") String language,
                                          @Query("key") String key);

}
