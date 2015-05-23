package com.zgrjb.fragments;

 
 

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.FragmentBase;
/*
 * 我的fragment暂时未实现
 * 同恺负责
 */
import com.zgrjb.model.Customer;
import com.zgrujb.selfdefindui.HeaderLayout;
import com.zgrujb.selfdefindui.HeaderLayout.HeaderStyle;
 
@SuppressLint("SimpleDateFormat")
public class MyInformationFragment extends FragmentBase{
	 
	private NetworkImageView networkImageView;
	private TextView username_tv = null;
	private TextView userage_tv = null;
	private TextView usergender_tv = null;
	private HeaderLayout hlTitleBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("frament", "create MyInformationFragment view");
		return inflater.inflate(R.layout.fragment_myinfor, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i("frament", "create MyInformationFragment Activity");
		Customer customer = BaseAuth.getcustomer();
		initView(customer);
	}

	private void initView(Customer customer) {
		 hlTitleBar = (HeaderLayout) findViewById(R.id.common_actionbar);
	     hlTitleBar.init(HeaderStyle.DEFAULT_TITLE);
	     hlTitleBar.setDefaultTitle("我的信息");
		networkImageView = (NetworkImageView) findViewById(R.id.user_face_image);
		username_tv = (TextView) findViewById(R.id.user_name);
		userage_tv = (TextView) findViewById(R.id.user_age);
		usergender_tv = (TextView) findViewById(R.id.user_gender);
		networkImageView.setImageUrl(BaseConfig.api.base+"/upload/"+customer.getFace(),
				BaseApp.imageLoader);
		username_tv.setText(customer.getUsername());
		userage_tv.setText(customer.getAge());
		usergender_tv.setText(customer.getGender());
	}

	private boolean hidden;

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if (!hidden) {
			refresh();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!hidden) {
			refresh();
		}
	}

	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
