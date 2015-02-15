package de.fhbrbg.listcal.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.fhbrbg.listcal.R;
import de.fhbrbg.listcal.helper.Android;
import de.fhbrbg.listcal.model.CalendarAccess;
import de.fhbrbg.listcal.model.CalendarData;
import de.fhbrbg.listcal.model.EventData;
import de.fhbrbg.listcal.model.RowData;

public class MainActivity extends Activity {

	private static MainActivity instance = null;

	private RowListView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private RowAdapter mAdapter;
	private ArrayList<RowData> mRowData;
	
	private ArrayList<CalendarData> mCalendars = new ArrayList<CalendarData>(0);
	private TextView mActionBarTitle;
	private RowData mActiveRowData;
	
	private String[] dialogItems;
	private boolean[] checkedItems;

	private TextView debugMessage;
	
	public boolean mShowLabels = true;
	public int mActiveItemPosition = 0;
	public int mFirstItemPosition = 0;
	public String mActiveDayOfWeek = "";
	public int mActiveItemPositionToday = 0;
	public ArrayList<String> mChoosenCalendars;
	public String[] mChoosenCalendarsParam;

	public int rowHeight;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = LayoutInflater.from(this);
		View view = inflator.inflate(R.layout.titleview, null);
		mActionBarTitle = (TextView) view.findViewById(R.id.title);
		mActionBarTitle.setText("");
		getActionBar().setCustomView(view);
		rowHeight = (int) Android.convertDpToPixel(25, this);
		debugMessage = (TextView) findViewById(R.id.DrawOnTop);
		debugMessage.setVisibility(View.GONE);
		
		if (setAllCalendars()) {
			mRecyclerView = (RowListView) findViewById(R.id.calendar);
			mLayoutManager = new LinearLayoutManager(this);
			mRecyclerView.setLayoutManager(mLayoutManager);
			mRecyclerView.addItemDecoration(new RowDivider(this));			
			loadRowData(mChoosenCalendarsParam, new GregorianCalendar(2014, 0, 1, 0, 0, 0),new GregorianCalendar(2015, 11, 32, 23, 59, 59), false);			
			mActiveRowData = mRowData.get(mActiveItemPositionToday);
			mRecyclerView.scrollToPosition(mActiveItemPositionToday);
			mActiveItemPosition = mActiveItemPositionToday;
			mActiveDayOfWeek = mActiveRowData.labelDayOfWeek;
			setActionBarTitle(mActiveRowData.labelMonth.substring(0, 3) + ". "+ mActiveRowData.labelYear);
		}		
		
		dialogItems = new String[mCalendars.size()];
		checkedItems = new boolean[mCalendars.size()];
		
