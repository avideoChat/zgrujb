package com.zgrjb.domain;

public class LocationModel {

	 private double latitude;  //纬度
	 private double longitude; //经度 
	 
	 private String name; //昵称

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
