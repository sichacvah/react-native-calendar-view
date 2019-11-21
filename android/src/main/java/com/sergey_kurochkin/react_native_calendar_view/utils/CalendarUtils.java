package com.sergey_kurochkin.react_native_calendar_view.utils;

import java.util.concurrent.atomic.AtomicInteger;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.sergey_kurochkin.react_native_calendar_view.models.Borders;

public class CalendarUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px * (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    @NonNull
    public  static Drawable getSelectableDrawableFor(int color) {
        RectShape r = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return getSelectableDrawableFor(color, shapeDrawable);
    }

    @NonNull
    public  static Drawable getSelectableDrawableFor(int color, ShapeDrawable shape) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            ShapeDrawable pressedDrawable = new ShapeDrawable(shape.getShape());
            pressedDrawable.getPaint().setColor(lightenOrDarken(color, 0.20D));

            ShapeDrawable focusedDrawable = new ShapeDrawable(shape.getShape());
            focusedDrawable.getPaint().setColor(lightenOrDarken(color, 0.40D));
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    pressedDrawable
            );
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_focused},
                    focusedDrawable
            );
            stateListDrawable.addState(
                    new int[]{},
                    shape
            );
            return stateListDrawable;
        } else {
            ColorStateList pressedColor = ColorStateList.valueOf(lightenOrDarken(color, 0.2D));
            return new RippleDrawable(
                    pressedColor,
                    shape,
                    shape
            );
        }
    }



    private static int lightenOrDarken(int color, double fraction) {
        if (canLighten(color, fraction)) {
            return lighten(color, fraction);
        } else {
            return darken(color, fraction);
        }
    }

    private static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);

        return Color.argb(alpha, red, green, blue);
    }

    private static boolean canLighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return canLightenComponent(red, fraction)
                && canLightenComponent(green, fraction)
                && canLightenComponent(blue, fraction);
    }

    private static boolean canLightenComponent(int colorComponent, double fraction) {
        int red = Color.red(colorComponent);
        int green = Color.green(colorComponent);
        int blue = Color.blue(colorComponent);
        return red + (red * fraction) < 255
                && green + (green * fraction) < 255
                && blue + (blue * fraction) < 255;
    }

    private static int darkenColor(int color, double fraction) {
        return (int) Math.max(color - (color * fraction), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }

    public static Drawable getBackgroundLayer(int backgroundColor, int borderColor, Borders borders) {
        return getBackgroundLayer(backgroundColor, borderColor, borders.left, borders.top, borders.right, borders.bottom);
    }

    static Drawable getBackgroundLayer(int backgroundColor, int borderColor, int left, int top, int right, int bottom) {

        ColorDrawable borderColorDrawable = new ColorDrawable(borderColor);
        Drawable rippleDrawable = getSelectableDrawableFor(backgroundColor);
        Drawable[] drawables = new Drawable[] {
                borderColorDrawable,
                rippleDrawable
        };

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        layerDrawable.setLayerInset(
                1, // Index of the drawable to adjust [background color layer]
                left, // Number of pixels to add to the left bound [left border]
                top, // Number of pixels to add to the top bound [top border]
                right, // Number of pixels to add to the right bound [right border]
                bottom // Number of pixels to add to the bottom bound [bottom border]
        );


        return layerDrawable;
    }
}
