package com.zgrjb.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.zgrjb.R;
import com.zgrjb.model.LastMsgRecord;
import com.zgrjb.utils.CacheUtil;

public class RecentAdapter extends BaseAdapter {
    private List<LastMsgRecord> list;
    private Context mContext;
    private ListSizeListenner mListSizeListenner;
    private ListView mListView;
    private final CacheUtil util = CacheUtil.getInstance();
    public interface ListSizeListenner{
    	public void SizeChange(List list);
     }
    
    public void setListSizeListenner(ListSizeListenner mListSizeListenner){
    	this.mListSizeListenner = mListSizeListenner;
    }
    
	public RecentAdapter(Context mContext,List<LastMsgRecord> list) {
		this.mContext = mContext;
		this.list = list;
		
	}
	
//	public RecentAdapter(Context mContext,List<LastMsgRecord> list,ListView mListView) {
//		this.mContext = mContext;
//		this.list = list;
//		this.mListView = mListView;
//	
//	}

	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<LastMsgRecord> list){
		this.list = list;
		mListSizeListenner.SizeChange(list);
		notifyDataSetChanged();
	}
	
	public List<LastMsgRecord> getList(){
		  return list;
	}
	
	public void updateItem(List<LastMsgRecord> tempList){
		for (LastMsgRecord tempItem:tempList){
			for (LastMsgRecord temp:list){
				if(temp.getName().equals(tempItem.getName())){
					list.remove(temp);
					list.add(list.indexOf(temp), tempItem);
				}
			}
		}
		notifyDataSetChanged();
	}
	public void update(LastMsgRecord lmr){
		for(LastMsgRecord temp:list){
			if(temp.getName().equals(lmr.getName())){
				list.remove(temp);
				list.add(0, lmr);
				notifyDataSetChanged();
				return;
			}
		}
		list.add(0, lmr);
		notifyDataSetChanged();
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LastMsgRecord lmr = list.get(position);
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.recent_listview_item, null);
		    holder = new ViewHolder();
		    holder.contactHeadPortrait = (ImageView) convertView.findViewById(R.id.recent_head_portrait);
		    holder.contactHeadPortrait.setTag(lmr.getHeadPortraitUrl());
		    
		    holder.name = (TextView) convertView.findViewById(R.id.recent_name);
		    holder.content = (TextView) convertView.findViewById(R.id.recent_content);
		    holder.date = (TextView) convertView.findViewById(R.id.recent_date);
		   
		    holder.badge = new BadgeView(mContext);
		    holder.badge.setTargetView(holder.content);

		    holder.badge.setBadgeMargin(0, 0, 8, 0);
		    
		    convertView.setTag(holder);
 		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		 
		
		 if(lmr.getChatId()!=null && lmr.getChatId().length()>0){
			 holder.name.setText(lmr.getName()+"-"+lmr.getChatId());
		 }else{
			 holder.name.setText(lmr.getName());
		 }
		 
		 holder.content.setText(lmr.getContent());
		 holder.date.setText(lmr.getTime());
		 if (lmr.getUnReadNum()!=0){

			 holder.badge.setBadgeCount(lmr.getUnReadNum());
			 holder.badge.setVisibility(View.VISIBLE);
			 
		 }else{
			 holder.badge.setVisibility(View.GONE);
		 }
		 util.loadBitmaps(holder.contactHeadPortrait, lmr.getHeadPortraitUrl());
		 return convertView;
	}

	
	
	
	final static class ViewHolder{
		ImageView contactHeadPortrait;
		TextView name;
		TextView content;
		TextView date;
	    BadgeView badge;
	}
	
	


}
