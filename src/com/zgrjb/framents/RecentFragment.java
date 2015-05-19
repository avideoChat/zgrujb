package com.zgrjb.framents;

import org.androidpn.client.ServiceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.FragmentBase;
/*
 * 最近聊天的fragement
 * 桂深负责
 */
import com.zgrjb.ui.UISlashActivity;
import com.zgrjb.ui.UITalkingActivity;

@SuppressLint("SimpleDateFormat")
public class RecentFragment extends FragmentBase {

	private ServiceManager serviceManager = null;
	 
	private Button btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("frament", "create RecentFragment view");
		return inflater.inflate(R.layout.fragment_recent, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		serviceManager = UISlashActivity.serviceManager;
		 
		btn = (Button) findViewById(R.id.sendb);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			 		 startActivity(new Intent(getActivity().getBaseContext(),UITalkingActivity.class));
				 
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
