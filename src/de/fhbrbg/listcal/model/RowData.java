package de.fhbrbg.listcal.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class RowData {
	
	public String labelDayOfWeek;
	public String labelDayOfMonth;
	public String labelMonth;
	public String labelYear;
	
	public int viewWidthPx;
	public int viewHeightPx;
	public boolean viewShowText;
	
	private Calendar mCalendar;
	public int calendarDayOfWeek;
	public int calendarDayOfMonth;
	public int calendarDayOfYear;
	public int calendarYear;
	public int calendarMonth;

	public long calendarStartMillis;
	public long calendarEndMillis;
	
	public boolean calendarIsToday;
	public boolean calendarIsWeekday;
	public boolean calendarIsSaturday;
	public boolean calendarIsSunday;
	public boolean calendarIsFirstDayOfMonth;
	public boolean calendarIsLastDayOfMonth;
	
	// Data
	public long dataRowId;
	public boolean dataIsFetched;
	public HashMap<Long, EventData> dataEvents;
	
	public RowData(long id, Calendar calendar, HashMap<Long, EventData> events, boolean fetched) {
		mCalendar = calendar;
		
		labelDayOfMonth = convertDayOfMonthToLabel(calendar.get(Calendar.DAY_OF_MONTH));
		labelDayOfWeek = convertDayOfWeekToLabel(calendar.get(Calendar.DAY_OF_WEEK));
		labelMonth = convertMonthToLabel(calendar.get(Calendar.MONTH));
		labelYear = String.valueOf(calendar.get(Calendar.YEAR));
		
		calendarDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendarDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		calendarDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		calendarYear = calendar.get(Calendar.YEAR);
		calendarMonth = calendar.get(Calendar.MONTH);
		calendarIsFirstDayOfMonth = calendarDayOfMonth == 1;
		calendarIsLastDayOfMonth = calendarDayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendarIsToday = calendar.get(Calendar.ERA) == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.ERA) &&
						  calendar.get(Calendar.YEAR) == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR) &&
						  calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_YEAR);

		calendarIsSaturday = calendarDayOfWeek == 7 ? true : false;
		calendarIsSunday = calendarDayOfWeek == 1 ? true : false;
		calendarIsWeekday = !calendarIsSaturday && !calendarIsSunday;
		
		calendarStartMillis = getCalendarDayStart(calendar);
		calendarEndMillis = getCalendarDayEnd(calendar);

		dataRowId = id;
		dataIsFetched = fetched;
		dataEvents = new HashMap<Long, EventData>();
		
		if (events != null && !events.isEmpty()) {
			dataEvents = events;
		}
 
		viewShowText = true;
	}
	
	private static String convertDayOfMonthToLabel(int dayOfMonth){
		return dayOfMonth < 10 ? "0"+dayOfMonth : ""+dayOfMonth;
	}
	
	private static String convertDayOfWeekToLabel(int dayOfWeek){
		switch (dayOfWeek) {
		case 1:
			return "So";
		case 2:
			return "Mo";
		case 3:
			return "Di";
		case 4:
			return "Mi";
		case 5:
			return "Do";
		case 6:
			return "Fr";
		case 7:
			return "Sa";
		default:
			return "";
		}
	}
	
	private static String convertMonthToLabel(int month){
		switch (month) {
		case 1:
			return "Februar";
		case 2:
			return "März";
		case 3:
			return "April";
		case 4:
			return "Mai";
		case 5:
			return "Juni";
		case 6:
			return "Juli";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "Oktober";
		case 10:
			return "November";
		case 11:
			return "Dezember";
		case 0:
			return "Januar";
		default:
			return "";
		}
	}
	
	public static long getCalendarDayStart(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getCalendarDayEnd(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis();
	}
	
}