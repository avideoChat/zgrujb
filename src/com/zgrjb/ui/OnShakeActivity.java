package com.zgrjb.ui;

import android.os.Bundle;
 
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;



import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.zgrjb.R;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.listener.ShakeListener;
import com.zgrjb.listener.ShakeListener.OnShakeListener;
import com.zgrjb.model.LocationModel;
/**
 * 摇一摇
 * @author tk
 *
 */
public class OnShakeActivity extends BaseActivity {
	public static final int RESULT_OK = 1;//附近有人在摇一摇
	public static final int RESULT_FAILURE = 0;//附近没有人在摇一摇
	/**
     * 摇一摇监听
     */
    private ShakeListener mShakeListener = null;

    /**
     * 重力感应仪
     */
    private Vibrator mVibrator;

    /**
     * 摇一摇动画上图标
     */
    private RelativeLayout mImgUp;

    /**
     * 摇一摇动画下图标
     */
    private RelativeLayout mImgDn;

    /**
     * 摇一摇标题栏
     */
    private RelativeLayout mTitle;
	
    /**
	 * 定位 
	 */
    LocationClient mLocClient;
	public MyLocationListenner myListener;
	private LocationMode mCurrentMode;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_shake);
	    initView();
	}
	
	private void locate(){
		mLocClient = new LocationClient(this);
		 myListener = new MyLocationListenner();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		//option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	private void initView(){
		 mVibrator = (Vibrator) getApplication().getSystemService(
	                VIBRATOR_SERVICE);
	    
		 
	        mShakeListener = new ShakeListener(this);
	        mShakeListener.setOnShakeListener(new OnShakeListener()
	        {
	            public void onShake()
	            {
	              //  startAnim(); // 开始 摇一摇手掌动画
	                mShakeListener.stop();
	                startVibrato(); // 开始 震动
	                /*
	                 * 应该向数据库发起连接，如果有数据返回就跳入地图，否则应该有提示信息
	                 */
	                forward( com.zgrjb.ui.LocationActivity.class);
	               
	              
	            }
	        });
	}
	
	  private void startAnim()
	    { // 定义摇一摇动画动画
	        AnimationSet animup = new AnimationSet(true);
	        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
	                -0.5f);
	        mytranslateanimup0.setDuration(1000);
	        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
	                +0.5f);
	        mytranslateanimup1.setDuration(1000);
	        mytranslateanimup1.setStartOffset(1000);
	        animup.addAnimation(mytranslateanimup0);
	        animup.addAnimation(mytranslateanimup1);
	        mImgUp.startAnimation(animup);

	        AnimationSet animdn = new AnimationSet(true);
	        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
	                +0.5f);
	        mytranslateanimdn0.setDuration(1000);
	        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
	                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
	                -0.5f);
	        mytranslateanimdn1.setDuration(1000);
	        mytranslateanimdn1.setStartOffset(1000);
	        animdn.addAnimation(mytranslateanimdn0);
	        animdn.addAnimation(mytranslateanimdn1);
	        mImgDn.startAnimation(animdn);
	    }

	    private void startVibrato()
	    { // 定义震动
	        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); // 第一个｛｝里面是节奏数组，
	                                                               // 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	    }
	    
	 
	    
	    @Override
	    protected void onDestroy()
	    {
	        super.onDestroy();
	        if (mShakeListener != null)
	        {
	            mShakeListener.stop();
	        }
	    }
      
	    public class MyLocationListenner implements BDLocationListener{
	    	
	    	@Override
	    	public void onReceiveLocation(BDLocation location) {
	    		if (location == null)
	                return ;
	    		LocationModel lModel = new LocationModel();
	    		lModel.setLatitude(location.getLatitude());
	    		lModel.setLongitude(location.getLongitude());
	    		//然后把数据传递到服务器
	    	}

	    }

}
