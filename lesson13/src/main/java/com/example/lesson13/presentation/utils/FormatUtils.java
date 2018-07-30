package com.example.lesson13.presentation.utils;

import com.example.lesson13.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private FormatUtils() {

    }

    public  static String formatTitle(String title) {
        StringBuilder result = new StringBuilder();
        String[] splitTitle = title.split(" ");
        for (String s : splitTitle) {
            char start = s.charAt(0);
            result.append(String.valueOf(start).toUpperCase()).append(s.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

    public static String titleDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String formatHumidity(double humidity) {
        int formattedHumidity = (int) (humidity * 100);
        return String.format(Locale.getDefault(), "%d%%", formattedHumidity);
    }

    public static String formatPressure(double pressure, String formatString) {
        int formattedPressure = (int) ((pressure * 7.501) / 10);
        return String.format(Locale.getDefault(), formatString, formattedPressure);
    }

    public static String otherDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String formatTemperature(double temp) {
        int temperature = (int) temp;
        if (temperature > 0) {
            return String.format("+%s\u00b0", String.valueOf(temperature));
        }
        return String.format("%s\u00b0", String.valueOf(temperature));
    }

    public static int getImageResource(String res) {
        int resource = 0;
        switch (res) {
            case "clear-day": {
                resource = R.drawable.ic_clear_day;
                break;
            }
            case "clear-night": {
                resource = R.drawable.ic_clear_night;
                break;
            }
            case "rain": {
                resource = R.drawable.ic_rain;
                break;
            }
            case "sleet": {
                resource = R.drawable.ic_sleet;
                break;
            }
            case "snow": {
                resource = R.drawable.ic_snow;
                break;
            }
            case "wind": {
                resource = R.drawable.ic_wind;
                break;
            }
            case "fog": {
                resource = R.drawable.ic_fog;
                break;
            }
            case "cloudy": {
                resource = R.drawable.ic_cloudy;
                break;
            }
            case "partly-cloudy-day": {
                resource = R.drawable.ic_partly_cloudy_day;
                break;
            }
            case "partly-cloudy-night": {
                resource = R.drawable.ic_partly_cloudy_night;
                break;
            }
            default: {
                resource = R.drawable.ic_clear_day;
                break;
            }
        }
        return resource;
    }
}
