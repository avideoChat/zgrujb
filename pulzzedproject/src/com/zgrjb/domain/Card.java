package com.zgrjb.domain;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;



public class Card extends FrameLayout {

	public Card(Context context) {
		super(context);

		LayoutParams lp = null;

		background = new View(getContext());
		lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		background.setBackgroundColor(0x33ffffff);
		addView(background, lp);

		label = new TextView(getContext());
		label.setTextSize(28);
		label.setGravity(Gravity.CENTER);
		label.setTextColor(Color.BLACK);

		lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);

		setNum(9);
	}


	private int num = 9;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;

		if (num==9) {
			label.setText("");
		}else{
			label.setText(num+"");
		}

		switch (num) {
		case 1:
			label.setBackgroundColor(0x00000000);
			break;
		case 2:
			label.setBackgroundColor(0xffeee4da);
			break;
		case 3:
			label.setBackgroundColor(0xffede0c8);
			break;
		case 4:
			label.setBackgroundColor(0xfff2b179);
			break;
		case 5:
			label.setBackgroundColor(0xfff59563);
			break;
		case 6:
			label.setBackgroundColor(0xfff67c5f);
			break;
		case 7:
			label.setBackgroundColor(0xfff65e3b);
			break;
		case 8:
			label.setBackgroundColor(0xffedcf72);
			break;
		case 9:
			label.setBackgroundColor(0xffedcc61);
			break;
		}
	}

	public boolean equals(Card o) {
		return getNum()==o.getNum();
	}
	
	protected Card clone(){
		Card c= new Card(getContext());
		c.setNum(getNum());
		return c;
	}

	public TextView getLabel() {
		return label;
	}
	
	private TextView label;
	private View background;
}
