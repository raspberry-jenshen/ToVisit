package com.jenshen.tovisit.api;

import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.api.entity.NearByResponse;
import com.jenshen.tovisit.api.entity.PlaceDetailsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("maps/api/place/nearbysearch/json?")
    Observable<NearByResponse> getPlaces(@Query("location") String location,
                                         @Query("radius") Integer radius,
                                         @Query("rankby") RankBy rankBy,
                                         @Query("language") String language,
                                         @Query("type") String types,
                                         @Query("name") String names,
                                         @Query("pagetoken") String pageToken,
                                         @Query("key") String key);

    @GET("maps/api/place/details/json?")
    Observable<PlaceDetailsResponse> getPlace(@Query("placeid") String location,
                                              @Query("language") String language,
                                              @Query("key") String key);

    enum RankBy {
        @SerializedName("prominence")
        PROMINENCE,
        @SerializedName("distance")
        DISTANCE,
        @SerializedName("keyword")
        KEYWORD,
        @SerializedName("name")
        NAME,
        @SerializedName("type")
        TYPE
    }

    enum Type {
        ACCOUNTING,
        AIRPORT,
        AMUSEMENT_PARK,
        AQUARIUM,
        ART_GALLERY,
        ATM,
        BAKERY,
        BANK,
        BAR,
        BEAUTY_SALON,
        BICYCLE_STORE,
        BOOK_STORE,
        BOWLING_ALLEY,
        BUS_STATION,
        CAFE,
        CAMPGROUND,
        CAR_DEALER,
        CAR_RENTAL,
        CAR_REPAIR,
        CAR_WASH,
        CASINO,
        CEMETERY,
        CHURCH,
        CITY_HALL,
        CLOTHING_STORE,
        CONVENIENCE_STORE,
        COURTHOUSE,
        DENTIST,
        DEPARTMENT_STORE,
        DOCTOR,
        ELECTRICIAN,
        ELECTRONICS_STORE,
        EMBASSY,
        FIRE_STATION,
        FLORIST,
        FUNERAL_HOME,
        FURNITURE_STORE,
        GAS_STATION,
        GYM,
        HAIR_CARE,
        HARDWARE_STORE,
        HINDU_TEMPLE,
        HOME_GOODS_STORE,
        HOSPITAL,
        INSURANCE_AGENCY,
        JEWELRY_STORE,
        LAUNDRY,
        LAWYER,
        LIBRARY,
        LIQUOR_STORE,
        LOCAL_GOVERNMENT_OFFICE,
        LOCKSMITH,
        LODGING,
        MEAL_DELIVERY,
        MEAL_TAKEAWAY,
        MOSQUE,
        MOVIE_RENTAL,
        MOVIE_THEATER,
        MOVING_COMPANY,
        MUSEUM,
        NIGHT_CLUB,
        PAINTER,
        PARK,
        PARKING,
        PET_STORE,
        PHARMACY,
        PHYSIOTHERAPIST,
        PLUMBER,
        POLICE,
        POST_OFFICE,
        REAL_ESTATE_AGENCY,
        RESTAURANT,
        ROOFING_CONTRACTOR,
        RV_PARK,
        SCHOOL,
        SHOE_STORE,
        SHOPPING_MALL,
        SPA,
        STADIUM,
        STORAGE,
        STORE,
        SUBWAY_STATION,
        SYNAGOGUE,
        TAXI_STAND,
        TRAIN_STATION,
        TRANSIT_STATION,
        TRAVEL_AGENCY,
        UNIVERSITY,
        VETERINARY_CARE,
        ZOO,
    }
}
