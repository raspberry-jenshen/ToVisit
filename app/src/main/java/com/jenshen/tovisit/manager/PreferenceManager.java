package com.jenshen.tovisit.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.api.model.PlaceType;
import com.jenshen.tovisit.api.model.RankBy;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager {

    public static final String SETTINGS_PREFERENCE_NAME = "ToVisit_pref";
    private final Context context;
    private final SharedPreferences preferences;
    private final Gson gson;

    @Inject
    public PreferenceManager(Context context) {
        this.context = context;
        this.gson = new Gson();
        this.preferences = context.getSharedPreferences(SETTINGS_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getRadiusForSearch() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.preference_search_radius), context.getString(R.string.first_radius)));
    }

    public String getWebApiKey() {
        return context.getString(R.string.GOOGLE_WEB_PLACES_KEY);
    }

    public RankBy getRankBy() {
        String myEnumString = preferences.getString(context.getString(R.string.preference_rank_by),
                RankBy.PROMINENCE.toString());
        return RankBy.toEnum(myEnumString);
    }

    public void setRankBy(RankBy item) {
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(context.getString(R.string.preference_rank_by), item.toString());
        editor.apply();
    }

    public List<PlaceType> getPlaceTypes() {
        Type listType = new TypeToken<List<PlaceType>>() {
        }.getType();
        return fromJsonToEntity(preferences.getString(context.getString(R.string.preference_places_types), null), listType);
    }

    public void setPlaceTypes(@Nullable List<PlaceType> types) {
        SharedPreferences.Editor editor = preferences.edit();
        if (types == null) {
            editor.putString(context.getString(R.string.preference_places_types), null);
        } else {
            editor.putString(context.getString(R.string.preference_places_types), entityToJson(types));
        }
        editor.apply();
    }


    /* private methods */

    private <T> String entityToJson(T entity) {
        return gson.toJson(entity);
    }

    private <T> T fromJsonToEntity(@Nullable String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    private <T> T fromJsonToEntity(@Nullable String json, Type type) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, type);
    }
}
