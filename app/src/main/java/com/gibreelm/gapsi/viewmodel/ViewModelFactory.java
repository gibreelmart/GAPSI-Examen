package com.gibreelm.gapsi.viewmodel;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Fragment fragment;
    private Activity activity;

    public ViewModelFactory(Activity activity) {
        this.activity = activity;
    }

    public ViewModelFactory(Activity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getDeclaredConstructor(activity.getClass()).newInstance(activity);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException ex1) {
            try {
                return modelClass.getDeclaredConstructor(activity.getClass(), fragment.getClass()).newInstance(activity, fragment);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException ex2) {
                throw new IllegalArgumentException(ex2.getMessage());
            }
        }
    }

}
