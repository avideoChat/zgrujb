package com.zgrjb.framents;
 

 

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.model.MsgRecord;
import com.zgrjb.utils.MsgDBUtils;
 

@SuppressLint("SimpleDateFormat")
public class DiscoveryFragment extends FragmentBase implements View.OnClickListener{
    private Button insert,insertAll,delete,deleteAll,show,showAll;
    private TextView tv;
	private MsgDBUtils utils = MsgDBUtils.getInstance(); 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		return inflater.inflate(R.layout.fragment_discovery, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init(); 
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
	
	private void init(){
		utils.createDB();
		tv = (TextView) findViewById(R.id.tv_show);
		insert = (Button) findViewById(R.id.btn_insert);
		insertAll = (Button) findViewById(R.id.btn_insert_all);
		delete = (Button) findViewById(R.id.btn_delete);
		deleteAll = (Button) findViewById(R.id.btn_delete_all);
		show = (Button) findViewById(R.id.btn_search);
		showAll = (Button) findViewById(R.id.btn_search_all);
		
		insert.setOnClickListener(this);
		insertAll.setOnClickListener(this);
		delete.setOnClickListener(this);
		deleteAll.setOnClickListener(this);
		show.setOnClickListener(this);
		showAll.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		  case R.id.btn_insert:
			 
			  MsgRecord mr = new MsgRecord();
			  mr.setSender(1);
			  mr.setReceiver(2);
			  mr.setChatId(10);
			  utils.insert(mr);
			break;
		  case R.id.btn_insert_all:
			  
			  List<MsgRecord> list = new ArrayList<MsgRecord>();
			  for(int i=0;i<20;i++){
				  MsgRecord m = new MsgRecord();
				  m.setSender(i+10);
				  m.setReceiver(i+12);
				  m.setChatId(5);
				  list.add(m);
			  }
			  utils.insert(list);
				break;
		  case R.id.btn_delete:
			 
			  utils.delete(MsgDBUtils.getInstance().getOne());
				break;
		  case R.id.btn_delete_all:
			  
			  utils.deleteAll();
				break;
		  case R.id.btn_search:
			 Log.v("datas",""+utils.searchByChatId(10));
			 tv.setText(utils.searchByChatId(5)+""); 
				break;
		  case R.id.btn_search_all:
			  Log.v("alldatas",""+utils.loardAllByChatId(10));
			  tv.setText(utils.loardAllByChatId(10)+"");
			  break;
		
		}
		
	}
	

	

}
