package com.zgrujb.selfdefindui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;

public class ContactHeaderView extends View {
    Paint mPaint;
	public ContactHeaderView(Context context) {
		super(context);
		init();
	}

	public ContactHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ContactHeaderView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init(){
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawLine(0, getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight(), mPaint);
	}
//
//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
////		this.setLayoutParams(new LayoutParams(
////                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
//	}

}
