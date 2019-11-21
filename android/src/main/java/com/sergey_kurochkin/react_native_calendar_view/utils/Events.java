package com.sergey_kurochkin.react_native_calendar_view.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class Events {
    public static class CalenderOnAnimatedEvent extends Event<CalenderOnAnimatedEvent> {
        WritableMap eventData;

        public CalenderOnAnimatedEvent(int viewTag, float x, float y) {
            super(viewTag);
            eventData = Arguments.createMap();
            eventData.putDouble("x", PixelUtil.toDIPFromPixel(x));
            eventData.putDouble("y", PixelUtil.toDIPFromPixel(y));
        }

        @Override
        public String getEventName() {
            return "onAnimatedEvent";
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            rctEventEmitter.receiveEvent(getViewTag(), getEventName(), eventData);
        }
    }

    public static class CalendarOnPressDate extends Event<CalendarOnPressDate> {
        WritableMap eventData;

        public CalendarOnPressDate(int viewTag, LocalDate date) {
            super(viewTag);

            eventData = Arguments.createMap();
            eventData.putString("month", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
            eventData.putString("date", date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        @Override
        public String getEventName() {
            return "onDatePress";
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            rctEventEmitter.receiveEvent(getViewTag(), getEventName(), eventData);
        }
    }
}

