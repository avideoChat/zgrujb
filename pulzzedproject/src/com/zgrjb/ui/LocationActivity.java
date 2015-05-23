package com.zgrjb.ui;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.zgrjb.R;
import com.zgrjb.base.BaseActivity;
import com.zgrjb.model.Data;

public class LocationActivity extends BaseActivity {

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode ;
	BitmapDescriptor mCurrentMarker = null;

	MapView mMapView;
	BaiduMap mBaiduMap;
	//这个是点击别人的位置后显示的信息
	private InfoWindow mInfoWindow;
	// UI相关
	

	boolean isFirstLoc = true;// 是否首次定位
	
	double latitude;//维度
	double longtitude;//经度
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_location);
        
		LatLng l1 = new LatLng(21.156666,110.307899);
		LatLng l2 = new LatLng(21.156672,110.307999);
		Log.v("distance",""+DistanceUtil.getDistance(l1, l2));
		
		
		
		

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(final Marker marker) {
				Data dt = (Data) marker.getExtraInfo().get("data");
				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				button.setText(dt.getName());
				OnInfoWindowClickListener listener = null;
				button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					marker.setTitle("haha")	;
					mBaiduMap.hideInfoWindow();
					forward(com.zgrjb.ui.GameActivity.class);
					}
				});
				LatLng ll = marker.getPosition();
				mInfoWindow = new InfoWindow(button, ll, -47);
				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}
			
		});
		// 开启定位图层,即在地图上显示我的位置
        mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		//option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
//		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//				   mCurrentMode, true, mCurrentMarker));
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			Data d = new Data(location.getLatitude(),location.getLongitude(),"我");
			addInfosOverlay(Data.list,d);
			
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
		   mBaiduMap.setMyLocationData(locData);
			
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
	//	mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	
	public void addInfosOverlay(List<Data> datas,Data mData)  
    {  
        mBaiduMap.clear();  
        LatLng latLng = null; 
        LatLng center = new LatLng(mData.getLatitude(), mData.getLongitude());
        OverlayOptions overlayOptions = null;  
        Marker marker = null;  
   
        for (Data data : datas)  
        {  
            // 位置  
            latLng = new LatLng(data.getLatitude(), data.getLongitude());  
            if(SpatialRelationUtil.isCircleContainsPoint(center, 1000, latLng)){
            // 图标  
            overlayOptions = new MarkerOptions().position(latLng)  
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo))
                    .zIndex(5);  
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));  
            Bundle bundle = new Bundle();  
            bundle.putSerializable("data", data);  
            marker.setExtraInfo(bundle); 
            }
        } 
       

    }

}
