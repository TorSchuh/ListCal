package de.fhbrbg.listcal.controller;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import de.fhbrbg.listcal.R;
import de.fhbrbg.listcal.helper.Android;
import de.fhbrbg.listcal.model.EventData;
import de.fhbrbg.listcal.model.RowData;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {

    private ArrayList<RowData> mRowData;
    private int mRowLayout;
    private Context mContext;
    
    private static int sColorBackgroundDefault;
    private static int sColorBackgroundWeekend;
    private static Drawable sColorBackgroundDayOfMonthWeekday;
    private static Drawable sColorBackgroundDayOfMonthWeekend;
    private static Drawable sColorBackgroundDayOfMonthFirstOfMonth;
    private static Drawable sColorBackgroundDayOfMonthWeekdayActiv;
    private static Drawable sColorBackgroundDayOfMonthWeekendActiv;
    private static Drawable sColorBackgroundDayOfMonthFirstOfMonthActiv;
    private static Drawable sColorBackgroundDayOfMonthWeekdayActivToday;
    private static Drawable sColorBackgroundDayOfMonthWeekendActivToday;
    private static Drawable sColorBackgroundDayOfMonthFirstOfMonthActivToday;
    private static int sColorBackgroundFirstDayOfMonth;
    private static int sColorTextFirstDayOfMonth;
    private static int sColorBackgroundFirstDayOfMonthActive;
    private static int sColorTextFirstDayOfMonthActive;
    private static int sColorBackgroundToday;
    private static int sColorBackgroundEventDefault;
    private static int sColorTextWeekend;
    private static int sColorTextWeekday;
    private static int sColorTextToday;
    private static int sColorTextActiv;
    
    
    public RowAdapter(ArrayList<RowData> rowData, int rowLayout, Context context) {
        mRowData = rowData;
        mRowLayout = rowLayout;
        mContext = context;
        
        sColorBackgroundFirstDayOfMonth = mContext.getResources().getColor(R.color.rose);
        sColorTextFirstDayOfMonth = mContext.getResources().getColor(R.color.pink);
        sColorBackgroundDefault = mContext.getResources().getColor(R.color.grey100);
        sColorBackgroundWeekend = mContext.getResources().getColor(R.color.grey150);
        sColorTextWeekday = mContext.getResources().getColor(R.color.grey700);
        sColorTextWeekend = mContext.getResources().getColor(R.color.grey400);
        sColorTextToday = mContext.getResources().getColor(R.color.yellow);
        sColorTextActiv = mContext.getResources().getColor(R.color.grey100);
        sColorBackgroundEventDefault = mContext.getResources().getColor(R.color.grey500);
        sColorBackgroundFirstDayOfMonthActive = mContext.getResources().getColor(R.color.pink);
        sColorTextFirstDayOfMonthActive = mContext.getResources().getColor(R.color.grey100);
        sColorBackgroundToday = mContext.getResources().getColor(R.color.yellow);
        sColorBackgroundDayOfMonthWeekday = mContext.getResources().getDrawable(R.drawable.bg_today);
        sColorBackgroundDayOfMonthWeekend = mContext.getResources().getDrawable(R.drawable.bg_today_weekend);
        sColorBackgroundDayOfMonthFirstOfMonth = mContext.getResources().getDrawable(R.drawable.bg_month_today);
        sColorBackgroundDayOfMonthWeekdayActiv = mContext.getResources().getDrawable(R.drawable.bg_selected);
        sColorBackgroundDayOfMonthWeekendActiv = mContext.getResources().getDrawable(R.drawable.bg_selected_weekend);
        sColorBackgroundDayOfMonthFirstOfMonthActiv = mContext.getResources().getDrawable(R.drawable.bg_month_selected);
        sColorBackgroundDayOfMonthWeekdayActivToday = mContext.getResources().getDrawable(R.drawable.bg_today_selected);
        sColorBackgroundDayOfMonthWeekendActivToday = mContext.getResources().getDrawable(R.drawable.bg_today_selected_weekend);
        sColorBackgroundDayOfMonthFirstOfMonthActivToday = mContext.getResources().getDrawable(R.drawable.bg_month_today_selected);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    	final RowData rowData = mRowData.get(position);
    	
    	viewHolder.position = position;
    	rowData.dataRowId = position;
    	
    	MainActivity.getInstance().setActionBarTitle(rowData.labelMonth.substring(0, 3)+". "+rowData.labelYear);
    	
		if (MainActivity.getInstance().mChoosenCalendars == null || MainActivity.getInstance().mChoosenCalendars.size() <= 0) {
			rowData.dataEvents.clear();
		}
    	
     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
		
		viewHolder.eventsAM.removeAllViewsInLayout();
		viewHolder.eventsPM.removeAllViewsInLayout();
		
		viewHolder.eventsAM.setVisibility(View.GONE);
		viewHolder.eventsPM.setVisibility(View.GONE);
		
		if (rowData.calendarIsFirstDayOfMonth) {
			rowData.dataEvents.put(-1l, new EventData(-1l, rowData.labelMonth));
		}
		
    	if ( MainActivity.getInstance().rowHeight != viewHolder.row.getHeight() && MainActivity.getInstance().rowHeight > 1) {
    		rowData.viewHeightPx = MainActivity.getInstance().rowHeight;
			viewHolder.row.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, rowData.viewHeightPx));
		}

    	if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(23, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(14);
	        viewHolder.dayOfMonth.setTextSize(14);
    	} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(22, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(13);
	        viewHolder.dayOfMonth.setTextSize(13);
    	} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(21, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(12);
	        viewHolder.dayOfMonth.setTextSize(12);
    	} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(20, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(11);
	        viewHolder.dayOfMonth.setTextSize(11);	 
    	} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(19, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(10);
	        viewHolder.dayOfMonth.setTextSize(10);
    	} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(18, mContext)) {
	     	viewHolder.dayOfWeek.setText(rowData.labelDayOfWeek.substring(0, 1));
	        viewHolder.dayOfMonth.setText(rowData.labelDayOfMonth);
	        viewHolder.dayOfWeek.setTextSize(9);
	        viewHolder.dayOfMonth.setTextSize(9);	
    	} else {
    		viewHolder.dayOfWeek.setTextSize(0);
	        viewHolder.dayOfMonth.setTextSize(0);
    	}
		
    	viewHolder.eventList.clear();
		if (viewHolder.eventList.isEmpty() && !rowData.dataEvents.isEmpty()) {
			for (EventData event : rowData.dataEvents.values()) {
				
				EventItem eventItem = new EventItem(mContext);
				eventItem.setText(event.title);
				eventItem.measure(0, 0);

				if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(23, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(14);
				} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(22, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(13);
				} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(21, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(12);
				} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(20, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(11);
				} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(19, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(10);
				} else if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(18, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(9);
				} else {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					eventItem.setTextSize(0);
				}
								
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				if (event.width > 0) {
					params = new LinearLayout.LayoutParams(event.width, event.height);	
				}
				
				params.setMargins(0, 0, (int) Android.convertDpToPixel(1, mContext), 0);
				eventItem.setLayoutParams(params);
								
				viewHolder.eventList.add(eventItem);
				if (MainActivity.getInstance().rowHeight > (int) Android.convertDpToPixel(44, mContext)) {
					event.width = eventItem.getMeasuredWidth();
					event.height = eventItem.getMeasuredHeight();
					if (event.isAM) {
						viewHolder.eventsAM.addView(eventItem);	
					} else {
						viewHolder.eventsPM.addView(eventItem);					
					}
				} else {
					viewHolder.eventsAM.addView(eventItem);
				}		
			}
		}

		if (viewHolder.eventsAM.getChildCount() > 0) {
			viewHolder.eventsAM.setVisibility(View.VISIBLE);
		}
		if (viewHolder.eventsPM.getChildCount() > 0) {
			viewHolder.eventsPM.setVisibility(View.VISIBLE);
		}
    	
		if (rowData.calendarIsFirstDayOfMonth) {
			viewHolder.eventsPM.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
			viewHolder.eventsAM.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
			viewHolder.events.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
			viewHolder.row.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
			viewHolder.dayOfWeek.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
	        viewHolder.dayOfMonth.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
        	viewHolder.dayOfWeek.setTextColor(sColorTextWeekday);
        	viewHolder.dayOfMonth.setTextColor(sColorTextWeekday);
        	if (rowData.calendarIsToday) {
    			viewHolder.dayOfMonth.setTextColor(sColorTextToday);
				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthFirstOfMonth);
	        	if (MainActivity.getInstance().mActiveItemPosition == position) {
	        		viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthFirstOfMonthActivToday);
	        	}
            } else {
            	if (MainActivity.getInstance().mActiveItemPosition == position) {
            		viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthFirstOfMonthActiv);	
    				viewHolder.dayOfMonth.setTextColor(sColorTextActiv);
            	}
            }
        	for (EventItem event : viewHolder.eventList) {
    			if (event.getText() == rowData.labelMonth) {
    				event.setBackgroundColor(sColorBackgroundFirstDayOfMonth);
    				event.setTextColor(sColorTextFirstDayOfMonth);
    			} else if (rowData.calendarIsToday) {
    				event.setBackgroundColor(sColorBackgroundToday);
    				event.setTextColor(sColorTextActiv);
    			} else if (MainActivity.getInstance().mActiveItemPosition == position) {
					event.setBackgroundColor(sColorBackgroundFirstDayOfMonthActive);
					event.setTextColor(sColorTextFirstDayOfMonthActive);
    			} else {
					event.setBackgroundColor(sColorBackgroundEventDefault);
					event.setTextColor(sColorTextActiv);
    			}
        	}
		} else if (rowData.calendarIsSaturday) {
			viewHolder.eventsAM.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.eventsPM.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.events.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.dayOfWeek.setBackgroundColor(sColorBackgroundWeekend);
        	viewHolder.dayOfMonth.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.row.setBackgroundColor(sColorBackgroundWeekend);
        	viewHolder.dayOfWeek.setTextColor(sColorTextWeekend);
        	viewHolder.dayOfMonth.setTextColor(sColorTextWeekend);
        	if (rowData.calendarIsToday) {
    			viewHolder.dayOfMonth.setTextColor(sColorTextToday);
				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekend);
	        	if (MainActivity.getInstance().mActiveItemPosition == position) {
					viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekendActivToday);
	        	}
            } else {
            	if (MainActivity.getInstance().mActiveItemPosition == position) {
    				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekendActiv);
    				viewHolder.dayOfMonth.setTextColor(sColorTextActiv);
            	}
            }
        	for (EventItem event : viewHolder.eventList) {
    			if (rowData.calendarIsToday) {
    				event.setBackgroundColor(sColorBackgroundToday);
    				event.setTextColor(sColorTextActiv);
    			} else if (MainActivity.getInstance().mActiveItemPosition == position) {
					event.setBackgroundColor(sColorBackgroundFirstDayOfMonthActive);
					event.setTextColor(sColorTextFirstDayOfMonthActive);
    			} else {
					event.setBackgroundColor(sColorBackgroundEventDefault);
					event.setTextColor(sColorTextActiv);
    			}
        	}
		} else if (rowData.calendarIsSunday) {
			viewHolder.eventsAM.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.eventsPM.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.events.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.dayOfWeek.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.dayOfMonth.setBackgroundColor(sColorBackgroundWeekend);
			viewHolder.row.setBackgroundColor(sColorBackgroundWeekend);
        	viewHolder.dayOfWeek.setTextColor(sColorTextWeekend);
        	viewHolder.dayOfMonth.setTextColor(sColorTextWeekend);
        	if (rowData.calendarIsToday) {
    			viewHolder.dayOfMonth.setTextColor(sColorTextToday);
				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekend);
	        	if (MainActivity.getInstance().mActiveItemPosition == position) {
					viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekendActivToday);
	        	}
            } else {
            	if (MainActivity.getInstance().mActiveItemPosition == position) {
    				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekendActiv);
    				viewHolder.dayOfMonth.setTextColor(sColorTextActiv);
            	}
            }
        	for (EventItem event : viewHolder.eventList) {
    			if (rowData.calendarIsToday) {
    				event.setBackgroundColor(sColorBackgroundToday);
    				event.setTextColor(sColorTextActiv);
    			} else if (MainActivity.getInstance().mActiveItemPosition == position) {
					event.setBackgroundColor(sColorBackgroundFirstDayOfMonthActive);
					event.setTextColor(sColorTextFirstDayOfMonthActive);
    			} else {
					event.setBackgroundColor(sColorBackgroundEventDefault);
					event.setTextColor(sColorTextActiv);
    			}
        	}
		} else {
			viewHolder.eventsAM.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.eventsPM.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.events.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.row.setBackgroundColor(sColorBackgroundDefault);
			viewHolder.dayOfWeek.setBackgroundColor(sColorBackgroundDefault);
	        viewHolder.dayOfMonth.setBackgroundColor(sColorBackgroundDefault);
        	viewHolder.dayOfWeek.setTextColor(sColorTextWeekday);
        	viewHolder.dayOfMonth.setTextColor(sColorTextWeekday);		
        	if (rowData.calendarIsToday) {
    			viewHolder.dayOfMonth.setTextColor(sColorTextToday);
				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekday);
	        	if (MainActivity.getInstance().mActiveItemPosition == position) {
					viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekdayActivToday);
	        	}
            } else {
            	if (MainActivity.getInstance().mActiveItemPosition == position) {
    				viewHolder.dayOfMonth.setBackground(sColorBackgroundDayOfMonthWeekdayActiv);
    				viewHolder.dayOfMonth.setTextColor(sColorTextActiv);
            	}
            }
        	for (EventItem event : viewHolder.eventList) {
    			if (rowData.calendarIsToday) {
    				event.setBackgroundColor(sColorBackgroundToday);
    				event.setTextColor(sColorTextActiv);
    			} else if (MainActivity.getInstance().mActiveItemPosition == position) {
					event.setBackgroundColor(sColorBackgroundFirstDayOfMonthActive);
					event.setTextColor(sColorTextActiv);
    			} else {
					event.setBackgroundColor(sColorBackgroundEventDefault);
					event.setTextColor(sColorTextActiv);
    			}
        	}
		}

		viewHolder.row.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
		
		viewHolder.eventsAM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
		
		viewHolder.eventsPM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
		
		viewHolder.events.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
		
		viewHolder.dayOfMonth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
		
		viewHolder.dayOfWeek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateActiveDayOfWeek(rowData, position);
			}
		});
    }
    
    @Override
    public int getItemCount() {
        return mRowData == null ? 0 : mRowData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    	public LinearLayout row;
    	public LinearLayout eventsAM;
    	public LinearLayout eventsPM;
    	public LinearLayout events;
        public Button dayOfWeek;
        public Button dayOfMonth;
        public int position;
        public ArrayList<EventItem> eventList;
        public ViewHolder(View itemView) {
            super(itemView);
            eventsAM = (LinearLayout) itemView.findViewById(R.id.EventsAM);
            eventsPM = (LinearLayout) itemView.findViewById(R.id.EventsPM);
            events = (LinearLayout) itemView.findViewById(R.id.Events);
            row = (LinearLayout) itemView.findViewById(R.id.Row);
            dayOfWeek = (Button) itemView.findViewById(R.id.DayOfWeek);
            dayOfMonth = (Button) itemView.findViewById(R.id.DayOfMonth);
            eventList = new ArrayList<EventItem>();
        }
    }
    
    private void updateActiveDayOfWeek(RowData rowData, int position){
    	int oldPosition = MainActivity.getInstance().mActiveItemPosition;
		MainActivity.getInstance().mActiveItemPosition = position;
		MainActivity.getInstance().mActiveDayOfWeek = rowData.labelDayOfWeek;
		notifyItemChanged(oldPosition);
		notifyItemChanged(position);
    }
    
}