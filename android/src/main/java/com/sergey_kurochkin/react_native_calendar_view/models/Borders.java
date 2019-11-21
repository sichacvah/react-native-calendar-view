package com.sergey_kurochkin.react_native_calendar_view.models;


public class Borders {
    public int top = 0;
    public int left = 0;
    public int right = 0;
    public int bottom = 0;

    @Override
    public int hashCode() {
        return top ^ left ^ right ^ bottom;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Borders && ((Borders) obj).left == left && ((Borders) obj).top == top && ((Borders) obj).right == right && ((Borders) obj).bottom == bottom;
    }
}
