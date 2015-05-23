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
private int sender;//发送者
private int receiver;//接收者
private int chatId;//会话id
private String content;//内容
private Date time;//时间
private boolean isRead;//可能会修改，初步定为未读信息的标志

public boolean isRead() {
	return isRead;
}
public void setRead(boolean isRead) {
	this.isRead = isRead;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getSender() {
	return sender;
}
public void setSender(int sender) {
	this.sender = sender;
}
public int getReceiver() {
	return receiver;
}
public void setReceiver(int receiver) {
	this.receiver = receiver;
}
public int getChatId() {
	return chatId;
}
public void setChatId(int chatId) {
	this.chatId = chatId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Date getTime() {
	return time;
}
public void setTime(Date time) {
	this.time = time;
}
@Override
public String toString() {
	return "MsgRecord [id=" + id + ", sender=" + sender + ", receiver="
			+ receiver + ", chatId=" + chatId + ", content=" + content
			+ ", time=" + time + ", isRead=" + isRead + "]";
}

}
