package com.sergey_kurochkin.react_native_calendar_view.utils;



import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class CalendarDateUtils {
    static SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEEEEE");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat headerDateFormat = new SimpleDateFormat("MMM yyyy");
    static Calendar calendar = Calendar.getInstance();

    static Date parse(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (Throwable error) {
            return null;
        }

    }

    static boolean isBetweenRange(Date date, Date from, Date to) {
        return date.getTime() > from.getTime() && date.getTime() < to.getTime();
    }

    static String format(Date date) {
        calendar.setTime(date);
        return dateFormat.format(date);
    }

    static String formatHeader(Date date) {
        calendar.setTime(date);
        return headerDateFormat.format(date);
    }

    static boolean isSameMonth(Date date, Date month) {
        return getYear(date) == getYear(month) && getMonth(date) == getMonth(month);
    }

    static boolean isPast(Date date) {
        Date now = new Date();
        return !equal(date, now) && (date.getTime() < now.getTime());
    }

    static int getYear(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    static int getMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    static int getDay(Date date) {
        calendar.setTime(date);
        return  calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return new Date(cal.getTime().getTime());
    }
    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return new Date(cal.getTime().getTime());
    }

    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return new Date(cal.getTime().getTime());
    }

    static boolean leq(Date date1, Date date2) {
        return equal(date1, date2) || after(date2, date1);
    }

    static boolean isBefore(YearMonth month1, YearMonth month2) {
        return month1.compareTo(month2) < 0;
    }

    static  boolean leq(YearMonth month1, YearMonth month2) {
        return isBefore(month1, month2) || month1.equals(month2);
    }

    static boolean equal(Date date1, Date date2) {
        return getYear(date1) == getYear(date2) && getMonth(date1) == getMonth(date2) && getDay(date1) == getDay(date2);
    }

    static boolean after(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    static ArrayList<Date> fromTo(Date from, Date to) {
        ArrayList<Date> dates = new ArrayList();

        for (Date date = from; after(to, date) || equal(date, to); date = addDay(date, 1)) {
            dates.add(date);
        }
        return dates;
    }

//    static ArrayList<CalendarViewItem> fromToCalendarItems(Date from, Date to, Date month) {
//        ArrayList<CalendarViewItem> items = new ArrayList<>();
//
//        for (Date date = from; after(to, date) || equal(date, to); date = addDay(date, 1)) {
//            CalendarViewCell cell = new CalendarViewCell(date);
//            cell.setMonth(month);
//            items.add(cell);
//        }
//        return items;
//    }

    static int getDaysOfMonth(Date date) {
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    static Date dateFromYearMonthDay(int year, int month, int day) {
        calendar.set(year, month, day);
        return new Date(calendar.getTime().getTime());
    }

//    static ArrayList<CalendarViewItem> monthItems(Date date) {
//        int year = getYear(date);
//        int month = getMonth(date);
//        int days = getDaysOfMonth(date);
//        Date firstDay = dateFromYearMonthDay(year, month, 1);
//        Date lastDay = dateFromYearMonthDay(year, month, days);
//        return fromToCalendarItems(firstDay, lastDay, date);
//    }

    static ArrayList<Date> month(Date date) {
        int year = getYear(date);
        int month = getMonth(date);
        int days = getDaysOfMonth(date);
        Date firstDay = dateFromYearMonthDay(year, month, 1);
        Date lastDay = dateFromYearMonthDay(year, month, days);
        return fromTo(firstDay, lastDay);
    }

    static int getDayOfWeek(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    static Date setDayOfWeek(Date date, int weekday) {
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, weekday);
        return calendar.getTime();
    }

    static String formatWeekDay(Date date) {
        calendar.setTime(date);
        return weekdayFormat.format(date);
    }


    static ArrayList<Date> page(Date date) {
        return page(date, calendar.getFirstDayOfWeek());
    }

    static ArrayList<Date> getNextPage(Date date) {
        return getNextPage(date, 4);
    }

    static ArrayList<Date> getNextPage(Date date, int pageSize) {
        ArrayList<Date> months = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            months.add(addMonth(date, i));
        }
        return months;
    }

    static int getFirstDayOfWeek() {
        return calendar.getFirstDayOfWeek();
    }

    static boolean isLastDayOfWeek(int weekday, int firstDayOfWeek) {
        return ((weekday % 7) + 1) == firstDayOfWeek;
    }


    static ArrayList<Date> page(Date date, int firstDayOfWeek) {
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Date> days = month(date);

        ArrayList<Date> before = new ArrayList<>();
        ArrayList<Date> after = new ArrayList<>();
        int _fdow = ((7 + firstDayOfWeek - 1) % 7);
        int fdow = (_fdow == 0 ? 7 : _fdow);
        int ldow = (fdow + 6) % 7;

        Date from = days.get(0);
        int fromDay = getDayOfWeek(from) - 1;
        from = fromDay == fdow ? from : addDay(date, -(fromDay + 7 - fdow) % 7);

        Date to = days.get(days.size() - 1);
        int toDay = getDayOfWeek(to) - 1;
        to = toDay != ldow ? addDay(to, (ldow + 7 - toDay) % 7) : to;

        before = fromTo(from, days.get(0));
        after  = fromTo(days.get(days.size() - 1), to);

        dates.addAll(before);
        dates.addAll(days);
        dates.addAll(after);
        return dates;
    }


}
