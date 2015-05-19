package com.zgrjb.application;

import org.androidpn.client.ServiceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
 
 


import com.zgrjb.R;

import android.app.Application;
import android.graphics.Bitmap;
/*
 * 应用的核心application类，初始化了加载网络图片的
 * imageloader
 * 
 */
public class BaseApp extends Application{
//	public static ServiceManager serviceManager;
	private RequestQueue requestQueue;
	public static ImageLoader imageLoader;
	public static boolean isQuickIn = false;
	
	public static BaseApp mInstance;
	
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
//		serviceManager = new ServiceManager(this);
//		serviceManager.setNotificationIcon(R.drawable.notification);
//		serviceManager.startService();
		initNetworkImageLoader(); 
	}

	public static BaseApp getInstance() {
		return mInstance;
	}
	
	private void initNetworkImageLoader() {
		requestQueue = Volley.newRequestQueue(this);
		  imageLoader = new ImageLoader(requestQueue, new ImageCache() {
				
				@Override
				public void putBitmap(String arg0, Bitmap arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public Bitmap getBitmap(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			});
	}

}
