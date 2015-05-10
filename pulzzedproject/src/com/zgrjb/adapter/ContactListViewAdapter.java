package com.zgrjb.adapter;

import com.zgrjb.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ContactListViewAdapter extends BaseExpandableListAdapter {

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;

	private final String[] mGroups = {
			"Cupcake",
			"Donut",
			"Eclair",
			"Froyo",
			"Gingerbread",
			"Honeycomb",
			"Ice Cream Sandwich",
			"Jelly Bean",
			"KitKat",
            "Kai"
	};

	private final String[] mGroupState = {
			"1/2",
			"1123/2245",
			"23/45",
			"1123/2245",
			"23/45",
			"23/45",
			"1123/2245",
			"23/45",
			"23/45",
            "0/0"
	};

	private final String[][] mChilds = {
			{"1.5"},
			{"1.6"},
			{"2.0","2.0.1","2.1"},
			{"2.2","2.2.1","2.2.2","2.2.3"},
			{"2.3","2.3.1","2.3.2","2.3.3","2.3.4","2.3.5","2.3.6","2.3.7"},
			{"3.0","3.1","3.2","3.2.1","3.2.2","3.2.3","3.2.4","3.2.5","3.2.6"},
			{"4.0", "4.0.1", "4.0.2", "4.0.3", "4.0.4"},
			{"4.1", "4.1.1", "4.1.2", "4.2", "4.2.1", "4.2.2", "4.3", "4.3.1"},
			{"4.4"}
	};

    public String[][] getChilds(){
        return mChilds;
    }
    
    public Object getGroupState(int groupPosition){
    	if (groupPosition<0){
    		return null;
    	}
    	return mGroupState[groupPosition];
    }

	public ContactListViewAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getGroupCount() {
		return mGroups.length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (groupPosition<0){
    		return null;
    	}
		return mGroups[groupPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gHolder = null;
		if(convertView == null) {
			gHolder = new GroupViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.contacts_list_group, parent, false);
			gHolder.groupName = (TextView) convertView.findViewById(R.id.group_name);
			gHolder.groupState = (TextView) convertView.findViewById(R.id.group_state);
			gHolder.expandedImage = (ImageView) convertView.findViewById(R.id.contact_list_group_expanded_image);
		    convertView.setTag(gHolder);
		}else{
			gHolder = (GroupViewHolder)convertView.getTag();
		}
		
		gHolder.groupName.setText(mGroups[groupPosition]);
		gHolder.groupState.setText(mGroupState[groupPosition]);
		final int resId = isExpanded ? R.drawable.minus : R.drawable.plus;
		gHolder.expandedImage.setImageResource(resId);
		
//		if(convertView == null){
//			convertView = mLayoutInflater.inflate(R.layout.contacts_list_group, parent, false);
//		}
//		TextView groupName = (TextView) convertView.findViewById(R.id.group_name);
//		TextView groupState = (TextView) convertView.findViewById(R.id.group_state);
//		ImageView expandedImage = (ImageView) convertView.findViewById(R.id.contact_list_group_expanded_image);
//		groupName.setText(mGroups[groupPosition]);
//		groupState.setText(mGroupState[groupPosition]);
//		final int resId = isExpanded ? R.drawable.minus : R.drawable.plus;
//        expandedImage.setImageResource(resId);
		
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
        if(groupPosition>=mChilds.length){
            return 0;
        }
		return mChilds[groupPosition].length;

	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition>=mChilds.length){
            return null;
        }
        return mChilds[groupPosition][childPosition];

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder cHolder = null;
		if(convertView == null) {
			cHolder = new ChildViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.contacts_list_child, parent, false);
			cHolder.headPortrait = (ImageView) convertView.findViewById(R.id.head_portrait);
			cHolder.childName = (TextView) convertView.findViewById(R.id.child_name);
			cHolder.childState = (TextView) convertView.findViewById(R.id.child_state);
		    convertView.setTag(cHolder);
		}else{
			cHolder = (ChildViewHolder)convertView.getTag();
		}
	 if(groupPosition<=mChilds.length) {
		
		 cHolder.childName.setText(mChilds[groupPosition][childPosition]);
		 cHolder.childState.setText(mChilds[groupPosition][childPosition]);
		 }
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}//标识子元素能否被点击，true可以，false不可以
    
	
    private class GroupViewHolder{
    	ImageView expandedImage;
    	TextView groupName;
    	TextView groupState;
    }
    
    static class ChildViewHolder{
    	ImageView headPortrait;
    	TextView childName;
    	TextView childState;
    }

}
