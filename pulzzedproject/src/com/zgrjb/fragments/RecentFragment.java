package com.zgrjb.fragments;

 

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.adapter.RecentAdapter;
import com.zgrjb.adapter.RecentAdapter.ListSizeListenner;
import com.zgrjb.application.BaseConfig.BroadcastTag;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.model.ChatMsgModel;
import com.zgrjb.model.LastMsgRecord;
import com.zgrjb.utils.ImagesArray;
import com.zgrujb.selfdefindui.HeaderLayout;
import com.zgrujb.selfdefindui.HeaderLayout.HeaderStyle;

 /*
  * 最近聊天的fragement
  * 桂深负责
  */

@SuppressLint("SimpleDateFormat")
public class RecentFragment extends FragmentBase{
    private ListView lv;
    private TextView tv;
	private RecentAdapter adapter;
	private boolean flag=false;
	private List<LastMsgRecord> list;
	private LocalBroadcastManager mLocalBroadcastManager;
	private BroadcastReceiver mReceiver;
	private HeaderLayout hlTitleBar;
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
		IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastTag.EXIT);
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		mReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(BroadcastTag.EXIT)){
					LastMsgRecord lmr = (LastMsgRecord)intent.getSerializableExtra(BroadcastTag.CHAT_ID);
					
					
					adapter.update(lmr);
					
				}
				
			}
			
		};
	   mLocalBroadcastManager.registerReceiver(mReceiver, filter);
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
	     hlTitleBar = (HeaderLayout) v.findViewById(R.id.common_actionbar);
	     hlTitleBar.init(HeaderStyle.DEFAULT_TITLE);
	     hlTitleBar.setDefaultTitle("最近会话");
		 
		 lv = (ListView)v.findViewById(R.id.recent_lv);
		 list = initData();
		 adapter = new RecentAdapter(getActivity(),list);
		 adapter.setListSizeListenner(new ListSizeListenner(){

			@Override
			public void SizeChange(List list) {
				  changeView(list);
		    }
			 
		 });
		 lv.setAdapter(adapter);
		 
		 final List<LastMsgRecord> tempList = adapter.getList();
		 lv.setOnItemClickListener(new OnItemClickListener(){
         
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				   
				    if(tempList.get(position).getUnReadNum()!=0){
				    	tempList.get(position).setUnReadNum(0);
				    }else{
				    	tempList.get(position).setUnReadNum(10);
				    }
//				    Toast.makeText(getActivity(), tempList.get(position).getNum()+"-"+position, Toast.LENGTH_LONG).show();
				    adapter.updateListView(tempList);
//				//    startAnimActivity(com.zgrjb.ui.UIChatActivity.class);
				  //  startActivityWithString(com.zgrjb.ui.UIChatActivity.class,tempList.get(position).getName());
//				    list.clear();
//				    adapter.updateListView(list);
			        
			}
			 
		 });
		 
		 tv = (TextView)v.findViewById(R.id.recent_tv); 
		 tv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				list = initData();
				adapter.updateListView(list);
				
				
			}
			 
		 });
     }
	 
	 private void changeView(List list){
		 if (!list.isEmpty()){
			 tv.setVisibility(View.GONE);
			 lv.setVisibility(View.VISIBLE);
		 }else{
			 tv.setVisibility(View.VISIBLE);
			 lv.setVisibility(View.GONE);
		 }
	 }
	 
	 
	 private List<LastMsgRecord> initData(){
		 List<LastMsgRecord> list = new ArrayList<LastMsgRecord>();
		 
		 for (int i=0;i<20;i++){
			 LastMsgRecord rm = new LastMsgRecord();
			 rm.setHeadPortraitUrl(ImagesArray.imageThumbUrls[i]);
			 rm.setName("aaaaaa"+i);
			 rm.setContent("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+i);
			// rm.setDate("星期六");
			 rm.setTime("2015-04-28");
			 rm.setUnReadNum(i);
			
			 list.add(rm);
		 }
		 LastMsgRecord rm = new LastMsgRecord();
		 rm.setName("人马");
		 rm.setContent("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		// rm.setDate("星期六");
		 rm.setTime("2015-04-28");
		 rm.setUnReadNum(0);
		 rm.setHeadPortraitUrl(ImagesArray.imageThumbUrls[20]);
		 list.add(rm);
		 
		 
		 return list;
	 }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocalBroadcastManager.unregisterReceiver(mReceiver);
		
	}
	


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	

}
