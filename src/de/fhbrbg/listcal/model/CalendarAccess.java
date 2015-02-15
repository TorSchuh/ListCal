package de.fhbrbg.listcal.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Instances;
import de.fhbrbg.listcal.helper.Common;


public final class CalendarAccess {
	
	private static final Uri mCalendarUri = Calendars.CONTENT_URI;
	private static final Uri mEventUri = CalendarContract.Events.CONTENT_URI;
	
	private static final int ALL_CALENDARS_INDEX_ID = 0;
	private static final int ALL_CALENDARS_INDEX_NAME = 1;	
	
	private static final int ALL_EVENTS_BY_DAY_INDEX_ID = 0;
	private static final int ALL_EVENTS_BY_DAY_INDEX_TITLE = 1;	
	private static final int ALL_EVENTS_BY_DAY_INDEX_CAL_ID = 2;
	private static final int ALL_EVENTS_BY_DAY_INDEX_DTSTART = 3;
	private static final int ALL_EVENTS_BY_DAY_INDEX_DTEND = 4;
	
	public static final ArrayList<CalendarData> getAllCalendars(final Context context) {
		final ArrayList<CalendarData> result = new ArrayList<CalendarData>();
		final ContentResolver contentResolver = context.getContentResolver();
		final String[] projectionAllCalendars = new String[] { Calendars._ID, Calendars.CALENDAR_DISPLAY_NAME };
		final String selectionAllCalendars = null;
		final String[] selectionArgsAllCalendars = null; 
		final String orderAllCalendars = null;
		final Cursor cursor = contentResolver.query(mCalendarUri, projectionAllCalendars, selectionAllCalendars, selectionArgsAllCalendars, orderAllCalendars);
//		Log.d("ListCal", "getAllCalendars|CURSOR.COUNT="+cursor.getCount());
		int i = 0;
		while (cursor.moveToNext()) {
			result.add(new CalendarData(cursor.getString(ALL_CALENDARS_INDEX_ID), i, cursor.getString(ALL_CALENDARS_INDEX_NAME)));
			i++;
//			Log.d("ListCal", "getAllCalendars|CALENDAR.ID="+cursor.getLong(ALL_CALENDARS_INDEX_ID)+" CALENDAR.NAME="+cursor.getString(ALL_CALENDARS_INDEX_NAME));
		}
		cursor.close();
		return result;
	}
	
