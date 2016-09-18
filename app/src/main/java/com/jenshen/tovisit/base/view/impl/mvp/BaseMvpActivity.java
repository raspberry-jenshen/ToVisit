package com.jenshen.tovisit.base.view.impl.mvp;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.jenshen.tovisit.R;
import com.jenshen.tovisit.base.view.BaseMvpView;

public abstract class BaseMvpActivity<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceActivity<CV, M, V, P>
        implements BaseMvpView<M> {

    /* callbacks */

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        createAlertDialog(getErrorMessage(throwable), null).show();
    }

    protected String getErrorMessage(Throwable throwable) {
        return throwable.getMessage() != null ? throwable.getMessage() : getString(R.string.error_unknown);
    }

    protected AlertDialog.Builder createAlertDialog(String message, @Nullable DoOnError doOnError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setTitle(getString(R.string.warning))
                .setMessage(message)
                .setOnDismissListener(v -> {
                    if (doOnError != null)
                        doOnError.doOnError();
                })
                .setPositiveButton(R.string.ok, null);
    }


    /* Functional interface */

    @FunctionalInterface
    public interface DoOnError {
        void doOnError();
    }
}
