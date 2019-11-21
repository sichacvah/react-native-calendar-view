package com.sergey_kurochkin.react_native_calendar_view.interfaces;

import org.threeten.bp.LocalDate;
import com.sergey_kurochkin.react_native_calendar_view.enums.DayOwner;

public interface ICalendarDay {
    LocalDate date();
    DayOwner getDayOwner();
}
