package de.fhbrbg.listcal.model;

import java.util.Calendar;

public class EventData {

	public String title;
	public long id;
	public boolean isAM = true;
	
	public int height = -1;
	public int width = -1;
	
	public Calendar start;
	
	public EventData(long id, String title, long start, long end) {
		this.id = id;
		this.title = title;
		this.start = Calendar.getInstance();
		this.start.setTimeInMillis(start);
		if (this.start.get(Calendar.AM_PM) != 0) {
			isAM = false;
		}
	}
	
	public EventData(long id, String title) {
		this.id = id;
		this.title = title;
		this.start = Calendar.getInstance();
	}
	
}

