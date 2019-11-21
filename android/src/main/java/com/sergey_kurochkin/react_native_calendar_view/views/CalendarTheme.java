package com.sergey_kurochkin.react_native_calendar_view.views;


import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarTheme;


public class CalendarTheme implements ICalendarTheme {
    static final int UNSET = -1;
    private int dayBackgroundColor = Color.TRANSPARENT;
    private int dayColor = Color.BLACK;
    private int borderColor = Color.BLACK;
    private @Nullable String dayFontFamily = null;
    private int dayFontSize = 16;
    private int dayFontWeight = Typeface.NORMAL;

    private int headerBackgroundColor = Color.TRANSPARENT;
    private int monthTitleColor = Color.BLACK;
    private @Nullable String monthTitleFontFamily = null;
    private int monthTitleFontSize = 16;
    private int monthTitleFontWeight = Typeface.NORMAL;
    private int weekdayColor = Color.BLACK;
    private int weekdayBackgroundColor = Color.BLACK;
    private @Nullable String weekdayFontFamily = null;
    private int weekdayFontSize = 16;
    private int weekdayFontWeight = Typeface.NORMAL;
    private int pastDayColor = Color.GRAY;
    private @Nullable String pastDayFontFamily = null;
    private int pastDayFontWeight = Typeface.NORMAL;
    private int pastDayFontSize = 16;
    private ReadableMap mTheme;

    private int selectionEdgeColor = Color.TRANSPARENT;
    private int selectionColor = Color.TRANSPARENT;


    private boolean getBooleanProp(String name, boolean defaultValue) {
        if (mTheme.hasKey(name)) {
            return mTheme.getBoolean(name);
        } else {
            return defaultValue;
        }
    }

    private String getStringProp(String name) {
        if (mTheme.hasKey(name)) {
            return mTheme.getString(name);
        } else {
            return null;
        }
    }

    private int getIntProp(String name, int defaultvalue) {
        if (mTheme.hasKey(name)) {
            return mTheme.getInt(name);
        } else {
            return defaultvalue;
        }
    }

    private static int parseNumericFontWeight(String fontWeightString) {
        // This should be much faster than using regex to verify input and Integer.parseInt
        return fontWeightString.length() == 3
                && fontWeightString.endsWith("00")
                && fontWeightString.charAt(0) <= '9'
                && fontWeightString.charAt(0) >= '1'
                ? 100 * (fontWeightString.charAt(0) - '0')
                : -1;
    }

    public int getFontWeight(@Nullable String fontWeightString) {
        int fontWeightNumeric =
                fontWeightString != null ? parseNumericFontWeight(fontWeightString) : -1;
        int fontWeight = UNSET;
        if (fontWeightNumeric >= 500 || "bold".equals(fontWeightString)) {
            fontWeight = Typeface.BOLD;
        } else if ("normal".equals(fontWeightString)
                || (fontWeightNumeric != -1 && fontWeightNumeric < 500)) {
            fontWeight = Typeface.NORMAL;
        }
        return fontWeight;
    }

    public  CalendarTheme() {
        this(new JavaOnlyMap());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;

        return (
                obj instanceof CalendarTheme &&
                        pastDayColor == ((CalendarTheme) obj).pastDayColor &&
                        pastDayFontFamily == ((CalendarTheme) obj).pastDayFontFamily &&
                        pastDayFontWeight == ((CalendarTheme) obj).pastDayFontWeight &&
                        pastDayFontSize == ((CalendarTheme) obj).pastDayFontSize &&
                        dayBackgroundColor == ((CalendarTheme) obj).dayBackgroundColor &&
                        dayColor == ((CalendarTheme) obj).dayColor &&
                        dayFontWeight ==  ((CalendarTheme) obj).dayFontWeight &&
                        dayFontSize == ((CalendarTheme) obj).dayFontSize &&
                        dayFontWeight == ((CalendarTheme) obj).dayFontWeight &&
                        headerBackgroundColor == ((CalendarTheme) obj).headerBackgroundColor &&
                        monthTitleColor == ((CalendarTheme) obj).monthTitleColor &&
                        monthTitleFontFamily == ((CalendarTheme) obj).monthTitleFontFamily &&
                        monthTitleFontSize == ((CalendarTheme) obj).monthTitleFontSize &&
                        monthTitleFontWeight == ((CalendarTheme) obj).monthTitleFontWeight &&
                        weekdayColor == ((CalendarTheme) obj).weekdayColor &&
                        weekdayBackgroundColor == ((CalendarTheme) obj).weekdayBackgroundColor &&
                        weekdayFontFamily == ((CalendarTheme) obj).weekdayFontFamily &&
                        weekdayFontSize == ((CalendarTheme) obj).weekdayFontSize &&
                        weekdayFontWeight == ((CalendarTheme) obj).weekdayFontWeight &&
                        selectionColor == ((CalendarTheme) obj).selectionColor &&
                        selectionEdgeColor == ((CalendarTheme) obj).selectionEdgeColor
        );
    }


