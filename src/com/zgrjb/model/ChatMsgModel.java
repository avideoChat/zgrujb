
package com.zgrjb.model;

public class ChatMsgModel {
    private static final String TAG = ChatMsgModel.class.getSimpleName();

    private String name;
    private String date;
    private String audioUrl = null;
    private String text;
    private boolean isComMeg = true;

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
