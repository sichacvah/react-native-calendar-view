package com.sergey_kurochkin.react_native_calendar_view.interfaces;

import com.facebook.react.bridge.ReadableMap;

public interface ICalendarTheme {

    ReadableMap getRawTheme();
    int getSelectionEdgeColor();
    int getSelectionColor();

    int getDayBackgroundColor();
    int getDayColor();
    int getBorderColor();
    String getDayFontFamily();
    int getDayFontSize();
    int getDayFontWeight();

    int getHeaderBackgroundColor();
    int getMonthTitleColor();
    String getMonthTitleFontFamily();
    int getMonthTitleFontSize();
    int getMonthTitleFontWeight();
    int getWeekdayColor();
    int getWeekdayBackgroundColor();
    String getWeekdayFontFamily();
    int getWeekdayFontSize();
    int getPastDayColor();
    String getPastDayFontFamily();
    int getPastDayFontWeight();
    int getPastDayFontSize();
    int getWeekdayFontWeight();

}