    @Override
    public int hashCode() {
        return (
                dayBackgroundColor ^
                        dayColor ^
                        borderColor ^
                        (dayFontFamily == null ? "" : dayFontFamily).hashCode() ^
                        dayFontSize ^
                        dayFontWeight ^
                        headerBackgroundColor ^
                        monthTitleColor ^
                        (monthTitleFontFamily == null ? "" : monthTitleFontFamily).hashCode() ^
                        monthTitleFontSize ^
                        monthTitleFontWeight ^
                        weekdayColor ^
                        weekdayBackgroundColor ^
                        (weekdayFontFamily == null ? "" : weekdayFontFamily).hashCode() ^
                        (pastDayFontFamily == null ? "" : pastDayFontFamily).hashCode() ^
                        weekdayFontSize ^
                        weekdayFontWeight ^
                        pastDayColor ^
                        pastDayFontWeight ^
                        pastDayFontSize ^
                        selectionColor ^
                        selectionEdgeColor
        );
    }

    public void setRawTheme(ReadableMap rawTheme) {
        mTheme = rawTheme;
        dayBackgroundColor = getIntProp("dayBackgroundColor", dayBackgroundColor);
        dayColor =  getIntProp("dayColor", dayColor);
        borderColor = getIntProp("borderColor", borderColor);
        dayFontFamily = getStringProp("dayFontFamily");
        dayFontSize = getIntProp("dayFontSize", dayFontSize);
        dayFontWeight = getFontWeight(getStringProp("dayFontWeight"));

        headerBackgroundColor = getIntProp("headerBackgroundColor", headerBackgroundColor);
        monthTitleColor = getIntProp("monthTitleColor", monthTitleColor);
        monthTitleFontFamily = getStringProp("monthTitleFontFamily");
        monthTitleFontSize = getIntProp("monthTitleFontSize", monthTitleFontSize);
        monthTitleFontWeight = getFontWeight(getStringProp("monthTitleFontWeight"));
        weekdayColor = getIntProp("weekdayColor", weekdayColor);
        weekdayBackgroundColor = getIntProp("weekdayBackgroundColor", weekdayBackgroundColor);
        weekdayFontFamily = getStringProp("weekdayFontFamily");
        weekdayFontSize = getIntProp("weekdayFontSize", weekdayFontSize);
        weekdayFontWeight = getFontWeight(getStringProp("weekdayFontWeight"));

        pastDayFontFamily = getStringProp("pastDayFontFamily");
        pastDayColor = getIntProp("pastDayColor", pastDayColor);
        pastDayFontWeight = getFontWeight(getStringProp("pastDayFontWeight"));
        pastDayFontSize = getIntProp("pastDayFontSize", pastDayFontSize);
        selectionColor = getIntProp("selectionColor", selectionColor);
        selectionEdgeColor = getIntProp("selectionEdgeColor", selectionEdgeColor);
    }


    @Override
    public String toString() {
        return mTheme.toString();
    }

    public CalendarTheme(ReadableMap rawTheme) {
        this.setRawTheme(rawTheme);
    }


    public ReadableMap getRawTheme() {
        return mTheme;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public int getDayBackgroundColor() {
        return dayBackgroundColor;
    }

    @Override
    public int getDayColor() {
        return dayColor;
    }

    @Override
    public int getDayFontSize() {
        return dayFontSize;
    }

    @Override
    public int getDayFontWeight() {
        return dayFontWeight;
    }

    @Override
    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    @Override
    public int getMonthTitleColor() {
        return monthTitleColor;
    }

    @Override
    public int getMonthTitleFontSize() {
        return monthTitleFontSize;
    }

    @Override
    public int getMonthTitleFontWeight() {
        return monthTitleFontWeight;
    }

    @Override
    public int getPastDayColor() {
        return pastDayColor;
    }

    @Override
    public int getPastDayFontWeight() {
        return pastDayFontWeight;
    }

    @Override
    public int getPastDayFontSize() {
        return pastDayFontSize;
    }

    @Override
    public int getSelectionEdgeColor() {
        return selectionEdgeColor;
    }

    @Override
    public int getSelectionColor() {
        return selectionColor;
    }

    @Override
    public int getWeekdayBackgroundColor() {
        return weekdayBackgroundColor;
    }

    @Override
    public int getWeekdayColor() {
        return weekdayColor;
    }

    @Override
    public int getWeekdayFontSize() {
        return weekdayFontSize;
    }

    public int getWeekdayFontWeight() {
        return weekdayFontWeight;
    }

    @Override
    @Nullable
    public String getDayFontFamily() {
        return dayFontFamily;
    }

    @Override
    @Nullable
    public String getMonthTitleFontFamily() {
        return monthTitleFontFamily;
    }

    @Override
    @Nullable
    public String getPastDayFontFamily() {
        return pastDayFontFamily;
    }

    @Override
    @Nullable
    public String getWeekdayFontFamily() {
        return weekdayFontFamily;
    }


}

