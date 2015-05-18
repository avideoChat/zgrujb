package com.zgrjb.ui;

import java.util.HashMap;
import java.util.List;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseAuth;
import com.zgrjb.base.BaseMessage;
import com.zgrjb.base.BaseUi;
import com.zgrjb.model.Customer;
import com.zgrjb.model.Friend;

/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UISlashActivity extends BaseUi {
	private SharedPreferences sharedPrefs;
	private String username;
	private String password;
	public static ServiceManager serviceManager = null;
	private List<Friend> friends = null;
	public static UISlashActivity instance;

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ui_slash);
		instance = this;
		sharedPrefs = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		username = sharedPrefs.getString(Constants.XMPP_USERNAME, "");
		password = sharedPrefs.getString(Constants.XMPP_PASSWORD, "");
	}

	 
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (isNetWorkConnected()) {

			if (!"".equals(username) && !"".equals(password)) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("method", "checkuser");
				params.put("name", sharedPrefs.getString(Constants.XMPP_USERNAME, ""));
				
				doTaskAsync(BaseConfig.task.checkuser, BaseConfig.api.checkuser, params);
				
			
			} else {

				 
				forward(UIStartActivity.class);
			}
		} else {
			showSetNetworkDialog();
		}
	}

	 
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case BaseConfig.task.checkuser:
			try {
				Customer c = (Customer) message.getResult("Customer");
		 
				 
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
				Log.i("result", "pengyou"+friends.size());
				//forward(MyFramentActivity.class);
				 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				showLoadBar();
				BaseApp.isQuickIn = true;
				if(serviceManager == null){
				serviceManager = new ServiceManager(UISlashActivity.this);
				}
				serviceManager.setNotificationIcon(R.drawable.notification);
				serviceManager.startService();
			}
			break;

		default:
			break;
		}
	}

	

	protected String getVesion() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info == null ? "1.0.0" : info.versionName;
	}

	private void showSetNetworkDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("设置网络");
		builder.setMessage("网络错误请检查网络状态");
		builder.setPositiveButton("设置网络", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog d = builder.create();
		d.setCanceledOnTouchOutside(false);
		d.show();
	}

}
