package com.sergey_kurochkin.react_native_calendar_view;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ThemedReactContext;
import com.sergey_kurochkin.react_native_calendar_view.enums.DayOwner;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarDay;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarMonth;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ICalendarTheme;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IDayBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IMarkedDate;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IMonthHeaderBinder;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.ViewContainer;
import com.sergey_kurochkin.react_native_calendar_view.models.Borders;
import com.sergey_kurochkin.react_native_calendar_view.utils.CalendarUtils;
import com.sergey_kurochkin.react_native_calendar_view.utils.EndlessRecyclerViewScrollListener;
import com.sergey_kurochkin.react_native_calendar_view.views.CalendarTheme;
import com.sergey_kurochkin.react_native_calendar_view.views.CalendarView;
import com.sergey_kurochkin.react_native_calendar_view.views.CalendarViewHelpers;
import com.sergey_kurochkin.react_native_calendar_view.R;
import com.sergey_kurochkin.react_native_calendar_view.views.MarkedDate;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.temporal.WeekFields;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarViewComponent extends RelativeLayout {
    private class DayViewContainer extends ViewContainer {
        public TextView textView;
        public View startingView;
        public View endingView;
        public ICalendarDay day;

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListener == null) {
                    return;
                }
                if (day.getDayOwner() == DayOwner.CURRENTMONTH && (day.date() == LocalDate.now() || day.date().isAfter(LocalDate.now()))) {
                    eventListener.onClickEvent(day.date());
                }
            }
        };

        public DayViewContainer(View view) {
            super(view);

            textView = view.findViewById(R.id.calendar_day_text);
            startingView = view.findViewById(R.id.calendar_staring_day);
            endingView = view.findViewById(R.id.calendar_ending_day);

            view.setOnClickListener(onClickListener);
            startingView.setOnClickListener(onClickListener);
            endingView.setOnClickListener(onClickListener);
        }
    }

    ICalendarTheme theme;
    Map<String, IMarkedDate> markedDates = new HashMap<>();
    private LocalDate from;
    private LocalDate to;
    private EventListener eventListener;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public boolean inRange(LocalDate date) {
        if (from == null || to == null) {
            return false;
        }
        return date.isAfter(from) && date.isBefore(to);
    }


    public void setFrom(@Nullable LocalDate from) {
        if (from == null && this.from != null) {
            this.from = null;
            calendarView.reloadCalendar();
            return;
        }
        if (this.from == null || this.from.hashCode() != from.hashCode() || !from.equals(this.from)) {
            this.from = from;
            calendarView.reloadCalendar();
        }
    }

    public void setTo(@Nullable  LocalDate to) {
        if (to == null && this.to != null) {
            this.to = null;
            calendarView.reloadCalendar();
            return;
        }
        if (this.to == null || this.to.hashCode() != to.hashCode() || !to.equals(this.to)) {
            this.to = to;
            calendarView.reloadCalendar();
        }
    }

    public void setTheme(CalendarTheme theme) {
        this.theme = theme;
        calendarView.reloadCalendar();
    }

    public void setMarkedDates(ReadableMap markedDates) {
        this.markedDates.clear();
        ReadableMapKeySetIterator it = markedDates.keySetIterator();
        while (it.hasNextKey()) {
            String key = it.nextKey();
            if (markedDates.hasKey(key) && markedDates.getType(key) == ReadableType.Map) {
                ReadableMap rawMarkedDate = markedDates.getMap(key);
                this.markedDates.put(key, new MarkedDate(rawMarkedDate));
            }
        }
        calendarView.reloadCalendar();
    }

    private boolean isLastWeek(LocalDate date) {
        LocalDate tDate = date.with(defaultFirstDayOfWeek());
        LocalDate nextWeekDate = tDate.plusDays(7);
        return nextWeekDate.getYear() > date.getYear() || nextWeekDate.getMonth().getValue() > date.getMonth().getValue();
    }

    IDayBinder<DayViewContainer> dayBinder = new IDayBinder<DayViewContainer>() {
        @Override
        public DayViewContainer create(View view) {
            return new DayViewContainer(view);
        }

        private Borders getBorders(ICalendarDay day) {
            Borders borders = new Borders();
            LocalDate date = day.date();
            if (day.getDayOwner() == DayOwner.CURRENTMONTH && (date.isAfter(LocalDate.now()) || date == LocalDate.now())) {
                borders.left = 1;
                borders.top  = 1;

                if (isLastDayOfWeek(day.date())) {
                    borders.right = 1;
                }
            }

            if (day.getDayOwner() == DayOwner.CURRENTMONTH) {
                if (isLastWeek(date) && !date.isBefore(LocalDate.now()) ) {
                    borders.bottom = 1;
                }
            }

            if (day.getDayOwner() == DayOwner.NEXTMONTH) {
                if (!date.isBefore(LocalDate.now()) && date.minusDays(1).getMonth().getValue() != date.getMonth().getValue()) {
                    borders.left = 1;
                }
                if (!date.isBefore(LocalDate.now())) {
                    borders.top = 1;
                }
            }

            return borders;
        }

        boolean isLastDayOfWeek(LocalDate date) {
            return date.plusDays(1).getDayOfWeek() == defaultFirstDayOfWeek();
        }


        private Drawable getCornerBackgroundLayer(int color, Borders borders, RoundRectShape roundRectShape) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(color);
            Drawable rippleDrawable = CalendarViewHelpers.getSelectableDrawableFor(color, shapeDrawable);
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{ rippleDrawable });

            layerDrawable.setLayerInset(0, borders.left, borders.top, borders.right, borders.bottom);
            return layerDrawable;
        }

        private Drawable getStartingDayBackgroundLayer(int color, Borders borders) {
            float roundRadius = CalendarUtils.convertPixelsToDp(4);
            RoundRectShape roundRectShape = new RoundRectShape(new float[] {
                    0, 0, roundRadius, roundRadius,
                    roundRadius, roundRadius, 0, 0

            }, null, null);
            return getCornerBackgroundLayer(color, borders, roundRectShape);
        }


        private Drawable getEndingDayBackgroundLayer(int color, Borders borders) {
            float roundRadius = CalendarUtils.convertPixelsToDp(4);
            RoundRectShape roundRectShape = new RoundRectShape(new float[] {
                    roundRadius, roundRadius, 0, 0,
                    0, 0, roundRadius, roundRadius

            }, null, null);
            return getCornerBackgroundLayer(color, borders, roundRectShape);
        }


        @Override
        public void bind(DayViewContainer container, ICalendarDay day) {
            container.day = day;
            Borders borders = getBorders(day);

            String title = String.valueOf(day.date().getDayOfMonth());

            container.view.setBackgroundColor(Color.TRANSPARENT);
            container.startingView.setBackgroundColor(Color.TRANSPARENT);
            container.endingView.setBackgroundColor(Color.TRANSPARENT);
            container.startingView.setVisibility(INVISIBLE);
            container.endingView.setVisibility(INVISIBLE);

            if (theme != null) {
                container.view.setBackgroundColor(theme.getDayBackgroundColor());
                container.view.setBackground(CalendarUtils.getBackgroundLayer(theme.getDayBackgroundColor(), theme.getBorderColor(), borders));
                container.textView.setTextColor(theme.getDayColor());
                container.textView.setTextSize(theme.getDayFontSize());
                container.textView.setTypeface(Typeface.create(theme.getDayFontFamily(), theme.getDayFontWeight()));
                if (day.date().isBefore(LocalDate.now()) || day.getDayOwner() != DayOwner.CURRENTMONTH) {
                    container.textView.setTextColor(theme.getPastDayColor());
                    container.textView.setTypeface(Typeface.create(theme.getPastDayFontFamily(), theme.getPastDayFontWeight()));
                    container.textView.setTextSize(theme.getPastDayFontSize());
                }
            }
            IMarkedDate markedDate = markedDates.get(day.date().format(DateTimeFormatter.ISO_LOCAL_DATE));

            if (markedDate != null && day.getDayOwner() == DayOwner.CURRENTMONTH && !day.date().isBefore(LocalDate.now())) {
                if (markedDate.getTextColor() != Color.TRANSPARENT) {
                    container.textView.setTextColor(markedDate.getTextColor());
                }
                if (markedDate.getColor() != Color.TRANSPARENT) {
                    container.view.setBackground(CalendarUtils.getBackgroundLayer(markedDate.getColor(), theme.getBorderColor(), borders));
                }
                if (markedDate.getStartingColor() != Color.TRANSPARENT) {
                    container.endingView.setVisibility(VISIBLE);
                    container.endingView.setBackground(getEndingDayBackgroundLayer(markedDate.getStartingColor(), borders));
                }
                if (markedDate.getEndingColor() != Color.TRANSPARENT) {
                    container.startingView.setBackground(getStartingDayBackgroundLayer(markedDate.getEndingColor(), borders));
                    container.startingView.setVisibility(VISIBLE);
                }
            }
            if (day.getDayOwner() == DayOwner.CURRENTMONTH) {
                if (from != null && day.date().isEqual(from)) {
                    container.endingView.setVisibility(VISIBLE);
                    if (theme != null) {
                        container.endingView.setBackground(getEndingDayBackgroundLayer(theme.getSelectionEdgeColor(), borders));
                    } else {
                        container.endingView.setBackground(getEndingDayBackgroundLayer(Color.TRANSPARENT, borders));
                    }
                }

                if (to != null && day.date().isEqual(to)) {
                    container.startingView.setVisibility(VISIBLE);
                    if (theme != null) {
                        container.startingView.setBackground(getStartingDayBackgroundLayer(theme.getSelectionEdgeColor(), borders));
                    } else {
                        container.startingView.setBackground(getStartingDayBackgroundLayer(Color.TRANSPARENT, borders));
                    }
                }

                if (inRange(day.date())) {
                    container.startingView.setVisibility(INVISIBLE);
                    container.endingView.setVisibility(INVISIBLE);
                    container.view.setBackground(CalendarUtils.getBackgroundLayer(theme == null ? Color.TRANSPARENT : theme.getSelectionColor(), theme == null ? Color.TRANSPARENT : theme.getBorderColor(), borders));
                }
            }


            container.textView.setText(title);
        }
    };

    CalendarView calendarView;

    class MonthViewContainer extends ViewContainer {
        TextView calendarHeaderText;
        LinearLayout calendarHeaderWeekdays;

        public MonthViewContainer(View view) {
            super(view);

            calendarHeaderText = view.findViewById(R.id.calendar_header_text);
            calendarHeaderWeekdays = view.findViewById(R.id.calendar_header_weekdays);
        }
    }


    IMonthHeaderBinder<MonthViewContainer> monthHeaderBinder = new IMonthHeaderBinder<MonthViewContainer>() {
        @Override
        public MonthViewContainer create(View view) {
            return new MonthViewContainer(view);
        }

        @Override
        public void bind(MonthViewContainer container, ICalendarMonth month) {
            String headerText = month.getMonth().format(DateTimeFormatter.ofPattern("MMM yyyy"));
            container.calendarHeaderText.setText(headerText);

            if (theme != null) {
                container.calendarHeaderText.setTypeface(Typeface.create(theme.getMonthTitleFontFamily(), theme.getMonthTitleFontWeight()));
                container.calendarHeaderText.setTextSize(theme.getMonthTitleFontSize());
                container.calendarHeaderText.setTextColor(theme.getMonthTitleColor());
//                container.calendarHeaderText.setTextAlignment(CENTER_HORIZONTAL);
                container.calendarHeaderText.setGravity(CENTER_HORIZONTAL);
            }


            for (int i = 0; i < 7; i++) {
                DayOfWeek firstDayOfWeek = defaultFirstDayOfWeek();

                View child = container.calendarHeaderWeekdays.getChildAt(i);
                if (child instanceof TextView) {
                    TextView weekDayText = (TextView)child;
                    String dayText = firstDayOfWeek.plus(i).getDisplayName(TextStyle.SHORT, Locale.getDefault());
                    weekDayText.setText(dayText);
                    if (theme != null) {
                        weekDayText.setTextColor(theme.getWeekdayColor());
                        weekDayText.setTypeface(Typeface.create(theme.getWeekdayFontFamily(), theme.getWeekdayFontWeight()));
                        weekDayText.setTextSize(theme.getWeekdayFontSize());
                    }
                }
            }
        }
    };

    static DayOfWeek defaultFirstDayOfWeek() {
        return WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    }

    private PointF position = new PointF(0, 0);


    public CalendarViewComponent(ThemedReactContext context) {
        super(context);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setLayoutParams(params);


        calendarView = new CalendarView(context, R.layout.calendar_view_item, R.layout.calendar_view_section_header);
        calendarView.setDayBinder(dayBinder);
        calendarView.setMonthHeaderBinder(monthHeaderBinder);


        calendarView.setup(YearMonth.now(), YearMonth.now().plusMonths(6), defaultFirstDayOfWeek());

        addView(calendarView);
        calendarView.setClipToPadding(false);
        setBackgroundColor(Color.TRANSPARENT);
        calendarView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                position.set(position.x + dx, position.y + dy);
                if (eventListener != null) {
                    eventListener.onAnimatedEvent(position.x, position.y);
                }
            }
        });
        calendarView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager)calendarView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                calendarView.setup(calendarView.getStartMonth(), calendarView.getEndMonth().plusMonths(4), defaultFirstDayOfWeek());
//                calendarView.updateMonthRange(calendarView.getStartMonth(), calendarView.getEndMonth().plusMonths(4));
            }
        });
    }

    public interface EventListener {
        void onAnimatedEvent(float x, float y);
        void onClickEvent(LocalDate date);
    }
}
