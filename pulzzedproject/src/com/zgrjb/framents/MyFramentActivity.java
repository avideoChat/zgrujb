package com.zgrjb.framents;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.zgrjb.R;
import com.zgrjb.base.BaseActivity;
import com.zgrujb.selfdefindui.BottomTabView;
 /*
  * 容纳四个fragement的activity，主要activity
  * 辉明负责
  */



@SuppressLint("SimpleDateFormat")
public class MyFramentActivity extends BaseActivity implements EventListener,OnPageChangeListener,OnClickListener{

	private ViewPager mViewPager;
	private ContactFragment contactFragment;
	private DiscoveryFragment discoveryFragment;
	private MyInformationFragment myInformationFragment;
	private RecentFragment recentFragment;
    
	private Fragment[] fragments;
	private FragmentPagerAdapter mAdapter;
	private int index;
	private int currentTabIndex;
	
	private List<BottomTabView> views = new ArrayList<BottomTabView>();
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frament_footer);
		 
		initView();
		
	}

	private void initView(){
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		initTab();
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return fragments.length;
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return fragments[arg0];
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		BottomTabView one = (BottomTabView) findViewById(R.id.id_indicator_one);
		BottomTabView two = (BottomTabView) findViewById(R.id.id_indicator_two);
		BottomTabView three = (BottomTabView) findViewById(R.id.id_indicator_three);
		BottomTabView four = (BottomTabView) findViewById(R.id.id_indicator_four);
	    
		views.add(one);
		views.add(two);
		views.add(three);
		views.add(four);
		
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		
		one.setIconAlpha(1.0f);
		
		
	
	}
	
	private void initTab(){
		contactFragment = new ContactFragment();
		recentFragment = new RecentFragment();
		discoveryFragment = new DiscoveryFragment();
		myInformationFragment = new MyInformationFragment();
		fragments = new Fragment[] {recentFragment, contactFragment, discoveryFragment,myInformationFragment };
        
		
//		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, recentFragment).
//			add(R.id.fragment_container, contactFragment).add(R.id.fragment_container, myInformationFragment).hide(contactFragment).show(recentFragment).commit();
	}
	
	
	 
//	public void onTabSelect(View view) {
//		switch (view.getId()) {
//		case R.id.btn_message:
//			index = 0;
//			break;
//		case R.id.btn_contract:
//			index = 1;
//			break;
//		case R.id.btn_discorvey:
//			index = 2;
//			break;
//		case R.id.btn_myinfor:
//			index = 3;
//			break;
//		}
//		if (currentTabIndex != index) {
//			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
//			trx.hide(fragments[currentTabIndex]);
//			if (!fragments[index].isAdded()) {
//				trx.add(R.id.fragment_container, fragments[index]);
//			}
//			trx.show(fragments[index]).commit();
//		}
//		mTabs[currentTabIndex].setSelected(false);
//	 
//		mTabs[index].setSelected(true);
//		currentTabIndex = index;
//	}

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
		 
	 
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset > 0)
		{
			BottomTabView left = views.get(position);
			BottomTabView  right = views.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			views.get(0).setIconAlpha(1.0f);
			
			views.get(0).setDrawFlag(!views.get(0).getDrawFlag());
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			views.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			views.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			views.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
		
	}
	
	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).setIconAlpha(0);
		}
	}
	
}
