package de.fhbrbg.listcal.model;

public class CalendarData {

	public String mCalendarId;
	public int mDialogId;
	public String mTitle;
	public boolean mIsChecked;
	
	public CalendarData(String calendarId, int dialogId, String title) {
		mCalendarId = calendarId;
		mDialogId = dialogId;
		mTitle = title;
		mIsChecked = false;
	}
	
}
