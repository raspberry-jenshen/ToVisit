package com.jenshen.tovisit.ui.fragment;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.jenshen.tovisit.R;

import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        getPreferenceManager().setSharedPreferencesName(com.jenshen.tovisit.manager.PreferenceManager.SETTINGS_PREFERENCE_NAME);
        addPreferencesFromResource(R.xml.settings);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.preference_search_radius)));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            List<String> values = Arrays.asList(getResources().getStringArray(R.array.radius_values));
            List<String> titles = Arrays.asList(getResources().getStringArray(R.array.radius_values_titles));
            int titleIndex = values.indexOf(stringValue);
            if (titleIndex != -1) {
                listPreference.setSummary(titles.get(titleIndex));
            }
        } else {
            // For all other preferences, set the summary to the value's
            // simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }


    /* private methods */

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);
        final String key = preference.getKey();
        if (preference instanceof ListPreference) {
            onPreferenceChange(preference, ((ListPreference) preference).getValue());
        } else {
            String value = PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(key, "");
            onPreferenceChange(preference, value);
        }
    }
}