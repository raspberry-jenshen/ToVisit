package com.jenshen.tovisit.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.jenshen.tovisit.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceManager {

    public static final String SETTINGS_PREFERENCE_NAME = "ToVisit_pref";
    private final Context context;
    private final SharedPreferences preferences;

    @Inject
    public PreferenceManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(SETTINGS_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public int getRadiusForSearch() {
       return Integer.parseInt(preferences.getString(context.getString(R.string.preference_search_radius), context.getString(R.string.first_radius)));
    }

    public String getWebApiKey() {
        return context.getString(R.string.GOOGLE_WEB_PLACES_KEY);
    }
}
