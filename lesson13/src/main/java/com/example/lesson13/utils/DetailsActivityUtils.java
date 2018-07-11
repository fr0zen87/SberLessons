package com.example.lesson13.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.lesson13.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsActivityUtils {

    private DetailsActivityUtils() {

    }

    public static String titleDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String otherDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String formatWindSpeed(Context context, double wind) {
        double windSpeed = 1.6093 * wind;
        return String.format(Locale.getDefault(), context.getString(R.string.wind_speed_value), windSpeed);
    }

    public static String formatHumidity(double humidity) {
        int fHumidity = (int)(humidity * 100);
        return String.format(Locale.getDefault(), "%d%%", fHumidity);
    }

    public static String formatPressure(Context context, double pressure) {
        int fPressure = (int)((pressure * 7.501) / 10);
        return String.format(Locale.getDefault(), context.getString(R.string.pressure_value), fPressure);
    }

    public static int setColor(Context context, int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }
}
