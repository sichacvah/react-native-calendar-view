package com.sergey_kurochkin.react_native_calendar_view.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ViewContainer;

public class DayHolder {

    View dateView;
    FrameLayout containerView;
    ViewContainer viewContainer;
    DayConfig config;
    ICalendarDay day;

    public DayHolder(DayConfig config) {
        this.config = config;
    }

    public View inflateView(LinearLayout parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        dateView = inflater.inflate(config.dayViewRes, parent, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dateView.setLayoutParams(params);
        containerView = new FrameLayout(parent.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(config.width, config.height);
        containerView.setLayoutParams(layoutParams);
        containerView.addView(dateView);
        return containerView;
    }

    public void bindView(ICalendarDay currentDay) {
        day = currentDay;
        if (viewContainer == null) {
            viewContainer = config.viewBinder.create(dateView);
        }

        int dayHash = currentDay.date().hashCode();
        if (containerView.getId() != dayHash) {
            containerView.setId(dayHash);
        }

        if (currentDay != null) {
            if (containerView.getVisibility() != View.VISIBLE) {
                containerView.setVisibility(View.VISIBLE);
            }
            config.viewBinder.bind(viewContainer, currentDay);
        } else if (containerView.getVisibility() != View.GONE) {
            containerView.setVisibility(View.GONE);
        }
    }

    public void reloadView() {
        bindView(day);
    }
}
