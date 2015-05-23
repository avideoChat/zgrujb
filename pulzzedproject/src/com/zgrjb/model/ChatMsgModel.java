
package com.zgrjb.model;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ChatMsgModel implements Serializable{
    private static final String TAG = ChatMsgModel.class.getSimpleName();
    
    
    private String headPortraitUrl;//头像
    private String name;//备注
    private String date;
    private String audioUrl = null;
    private String text;
    private String chatId;//会话id,即对方id号，由服务器传过来
   
    
    
    
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	private boolean isComMeg = true;

    public String getHeadPortraitUrl() {
		return headPortraitUrl;
	}
	public void setHeadPortraitUrl(String headPortraitUrl) {
		this.headPortraitUrl = headPortraitUrl;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getAudioUrl(){
    	return this.audioUrl;
    }
    public void setAudioUrl(String audioUrl){
    	this.audioUrl = audioUrl;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public boolean isComeMsg() {
        return isComMeg;
    }
    public void setIsComMeg(boolean isComeMsg){
    	isComMeg = isComeMsg;
    }

    public ChatMsgModel() {
    }

    public ChatMsgModel(String name, String date, String text, boolean isComMsg) {
        super();
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }

}
