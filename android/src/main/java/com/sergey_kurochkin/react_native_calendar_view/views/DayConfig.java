package com.sergey_kurochkin.react_native_calendar_view.views;


import com.sergey_kurochkin.react_native_calendar_view.interfaces.IDayBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ViewContainer;

public class DayConfig {
    public DayConfig(int width, int height, int dayViewRes, IDayBinder<ViewContainer> viewBinder) {
        this.width = width;
        this.height = height;
        this.dayViewRes = dayViewRes;
        this.viewBinder = viewBinder;
    }
    int width;
    int height;
    int dayViewRes;
    IDayBinder<ViewContainer> viewBinder;
}
