package com.zgrjb.base;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class BaseActivity extends ActivityBase{
	
	public void showLongToast(String mes){
		Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
	}
	public void showShortToast(String mes){
		Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
	}
	public void forword(Class<?> clazz){		
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
	}

}
