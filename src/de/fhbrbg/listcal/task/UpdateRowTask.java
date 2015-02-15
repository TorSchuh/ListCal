package de.fhbrbg.listcal.task;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import de.fhbrbg.listcal.controller.RowAdapter;
import de.fhbrbg.listcal.model.CalendarAccess;
import de.fhbrbg.listcal.model.EventData;
import de.fhbrbg.listcal.model.RowData;

public class UpdateRowTask extends AsyncTask<Void, Void, HashMap<Long, EventData>> {

	private Context mContext;
	private RowAdapter mAdapter;
	private int mPosition;
	private String mStartDateMillis;
	private String mEndDateMillis;
	private String[] mCalendars;
	private RowData mDateData;
	
	public UpdateRowTask(Context context, RowAdapter adapter, RowData dateData, int position, String startDateMillis, String endDateMillis, String[] calendars) {
		this.mContext = context;
		this.mAdapter = adapter;
		this.mPosition = position;
		this.mStartDateMillis = startDateMillis;
		this.mEndDateMillis = endDateMillis;
		this.mCalendars = calendars;
		this.mDateData = dateData;
	}
	
	@Override
	protected HashMap<Long, EventData> doInBackground(Void... params) {
		return CalendarAccess.getAllInstancesByInterval(this.mContext, this.mCalendars, this.mStartDateMillis, this.mEndDateMillis);
	}
	
	@Override
	protected void onPostExecute(final HashMap<Long, EventData> results) {
		super.onPostExecute(results);
		this.mDateData.dataEvents = results;
		this.mDateData.dataIsFetched = true;
		mAdapter.notifyItemChanged(this.mPosition);
	}
}
