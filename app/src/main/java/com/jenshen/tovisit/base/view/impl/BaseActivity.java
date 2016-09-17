package com.jenshen.tovisit.base.view.impl;


import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.jenshen.tovisit.R;
import com.jenshen.tovisit.base.view.BaseView;
import com.jenshen.tovisit.base.view.impl.mvp.BaseMvpActivity;

public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        String errorMessage = throwable.getMessage() != null ? throwable.getMessage() : getString(R.string.error_unknown);
        createAlertDialog(errorMessage, null).show();
    }

    protected AlertDialog.Builder createAlertDialog(String message, @Nullable BaseMvpActivity.DoOnError doOnError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder.setTitle(getString(R.string.warning))
                .setMessage(message)
                .setOnDismissListener(v -> {
                    if (doOnError != null)
                        doOnError.doOnError();
                })
                .setPositiveButton(R.string.ok, null);
    }
}
