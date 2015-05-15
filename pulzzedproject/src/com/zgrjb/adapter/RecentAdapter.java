package com.zgrjb.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.zgrjb.R;
import com.zgrjb.domain.RecentModel;

public class RecentAdapter extends BaseAdapter {
    private List<RecentModel> list;
    private Context mContext;
    Paint paint;
	public RecentAdapter(Context mContext,List<RecentModel> list) {
		this.mContext = mContext;
		this.list = list;
		
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<RecentModel> list){
		this.list = list;
		notifyDataSetChanged();
	}
	
	public List<RecentModel> getList(){
		  return list;
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
		RecentModel rm = list.get(position);
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.recent_listview_item, null);
		    holder = new ViewHolder();
		    holder.img = (ImageView) convertView.findViewById(R.id.recent_head_portrait);
		    holder.name = (TextView) convertView.findViewById(R.id.recent_name);
		    holder.content = (TextView) convertView.findViewById(R.id.recent_content);
		    holder.date = (TextView) convertView.findViewById(R.id.recent_date);
		    holder.dot = (View) convertView.findViewById(R.id.recent_dot);
		    holder.badge = new BadgeView(mContext);
		    holder.badge.setTargetView(holder.dot);
		    
		    convertView.setTag(holder);
 		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		 holder.name.setText(rm.getName());
		 holder.content.setText(rm.getContent());
		 holder.date.setText(rm.getDate());
		 if (rm.isVisible()){

			 holder.badge.setText(rm.getNum());
			 holder.badge.setVisibility(View.VISIBLE);
			 
		 }else{
			 holder.badge.setVisibility(View.GONE);
		 }
		
		 return convertView;
	}

	
	
	
	final static class ViewHolder{
		ImageView img;
		TextView name;
		TextView content;
		TextView date;
		View dot;
	    BadgeView badge;
	}

}
