package com.jenshen.tovisit.base.view;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

public interface BaseMvpView<M> extends MvpLceView<M>, BaseView {

    Context getContext();
}
