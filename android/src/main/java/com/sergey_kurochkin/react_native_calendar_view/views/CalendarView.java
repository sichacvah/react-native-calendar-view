package com.sergey_kurochkin.react_native_calendar_view.views;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcelable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sergey_kurochkin.react_native_calendar_view.enums.DayOwner;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IDayBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IMonthHeaderBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ViewContainer;
import com.sergey_kurochkin.react_native_calendar_view.models.CalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.models.MonthConfig;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;

import java.util.List;


public class CalendarView extends RecyclerView {
    static int DAY_SIZE_SQUARE = Integer.MIN_VALUE;

    private int dayViewResource = 0;
    private int headerViewResource = 0;
    private YearMonth startMonth;
    private YearMonth endMonth;
    private DayOfWeek firstDayOfWeek;
    private boolean mRequestedLayout = false;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CalendarView(Context context, int dayViewResource) {
        super(context);
        this.dayViewResource = 0;
    }

    public YearMonth getStartMonth() {
        return startMonth;
    }

    public YearMonth getEndMonth() {
        return endMonth;
    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        if (!mRequestedLayout) {
            mRequestedLayout = true;
            post(new Runnable() {
                @SuppressLint("WrongCall")
                @Override
                public void run() {
                    mRequestedLayout = false;
                    layout(getLeft(), getTop(), getRight(), getBottom());
                    onLayout(false, getLeft(), getTop(), getRight(), getBottom());
                }
            });
        }
    }

    public void reloadCalendar() {
        if (calendarAdapter != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    public void notifyDayChanged(ICalendarDay day) {
        if (calendarAdapter != null) {
            calendarAdapter.reloadDay(day);
        }
    }

    public void notifyDateChanged(LocalDate date) {
        notifyDayChanged(new CalendarDay(date, DayOwner.CURRENTMONTH));
    }

    public CalendarView(Context context, int dayViewResource, int headerViewResource) {
        super(context);
        this.dayViewResource = dayViewResource;
        this.headerViewResource = headerViewResource;
    }

    public void updateMonthRange(YearMonth startMonth, YearMonth endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        MonthConfig oldMonthConfig = calendarAdapter.getMonthConfig();
        if (firstDayOfWeek == null) {
            throw new IllegalStateException("`firstDayOfWeek` is not set. Have you called `setup()`?");
        }
        MonthConfig newConfig = new MonthConfig(
                firstDayOfWeek,
                startMonth,
                endMonth
        );

        calendarAdapter.setMonthConfig(newConfig);
//        DiffUtil
//                .calculateDiff(new MonthRangeDiffCallback(oldMonthConfig.getMonths(), newConfig.getMonths()), false)
//                .dispatchUpdatesTo(calendarAdapter);
    }


    public void setup(YearMonth startMonth, YearMonth endMonth, DayOfWeek firstDayOfWeek) {
        if (this.startMonth != null && this.endMonth != null && this.firstDayOfWeek != null) {
            this.firstDayOfWeek = firstDayOfWeek;
            updateMonthRange(startMonth, endMonth);
        } else {
            this.startMonth = startMonth;
            this.endMonth = endMonth;
            this.firstDayOfWeek = firstDayOfWeek;

            setClipToPadding(true);
            setClipChildren(true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(orientation);
            setLayoutManager(linearLayoutManager);
            calendarAdapter = new CalendarAdapter(
                    this,
                    new ViewConfig(this.dayViewResource, this.headerViewResource),
                    new MonthConfig(
                            this.firstDayOfWeek,
                            startMonth,
                            endMonth
                    )
            );
            if (monthHeaderBinder == null) {
                monthHeaderBinder = new IMonthHeaderBinder() {
                    @Override
                    public ViewContainer create(View view) {
                        return null;
                    }

                    @Override
                    public void bind(ViewContainer container, ICalendarMonth month) {
                        Log.d("BIND", "DEFAULT");
                    }
                };
            }

            setAdapter(calendarAdapter);
        }
    }

    private int orientation = VERTICAL;

    private boolean autoSize = true;
    private int dayHeight;

    private IDayBinder dayBinder;
    private IMonthHeaderBinder monthHeaderBinder;

    IMonthHeaderBinder getMonthHeaderBinder() {
        return monthHeaderBinder;
    }

    public void setMonthHeaderBinder(IMonthHeaderBinder monthHeaderBinder) {
        this.monthHeaderBinder = monthHeaderBinder;
    }

    IDayBinder getDayBinder() {
        return dayBinder;
    }

    public void setDayBinder(IDayBinder dayBinder) {
        this.dayBinder = dayBinder;
    }

    public int getDayWidth() {
        return dayWidth;
    }

    public int getDayHeight() {
        return dayHeight;
    }

    public void setDayHeight(int dayHeight) {
        this.dayHeight = dayHeight;
    }

    private int dayWidth;

    public void setDayWidth(int dayWidth) {
        this.dayWidth = dayWidth;
    }

    private CalendarAdapter calendarAdapter;

    void invalidateViewHolders() {
        LayoutManager layoutManager = getLayoutManager();
        if (calendarAdapter == null || layoutManager == null) return;
        Parcelable state = layoutManager.onSaveInstanceState();
        this.setAdapter(calendarAdapter);
        layoutManager.onRestoreInstanceState(state);
    }

    private class MonthRangeDiffCallback extends DiffUtil.Callback {
        List<ICalendarMonth> oldItems;
        List<ICalendarMonth> newItems;
        public MonthRangeDiffCallback(List<ICalendarMonth> oldItems, List<ICalendarMonth> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getNewListSize() {
            return oldItems.size();
        }

        @Override
        public int getOldListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return areItemsTheSame(i, i1);
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (autoSize && !isInEditMode()) {
            int widthMode = MeasureSpec.getMode(widthSpec);
            int widthSize = MeasureSpec.getSize(widthSpec);
            int heightMode = MeasureSpec.getMode(heightSpec);

            if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
                throw new UnsupportedOperationException("Cannot calculate the values for day Width/Height with the current configuration.");
            }

            int squareSize = (int)(widthSize / 7f + 0.5);

            if (dayWidth != squareSize || dayHeight != squareSize) {
                dayWidth = squareSize;
                dayHeight = squareSize;
                invalidateViewHolders();
            }
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
