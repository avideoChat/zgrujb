package com.zgrjb.model;

import java.io.Serializable;

import org.litepal.crud.DataSupport;
/**
 * 本地用户信息表
 * @author tk
 *
 */
public class LocalUserInfo extends DataSupport implements Serializable{
     private int id;//与服务器端的用户id一致
     private String name;//对方的昵称
     private String localName;//备注，当备注为空时显示name
     private String email;
 	 private String gender;//性别
  // private String headPortraitDir;//头像的本地缓存地址
 	 private String headPortraitUrl;//头像在服务器的地址
 	 private String age;
 	 private String area;//地区
 	 private String selfSign;//个性签名
	
 	 
 	 
 	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
//	public String getHeadPortraitDir() {
//		return headPortraitDir;
//	}
//	public void setHeadPortraitDir(String headPortraitDir) {
//		this.headPortraitDir = headPortraitDir;
//	}
	public String getHeadPortraitUrl() {
		return headPortraitUrl;
	}
	public void setHeadPortraitUrl(String headPortraitUrl) {
		this.headPortraitUrl = headPortraitUrl;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
 	
 	public String getSelfSign() {
		return selfSign;
	}
	public void setSelfSign(String selfSign) {
		this.selfSign = selfSign;
	}
 	 
}
