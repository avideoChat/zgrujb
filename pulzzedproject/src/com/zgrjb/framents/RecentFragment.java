package com.zgrjb.framents;

 

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgrjb.R;
import com.zgrjb.base.FragmentBase;
import com.zgrujb.selfdefindui.ClearEditText;
 /*
  * 最近聊天的fragement
  * 桂深负责
  */

@SuppressLint("SimpleDateFormat")
public class RecentFragment extends FragmentBase{

	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recent, container, false);
		init(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 	 
	}

	 


	private boolean hidden;
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if(!hidden){
			refresh();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!hidden){
			refresh();
		}
	}
	
	public void refresh(){
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					 
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private void init(View v){
		 final ClearEditText edt= (ClearEditText)v.findViewById(R.id.edt_search);
        
	 }
	

	

}
