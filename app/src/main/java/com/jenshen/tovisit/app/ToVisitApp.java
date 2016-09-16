package com.jenshen.tovisit.app;

import android.app.Application;

import com.jenshen.tovisit.inject.component.AppComponent;
import com.jenshen.tovisit.inject.component.DaggerAppComponent;
import com.jenshen.tovisit.inject.module.AppModule;
import com.squareup.leakcanary.LeakCanary;

public class ToVisitApp extends Application {

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
                    .build();
        }
        return appComponent;
    }

}
