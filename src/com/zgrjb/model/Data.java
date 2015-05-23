package com.zgrjb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是模拟地理位置的数据
 * @author tk
 *
 */

public class Data implements Serializable{
	private static final long serialVersionUID = -758459502806858414L;
	
	 private double latitude;  //纬度
	 private double longitude; //经度 
	 
	 private String name; //昵称
	 public static List<Data> list = new ArrayList<Data>();
	 static{
		 list.add(new Data(21.156666,110.307899,"张三"));
		 list.add(new Data(21.156672,110.307999,"李四"));
		 list.add(new Data(21.150000,110.307899,"王五"));
		 list.add(new Data(22.335689,110.327764,"小丫"));
		 list.add(new Data(21.335689,116.327764,"小丹"));
	 }
     public Data(){
    	 
     }
     public Data(double latitude,double longtitude,String name){
    	 this.latitude = latitude;
    	 this.longitude = longtitude;
    	 this.name  = name;
     }
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
