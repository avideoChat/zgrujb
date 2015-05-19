package com.zgrjb.model;
/**
 * 定位数据的类
 * @author 
 *
 */
public class LocationModel {

	 private double latitude;  //纬度
	 private double longitude; //经度 
	 
	 private String username; //用户名，因为用户名是unique的，所以可以唯一识别
	 

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	 
	 
}
