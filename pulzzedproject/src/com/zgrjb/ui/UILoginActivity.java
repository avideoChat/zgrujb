package com.zgrjb.ui;

import java.util.HashMap;
import java.util.List;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;
import org.androidpn.demoapp.DemoAppActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zgrjb.R;
import com.zgrjb.adapter.InfiniteLoopViewPagerAdapter;
import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.BaseMessage;
import com.zgrjb.base.BaseUi;
import com.zgrjb.fragments.MyFramentActivity;
import com.zgrjb.model.Customer;
import com.zgrjb.model.Friend;
import com.zgrujb.selfdefindui.InfiniteLoopViewPager;

/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UILoginActivity extends BaseUi {
	private List<Friend> friends = null;
	private EditText name_edt = null;
	private EditText password_edt = null;
	private Button login_btn = null;
	public static UILoginActivity instance;
 
	private SharedPreferences sharedPrefs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_login);
		BaseApp.isQuickIn = false;
		instance = this;
		if(UISlashActivity.serviceManager == null){
		 UISlashActivity.serviceManager = new ServiceManager(UILoginActivity.this);
		
		}
		initView();

	}

	private void initView() {
		sharedPrefs = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
		name_edt = (EditText) findViewById(R.id.login_name);
		password_edt = (EditText) findViewById(R.id.login_password);
		name_edt.setText(sharedPrefs.getString(Constants.XMPP_USERNAME, ""));
		password_edt.setText(sharedPrefs.getString(Constants.XMPP_PASSWORD, ""));
		login_btn = (Button) findViewById(R.id.login_confirm);
		login_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!name_edt.getText().toString().equals("")
						&& name_edt.getText().toString().length() > 0
						&& !password_edt.getText().toString().equals("")
						&& password_edt.getText().toString().length() > 0) {
					showLoadBar();
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("method", "checkuser");
					params.put("name", name_edt.getText().toString());
					
					doTaskAsync(BaseConfig.task.checkuser, BaseConfig.api.checkuser, params);
					
					//
				} else {
					showLongToast("用户名或密码不能为空");
				}
			}
		});
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case BaseConfig.task.checkuser:
			try {
				Customer c = (Customer) message.getResult("Customer");
		 
				Editor ed = sharedPrefs.edit();
				ed.putString(Constants.XMPP_USERNAME, name_edt.getText().toString());
				ed.putString(Constants.XMPP_PASSWORD, password_edt.getText().toString());
				ed.commit();
				if(c!=null){
					BaseAuth.setcustomer(c);
					HashMap<String, String> urlParams = new HashMap<String, String>();
					urlParams.put("method", "getfriends");
					urlParams.put("id", BaseAuth.getcustomer().getId());
					doTaskAsync(BaseConfig.task.getfriend, BaseConfig.api.getfriend, urlParams);
//					BaseApp.serviceManager.getService().login(apn的登陆，不要删了
//							name_edt.getText().toString(),
//							password_edt.getText().toString());
					
				}else{
					showLongToast("shibai");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case BaseConfig.task.getfriend:
			try {
				friends = (List<Friend>) message.getResultList("Friend");
				BaseAuth.getcustomer().setFriends(friends);
				 
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 BaseAuth.getcustomer().setFriends(friends);
			}
			finally{
				showLoadBar();
				 UISlashActivity.serviceManager.setNotificationIcon(R.drawable.notification);
				 UISlashActivity.serviceManager.startService();
			}
			break;

		default:
			break;
		}
	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			 
			forward(UIStartActivity.class);			 
		}
		return true;
	}
}
