package com.zgrujb.selfdefindui;

import java.util.ArrayList;
import java.util.List;

import com.zgrjb.application.GameConfig;
import com.zgrjb.model.Card;
import com.zgrjb.ui.GameActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


public class GameView extends LinearLayout {
	private Card[][] cardsMap = new Card[GameConfig.LINES][GameConfig.LINES];
	private List<Point> emptyPoints = new ArrayList<Point>();
	private List<Integer> answer = new ArrayList<Integer>();//答案
	private List<Integer> init = new ArrayList<Integer>();//初始化的数字
	public GameView(Context context) {
		super(context);

		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initGameView();
	}
	private void setInit(){
		if(init.isEmpty()){
		for(int i=1;i<=(GameConfig.LINES*GameConfig.LINES);i++){
			init.add(i);
		 }
		}else{
			init.clear();
			for(int i=1;i<=(GameConfig.LINES*GameConfig.LINES);i++){
				init.add(i);
			 }
		}
	}
	private void setAnswer(){
		if(answer.isEmpty()){
			for(int i=1;i<=(GameConfig.LINES*GameConfig.LINES);i++){
				answer.add(i);
			 }
			}else{
				answer.clear();
				for(int i=1;i<=(GameConfig.LINES*GameConfig.LINES);i++){
					answer.add(i);
				 }
			}
	}
	private void initGameView(){
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(0xffbbada0);


		setOnTouchListener(new View.OnTouchListener() {

			private float startX,startY,offsetX,offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX()-startX;
					offsetY = event.getY()-startY;


					if (Math.abs(offsetX)>Math.abs(offsetY)) {
						if (offsetX<-5) {
							swipeLeft();
						}else if (offsetX>5) {
							swipeRight();
						}
					}else{
						if (offsetY<-5) {
							swipeUp();
						}else if (offsetY>5) {
							swipeDown();
						}
					}

					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		GameConfig.CARD_WIDTH = (Math.min(w, h)-10)/GameConfig.LINES;

		addCards(GameConfig.CARD_WIDTH,GameConfig.CARD_WIDTH);

		startGame();
	}

	private void addCards(int cardWidth,int cardHeight){

		Card c;

		LinearLayout line;
		LinearLayout.LayoutParams lineLp;
		
		for (int y = 0; y < GameConfig.LINES; y++) {
			line = new LinearLayout(getContext());
			lineLp = new LinearLayout.LayoutParams(-1, cardHeight);
			addView(line, lineLp);
			
			for (int x = 0; x < GameConfig.LINES; x++) {
				c = new Card(getContext());
				line.addView(c, cardWidth, cardHeight);

				cardsMap[x][y] = c;
			}
		}
	}

	public void startGame(){
        
		GameActivity aty = GameActivity.getGameActivity();
	
		setInit();
		setAnswer();

		for (int y = 0; y < GameConfig.LINES; y++) {
			for (int x = 0; x < GameConfig.LINES; x++) {
				cardsMap[x][y].setNum(9);
			}
		}
		
		for (int n=0;n<(GameConfig.LINES*GameConfig.LINES);n++)
	//	    addRandomNum();
			test();
        
	}
	
	

	private void addRandomNum(){
        
		if(!emptyPoints.isEmpty())
		    emptyPoints.clear();

		for (int y = 0; y < GameConfig.LINES; y++) {
			for (int x = 0; x < GameConfig.LINES; x++) {
				if (cardsMap[x][y].getNum()==9) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		if (emptyPoints.size()>0) {
            int index = (int)(Math.random()*emptyPoints.size());
            Log.v("Index", ""+index);
			Point p = emptyPoints.remove(index);
			Log.v("Num", ""+init.get(index));
			cardsMap[p.x][p.y].setNum(init.remove(0));
			
			GameActivity.getGameActivity().getAnimLayer().createScaleTo1(cardsMap[p.x][p.y]);

		}
	}
   private void test(){

		if(!emptyPoints.isEmpty())
		    emptyPoints.clear();

		for (int y = 0; y < GameConfig.LINES; y++) {
			for (int x = 0; x < GameConfig.LINES; x++) {
				if (cardsMap[x][y].getNum()==9) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}
		if (emptyPoints.size()>0) {
            
			Point p = emptyPoints.remove(0);
			
			cardsMap[p.x][p.y].setNum(init.remove(0));
			
			GameActivity.getGameActivity().getAnimLayer().createScaleTo1(cardsMap[p.x][p.y]);

		}
   }

	private void swipeLeft(){

		boolean merge = false;

		for (int y = 0; y < GameConfig.LINES; y++) {
			for (int x = 0; x < GameConfig.LINES; x++) {

				for (int x1 = x+1; x1 < GameConfig.LINES; x1++) {
					if (cardsMap[x1][y].getNum()>0) {

						if (cardsMap[x][y].getNum()==9) {

							GameActivity.getGameActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(9);

							x--;
						
							merge = true;
							if (merge) {
								Log.v("Left","Left$$");
								checkComplete();
							}
							return;

						}

						
					}
				}
			}
		}
       
		
	}
	private void swipeRight(){

		boolean merge = false;

		for (int y = 0; y < GameConfig.LINES; y++) {
			for (int x = GameConfig.LINES-1; x >=0; x--) {

				for (int x1 = x-1; x1 >=0; x1--) {
					if (cardsMap[x1][y].getNum()>0) {

						if (cardsMap[x][y].getNum()==9) {
							GameActivity.getGameActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(9);

							x++;
						
							merge = true;
					   if (merge) {
								Log.v("Right","Right$$");
								checkComplete();
							}
							return;
						}

						
					}
				}
			}
		}

		
	}
	private void swipeUp(){

		boolean merge = false;

		for (int x = 0; x < GameConfig.LINES; x++) {
			for (int y = 0; y < GameConfig.LINES; y++) {

				for (int y1 = y+1; y1 < GameConfig.LINES; y1++) {
					if (cardsMap[x][y1].getNum()>0) {

						if (cardsMap[x][y].getNum()==9) {
							GameActivity.getGameActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(9);

							y--;

							merge = true;
							if (merge) {
								Log.v("Up","Up$$");
								checkComplete();
							}
							return;
						}

						

					}
				}
			}
		}

		
	}
	private void swipeDown(){

		boolean merge = false;

		for (int x = 0; x < GameConfig.LINES; x++) {
			for (int y = GameConfig.LINES-1; y >=0; y--) {

				for (int y1 = y-1; y1 >=0; y1--) {
					if (cardsMap[x][y1].getNum()>0) {

						if (cardsMap[x][y].getNum()==9) {
							GameActivity.getGameActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(9);

							y++;
		
							merge = true;
							if (merge) {
								Log.v("Down","Down$$");
								checkComplete();
							}
							return;
						}

						
					}
				}
			}
		}

		
	}

	private void checkComplete(){
        int n = 0;
        int z = 0;
		boolean complete = true;
		Log.v("HELLO","@!#!%$@%");
		ALL:
			for (int y = 0; y < GameConfig.LINES; y++) {
				for (int x = 0; x < GameConfig.LINES; x++) {
				  	n = cardsMap[x][y].getNum();
				  	z = answer.get(0);
					if (cardsMap[x][y].getNum()!=answer.remove(0)) {

						complete = false;
						
						setAnswer();
						break ALL;
					}
					Log.v("getNum",""+cardsMap[x][y].getNum());
				}
			}
        
		if (complete) {
			new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).show();
		}

	}
    
	
	
}
