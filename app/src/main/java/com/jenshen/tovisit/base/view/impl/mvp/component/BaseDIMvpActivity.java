package com.jenshen.tovisit.base.view.impl.mvp.component;


import android.support.annotation.NonNull;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.jenshen.tovisit.base.component.PresenterComponent;
import com.jenshen.tovisit.base.view.impl.mvp.BaseMvpActivity;

public abstract class BaseDIMvpActivity<
        Component extends PresenterComponent<M, V, P>,
        CV extends View,
        M,
        V extends MvpLceView<M>,
        P extends MvpPresenter<V>>

        extends BaseMvpActivity<CV, M, V, P> {

    public final Component component;

    public BaseDIMvpActivity() {
        component = createComponent();
        inject(component);
    }

    public abstract Component createComponent();

    public abstract void inject(Component activityComponent);

    @NonNull
    @Override
    public P createPresenter() {
        return component.getPresenter();
    }
}
