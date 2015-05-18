package com.zgrjb.model;

import android.graphics.Bitmap;
/**
 * 搜索结果列表的数据模型
 * @author tk
 *
 */
public class SortModel {
	private Bitmap headPortrait;//头像
	private String name;   //昵称,
	private String remark;  //备注，
	private String group;//分组

	
	private String sortLetters;  //数据拼音的首字母,排序用
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(Bitmap headPortrait) {
		this.headPortrait = headPortrait;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
