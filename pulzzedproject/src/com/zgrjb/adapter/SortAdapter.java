package com.zgrjb.adapter;

import java.util.List;

import com.zgrjb.R;
import com.zgrjb.domain.SortModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;



public class SortAdapter extends BaseAdapter implements SectionIndexer{
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
	if (list.get(position).getRemark().trim().length()>0){
		viewHolder.sortName.setText(list.get(position).getRemark()+"( "+list.get(position).getName()+" )");
	}else {
		viewHolder.sortName.setText(list.get(position).getName());
	}
	
	viewHolder.groupName.setText(list.get(position).getGroup());
	//viewHolder.sortHeadPortrait.setImageBitmap(list.get(position).getHeadPortrait());
	
  } 
	return view;

}
	


	final static class ViewHolder {
		TextView sortName;
		TextView groupName;
		ImageView sortHeadPortrait;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		if (list!=null)
		  return list.get(position).getSortLetters().charAt(0);
	    return -1;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		if (list!=null){
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		}
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
