package com.zgrjb.ui;

import com.zgrjb.R;
import com.zgrjb.base.BaseActivity;
import com.zgrujb.selfdefindui.AnimLayer;
import com.zgrujb.selfdefindui.GameView;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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

		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);

		gameView = (GameView) findViewById(R.id.gameView);

		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
			gameView.startGame();
		}});
		
		animLayer = (AnimLayer) findViewById(R.id.animLayer);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void clearScore(){
		score = 0;
		showScore();
	}

	public void showScore(){
		tvScore.setText(score+"");
	}

	public void addScore(int s){
		score+=s;
		showScore();
		setBestScore();
    }
	
	public void setBestScore(){
		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}

	public void saveBestScore(int s){
		Editor e = getPreferences(MODE_PRIVATE).edit();
		e.putInt(SP_KEY_BEST_SCORE, s);
		e.commit();
	}

	public int getBestScore(){
		return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
	}

	public void showBestScore(int s){
		tvBestScore.setText(s+"");
	}
	
	public AnimLayer getAnimLayer() {
		return animLayer;
	}

	private int score = 0;
	private TextView tvScore,tvBestScore;
	private LinearLayout root = null;
	private Button btnNewGame;
	private GameView gameView;
	private AnimLayer animLayer = null;

	private static GameActivity gameActivity = null;

	public static GameActivity getGameActivity() {
		return gameActivity;
	}

	public static final String SP_KEY_BEST_SCORE = "bestScore";
}
