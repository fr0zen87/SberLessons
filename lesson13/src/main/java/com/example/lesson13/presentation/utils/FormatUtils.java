package com.example.lesson13.presentation.utils;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private FormatUtils() {

    }

    public static String titleDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String formatHumidity(double humidity) {
        int formattedHumidity = (int)(humidity * 100);
        return String.format(Locale.getDefault(), "%d%%", formattedHumidity);
    }

    public static String formatPressure(double pressure, String formatString) {
        int formattedPressure = (int)((pressure * 7.501) / 10);
        return String.format(Locale.getDefault(), formatString, formattedPressure);
    }

    public static String formatWind(double windSpeed, String formatString) {
        double formattedWindSpeed = 1.6093 * windSpeed;
        return String.format(Locale.getDefault(), formatString, formattedWindSpeed);
    }

    public static String otherDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    public static String formatTemperature(double temp) {
        if (temp > 0) {
            return String.format("+%s\u00b0", String.valueOf((int) temp));
        }
        return String.format("%s\u00b0", String.valueOf((int) temp));
    }

    public static int getImageResource(Data data) {
        int resource = 0;
        int backgroundColor = 0;
        switch (data.getIcon()) {
            case "clear-day": {
                //same as clear-night
            }
            case "clear-night": {
                resource = R.drawable.ic_clear;
                backgroundColor = R.color.background_sun;
                break;
            }
            case "rain": {
                resource = R.drawable.ic_rain;
                backgroundColor = R.color.background_rain;
                break;
            }
            case "sleet": {
                //same as snow
            }
            case "snow": {
                resource = R.drawable.ic_snow;
                backgroundColor = R.color.background_snow;
                break;
            }
            case "wind": {

            }
            case "fog": {
                resource = R.drawable.ic_fog;
                backgroundColor = R.color.background_fog;
                break;
            }
            case "cloudy": {
                resource = R.drawable.ic_cloudy;
                backgroundColor = R.color.background_cloud;
                break;
            }
            case "partly-cloudy-day": {
                //same as partly-cloudy-night
            }
            case "partly-cloudy-night": {
                resource = R.drawable.ic_light_clouds;
                backgroundColor = R.color.background_partly_cloudy;
                break;
            }
            default: {
                resource = R.drawable.ic_clear;
                backgroundColor = R.color.background_sun;
                break;
            }
        }
        data.setImageResource(resource);
        data.setBackgroundColor(backgroundColor);
        return resource;
    }
}
