package com.zgrjb.model;

import android.graphics.Bitmap;
/**
 * 最近会话的类
 * @author tk
 *
 */
public class RecentModel {
      private Bitmap headPortrait;//头像
      private String name;//会话名称，即对方的名字，对应数据库中的from
      private String content;
      private String date;
  	  private String num;//未读消息数量
  	  private boolean isVisible;//设置提醒是否可见
  	
      
    public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Bitmap getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(Bitmap headPortrait) {
		this.headPortrait = headPortrait;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
      
      
}
