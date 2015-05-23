/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.demoapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidpn.client.LogUtil;
import org.androidpn.client.NotificationService;
import org.androidpn.client.ServiceManager;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zgrjb.R;
import com.zgrjb.application.BaseApp;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseHandler;
import com.zgrjb.base.BaseMessage;
import com.zgrjb.base.BaseTask;
import com.zgrjb.base.BaseUi;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity extends BaseUi {
	 ServiceManager serviceManager = new ServiceManager(
			BaseApp.mInstance);
	private static final String LOGTAG = LogUtil
			.makeLogTag(NotificationService.class);

	public static DemoAppActivity instance;
	private EditText ed1 = null;
	private EditText ed2 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("DemoAppActivity", "onCreate()...");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		instance = this;
		serviceManager.setNotificationIcon(R.drawable.notification);
		serviceManager.startService();
		ed1 = (EditText) findViewById(R.id.ed1);
		ed2 = (EditText) findViewById(R.id.ed2);
		// Settings
		Button okButton = (Button) findViewById(R.id.btn_settings);
		Button okButton2 = (Button) findViewById(R.id.btn_settings2);
		okButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				ServiceManager.viewNotificationSettings(DemoAppActivity.this);
			}
		});

		// Start the service

		okButton2.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("static-access")
			public void onClick(View view) {
				if (serviceManager.isInit) {
					Log.i(LOGTAG, "clivc");
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("method", "checkuser");
					params.put("name", ed1.getText().toString());
					doTaskAsync(BaseConfig.task.checkuser, BaseConfig.api.checkuser, params);
				}

			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		Log.i(LOGTAG, "taskid:" + taskId);
		switch (taskId) {
		case BaseConfig.task.checkuser:
			String isRegister = message.getCode();
			if ("1".equals(isRegister)) {
//				serviceManager.getService().setUserRegister(true);
//				Log.i(LOGTAG, "用户已经存在");
			} else {
//				serviceManager.getService().setUserRegister(false);
//				serviceManager.getService().registerByUser(
//						ed1.getText().toString(), ed2.getText().toString());
//				Log.i(LOGTAG, "用户不存在");
//				showLoadBar();

			}

			break;

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		serviceManager.stopService();
	}

}