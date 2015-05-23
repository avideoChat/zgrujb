package com.zgrujb.selfdefindui;

import com.zgrjb.R;
import com.zgrjb.utils.DrawableUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.AbsListView.LayoutParams;

public class ClearEditText extends EditText implements TextWatcher,  
OnFocusChangeListener{
    Drawable left;
    Drawable right;
    
    private boolean hasFocus = false;
    
    private int xUp = 0;
    private int xDown = 0;
    //可以获取edittext上下左右四个位置的drawable，右边下标是二，左边是0
    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_RIGHT = 2;
    
	public ClearEditText(Context context) {
		super(context);
		initWidget();
		
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWidget();
	
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initWidget();
		Resources res = getResources();
	}
	
	private void initWidget(){
		left = getCompoundDrawables()[DRAWABLE_LEFT];
		right = getCompoundDrawables()[DRAWABLE_RIGHT];
		int side1 = Math.min(left.getIntrinsicWidth(), right.getIntrinsicHeight());
		int side2 = Math.min(right.getIntrinsicWidth(), right.getIntrinsicHeight());
		int side = Math.min(side1, side2);
		left = DrawableUtils.zoomDrawable(left, side, side);
		right = DrawableUtils.zoomDrawable(right, side,side);

		
		initDatas();
	}
    
	private void initDatas(){
		setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
		addListeners();
	}
	
	private void addListeners(){
		setOnFocusChangeListener(this);  
        addTextChangedListener(this);  
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFocus = hasFocus;
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int count,
			int after) {
		if (hasFocus){
		  if (TextUtils.isEmpty(getText().toString().trim())){
			  setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
		  }else {
			  if (right == null){
				  right = (BitmapDrawable)getCompoundDrawables()[DRAWABLE_RIGHT];
			  }
			  setCompoundDrawablesWithIntrinsicBounds(left,null,right,null);
		  }
		}
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	 switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			  xDown = (int) event.getX();
			  break;
		case MotionEvent.ACTION_UP:
			 xUp = (int) event.getX();  
			 if ((getWidth()-xUp)<=getCompoundPaddingRight() && (xUp-xDown)<10){
				 if(!TextUtils.isEmpty(getText().toString().trim())){
					 setText("");
				 }
			 }
			 break;
		}
		return super.onTouchEvent(event);
	}

	
    public void reset(){
    	setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
    }
	
	
}
