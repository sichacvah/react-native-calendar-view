package com.sergey_kurochkin.react_native_calendar_view.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;

import org.threeten.bp.YearMonth;

import java.io.Serializable;
import java.util.List;



public class CalendarMonth implements ICalendarMonth, Comparable<CalendarMonth>, Serializable {
    private YearMonth month;
    private List<List<ICalendarDay>> mDaysByWeeks;
    int indexInSameMonth;

    public CalendarMonth(YearMonth month, List<List<ICalendarDay>> daysByWeeks, int indexInSameMonth) {
        this.month = month;
        this.mDaysByWeeks = daysByWeeks;
        this.indexInSameMonth = indexInSameMonth;
    }

    @Override
    public YearMonth getMonth() {
        return month;
    }

    @Override
    public List<List<ICalendarDay>> getCalendarDaysByWeeks() {
        return mDaysByWeeks;
    }

    @Override
    public int hashCode() {

        List<ICalendarDay> lastWeek = mDaysByWeeks.get(mDaysByWeeks.size() - 1);
        return 31 * month.hashCode() +
                mDaysByWeeks.get(0).get(0).hashCode() +
                lastWeek.get(lastWeek.size() - 1).hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        if (!(obj instanceof CalendarMonth)) {
            return false;
        }

        List<ICalendarDay> lastWeek = mDaysByWeeks.get(mDaysByWeeks.size() - 1);
        List<ICalendarDay> otherLastWeek = ((CalendarMonth) obj).getCalendarDaysByWeeks().get(((CalendarMonth) obj).getCalendarDaysByWeeks().size() - 1);

        return month == ((CalendarMonth) obj).getMonth() &&
                mDaysByWeeks.get(0).get(0).equals(otherLastWeek.get(0)) &&
                lastWeek.get(lastWeek.size() - 1).equals(otherLastWeek.get(otherLastWeek.size() - 1));

    }

    public int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    @Override
    public int compareTo(@NonNull CalendarMonth other) {
        int monthResult = month.compareTo(other.getMonth());
        if (monthResult == 0) {
            return compare(indexInSameMonth, other.indexInSameMonth);
        }
        return monthResult;
    }
}
