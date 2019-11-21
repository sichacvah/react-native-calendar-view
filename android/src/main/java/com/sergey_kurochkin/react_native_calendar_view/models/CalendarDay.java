package com.sergey_kurochkin.react_native_calendar_view.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergey_kurochkin.react_native_calendar_view.enums.DayOwner;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;

import org.threeten.bp.LocalDate;

public class CalendarDay implements ICalendarDay {
    private DayOwner dayOwner;
    private LocalDate date;

    public CalendarDay(LocalDate date, DayOwner dayOwner) {
        this.date = date;
        this.dayOwner = dayOwner;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (obj instanceof CalendarDay) {
            return date.equals(((CalendarDay) obj).date()) && dayOwner == ((CalendarDay) obj).getDayOwner();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * (date.hashCode() + dayOwner.hashCode());
    }

    public DayOwner getDayOwner() {
        return dayOwner;
    }

    public LocalDate date() {
        return date;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("PCCalendarDay { date = %s, owner = %s } ", date(), dayOwner);
    }
}