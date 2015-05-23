package com.zgrjb.utils;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.database.sqlite.SQLiteDatabase;

import com.zgrjb.model.LocalUserInfo;
import com.zgrjb.model.MsgRecord;

public class LocalUserInfoDBUtil {
	
	private static final LocalUserInfoDBUtil instance= new LocalUserInfoDBUtil();
	public static LocalUserInfoDBUtil getInstance(){
		return instance;
	}
	private LocalUserInfoDBUtil(){
		
	}
	
     public boolean insert(LocalUserInfo info){
    	 return info.save();
     }
     
     public void insert(List<LocalUserInfo> list){
    	 DataSupport.saveAll(list);
     }
     
     public void deleteAll(){
    	 DataSupport.deleteAll(LocalUserInfo.class);
     }
     
     public void delete(LocalUserInfo info){
    	 DataSupport.delete(LocalUserInfo.class,info.getId());
     }
  
     public List<LocalUserInfo> findAll(){
    	 return DataSupport.findAll(LocalUserInfo.class);
     }
     
     public void updateInfo(LocalUserInfo newInfo,int id){
    	    newInfo.update(id);
     }
     
     
     
   
    
    
}
