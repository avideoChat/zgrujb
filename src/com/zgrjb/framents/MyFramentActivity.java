package com.zgrjb.framents;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
 
import com.zgrjb.base.BaseActivity;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.BaseMessage;
/*
  * 容纳四个fragement的activity，主要activity
  * 辉明负责
  */
import com.zgrjb.base.BaseUi;
import com.zgrjb.model.Friend;
import com.zgrjb.ui.UISlashActivity;

@SuppressLint("SimpleDateFormat")
public class MyFramentActivity extends BaseUi implements EventListener{

	private Button[] mTabs;
	private ContactFragment contactFragment;
	public static MyFramentActivity instance;
	private MyInformationFragment myInformationFragment;
	private RecentFragment recentFragment;
   
	private Fragment[] fragments;
	private int index;
	private int currentTabIndex;
	
	 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frament_footer);
		instance = this;
		Log.i("frament", "create MyFramentActivity view");
		initView();
		initTab();
		
	}

	private void initView(){
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_message);
		mTabs[1] = (Button) findViewById(R.id.btn_contract);		 
		mTabs[2] = (Button) findViewById(R.id.btn_myinfor);		 	 
		mTabs[0].setSelected(true);
	}
	
	private void initTab(){
		contactFragment = new ContactFragment();
		recentFragment = new RecentFragment();
	 
		myInformationFragment = new MyInformationFragment();
		fragments = new Fragment[] {recentFragment, contactFragment ,myInformationFragment };
 
		getSupportFragmentManager().beginTransaction().
		add(R.id.fragment_container, recentFragment).
		add(R.id.fragment_container, contactFragment).
		 
		add(R.id.fragment_container, myInformationFragment).
		hide(contactFragment).
	 
		hide(myInformationFragment).		
		show(recentFragment).commit();
	}
	
	
	 
	public void onTabSelect(View view) {
		switch (view.getId()) {
		case R.id.btn_message:
			index = 0;
			break;
		case R.id.btn_contract:
			index = 1;
			break;
		 
		case R.id.btn_myinfor:
			index = 2;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
	 
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	 
		 
		
	}
	
	 

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 
	}
	
	 
	 
	 
	private static long firstTime;
	 
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			showLongToast("再按一次退出");
		}
		firstTime = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("result", "destory frament");
		 
		 UISlashActivity.serviceManager.stopService();
	 
	}
	
}
