package com.zgrjb.base;

 
import org.androidpn.client.ServiceManager;
import org.androidpn.demoapp.DemoAppActivity;

import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.fragments.MyFramentActivity;
import com.zgrjb.ui.UILoginActivity;
import com.zgrjb.ui.UISlashActivity;
import com.zgrjb.ui.UIStartActivity;
import com.zgrjb.utils.AppUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class BaseHandler extends Handler {
	
	protected BaseUi ui;
	
	public BaseHandler (BaseUi ui) {
		this.ui = ui;
	}
	
	public BaseHandler (Looper looper) {
		super(looper);
	}
	
	@Override
	public void handleMessage(Message msg) {
		try {
			int taskId;
			String result;
			switch (msg.what) {
				case BaseTask.TASK_COMPLETE:
					ui.hideLoadBar();
					taskId = msg.getData().getInt("task");
					result = msg.getData().getString("data");
					if (result != null) {
						ui.onTaskComplete(taskId, AppUtil.getMessage(result));
					} else if (!AppUtil.isEmptyInt(taskId)) {
						ui.onTaskComplete(taskId);
					} else {
						ui.toast(BaseConfig.err.message);
					}
					break;
				case BaseTask.NETWORK_ERROR:
					ui.hideLoadBar();
					taskId = msg.getData().getInt("task");
					ui.onNetworkError(taskId);
					break;
				case BaseTask.SHOW_LOADBAR:
					ui.showLoadBar();
					break;
				case BaseTask.HIDE_LOADBAR:
					ui.hideLoadBar();
					break;
				case BaseTask.REGISTER_SUCCESS:
					ui.hideLoadBar();
					DemoAppActivity.instance.finish();
					break;
				case BaseTask.LOGIN_SUCCESS:
					ui.hideLoadBar();				 
				 				
					if(BaseApp.isQuickIn){
					UISlashActivity.instance.forward(MyFramentActivity.class );
					}else{
						UILoginActivity.instance.forward(MyFramentActivity.class );
						
					}
					break;
				case BaseTask.SHOW_TOAST:
					ui.hideLoadBar();
					result = msg.getData().getString("data");
					ui.toast(result);
					break;
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
			ui.toast(e.getMessage());
		}
	}
	
}