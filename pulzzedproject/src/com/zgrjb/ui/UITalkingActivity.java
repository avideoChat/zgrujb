package com.zgrjb.ui;

import java.util.Date;
import java.util.HashMap;

import org.androidpn.demoapp.DemoAppActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zgrjb.R;
import com.zgrjb.adapter.InfiniteLoopViewPagerAdapter;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.base.BaseMessage;
import com.zgrjb.base.BaseUi;
import com.zgrjb.framents.MyFramentActivity;
import com.zgrujb.selfdefindui.InfiniteLoopViewPager;
/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UITalkingActivity extends BaseUi {
 
	private EditText sendmes_ed = null;
	private Button send_btn = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talking_recent);
		
		sendmes_ed = (EditText) findViewById(R.id.send);
		send_btn = (Button) findViewById(R.id.sendb);
		send_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(sendmes_ed.getText().toString()!=null&&sendmes_ed.getText().toString().length()>0){
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("method", "sendmes");
				params.put("from", "123");
				params.put("to", "9999");
				params.put("time", new Date().toGMTString());
				params.put("mes", sendmes_ed.getText().toString());
				params.put("image", "294695154834a2a64501369d2399a802.jpg");
				params.put("media", "");
				
				doTaskAsync(BaseConfig.task.sendmes, BaseConfig.api.sendmes, params);
				}
			}
		});
		 
	}
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case BaseConfig.task.sendmes:
			Log.i("result", "ok");
			break;

		default:
			break;
		}
	}

	
}
