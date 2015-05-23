package com.zgrjb.selfdefindui;


import com.zgrjb.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

public class BottomTabView extends View {
    
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;
	//颜色
	private int mColor = 0x8A2BE2;
	//透明度
	private float mAlpha = 0.0f;
	//图标
	private Bitmap icon;
	//icon的绘制范围
	private Rect iconRect;
	// icon底部文本
	private String mText = "微信";
	//设置字体大小，这里是10sp
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
	private Paint mTextPaint;
	private Rect mTextBound = new Rect();
	
	//绘制消息提醒的标志
	private Boolean drawFlag = false;

	
	
	public BottomTabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BottomTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取设置的图标
				TypedArray a = context.obtainStyledAttributes(attrs,
						R.styleable.BottomTabView);

				int n = a.getIndexCount();
				for (int i = 0; i < n; i++)
				{

					int attr = a.getIndex(i);
					switch (attr)
					{
					case R.styleable.BottomTabView_icon:
						BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
						
						icon = drawable.getBitmap();
						
						
						
						break;
					case R.styleable.BottomTabView_color:
						mColor = a.getColor(attr, 0x45C01A);
						break;
					case R.styleable.BottomTabView_text:
						mText = a.getString(attr);
						break;
					case R.styleable.BottomTabView_text_size:
						mTextSize = (int) a.getDimension(attr, TypedValue
								.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
										getResources().getDisplayMetrics()));
						break;

					}
				}

				a.recycle();
				mTextPaint = new Paint();
				mTextPaint.setTextSize(mTextSize);
				mTextPaint.setColor(0xff555555);
				mTextPaint.setAntiAlias(true);
				// 得到text绘制范围
				mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 得到绘制icon的宽 
		int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() / 2 - bitmapWidth / 2;
		int top = (getMeasuredHeight() - mTextBound.height()) / 2 - bitmapWidth
				/ 2;
		// 设置icon的绘制范围
		iconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
        
	}

	@Override
	protected void onDraw(Canvas canvas)
	{

		int alpha = (int) Math.ceil((255 * mAlpha));
//		Paint iPaint = new Paint();
//		iPaint.setFilterBitmap(true);//去掉该句会使下面两项设置失效
//		iPaint.setAntiAlias(true);//设置是否使用抗锯齿功能
//		iPaint.setDither(true);//设定是否使用图像抖动处理
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
//		canvas.drawBitmap(icon, null, iconRect, iPaint);
		canvas.drawBitmap(icon, null, iconRect, null);
		
		setupTargetBitmap(alpha);
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
	    canvas.drawBitmap(mBitmap, 0, 0,null);
	    if (drawFlag)
	       drawCirc(canvas);
	}
	
	
	
	private void setupTargetBitmap(int alpha)
	{
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				   Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setFilterBitmap(true);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
	
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(iconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(icon, null, iconRect, mPaint);
		
	}

	private void drawSourceText(Canvas canvas, int alpha)
	{
		mTextPaint.setTextSize(mTextSize);
	
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, iconRect.left + iconRect.width() / 2
				- mTextBound.width() / 2,
				iconRect.bottom + mTextBound.height(), mTextPaint);
	}
	
	private void drawTargetText(Canvas canvas, int alpha)
	{
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		
		canvas.drawText(mText, iconRect.left + iconRect.width() / 2
				- mTextBound.width() / 2,
				iconRect.bottom + mTextBound.height(), mTextPaint);
		
	}

	

	

	public void setIconAlpha(float alpha)
	{
		this.mAlpha = alpha;
		invalidateView();
	}

	private void invalidateView()
	{
		if (Looper.getMainLooper() == Looper.myLooper())
		{
			invalidate();
		} else
		{
			postInvalidate();
		}
	}

	public void setIconColor(int color)
	{
		mColor = color;
	}

	public void setIcon(int resId)
	{
		this.icon = BitmapFactory.decodeResource(getResources(), resId);
		if (iconRect != null)
			invalidateView();
	}

	public void setIcon(Bitmap icon)
	{
		this.icon = icon;
		if (iconRect != null)
			invalidateView();
	}
	
	private void drawCirc(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setDither(true);
		
		canvas.drawCircle(iconRect.right, (iconRect.bottom/3), 10, paint);
		
	}
	
	public void setDrawFlag(Boolean drawFlag){

		this.drawFlag = drawFlag;
		if (iconRect != null)
			  invalidateView();
	}
	
	public boolean getDrawFlag(){
		return drawFlag;
	}
	
	

	private static final String INSTANCE_STATE = "instance_state";
	private static final String STATE_ALPHA = "state_alpha";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putFloat(STATE_ALPHA, mAlpha);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATE_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
		} else
		{
			super.onRestoreInstanceState(state);
		}
		

	}
	
}
