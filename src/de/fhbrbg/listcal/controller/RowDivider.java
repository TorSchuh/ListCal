package de.fhbrbg.listcal.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.fhbrbg.listcal.helper.Android;

public class RowDivider extends RecyclerView.ItemDecoration {

	    private static final int[] ATTRS = new int[]{ android.R.attr.listDivider };

	    private Drawable mDivider;
	    private Context mContext;

	    public RowDivider(Context context) {
	        final TypedArray attrs = context.obtainStyledAttributes(ATTRS);
	        mDivider = attrs.getDrawable(0);
	        mContext = context;
	        attrs.recycle();
	    }

	    @Override
	    public void onDraw(Canvas c, RecyclerView parent) {
	            drawVertical(c, parent);
	    }

	    public void drawVertical(Canvas c, RecyclerView parent) {
	        final int left = parent.getPaddingLeft() + (int)Android.convertDpToPixel(68, mContext);
	        final int right = parent.getWidth() - parent.getPaddingRight();
	        final int childCount = parent.getChildCount();
	        for (int i = 0; i < childCount; i++) {
	            final View child = parent.getChildAt(i);
	            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
	            final int top = child.getBottom() + params.bottomMargin;
	            final int bottom = top + mDivider.getIntrinsicHeight();
	            mDivider.setBounds(left, top, right, bottom);
	            mDivider.draw(c);
	        }
	    }

	    @Override
	    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
	        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
	    }
	
}
