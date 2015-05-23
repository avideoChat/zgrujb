package com.zgrjb.ui;

import java.io.IOException;

import com.zgrjb.R;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.selfdefindui.AnimLayer;
import com.zgrjb.selfdefindui.GameView;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class GameActivity extends BaseActivity{
	public GameActivity() {
		gameActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		root = (LinearLayout) findViewById(R.id.container);
		root.setBackgroundColor(0xfffaf8ef);

		tvClue = (TextView) findViewById(R.id.game_clue);
        

		gameView = (GameView) findViewById(R.id.gameView);

		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
			gameView.startGame();
		}});
		
		btnCancelGame = (Button)findViewById(R.id.btnCancelGame);
		btnCancelGame.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
			} catch (IOException e) {
				throw new RuntimeException(e);
		//		e.printStackTrace();
			}
		}});
		
		animLayer = (AnimLayer) findViewById(R.id.animLayer);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	
	public AnimLayer getAnimLayer() {
		return animLayer;
	}

	private int score = 0;
	private TextView tvClue;
	private LinearLayout root = null;
	private Button btnNewGame,btnCancelGame;
	private GameView gameView;
	private AnimLayer animLayer = null;

	private static GameActivity gameActivity = null;

	public static GameActivity getGameActivity() {
		return gameActivity;
	}

	
}
