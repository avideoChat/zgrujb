package com.zgrjb.framents;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
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
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zgrjb.R;
import com.zgrjb.adapter.ContanctAdapter;
import com.zgrjb.adapter.SortAdapter;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.FragmentBase;
import com.zgrjb.model.Friend;
import com.zgrjb.model.PinyinComparator;
import com.zgrjb.model.SortModel;
import com.zgrjb.selfdefindui.ClearEditText;
import com.zgrjb.selfdefindui.SideBar;
import com.zgrjb.selfdefindui.StickyLayout;
import com.zgrjb.selfdefindui.SideBar.OnTouchingLetterChangedListener;
import com.zgrjb.selfdefindui.StickyLayout.OnGiveUpTouchEventListener;
import com.zgrjb.selfdefindui.StickyLayout.OnHeaderHeightChangeListener;
import com.zgrjb.utils.CharacterParser;
 /*
  * 联系人fragement，暂时未实现
  * 同恺负责
  */
@TargetApi(Build.VERSION_CODES.FROYO) @SuppressLint("SimpleDateFormat")
public class ContactFragment extends FragmentBase{

	private ListView contactListView;
	private SideBar sideBar;
	private TextView dialog;
	private ContanctAdapter adapter;
	private SortAdapter sortAdapter;
	private  StickyLayout stickyLayout;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList,SortDateList;
	
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
		List<Friend> friends = BaseAuth.getcustomer().getFriends();
		List<String> friendsnames = new ArrayList<String>();
		for(Friend friend:friends){
			friendsnames.add(friend.getUsername());
		}
	 
		SourceDateList = filledData(friendsnames);
		SortDateList = filledData(friendsnames);
	 
		initView(view,savedInstanceState);
        

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//SourceDateList = filledData(getActivity().getResources().getStringArray(R.array.date));
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
	 
   @SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
   private void initView(View v ,final Bundle savedInstanceState){
	     
	     
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
         sortAdapter = new SortAdapter(this.getActivity(), SortDateList);
  	     lv.setAdapter(sortAdapter);
  	   
         
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

		 
		    sideBar = (SideBar) v.findViewById(R.id.contact_sidrbar);
//		    
		    sideBar.addOnLayoutChangeListener(new OnLayoutChangeListener(){

				@Override
				public void onLayoutChange(View v, int left, int top,
						int right, int bottom, int oldLeft, int oldTop,
						int oldRight, int oldBottom) {
					// TODO Auto-generated method stub
					
				}
		    	
		    });
		   
		    dialog = (TextView) v.findViewById(R.id.contact_dialog);
			sideBar.setTextView(dialog);
			
			//设置右侧触摸监听
			sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
				
				@Override
				public void onTouchingLetterChanged(String s) {
					//该字母首次出现的位置
					int position = adapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						contactListView.setSelection(position);
					}
					
				}
			});
			
			contactListView = (ListView) v.findViewById(R.id.contact_list);
			contactListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//这里要利用adapter.getItem(position)来获取当前position所对应的对象
					Toast.makeText(getActivity(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				}
			});

		
			
			// 根据a-z进行排序源数据
			Collections.sort(SourceDateList, pinyinComparator);
			adapter = new ContanctAdapter(getActivity(), SourceDateList);
			contactListView.setAdapter(adapter); 
		//收缩的头部
		 stickyLayout = (StickyLayout)v.findViewById(R.id.sticky_layout);
	     stickyLayout.setOnGiveUpTouchEventListener(new OnGiveUpTouchEventListener(){

			@Override
			public boolean giveUpTouchEvent(MotionEvent event) {
				if (contactListView.getFirstVisiblePosition() == 0) {
		            View view = contactListView.getChildAt(0);
		            if (view != null && view.getTop() >= 0) {
		                  return true;
		            }
		        }
				
		        return false;
			}
	    	 
	     });
	     
	     stickyLayout.setOnHeaderHeightChangeListener(new OnHeaderHeightChangeListener(){
	    	 @Override
	    	 public void HeaderHeightChange(View view,int height,int oldHeight){
//	    		 Toast.makeText(getActivity(), ""+stickyLayout.getHeaderHeight(), 0).show();
	    		 if (height>=0){
	 				     resetSideBar();
	 			  }
	    	 }
	     });
	     
	     
	     
	     //附近的人
	    final ImageView imgNearby = (ImageView)v.findViewById(R.id.contanct_nearby);
	    imgNearby.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			   
				//startAnimActivity(com.zgrjb.ui.LocationActivity.class);
				startAnimActivity(com.zgrjb.ui.OnShakeActivity.class);
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
	private List<SortModel> filledData(List<String> date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		 
		for(int i=0; i<date.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date.get(i));
			sortModel.setRemark(date.get(i));
			sortModel.setGroup(date.get(i));
		 
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date.get(i));
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		//mSortList.get(0).setRemark("，王");  
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
           for (SortModel sortModel : SortDateList) {  
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
           sortAdapter.updateListView(filterDateList); 
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
   
   public void resetSideBar(){
	   if (dialog!=null && dialog.isShown() ){ 
		       sideBar.reset();
	   }
   }
	

}
