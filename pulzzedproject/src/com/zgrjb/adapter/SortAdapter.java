package com.zgrjb.adapter;

import java.util.List;

import com.zgrjb.R;
import com.zgrjb.adapter.ContanctAdapter.ViewHolder;
import com.zgrjb.domain.SortModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter{

	private List<SortModel> list = null;
	private Context mContext;
	
	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		if (list!=null){
		  return this.list.size();
		}
		return 0;
	}

	public Object getItem(int position) {
		if (list!=null)
		   return list.get(position);
		
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.sort_list_item, null);
			
			viewHolder.sortName = (TextView) view.findViewById(R.id.sort_name);
			viewHolder.groupName = (TextView) view.findViewById(R.id.sort_group_name);
			viewHolder.sortHeadPortrait = (ImageView) view.findViewById(R.id.sort_headportrait);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
  if (list!=null){
	if (mContent.getRemark().trim().length()>0){
		viewHolder.sortName.setText(mContent.getRemark()+"( "+mContent.getName()+" )");
	}else {
		viewHolder.sortName.setText(mContent.getName());
	}
	
	viewHolder.groupName.setText(mContent.getGroup());
	//viewHolder.sortHeadPortrait.setImageBitmap(mContent.getHeadPortrait());
	
  } 
  
      
	return view;

}
	


	final static class ViewHolder {
		
		TextView sortName;
		TextView groupName;
		ImageView sortHeadPortrait;
	}




}
