package com.zgrjb.framents;

 

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zgrjb.R;
import com.zgrjb.adapter.RecentAdapter;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.model.RecentModel;
import com.zgrjb.ui.UIChatActivity;

 /*
  * 最近聊天的fragement
  * 桂深负责
  */

@SuppressLint("SimpleDateFormat")
public class RecentFragment extends FragmentBase{
    private ListView lv;
	//private RecentListView lv;
	private RecentAdapter adapter;
	private boolean flag=false;
	
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
		 lv = (ListView)v.findViewById(R.id.recent_lv);
		 adapter = new RecentAdapter(getActivity(),initData());
		 lv.setAdapter(adapter);
		 final List<RecentModel> tempList = adapter.getList();
		 lv.setOnItemClickListener(new OnItemClickListener(){
         
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				   
				    if(tempList.get(position).isVisible()){
				    	tempList.get(position).setVisible(false);
				    }else{
				    	tempList.get(position).setVisible(true);
				    }
				    Toast.makeText(getActivity(), tempList.get(position).getNum()+"-"+position, Toast.LENGTH_LONG).show();
				    adapter.updateListView(tempList);
				    startAnimActivity(com.zgrjb.ui.UIChatActivity.class);
			        
			}
			 
		 });
		 
     }
	 
	 
	 
	 private List<RecentModel> initData(){
		 List<RecentModel> list = new ArrayList<RecentModel>();
		 
		 for (int i=0;i<20;i++){
			 RecentModel rm = new RecentModel();
			 rm.setName("aaaaaa"+i);
			 rm.setContent("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+i);
			// rm.setDate("星期六");
			 rm.setDate("2015-04-28");
			 rm.setNum(""+i);
			 rm.setVisible(true);
			 list.add(rm);
		 }
		 
		 
		 return list;
	 }
	

	

}
