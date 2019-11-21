package com.sergey_kurochkin.react_native_calendar_view.interfaces;

import android.view.View;

public interface IMonthHeaderBinder<T extends ViewContainer> {
    T create(View view);
    void bind(T container, ICalendarMonth month);
}
