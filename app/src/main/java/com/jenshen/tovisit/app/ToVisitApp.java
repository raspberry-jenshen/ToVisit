package com.jenshen.tovisit.app;

import android.app.Application;

import com.jenshen.tovisit.inject.component.AppComponent;
import com.jenshen.tovisit.inject.component.DaggerAppComponent;
import com.jenshen.tovisit.inject.module.ApiModule;
import com.jenshen.tovisit.inject.module.AppModule;
import com.squareup.leakcanary.LeakCanary;

public class ToVisitApp extends Application {

    public static final String BASE_URL = "https://maps.googleapis.com/";
    private static ToVisitApp application;
    private AppComponent appComponent;

    public static ToVisitApp getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        LeakCanary.install(this);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .apiModule(new ApiModule(BASE_URL))
                    .build();
        }
        return appComponent;
    }
}
