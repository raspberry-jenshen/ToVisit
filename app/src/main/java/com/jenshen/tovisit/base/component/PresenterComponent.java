package com.jenshen.tovisit.base.component;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;


public interface PresenterComponent<
        M,
        V extends MvpLceView<M>,
        P extends MvpPresenter<V>> {
    P getPresenter();
}
