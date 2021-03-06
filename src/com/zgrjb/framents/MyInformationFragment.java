package com.zgrjb.framents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.model.Customer;
import com.zgrjb.ui.UIUpdateInforActivity;

/*
 * 我的fragment暂时未实现
 * 同恺负责
 */

@SuppressLint("SimpleDateFormat")
public class MyInformationFragment extends FragmentBase {
	private NetworkImageView networkImageView;
	private TextView username_tv = null;
	private TextView userage_tv = null;
	private TextView usergender_tv = null;

	private Button updateInfor_btn = null;
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
		networkImageView = (NetworkImageView) findViewById(R.id.user_face_image);
		username_tv = (TextView) findViewById(R.id.user_name);
		userage_tv = (TextView) findViewById(R.id.user_age);
		usergender_tv = (TextView) findViewById(R.id.user_gender);
		networkImageView.setImageUrl(BaseConfig.api.base+"/upload/"+customer.getFace(),
				BaseApp.imageLoader);
		username_tv.setText(customer.getUsername());
		userage_tv.setText(customer.getAge());
		usergender_tv.setText(customer.getGender());
		updateInfor_btn = (Button) findViewById(R.id.updateinfor);
		updateInfor_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startAnimActivity(UIUpdateInforActivity.class);
			}
		});
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
