package com.zgrjb.model;

import com.zgrjb.base.BaseModel;



public class Friend extends BaseModel {
	private String createdDate;
	private String updatedDate;
	private String id;
	private String online;
	private String username;

	private String password;

	private String email;
	private String gender;

	private String name;
	private String face;
	private String age;
	
	// default is no login
	private boolean isLogin = false;
	
	 
	public Friend () {}
	
 
	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public Boolean getLogin () {
		return this.isLogin;
	}
	
	public void setLogin (boolean isLogin) {
		this.isLogin = isLogin;
	}
}