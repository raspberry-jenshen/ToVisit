package com.jenshen.tovisit.inject.component;

import com.jenshen.tovisit.inject.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {

}
