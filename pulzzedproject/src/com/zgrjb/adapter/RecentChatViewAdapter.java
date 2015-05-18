package com.zgrjb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.model.ChatMsgModel;

public class RecentChatViewAdapter extends BaseAdapter {

	private static final String TAG = RecentChatViewAdapter.class.getSimpleName();

	private List<ChatMsgModel> coll;

	private Context ctx;

	private LayoutInflater mInflater;

	public RecentChatViewAdapter(Context context, List<ChatMsgModel> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ChatMsgModel entity = coll.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_recent_frag, null);

			viewHolder = new ViewHolder();
			viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.recent_item_head);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.recent_item_time);
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.recent_item_name);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.recent_item_content);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.ivHead.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.notification));
		viewHolder.tvTime.setText(entity.getDate());
		viewHolder.tvName.setText(entity.getName());
		viewHolder.tvContent.setText(entity.getText());

		return convertView;
	}

	static class ViewHolder {
		public ImageView ivHead;
		public TextView tvName;
		public TextView tvTime;
		public TextView tvContent;
		public boolean isComMsg = true;
	}

}
