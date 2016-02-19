package ru.mdsps.contacts.core.views;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FloatingActionButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private float translationY1 = 0.0f;
    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {}

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof AppBarLayout || dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {

        if(dependency instanceof AppBarLayout ) {
            float mHeight = dependency.getY();
            if (mHeight > 0) {
                child.show();
            } else {
                child.hide();
            }
        }
        if(dependency instanceof Snackbar.SnackbarLayout){

            float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());

            if(translationY != translationY1){
                Log.d("MainBehavior", "DTY=" + dependency.getTranslationY() +
                        " TY=" + translationY + " TY1=" + translationY1);
                child.setTranslationY(translationY);
                translationY1 = translationY;
                Log.d("MainBehavior", "DTY=" + dependency.getTranslationY() +
                        " TY=" + translationY + " TY1=" + translationY1);
            } else {
                Log.d("MainBehavior", "DTY=" + dependency.getTranslationY() +
                        " TY=" + translationY + " TY1=" + translationY1);
                child.setTranslationY(0);
            }


        }
        return true;
    }

}
