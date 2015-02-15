package de.fhbrbg.listcal.controller;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.widget.TextView;
import de.fhbrbg.listcal.helper.Android;

public class EventItem extends TextView{
			
	public EventItem(Context context) {
		super(context);
		setMinHeight(0);
		setMinWidth(0);
		setSingleLine();
		setMaxLines(1);
		setEllipsize(TruncateAt.END);
		setClickable(false);
		setPadding((int) Android.convertDpToPixel(3, context), 0, (int) Android.convertDpToPixel(3, context), 0);
		setTextSize(14);				
	}
	
}
