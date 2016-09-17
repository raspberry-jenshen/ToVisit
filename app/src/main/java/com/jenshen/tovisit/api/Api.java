package com.jenshen.tovisit.api;

import com.jenshen.tovisit.api.entity.Place;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("maps/api/place/nearbysearch/json?")
    Observable<List<Place>> getPlaces(@Query("location") String location,
                                      @Query("radius") int radius,
                                      @Query("sensor") boolean sensor,
                                      @Query("types") String types,
                                      @Query("key") String key);

}
