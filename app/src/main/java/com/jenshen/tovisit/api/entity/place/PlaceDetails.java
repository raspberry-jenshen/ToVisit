package com.jenshen.tovisit.api.entity.place;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetails extends Place {

    public static final Creator<PlaceDetails> CREATOR = new Creator<PlaceDetails>() {
        @Override
        public PlaceDetails createFromParcel(Parcel source) {
            return new PlaceDetails(source);
        }

        @Override
        public PlaceDetails[] newArray(int size) {
            return new PlaceDetails[size];
        }
    };
    @SerializedName("address_components")
    private List<AddressComponent> addressComponents;
    @SerializedName("adr_address")
    private String adrAddress;
    @SerializedName("formatted_address")
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;
    @SerializedName("international_phone_number")
    private String internationalPhoneNumber;
    @SerializedName("reviews")
    private List<Review> reviews;
    @SerializedName("url")
    private String url;
    @SerializedName("utc_offset")
    private Integer utcOffset;
    @SerializedName("website")
    private String website;

    protected PlaceDetails(Parcel in) {
        super(in);
        this.addressComponents = in.createTypedArrayList(AddressComponent.CREATOR);
        this.adrAddress = in.readString();
        this.formattedAddress = in.readString();
        this.formattedPhoneNumber = in.readString();
        this.internationalPhoneNumber = in.readString();
        this.reviews = in.createTypedArrayList(Review.CREATOR);
        this.url = in.readString();
        this.utcOffset = (Integer) in.readValue(Integer.class.getClassLoader());
        this.website = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.icon = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.openingHours = in.readParcelable(OpeningHours.class.getClassLoader());
        this.photos = in.createTypedArrayList(Photo.CREATOR);
        this.placeId = in.readString();
        this.scope = in.readString();
        this.altIds = new ArrayList<>();
        in.readList(this.altIds, AltId.class.getClassLoader());
        this.reference = in.readString();
        this.types = in.createStringArrayList();
        this.vicinity = in.readString();
        this.rating = in.readString();
    }

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public String getAdrAddress() {
        return adrAddress;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getUrl() {
        return url;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.addressComponents);
        dest.writeString(this.adrAddress);
        dest.writeString(this.formattedAddress);
        dest.writeString(this.formattedPhoneNumber);
        dest.writeString(this.internationalPhoneNumber);
        dest.writeTypedList(this.reviews);
        dest.writeString(this.url);
        dest.writeValue(this.utcOffset);
        dest.writeString(this.website);
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