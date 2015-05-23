package com.zgrjb.model;

import java.io.Serializable;

import android.graphics.Bitmap;
/**
 * 搜索结果列表的数据模型,和联系人列表的模型一样
 * @author tk
 *
 */
public class SortModel extends LocalUserInfo implements Serializable{
	//private LocalUserInfo info;
//	private String name;   //昵称
//	private String remark;  //备注，
//	private String url;//头像的url
	private String sortLetters;  //数据拼音的首字母,排序用
	
	
//	public LocalUserInfo getInfo() {
//		return info;
//	}
//	public void setInfo(LocalUserInfo info) {
//		this.info = info;
//	}
//	public String getUrl() {
//		return url;
//	}
//	public void setUrl(String url) {
//		this.url = url;
//	}
//	
//	
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
	
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
