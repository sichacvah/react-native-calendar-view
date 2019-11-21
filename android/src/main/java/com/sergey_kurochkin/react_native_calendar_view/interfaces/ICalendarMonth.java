package com.sergey_kurochkin.react_native_calendar_view.interfaces;

import org.threeten.bp.YearMonth;

import java.util.List;

public interface ICalendarMonth {
    List<List<ICalendarDay>> getCalendarDaysByWeeks();
    YearMonth getMonth();
}

