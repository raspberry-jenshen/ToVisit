package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.inject.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
}
