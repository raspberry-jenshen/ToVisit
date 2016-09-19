package com.jenshen.tovisit.api.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.jenshen.tovisit.R;

public enum RankBy {
    @SerializedName("prominence")PROMINENCE,
    @SerializedName("distance")DISTANCE,
    @SerializedName("keyword")KEYWORD,
    @SerializedName("name")NAME,
    @SerializedName("type")TYPE;

    public static RankBy toEnum(String myEnumString) {
        try {
            return valueOf(myEnumString);
        } catch (Exception ex) {
            // For error cases
            return PROMINENCE;
        }
    }

    public String getName(Context context) {
        switch (this) {
            case PROMINENCE:
                return context.getString(R.string.prominence);
            case DISTANCE:
                return context.getString(R.string.distance);
            case KEYWORD:
                return context.getString(R.string.keyword);
            case NAME:
                return context.getString(R.string.name);
            case TYPE:
                return context.getString(R.string.type);
            default:
                throw new RuntimeException("Can't support this type");
        }
    }
}