package com.zgrjb.ui;

import org.androidpn.client.ServiceManager;
import org.androidpn.demoapp.DemoAppActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zgrjb.R;
import com.zgrjb.adapter.InfiniteLoopViewPagerAdapter;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.framents.MyFramentActivity;
import com.zgrjb.selfdefindui.InfiniteLoopViewPager;
/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UIStartActivity extends BaseActivity {
	private InfiniteLoopViewPager viewPager;
	private int[] imageViewIds;
	private ImageView[] imageViews;
	private InfiniteLoopViewPagerAdapter pagerAdapter;
	private Handler mHandler;
	private int sleepTime = 1000;
	public boolean isRun = false;
	public boolean isDown = false;
 
	private Button login_btn;
	private Button register_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_start);
		
	 	mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,
							true);
					if (isRun && !isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;

				case 1:
					if (isRun && !isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;
				}

			}
		};
		initView();
	}

	private void initView() {
		imageViewIds = new int[] { R.drawable.shuffling_figure1, R.drawable.shuffling_figure2, R.drawable.shuffling_figure3
				 };
		imageViews = new ImageView[imageViewIds.length];
		for (int i = 0; i < imageViewIds.length; i++) {
			imageViews[i] = new ImageView(this);
 
			imageViews[i].setImageResource(imageViewIds[i]);
			 
		}
		viewPager = (InfiniteLoopViewPager) findViewById(R.id.viewpager);
		pagerAdapter = new InfiniteLoopViewPagerAdapter(
				new MyLoopViewPagerAdatper());
		viewPager.setInfinateAdapter(this, mHandler, pagerAdapter);
		login_btn = (Button) findViewById(R.id.login);
		register_btn = (Button) findViewById(R.id.register);
		login_btn.setOnClickListener(new MyButtonOnclikListener());
		register_btn.setOnClickListener(new MyButtonOnclikListener());
	}

	public class MyButtonOnclikListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.login:
				 
				forward(UILoginActivity.class );
				break;
			case R.id.register:
				forward(UIRegisterFirstActivity.class);
				break;

			 
			}
		}

	}

	private class MyLoopViewPagerAdatper extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (View) object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// return super.instantiateItem(container, position);
			container.addView(imageViews[position]);
			return imageViews[position];
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		isRun = false;
		mHandler.removeCallbacksAndMessages(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		isRun = true;
		mHandler.sendEmptyMessageDelayed(0, sleepTime);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			 
			UISlashActivity.serviceManager.stopService();	
			finish();
		}
		return true;
	}
}