	public static final HashMap<Long, String> getAllEventsByInterval(final Context context, final String[] calendars, final String startDateMillis, final String endDateMillis) {
		final HashMap<Long, String> result = new HashMap<>();
		final ContentResolver contentResolver = context.getContentResolver();
		String[] projectionAllEventsByInterval;
		String selectionAllEventsByInterval;
		String[] selectionArgsAllEventsByInterval;
		if (calendars == null || calendars.length <= 0) {
			projectionAllEventsByInterval = new String[] { CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART };
			selectionAllEventsByInterval = CalendarContract.Events.DTSTART + " >= ? AND " + CalendarContract.Events.DTSTART + " <= ?";
			selectionArgsAllEventsByInterval = new String[] { startDateMillis, endDateMillis };
		} else {
			projectionAllEventsByInterval = new String[] { BaseColumns._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.CALENDAR_ID };
			if (calendars.length == 1) {
				selectionAllEventsByInterval = CalendarContract.Events.DTSTART + " >= ? AND " + CalendarContract.Events.DTSTART + " <= ? AND " + CalendarContract.Events.CALENDAR_ID + " = ?";
				selectionArgsAllEventsByInterval = new String[] { startDateMillis, endDateMillis, calendars[0] };
			} else {
				selectionAllEventsByInterval = CalendarContract.Events.DTSTART + " >= ? AND " + CalendarContract.Events.DTSTART + " <= ? AND (";
				selectionArgsAllEventsByInterval = new String[] { startDateMillis, endDateMillis };
				for (int i = 0; i < calendars.length; i++) {
					if (i == calendars.length-1) {
						selectionAllEventsByInterval += CalendarContract.Events.CALENDAR_ID + " = ?";
					} else {
						selectionAllEventsByInterval += CalendarContract.Events.CALENDAR_ID + " = ? OR ";
					}
				}	
				selectionAllEventsByInterval += ")";
				selectionArgsAllEventsByInterval = Common.concatArrays(selectionArgsAllEventsByInterval, calendars);
			}
		}
		final String orderAllEventsByInterval = null;
		final Cursor cursor = contentResolver.query(mEventUri, projectionAllEventsByInterval, selectionAllEventsByInterval, selectionArgsAllEventsByInterval, orderAllEventsByInterval);
//		Log.d("ListCal", "getAllEventsByInterval|CURSOR.COUNT="+cursor.getCount());
		while (cursor.moveToNext()) {
			result.put(cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_ID), cursor.getString(ALL_EVENTS_BY_DAY_INDEX_TITLE));
//			Log.d("ListCal", "getAllEventsByInterval|EVENT.ID="+cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_ID)+" EVENT.TITLE="+cursor.getString(ALL_EVENTS_BY_DAY_INDEX_TITLE));
		}
		cursor.close();
		return result;
	}
	
	// CalendarData.getAllInstancesByInterval(this, new String[] { "4","6","8" }, Long.toString(1418598000000l), Long.toString(1419202799000l));
	public static final HashMap<Long, EventData> getAllInstancesByInterval(final Context context, final String[] calendars, final String startDateMillis, final String endDateMillis) {
//		Log.d("ListCal", "calendars"+calendars.length+" startDateMillis"+startDateMillis+" endDateMillis"+endDateMillis);
		final HashMap<Long, EventData> result = new HashMap<>();
		final ContentResolver contentResolver = context.getContentResolver();
		final Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
		ContentUris.appendId(builder, Long.parseLong(startDateMillis));
		ContentUris.appendId(builder, Long.parseLong(endDateMillis));
		String[] projectionAllInstancesByInterval;
		String selectionAllInstancesByInterval;
		String[] selectionArgsAllInstancesByInterval;
		if (calendars == null || calendars.length <= 0) {
			projectionAllInstancesByInterval = new String[] { CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};
			selectionAllInstancesByInterval = null;
			selectionArgsAllInstancesByInterval = null;
		} else {
			projectionAllInstancesByInterval = new String[] { CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND };
			if (calendars.length == 1) {
				selectionAllInstancesByInterval = CalendarContract.Events.CALENDAR_ID + " = ?";
				selectionArgsAllInstancesByInterval = new String[] { calendars[0] };
			} else {
				selectionAllInstancesByInterval = "";
				selectionArgsAllInstancesByInterval = new String[] {};
				for (int i = 0; i < calendars.length; i++) {
					if (i == calendars.length-1) {
						selectionAllInstancesByInterval += CalendarContract.Events.CALENDAR_ID + " = ?";
					} else {
						selectionAllInstancesByInterval += CalendarContract.Events.CALENDAR_ID + " = ? OR ";
					}
				}	
				selectionArgsAllInstancesByInterval = Common.concatArrays(selectionArgsAllInstancesByInterval, calendars);
			}
		}
		final String orderAllInstancesByInterval = CalendarContract.Events.DTSTART +" DESC";
		final Cursor cursor = contentResolver.query(builder.build(), projectionAllInstancesByInterval, selectionAllInstancesByInterval, selectionArgsAllInstancesByInterval, orderAllInstancesByInterval);
//		Log.d("ListCal", "getAllInstancesByInterval|CURSOR.COUNT="+cursor.getCount());
		while (cursor.moveToNext()) {
			result.put(cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_ID),new EventData(cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_ID), cursor.getString(ALL_EVENTS_BY_DAY_INDEX_TITLE), cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_DTSTART), cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_DTEND)));
//			Log.d("ListCal", "getAllInstancesByInterval|EVENT.ID="+cursor.getLong(ALL_EVENTS_BY_DAY_INDEX_ID)+" EVENT.TITLE="+cursor.getString(ALL_EVENTS_BY_DAY_INDEX_TITLE)+" EVENT.DTSTART="+cursor.getString(ALL_EVENTS_BY_DAY_INDEX_DTSTART)+" EVENT.DTEND="+cursor.getString(ALL_EVENTS_BY_DAY_INDEX_DTEND));
		}
		cursor.close();
		return result;
	}
	
}