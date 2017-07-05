package fr.unice.polytech.utils;

import java.time.LocalDateTime;

import org.json.JSONObject;


public class Utils {

    public static LocalDateTime jSonToLocalDateTime(JSONObject jsonObject) {
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int dayOfMonth = jsonObject.getInt("day");
        int hour = jsonObject.getInt("hour");
        int minute = jsonObject.getInt("minute");
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    public static JSONObject LocalDateTimeTOJson(LocalDateTime date) {
        JSONObject json = new JSONObject();
        json.put("year", date.getYear());
        json.put("month", date.getMonthValue());
        json.put("day", date.getDayOfMonth());
        json.put("minute", date.getMinute());
        json.put("hour", date.getHour());
        return json;
    }

    public static LocalDateTime stringToLocalDateTime(String date) {
        int Y = new Integer(date.substring(0, 4)).intValue();
        int M = new Integer(date.substring(4, 6)).intValue();
        int D = new Integer(date.substring(6, 8)).intValue();
        int h = new Integer(date.substring(8, 10)).intValue();
        int m = new Integer(date.substring(10, 12)).intValue();
        return LocalDateTime.of(Y, M, D, h, m);
    }

    public static String LocalDateTimeTOStringYYYYMMDDmmhh(LocalDateTime date) {
        String year = "" + date.getYear();
        String month = "" + date.getMonthValue();
        String day = "" + date.getDayOfMonth();
        String hour = "" + date.getHour();
        String minute = "" + date.getMinute();
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return year + month + day + hour + minute;
    }
}