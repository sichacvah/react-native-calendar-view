package com.sergey_kurochkin.react_native_calendar_view.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IMonthHeaderBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ViewContainer;

import java.util.ArrayList;
import java.util.List;


public class MonthViewHolder extends RecyclerView.ViewHolder {
    List<WeekHolder> weekHolders;
    View headerView;
    ViewContainer headerContainer;
    ICalendarMonth month;
    RecyclerView.Adapter adapter;
    ViewGroup rootLayout;
    DayConfig dayConfig;
    IMonthHeaderBinder headerBinder;

    LinearLayout bodyLayout;

    private List<WeekHolder> initWeekHolders(DayConfig dayConfig) {
        List<WeekHolder> weekHolders = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            weekHolders.add(new WeekHolder(dayConfig));
        }
        return weekHolders;
    }

    private void init() {
        for (int i = 0; i < weekHolders.size(); i++) {
            WeekHolder weekHolder = weekHolders.get(i);
            bodyLayout.addView(weekHolder.inflateWeekView(bodyLayout));
        }
    }


    public MonthViewHolder(CalendarAdapter adapter, ViewGroup rootLayout, DayConfig dayConfig, IMonthHeaderBinder headerBinder) {
        super(rootLayout);
        weekHolders = initWeekHolders(dayConfig);
        this.adapter = adapter;
        this.rootLayout = rootLayout;
        this.dayConfig = dayConfig;
        this.headerBinder = headerBinder;
        this.bodyLayout = rootLayout.findViewById(adapter.bodyViewId);
        this.headerView = rootLayout.findViewById(adapter.headerViewId);
        this.init();
    }

    void bindMonth(ICalendarMonth month) {
        this.month = month;
        if (headerView != null) {
            headerContainer = headerContainer != null ? headerContainer : headerBinder.create(headerView);
        }
        headerBinder.bind(headerContainer, month);
        for (int i = 0; i < weekHolders.size(); i++) {
            WeekHolder weekHolder = weekHolders.get(i);
            if (month.getCalendarDaysByWeeks().size() > i) {
                weekHolder.bindWeekView(month.getCalendarDaysByWeeks().get(i));
            } else {
                weekHolder.bindWeekView(new ArrayList<ICalendarDay>());
            }
        }
    }

    void reloadDay(ICalendarDay day) {
        for (int i = 0; i < weekHolders.size(); i++) {
            WeekHolder weekHolder = weekHolders.get(i);
            for (int j = 0; j < weekHolder.dayHolders.size(); j++) {
                DayHolder holder = weekHolder.dayHolders.get(j);
                if (day.equals(holder.day)) {
                    holder.reloadView();
                }
            }
        }
    }



}
