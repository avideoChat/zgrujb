package com.zgrjb.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.application.BaseConfig.BroadcastTag;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.model.LocalUserInfo;
import com.zgrjb.utils.CacheUtil;
import com.zgrjb.utils.GraphicsBitmapUtils;

public class UIUserInfoActivity extends BaseActivity implements OnClickListener{
	private TextView tvLocalName, tvName,tvEmail,tvPlace, tvSign,tvGender,tvAge;
	private ImageView headImg;
	private Button btnSend;
	private LocalUserInfo info;
	private final CacheUtil util = CacheUtil.getInstance();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_user_info);     
        
        tvGender = (TextView) findViewById(R.id.tv_info_gender);
        tvAge = (TextView) findViewById(R.id.tv_info_age);
        tvLocalName = (TextView) findViewById(R.id.tv_info_localname);
        tvName = (TextView) findViewById(R.id.tv_info_name);
        tvEmail = (TextView) findViewById(R.id.tv_info_email);
        tvPlace = (TextView) findViewById(R.id.tv_info_place);
        tvSign = (TextView) findViewById(R.id.tv_info_sign);
        headImg = (ImageView)findViewById(R.id.iv_info_head);
        btnSend = (Button) findViewById(R.id.btn_info_sendMsg);
        btnSend.setOnClickListener(this);
        initData();
    }
    
	private void initData(){
		Intent i = getIntent();
		info = (LocalUserInfo)(i.getSerializableExtra(BroadcastTag.ENTER));
		
		tvGender.setText(info.getGender());
		tvAge.setText(info.getAge());
	    tvLocalName.setText(info.getLocalName());
		tvName.setText("昵称："+info.getName());
		tvPlace.setText(info.getArea());
		tvSign.setText(info.getSelfSign());
		tvEmail.setText(info.getEmail()==null?"":info.getEmail());
		Bitmap bm = util.getBitmapFromDiskLruCache(info.getHeadPortraitUrl());
		if(bm!=null){
		  headImg.setImageBitmap(bm);
	    }
	}	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_info_sendMsg:
			Bundle b = new Bundle();
			b.putSerializable(BroadcastTag.ENTER, info);
			this.forward(com.zgrjb.ui.UIChatActivity.class, b);
			break;
		}
		
	}
	
}
