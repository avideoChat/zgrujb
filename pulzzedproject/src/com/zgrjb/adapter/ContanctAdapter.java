package com.zgrjb.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.model.SortModel;
import com.zgrjb.utils.CacheUtil;





public class ContanctAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;
	
	private ListView mListView;
	
    private final CacheUtil util = CacheUtil.getInstance();
	
	public ContanctAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	

	public ContanctAdapter(Context mContext, List<SortModel> list,ListView mListView) {
		this.mContext = mContext;
		this.list = list;
		this.mListView = mListView;
        initCacheUtil();
	}
	
	private void initCacheUtil(){
		util.init(mContext,"headThum",10);
		util.setCacheSize(0.125f);
		util.setHolderView(mListView);
		util.setWidthAndHeight(70, 70);
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
			view = LayoutInflater.from(mContext).inflate(R.layout.contancts_list_item, null);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.sort_log);
			viewHolder.contactName = (TextView) view.findViewById(R.id.sort_name);
			viewHolder.contactHeadPortrait = (ImageView) view.findViewById(R.id.sort_headportrait);
			viewHolder.contactHeadPortrait.setTag(mContent.getHeadPortraitUrl());
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
  if (list!=null){
	if (mContent.getLocalName()!=null && mContent.getLocalName().trim().length()>0){
		viewHolder.contactName.setText(mContent.getLocalName());
	}else {
		viewHolder.contactName.setText(mContent.getName());
	}
	
	util.loadBitmaps(viewHolder.contactHeadPortrait, mContent.getHeadPortraitUrl());
	
	
	
    } 
  
      //根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	return view;

}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView contactName;
		ImageView contactHeadPortrait;
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

	
	
	public void fluchCache()
	{   
		util.fluchCache();
	}
	
	public void cancelAllTasks(){
		util.cancelAllTasks();
	}
	
	public void deleteCache(){
		try {
			util.deleteCache();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public void closeCache(){
		 try {
			util.closeCache();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	
}