		for (int i = 0; i < mCalendars.size(); i++) {
			dialogItems[i] = mCalendars.get(i).mTitle;
			checkedItems[i] = mCalendars.get(i).mIsChecked;
		}


	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case android.R.id.home:
			showCalendarDialog(dialogItems, checkedItems);
		case R.id.action_today:
			mRecyclerView.scrollToPosition(mActiveItemPositionToday);
            break; 
		case R.id.action_settings:
			if (debugMessage.getVisibility() == View.GONE) {
				debugMessage.setVisibility(View.VISIBLE);	
			} else {
				debugMessage.setVisibility(View.GONE);
			}
            break; 
		}
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		instance = this;
	}

	@Override
	public void onPause() {
		super.onPause();
		instance = null;
	}

	public static MainActivity getInstance() {
		return instance;
	}

	public void setActionBarTitle(String title) {
		mActionBarTitle.setText(title);
	}

	public int getVisibleItemCount() {
		return mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition();
	}

	public int getFirstVisibleItemPosition() {
		return mLayoutManager.findFirstCompletelyVisibleItemPosition();
	}
	
	public void setDataNotFetched() {
		for (RowData data : mRowData) {
			data.dataIsFetched = false;
		}
	}

	public boolean setAllCalendars() {
		mCalendars = CalendarAccess.getAllCalendars(this);
		return true;
	}

	public void showCalendarDialog(String[] dialogItems, boolean[] checkedItems) {
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Kalender wählen");
		builder.setMultiChoiceItems(dialogItems, checkedItems,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {
						for (int i = 0; i < mCalendars.size(); i++) {
							if (mCalendars.get(i).mDialogId == indexSelected) {
								if (isChecked) {
									mCalendars.get(i).mIsChecked = true;
								} else {
									mCalendars.get(i).mIsChecked = false;
								}
							}
						}
					}
				})

		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				mChoosenCalendars = new ArrayList<String>();
				for (int i = 0; i < mCalendars.size(); i++) {
					if (mCalendars.get(i).mIsChecked) {
						mChoosenCalendars.add(mCalendars.get(i).mCalendarId);
					}
				}
				MainActivity.getInstance().setDataNotFetched();
				mChoosenCalendarsParam = mChoosenCalendars.toArray(new String[mChoosenCalendars.size()]);
				loadRowData(mChoosenCalendarsParam, new GregorianCalendar(2014, 0, 1, 0, 0, 0),new GregorianCalendar(2015, 11, 32, 23, 59, 59), true);
			}
		});

		dialog = builder.create();
		dialog.show();
	}

	public void setZoomLevel(int newItemHeight) {
		rowHeight = newItemHeight;
	    debugMessage.setText("RowHeight:"+rowHeight+" | VisibleItemCount:"+getVisibleItemCount());
		mAdapter.notifyDataSetChanged();
	}
	
	public static Calendar getCalendarDayStart(Calendar calendar) {
		Calendar calendarTmp = (Calendar) calendar.clone();
		calendarTmp.set(Calendar.HOUR_OF_DAY, 0);
		calendarTmp.set(Calendar.MINUTE, 0);
		calendarTmp.set(Calendar.SECOND, 0);
		return calendarTmp;
	}
	
	public static Calendar getCalendarDayEnd(Calendar calendar) {
		Calendar calendarTmp = (Calendar) calendar.clone();
		calendarTmp.set(Calendar.HOUR_OF_DAY, 23);
		calendarTmp.set(Calendar.MINUTE, 59);
		calendarTmp.set(Calendar.SECOND, 59);
		return calendarTmp;
	}

	private ArrayList<RowData> getCalendarItems(String[] calendarIds, Calendar calendarBegin, Calendar calendarEnd) {
		ArrayList<RowData> calendarList = new ArrayList<RowData>();
		long begin = calendarBegin.getTimeInMillis();
	    long end = calendarEnd.getTimeInMillis();
	    long days =TimeUnit.MILLISECONDS.toDays(Math.abs(end - begin));
		for (long d = 0; d < days; d++) {
			calendarList.add(new RowData(d, calendarBegin, null, false));
			calendarBegin.add(Calendar.DAY_OF_YEAR, 1);
		}
		return calendarList;
	}

	private ArrayList<RowData> getCalendarItemsWithEvents(String[] calendarIds, Calendar calendarBegin, Calendar calendarEnd) {
		ArrayList<RowData> calendarList = new ArrayList<RowData>();
		HashMap<Long, EventData> eventList = new HashMap<Long, EventData>(); 
		long begin = calendarBegin.getTimeInMillis();
	    long end = calendarEnd.getTimeInMillis();
	    long days =TimeUnit.MILLISECONDS.toDays(Math.abs(end - begin));
	    Calendar calendarNow = (Calendar) calendarBegin.clone();
	    Calendar startBegin = (Calendar) calendarBegin.clone();
	    Calendar endBegin = (Calendar) calendarBegin.clone();
		for (int d = 0; d < days; d++) {
			startBegin.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			endBegin.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH), 23, 0, 0);
			eventList = CalendarAccess.getAllInstancesByInterval(this.getApplicationContext(), mChoosenCalendarsParam, String.valueOf(startBegin.getTimeInMillis()), String.valueOf(endBegin.getTimeInMillis()));
			if (calendarNow.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && calendarNow.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
				mActiveItemPositionToday = d;
			}
			if (d > 0) {
				for (EventData eventPrev : calendarList.get(d-1).dataEvents.values()) {
					for (EventData eventNow : eventList.values()) {
						if (eventPrev.id == eventNow.id) {
							eventList.remove(eventNow.id);
							break;
						}
					}
				}
			}
			calendarList.add(new RowData(d, calendarNow, eventList, true));
			calendarNow.add(Calendar.DAY_OF_YEAR, 1);
			
		}
		return calendarList;
	}
	
	private void loadRowData(String[] calendarIds, Calendar calendarBegin, Calendar calendarEnd, boolean prefetching) {
		if (prefetching) {
			mRowData = getCalendarItemsWithEvents(calendarIds, calendarBegin, calendarEnd);
		} else {
			mRowData = getCalendarItems(calendarIds, calendarBegin, calendarEnd);
		}
		mAdapter = new RowAdapter(mRowData, R.layout.row, this);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		mRecyclerView.scrollToPosition(mActiveItemPositionToday);
		mActiveItemPosition = mActiveItemPositionToday;
		rowHeight = (int) Android.convertDpToPixel(28, this);
	}
	
}