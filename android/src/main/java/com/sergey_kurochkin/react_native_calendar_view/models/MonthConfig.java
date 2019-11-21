package com.sergey_kurochkin.react_native_calendar_view.models;

import android.util.Log;

import com.sergey_kurochkin.react_native_calendar_view.enums.DayOwner;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Period;
import org.threeten.bp.YearMonth;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.WeekFields;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthConfig {
    public DayOfWeek firstDayOfWeek;
    public YearMonth startMonth;
    public YearMonth endMonth;
    public List<ICalendarMonth> months;


    public MonthConfig(DayOfWeek firstDayOfWeek, YearMonth startMonth, YearMonth endMonth) {
        this.firstDayOfWeek = firstDayOfWeek;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.months = getMonths();
    }

    private List<ICalendarDay> monthDays(YearMonth month) {
        List<ICalendarDay> days = new ArrayList<>();
        for (int i = 1; i <= month.lengthOfMonth(); i++) {
            days.add(new CalendarDay(LocalDate.of(month.getYear(), month.getMonth(), i), DayOwner.CURRENTMONTH));
        }
        return days;
    }

    private ICalendarMonth generateMonth(YearMonth month, int indexInSameMonth) {
        List<ICalendarDay> thisMonthDays = monthDays(month);
        List<List<ICalendarDay>> weekDaysGroup= new ArrayList<>();

        TemporalField weekOfMonthField = WeekFields.of(firstDayOfWeek, 1).weekOfMonth();

        for (int i = 0; i < thisMonthDays.size(); i++) {
            ICalendarDay day = thisMonthDays.get(i);
            int weekNumber = day.date().get(weekOfMonthField);
            if (weekDaysGroup.size() < weekNumber) {
                weekDaysGroup.add(new ArrayList<ICalendarDay>());
            }
            weekDaysGroup.get(weekNumber - 1).add(thisMonthDays.get(i));
        }

        List<ICalendarDay> firstWeek = weekDaysGroup.get(0);
        List<ICalendarDay> lastWeek = weekDaysGroup.get(weekDaysGroup.size() - 1);

        if (firstWeek.size() < 7) {
            int firstWeekSize = firstWeek.size();
            YearMonth prevMonth = month.minusMonths(1);
            int lengthOfMonth = prevMonth.lengthOfMonth();
            for (int j = 0; j < 7 - firstWeekSize; j++) {
                int dayOfMonth = lengthOfMonth - j;
                firstWeek.add(
                        0,
                        new CalendarDay(
                                LocalDate.of(prevMonth.getYear(), prevMonth.getMonth(), dayOfMonth),
                                DayOwner.PREVMONTH
                        )
                );
            }
        }

        if (lastWeek.size() < 7) {
            int lastWeekSize = lastWeek.size();
            YearMonth nextMonth = month.plusMonths(1);
            for (int k = 1; k <= 7 - lastWeekSize; k++) {
                lastWeek.add(new CalendarDay(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), k), DayOwner.NEXTMONTH));
            }
        }

        return new CalendarMonth(month, weekDaysGroup, indexInSameMonth);
    }

    public List<ICalendarMonth> getMonths() {
        YearMonth currentMonth = startMonth;
        List<ICalendarMonth> months = new ArrayList<ICalendarMonth>();
        while (currentMonth.equals(endMonth) || currentMonth.isBefore(endMonth)) {
            ICalendarMonth month = generateMonth(currentMonth, months.size());
            months.add(month);
            currentMonth = currentMonth.plusMonths(1);
        }

        return months;
    }
}
