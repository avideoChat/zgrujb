package com.zgrjb.framents;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;



import com.zgrjb.R;
import com.zgrjb.adapter.ContactListViewAdapter;
import com.zgrjb.adapter.SortAdapter;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.domain.PinyinComparator;
import com.zgrjb.domain.SortModel;
import com.zgrjb.utils.CharacterParser;
import com.zgrujb.selfdefindui.ClearEditText;
import com.zgrujb.selfdefindui.PinnedHeaderExpandableListView;
import com.zgrujb.selfdefindui.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.zgrujb.selfdefindui.StickyLayout;
import com.zgrujb.selfdefindui.StickyLayout.OnGiveUpTouchEventListener;
 /*
  * 联系人fragement，暂时未实现
  * 同恺负责
  */
@TargetApi(Build.VERSION_CODES.FROYO) @SuppressLint("SimpleDateFormat")
public class ContactFragment extends FragmentBase{
	
	private SortAdapter adapter;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
		
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		initView(view,savedInstanceState);
        

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		SourceDateList = filledData(getActivity().getResources().getStringArray(R.array.date));
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
	/** 
     * 设置添加屏幕的背景透明度 
     * @param bgAlpha 
     */
	 private void backgroundAlpha(float bgAlpha)  
	    {  
	        WindowManager.LayoutParams lp = this.getActivity().getWindow().getAttributes();  
	        lp.alpha = bgAlpha; //0.0-1.0  
	        this.getActivity().getWindow().setAttributes(lp);  
	    }  
	 
   private void initView(View v ,final Bundle savedInstanceState){
	     //弹出分组管理的PopupWindow
	     View popupView = getLayoutInflater(savedInstanceState).inflate(R.layout.contacts_list_popupview, null);
		 final PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true); 
		 mPopupWindow.setTouchable(true);
	     mPopupWindow.setOutsideTouchable(true);
	     mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	    
	     Button btn = (Button)mPopupWindow.getContentView().findViewById(R.id.btn_pop);
	     btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
				
