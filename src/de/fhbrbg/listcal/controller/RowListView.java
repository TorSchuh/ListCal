package de.fhbrbg.listcal.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import de.fhbrbg.listcal.helper.Android;

public class RowListView extends RecyclerView {

	private ScaleGestureDetector mScaleDetector;	
	private boolean isScaling = false;
	
	private float mRowHeight;
	private float mRowOffset;
	private float mRowsOffset;
	private float mOffsetFocus;
	private float mFocus;
	private float mRowsCount;
	
	public RowListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRowHeight = (int) Android.convertDpToPixel(25, context);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener(this));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mScaleDetector.onTouchEvent(ev);
		if (isScaling && ev.getAction() == MotionEvent.ACTION_MOVE) {
	    	return false;
	    }
	    super.onTouchEvent(ev);
	    return true;
	}
		
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		
		public ScaleListener(RowListView zoomListView) {
			super();		
		}
		
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			isScaling = true;
			
			return super.onScaleBegin(detector);
		}
		
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	    	mFocus = detector.getFocusY();
	    	mRowOffset = mRowHeight * detector.getScaleFactor() - mRowHeight;
	    	mRowsCount = getHeight() / mRowHeight;
	    	mRowHeight = mRowHeight + mRowOffset;

	    	if (mRowHeight >= 2) {
		    	mRowsOffset = mRowsCount * (mRowHeight * detector.getScaleFactor() - mRowHeight);
		    	mOffsetFocus = ((mFocus / mRowHeight) * mRowOffset);
		    	scrollBy(0, (int) mOffsetFocus);   			    	
	    	} else {
	    		mRowHeight = 2;
	    	}
	    	MainActivity.getInstance().setZoomLevel((int) mRowHeight);
	        return true;
	    }
	    
	    @Override
	    public void onScaleEnd(ScaleGestureDetector detector) {
			isScaling = false;
	    	super.onScaleEnd(detector);
	    }
	    
	}
	
}
