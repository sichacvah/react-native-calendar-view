package com.sergey_kurochkin.react_native_calendar_view.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;
import com.sergey_kurochkin.react_native_calendar_view.models.MonthConfig;
import com.sergey_kurochkin.react_native_calendar_view.utils.CalendarUtils;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<MonthViewHolder> {


    CalendarView calView;
    ViewConfig viewConfig;
    private MonthConfig monthConfig;

    private boolean isAttached() {
        return calView.getAdapter() == this;
    }

    MonthConfig getMonthConfig() {
        return monthConfig;
    }

    public void setMonthConfig(MonthConfig monthConfig) {
        this.monthConfig = monthConfig;
    }

    int bodyViewId = CalendarUtils.generateViewId();
    int rootViewId = CalendarUtils.generateViewId();
    ICalendarMonth visibleMonth;
    int headerViewId = CalendarUtils.generateViewId();

    public CalendarAdapter(CalendarView calView, ViewConfig viewConfig, MonthConfig monthConfig) {
        this.calView = calView;
        this.viewConfig = viewConfig;
        this.monthConfig = monthConfig;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private void setupRootLayout(LinearLayout rootLayout) {
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setId(rootViewId);
        rootLayout.setClipChildren(true);
    }

    private void setupBodyLayout(LinearLayout monthBodyLayout) {
        LinearLayout.LayoutParams monthBodyLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        monthBodyLayout.setLayoutParams(monthBodyLayoutParams);
        monthBodyLayout.setOrientation(LinearLayout.VERTICAL);
        monthBodyLayout.setId(bodyViewId);
        monthBodyLayout.setClipChildren(true);
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LinearLayout rootLayout = new LinearLayout(context);
        setupRootLayout(rootLayout);

        if (viewConfig.monthHeaderRes != 0) {
            View monthHeaderView = LayoutInflater.from(context).inflate(viewConfig.monthHeaderRes, rootLayout, false);
            if (monthHeaderView.getId() == View.NO_ID) {
                monthHeaderView.setId(headerViewId);
            } else {
                headerViewId = monthHeaderView.getId();
            }
            rootLayout.addView(monthHeaderView);
        }

        LinearLayout monthBodyLayout = new LinearLayout(context);
        setupBodyLayout(monthBodyLayout);
        rootLayout.addView(monthBodyLayout);

        DayConfig dayConfig = new DayConfig(calView.getDayWidth(), calView.getDayHeight(), viewConfig.dayViewRes, calView.getDayBinder());
        return new MonthViewHolder(this, rootLayout, dayConfig, calView.getMonthHeaderBinder());
    }

    private int findFirstVisibleMonthPosition() {
        return findVisibleMonthPosition(true);
    }

    private LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) calView.getLayoutManager();
    }


    private int findVisibleMonthPosition(boolean isFirst) {
        int visibleItemPos = isFirst ? getLayoutManager().findFirstVisibleItemPosition() : getLayoutManager().findLastVisibleItemPosition();

        return visibleItemPos;
    }


    private ICalendarMonth getItem(int i) {
        return monthConfig.months.get(i);
    }

    @Override
    public int getItemCount() {
        return monthConfig.months.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int i) {
        holder.bindMonth(getItem(i));
    }

    private static int NO_INDEX = -1;

    private int getAdapterPosition(ICalendarDay day) {
        for (int mi = 0; mi < monthConfig.months.size(); mi++) {
            ICalendarMonth month = monthConfig.months.get(mi);
            for (int wi = 0; wi < month.getCalendarDaysByWeeks().size(); wi++) {
                List<ICalendarDay> days = month.getCalendarDaysByWeeks().get(wi);
                for (int di = 0; di < days.size(); di++) {
                    ICalendarDay d = days.get(di);
                    if (d.equals(day)) {
                        return mi;
                    }
                }
            }
        }
        return NO_INDEX;
    }

    public void reloadDay(ICalendarDay day) {
        int position = getAdapterPosition(day);
        Log.d("RELOAD_DAY", String.valueOf(position));
        if (position != NO_INDEX) {
            notifyItemChanged(position, day);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position, @NonNull List<Object> payloads) {
        Log.d("PAYLOADS", String.valueOf(payloads));
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {

            for (int i = 0; i < payloads.size(); i++) {
                ICalendarDay payload = (ICalendarDay)payloads.get(i);
                Log.d("PAYLOAD", String.valueOf(payload.date()));
                holder.reloadDay(payload);
            }
        }

    }
}
