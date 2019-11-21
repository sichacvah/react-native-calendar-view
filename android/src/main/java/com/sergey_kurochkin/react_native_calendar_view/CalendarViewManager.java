package com.sergey_kurochkin.react_native_calendar_view;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.sergey_kurochkin.react_native_calendar_view.utils.Events;
import com.sergey_kurochkin.react_native_calendar_view.views.CalendarTheme;

import org.threeten.bp.LocalDate;

import java.util.Map;

import javax.annotation.Nonnull;


public class CalendarViewManager extends SimpleViewManager<CalendarViewComponent> {
    public static final String REACT_CLASS = "CalendarView";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected CalendarViewComponent createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new CalendarViewComponent(reactContext);
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("onAnimatedEvent", MapBuilder.of("registrationName", "onAnimatedEvent"))
                .put("onDatePress",
                        MapBuilder.of("phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onDatePress")))
                .build();
    }

    @ReactProp(name = "theme")
    public void setTheme(CalendarViewComponent view, ReadableMap theme) {
        view.setTheme(new CalendarTheme(theme));
    }

    @ReactProp(name = "markedDates")
    public void setMarkedDates(CalendarViewComponent view, ReadableMap markedDates) {
        view.setMarkedDates(markedDates);
    }

    @ReactProp(name = "from")
    public void setFrom(CalendarViewComponent view,@Nullable String from) {
        if (from == null) {
            view.setFrom(null);
        } else {
            view.setFrom(LocalDate.parse(from));
        }

    }

    @ReactProp(name = "to")
    public void setTo(CalendarViewComponent view, @Nullable  String to) {
        if (to == null) {
            view.setTo(null);
        } else {
            view.setTo(LocalDate.parse(to));
        }
    }

    @Override
    protected void addEventEmitters(@Nonnull ThemedReactContext reactContext, @Nonnull CalendarViewComponent view) {
        view.setEventListener(new EventListener(view, reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher()));
    }

    protected class EventListener implements CalendarViewComponent.EventListener {
        private final CalendarViewComponent component;
        private final EventDispatcher dispatcher;

        public EventListener(CalendarViewComponent component, EventDispatcher dispatcher) {
            this.component = component;
            this.dispatcher = dispatcher;
        }

        @Override
        public void onAnimatedEvent(float x, float y) {
            dispatcher.dispatchEvent(new Events.CalenderOnAnimatedEvent(component.getId(), x, y));
        }

        @Override
        public void onClickEvent(LocalDate date) {
            dispatcher.dispatchEvent(new Events.CalendarOnPressDate(component.getId(), date));
        }
    }
}
