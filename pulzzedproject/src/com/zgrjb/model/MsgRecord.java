package com.zgrjb.model;

import java.util.Date;

import org.litepal.crud.DataSupport;
/**
 * 聊天记录的类
 * @author tk
 *
 */

public class MsgRecord extends DataSupport{
private int id;//主键
private String sender;//发送者本地备注
private String receiver;//接收者本地备注
private String chatId;//会话id,即对方id号，由服务器传过来
private String content;//内容
private String time;//时间



public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getSender() {
	return sender;
}
public void setSender(String sender) {
	this.sender = sender;
}
public String getReceiver() {
	return receiver;
}
public void setReceiver(String receiver) {
	this.receiver = receiver;
}
public String getChatId() {
	return chatId;
}
public void setChatId(String chatId) {
	this.chatId = chatId;
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
@Override
public String toString() {
	return "MsgRecord [id=" + id + ", sender=" + sender + ", receiver="
			+ receiver + ", chatId=" + chatId + ", content=" + content
			+ ", time=" + time + "]";
}

}
