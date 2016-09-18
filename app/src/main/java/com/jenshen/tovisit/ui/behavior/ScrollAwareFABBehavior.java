package com.jenshen.tovisit.ui.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.jenshen.tovisit.R;


public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    private float offset;
    private int appBarId = -1;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
        offset = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollAwareFABBehavior_Params);
        appBarId = a.getResourceId(R.styleable.ScrollAwareFABBehavior_Params_behavior_appBarId, -1);
        a.recycle();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (appBarId == -1) {
            throw new RuntimeException("You need to set appBarId");
        }

        if (offset == 0)
            setOffsetValue(parent);


        if (child.getY() <= (offset + child.getHeight()) && child.getVisibility() == View.VISIBLE)
            child.hide();
        else if (child.getY() > offset && child.getVisibility() != View.VISIBLE)
            child.show();

        return false;
    }

    private void setOffsetValue(CoordinatorLayout coordinatorLayout) {

        for (int i = 0; i < coordinatorLayout.getChildCount(); i++) {
            View child = coordinatorLayout.getChildAt(i);

            if (child instanceof AppBarLayout) {
                if (child.getId() == appBarId) {
                    offset = child.getY() + child.getHeight();
                    break;
                }
            }
        }
    }
}