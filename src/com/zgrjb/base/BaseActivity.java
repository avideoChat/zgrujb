package com.zgrjb.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
/*
 * 提供所有activity公共的方法
 * 
 */
public class BaseActivity extends ActivityBase{
	
	public void showLongToast(String mes){
		Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
	}
	public void showShortToast(String mes){
		Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
	}
	public void forward(Class<?> clazz){		
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
	}
	public void forward (Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
	}

}
