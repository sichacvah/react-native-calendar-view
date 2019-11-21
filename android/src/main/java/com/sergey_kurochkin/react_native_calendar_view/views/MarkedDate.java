package com.sergey_kurochkin.react_native_calendar_view.views;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.sergey_kurochkin.react_native_calendar_view.interfaces.IMarkedDate;

public class MarkedDate implements IMarkedDate {
    private int startingColor = Color.TRANSPARENT;
    private int endingColor = Color.TRANSPARENT;
    private int color = Color.TRANSPARENT;
    private int textColor = Color.TRANSPARENT;

    public MarkedDate(int color, int textColor) {
        this.color = color;
        this.textColor = textColor;
    }

    private int getColor(String key, ReadableMap raw) {
        if (raw.hasKey(key) && raw.getType(key) == ReadableType.Number) {
            return raw.getInt(key);
        }
        return Color.TRANSPARENT;
    }

    @Nullable
    private ReadableMap getReadableMap(String key, ReadableMap raw) {
        if (raw.hasKey(key) && raw.getType(key) == ReadableType.Map) {
            return raw.getMap(key);
        }
        return null;
    }

    private int getEdgeColor(String key, ReadableMap raw) {
        ReadableMap edgeMap = getReadableMap(key, raw);
        if (edgeMap == null) {
            return Color.TRANSPARENT;
        }
        return getColor("color", edgeMap);
    }

    public MarkedDate(ReadableMap raw) {
        color = getColor("color", raw);
        textColor = getColor("textColor", raw);
        startingColor = getEdgeColor("starting", raw);
        endingColor = getEdgeColor("ending", raw);
    }

    static MarkedDate starting(int color, int textColor, int startingColor) {
        return new MarkedDate(startingColor, Color.TRANSPARENT, color, textColor);
    }

    static MarkedDate ending(int color, int textColor, int endingColor) {
        return new MarkedDate(Color.TRANSPARENT, endingColor, color, textColor);
    }

    public MarkedDate(int startingColor, int endingColor, int color, int textColor) {
        this.startingColor = startingColor;
        this.endingColor = endingColor;
        this.color = color;
        this.textColor = textColor;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getTextColor() {
        return textColor;
    }

    @Override
    public int getEndingColor() {
        return endingColor;
    }

    @Override
    public int getStartingColor() {
        return startingColor;
    }
}
