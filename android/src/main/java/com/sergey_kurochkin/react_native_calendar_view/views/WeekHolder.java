package com.sergey_kurochkin.react_native_calendar_view.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;

import java.util.ArrayList;
import java.util.List;

public class WeekHolder {
    DayConfig dayConfig;
    List<DayHolder> dayHolders;
    LinearLayout container;

    public WeekHolder(DayConfig dayConfig) {
        this.dayConfig = dayConfig;
        this.dayHolders = generateDayHolders();
    }

    List<DayHolder> generateDayHolders() {
        ArrayList<DayHolder> dayHolders = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayHolders.add(new DayHolder(dayConfig));
        }
        return dayHolders;
    }



    public View inflateWeekView(LinearLayout parent) {
        container = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        container.setLayoutParams(layoutParams);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setWeightSum(dayHolders.size());
        container.setClipChildren(true);

        for (int i = 0; i < dayHolders.size(); i++) {
            DayHolder holder = dayHolders.get(i);
            container.addView(holder.inflateView(container));
        }
        return container;
    }


    public void bindWeekView(List<ICalendarDay> days) {
        if (days.isEmpty()) {
            container.setVisibility(View.GONE);
            return;
        } else {
            container.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < days.size(); i++) {
            DayHolder holder = dayHolders.get(i);
            holder.bindView(days.get(i));
        }
    }

    public void reloadDay(ICalendarDay day) {
        for (int i = 0; i < dayHolders.size(); i++) {
            DayHolder holder = dayHolders.get(i);
            if (holder.day == day) {
                holder.reloadView();
                return;
            }
        }
    }

}