				mPopupWindow.dismiss();
			}
	    	 
	     });
	     
	     //弹出搜索框，根据数据切换搜索框下面的内容
	     final View sortPopupView = getLayoutInflater(savedInstanceState).inflate(R.layout.contacts_list_search, null);
		 final PopupWindow sortPopupWindow = new PopupWindow(sortPopupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true); 
		 sortPopupWindow.setTouchable(true);
		 sortPopupWindow.setOutsideTouchable(true);
         sortPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
         //透明背景
         final View bgView = (View)sortPopupWindow.getContentView().findViewById(R.id.sort_view);
         bgView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				sortPopupWindow.dismiss();
				return false;
			}
        	 
         });
         //搜索内容列表	 
         final ListView lv = (ListView) sortPopupWindow.getContentView().findViewById(R.id.sort_list);
         adapter = new SortAdapter(this.getActivity(), SourceDateList);
  	     lv.setAdapter(adapter);
  	   
         
         //没有搜索结果的textview
		 final TextView tx = (TextView) sortPopupWindow.getContentView().findViewById(R.id.sort_no_result);
		
		 //搜索框
		 final ClearEditText cedt = (ClearEditText)sortPopupWindow.getContentView().findViewById(R.id.edt_search);
		 cedt.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				if (TextUtils.isEmpty(s)){
					if (sortPopupWindow.isShowing()){
						bgView.setVisibility(View.VISIBLE);
						lv.setVisibility(View.GONE);
						tx.setVisibility(View.GONE);
					}
				}else if (!filterData(s.toString()).isEmpty()){
					bgView.setVisibility(View.GONE);
					tx.setVisibility(View.GONE);
					lv.setVisibility(View.VISIBLE);
				    
				   }else {
					bgView.setVisibility(View.GONE);
					tx.setVisibility(View.VISIBLE);
					lv.setVisibility(View.GONE);
					
				}
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
			 
		 });
		 
        //监听搜索界面的消失，重置状态
		 sortPopupWindow.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
				
				if (!(cedt.getText().toString().trim().length()<0)){
					 cedt.setText("");
					 cedt.reset();
					 
				}
				
				tx.setVisibility(View.GONE);
				bgView.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
				
			}
			 
		 });
		 
         //取消搜索
		 TextView tvCancel = (TextView)sortPopupWindow.getContentView().findViewById(R.id.sort_cancel);
		 tvCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				sortPopupWindow.dismiss();
				
				        
			}
			 
		 });
	     //分组列表
		final PinnedHeaderExpandableListView expandableListView = (PinnedHeaderExpandableListView) v.findViewById(R.id.contacts_list);
		final ContactListViewAdapter adapter = new ContactListViewAdapter(getActivity());

		
		expandableListView.setAdapter(adapter);
		
		expandableListView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(parent instanceof PinnedHeaderExpandableListView){
					PinnedHeaderExpandableListView mList = (PinnedHeaderExpandableListView)parent;
					 int childPosition = mList.getPackedPositionChild(mList.getExpandableListPosition(position));//取得child下标标，当选中group时，下标为-1
					 if(childPosition < 0)
					   mPopupWindow.showAsDropDown(view,0,-view.getHeight()*2-20);
					 
				}
				return true;
			}
      	
      });
	
		expandableListView.setOnGroupClickListener(new OnGroupClickListener(){

			@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				 if(parent.isGroupExpanded(groupPosition)){
		             parent.collapseGroup(groupPosition);
		         }else{
		           //第二个参数false表示展开时是否触发默认滚动动画
		             parent.expandGroup(groupPosition,false);
		         }
		         
		         return true;
			}
			
		});
		
		expandableListView.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(getActivity(),groupPosition+"-"+childPosition+"hello", Toast.LENGTH_SHORT).show();
				
				return false;
			}
			
		});
		
		expandableListView.setOnHeaderUpdateListener(new OnHeaderUpdateListener(){

			@Override
			public View getPinnedHeader() {
				View headerView = (ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.contacts_list_group, null);
                
				headerView.setLayoutParams(new LayoutParams(
		                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
		        return headerView;
			}
			
			@Override
			public void updatePinnedHeader(View headerView,
					int firstVisibleGroupPos) {
				    
				    ImageView expandedImage = (ImageView)headerView.findViewById(R.id.contact_list_group_expanded_image);
				    String firstVisibleGroup = (String) adapter.getGroup(firstVisibleGroupPos);
			        TextView groupName = (TextView) headerView.findViewById(R.id.group_name);
			        groupName.setText(firstVisibleGroup);
			        String firstVisibleGroupState = (String) adapter.getGroupState(firstVisibleGroupPos);
			        TextView groupState = (TextView) headerView.findViewById(R.id.group_state);
			        groupState.setText(firstVisibleGroupState);
			        final int resId = expandableListView.isGroupExpanded(firstVisibleGroupPos) ? R.drawable.minus : R.drawable.plus;
					expandedImage.setImageResource(resId);
				 
					
			}
			
		});
		//收缩的头部
		 StickyLayout stickyLayout = (StickyLayout)v.findViewById(R.id.sticky_layout);
	     stickyLayout.setOnGiveUpTouchEventListener(new OnGiveUpTouchEventListener(){

			@Override
			public boolean giveUpTouchEvent(MotionEvent event) {
				if (expandableListView.getFirstVisiblePosition() == 0) {
		            View view = expandableListView.getChildAt(0);
		            if (view != null && view.getTop() >= 0) {
		                return true;
		            }
		        }
		        return false;
			}
	    	 
	     });
	     //点击弹出搜索框，并设置背景色
	     ImageView imgSort = (ImageView)v.findViewById(R.id.search);
	     imgSort.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				backgroundAlpha(0.6f);
				sortPopupWindow.showAtLocation(v, Gravity.TOP, 0, 0);
			}
	    	 
	     });

   }
   
   /**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			sortModel.setRemark(date[i]);
			sortModel.setGroup(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		mSortList.get(0).setRemark("，王");  
		return mSortList;
		
	}
	
   /** 
    * 根据输入框中的值来过滤数据并更新ListView 
    * @param filterStr 
    */  
   private List<SortModel> filterData(String filterStr) {  
       
	   
	   List<SortModel> filterDateList = new ArrayList<SortModel>();  
 
       if (TextUtils.isEmpty(filterStr)) {  
           filterDateList = null;  
       } else {  
           filterDateList.clear();  
           for (SortModel sortModel : SourceDateList) {  
               String name = sortModel.getName();
               String remark = sortModel.getRemark();
               
               if (name.toUpperCase().indexOf(  
                       filterStr.toString().toUpperCase()) != -1  
                       || characterParser.getSelling(name).toUpperCase()  
                               .contains(filterStr.toString().toUpperCase())||remark.toUpperCase().indexOf(  
                                       filterStr.toString().toUpperCase()) != -1  
                                       || characterParser.getSelling(remark).toUpperCase()  
                                               .contains(filterStr.toString().toUpperCase())) {  
                     
            	   filterDateList.add(sortModel);  
               }  
           }  
       }
    if (filterDateList != null && !(filterDateList.isEmpty())) {  
       // 根据a-z进行排序  
           Collections.sort(filterDateList, pinyinComparator);  
           adapter.updateListView(filterDateList); 
           return filterDateList;
      }
      
      return filterDateList;
  
   
   
   } 
   
   
   private List<String> getData(){
       
       List<String> data = new ArrayList<String>();
       data.add("测试数据1");
//       data.add("测试数据2");
//       data.add("测试数据3");
//       data.add("测试数据4");
//        
       return data;
   }
	

}
