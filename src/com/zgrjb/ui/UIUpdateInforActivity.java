package com.zgrjb.ui;

import org.androidpn.demoapp.DemoAppActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zgrjb.R;
import com.zgrjb.adapter.InfiniteLoopViewPagerAdapter;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.base.BaseUi;
import com.zgrjb.framents.MyFramentActivity;
 
/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UIUpdateInforActivity extends BaseUi {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_start);
		 
	}

	
}
