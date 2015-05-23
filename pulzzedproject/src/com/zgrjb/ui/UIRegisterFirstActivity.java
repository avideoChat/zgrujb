package com.zgrjb.ui;

import org.androidpn.demoapp.DemoAppActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zgrjb.R;
import com.zgrjb.adapter.InfiniteLoopViewPagerAdapter;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.framents.MyFramentActivity;
import com.zgrujb.selfdefindui.InfiniteLoopViewPager;

/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UIRegisterFirstActivity extends BaseActivity {
	private EditText name_etd = null;
	private EditText password_etd = null;
	private EditText password_confirm_etd = null;
	private Button return_btn = null;
	private Button next_btn = null;
	private String name = null;
	private String password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_registerfirst);
		Intent params = getIntent();
		name = params.getStringExtra("name");
		password = params.getStringExtra("password");
		initView();

	}

	private void initView() {
		name_etd = (EditText) findViewById(R.id.register_name);
		name_etd.setText(name);
		password_etd = (EditText) findViewById(R.id.register_password);
		password_etd.setText(password);
		password_confirm_etd = (EditText) findViewById(R.id.register_password_confirm);
		password_confirm_etd.setText(password);
		return_btn = (Button) findViewById(R.id.register_return_first);
		next_btn = (Button) findViewById(R.id.register_next_first);

		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getId()) {
				case R.id.register_return_first:
					forward(UIStartActivity.class);

					break;

				case R.id.register_next_first:
					if (!name_etd.getText().toString().equals("")
							&& name_etd.getText().toString().length() > 0
							&& !password_etd.getText().toString().equals("")
							&& password_etd.getText().toString().length() > 0
							&& password_etd
									.getText()
									.toString()
									.equals(password_confirm_etd.getText()
											.toString())) {
						Bundle params = new Bundle();
						params.putString("name", name_etd.getText().toString());
						params.putString("password", password_confirm_etd
								.getText().toString());
						forward(UIRegisterSecondActivity.class,params);
					} else
						showLongToast("输入有误，请核对信息！");
					break;
				}
			}
		};
		return_btn.setOnClickListener(onClickListener);
		next_btn.setOnClickListener(onClickListener);
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
