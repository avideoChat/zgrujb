package com.zgrjb.model;

import java.io.Serializable;
import java.util.Date;

import org.litepal.crud.DataSupport;

/**
 * 最后一条消息的表
 * @author tk
 *
 */
public class LastMsgRecord extends DataSupport implements Serializable{
   private int id;
   private String chatId;//会话id,即对方id号，由服务器传过来
   private String name;//对方的名字,默认显示备注，备注为空时显示用户名
   private String headPortraitUrl;//头像在服务器的地址
   private String content;
   private String time;
   private int unReadNum;//未读消息提醒
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getChatId() {
	return chatId;
}
public void setChatId(String chatId) {
	this.chatId = chatId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getHeadPortraitUrl() {
	return headPortraitUrl;
}
public void setHeadPortraitUrl(String headPortraitUrl) {
	this.headPortraitUrl = headPortraitUrl;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getUnReadNum() {
	return unReadNum;
}
public void setUnReadNum(int unReadNum) {
	this.unReadNum = unReadNum;
	
}
   

   
}
