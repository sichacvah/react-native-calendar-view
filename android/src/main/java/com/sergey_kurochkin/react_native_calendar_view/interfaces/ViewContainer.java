package com.sergey_kurochkin.react_native_calendar_view.interfaces;

import android.view.View;

public abstract class ViewContainer {
    public View view;
    public ViewContainer(View view) {
        this.view = view;
    }
}
