package droneexpress.tools;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


public class Converter {

    public static Date LocalDateTimeTODate(LocalDateTime datetime) {
        return Date.from(datetime.toInstant(ZoneOffset.UTC));
    }

    public static String LocalDateTimeTOStringYYYYMMDDmmhhss(LocalDateTime now) {
        String year = "" + now.getYear();
        String month = "" + now.getMonthValue();
        String day = "" + now.getDayOfMonth();
        String hour = "" + now.getHour();
        String minute = "" + now.getMinute();
        String second = "" + now.getSecond();
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
        if (second.length() == 1) {
            second = "0" + second;
        }
        return year + month + day + hour + minute + second;
    }

    public static LocalDateTime StringYYYYMMDDmmhhssTOLocalDateTime(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String dayOfMonth = date.substring(6, 8);
        String hour = date.substring(8, 10);
        String minute = date.substring(10, 12);
        String second = date.substring(12, 14);
        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month),
                Integer.parseInt(dayOfMonth), Integer.parseInt(hour), Integer.parseInt(minute),
                Integer.parseInt(second));
        return ldt;
    }

}
