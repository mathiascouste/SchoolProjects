package droneexpress.tools;

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

}
