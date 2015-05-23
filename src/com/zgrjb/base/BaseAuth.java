package com.zgrjb.base;

import com.zgrjb.model.Customer;

 

public class BaseAuth {
	static public boolean isLogin () {
		Customer customer = Customer.getInstance();
		if (customer.isLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setLogin (Boolean status) {
		Customer customer = Customer.getInstance();
		customer.setLogin(status);
	}
	
	static public void setcustomer (Customer mc) {
		Customer customer = Customer.getInstance();	 
		customer.setAge(mc.getAge());
		customer.setEmail(mc.getEmail());
		customer.setFace(mc.getFace());
		customer.setGender(mc.getGender());
		customer.setName(mc.getName());
		customer.setOnline(mc.getOnline());
		customer.setUsername(mc.getUsername());	 
		customer.setId(mc.getId());
		customer.setLogin(mc.isLogin());		
	}
	
	static public Customer getcustomer () {
		return Customer.getInstance();
	}
//	static public boolean isLogin () {
//		Customer customer = Customer.getInstance();
//		if (customer.getLogin() == true) {
//			return true;
//		}
//		return false;
//	}
//	
//	static public void setLogin (Boolean status) {
//		Customer customer = Customer.getInstance();
//		customer.setLogin(status);
//	}
//	
//	static public void setCustomer (Customer mc) {
//		Customer customer = Customer.getInstance();
//		customer.setId(mc.getId());
//		customer.setSid(mc.getSid());
//		customer.setName(mc.getName());
//		customer.setSign(mc.getSign());
//		customer.setFace(mc.getFace());
//	}
//	
//	static public Customer getCustomer () {
//		return Customer.getInstance();
//	}
}