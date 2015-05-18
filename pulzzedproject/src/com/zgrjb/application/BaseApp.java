package com.zgrjb.application;

import org.litepal.LitePalApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.zgrjb.utils.MsgDBUtils;
 
 

import android.app.Application;
import android.graphics.Bitmap;

public class BaseApp extends LitePalApplication{
 
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
