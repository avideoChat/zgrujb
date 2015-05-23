package com.zgrjb.utils;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.zgrjb.model.LastMsgRecord;
import com.zgrjb.model.LocalUserInfo;

public class LastMsgRecordDBUtil {
	private static final LastMsgRecordDBUtil instance= new LastMsgRecordDBUtil();
	public static LastMsgRecordDBUtil getInstance(){
		return instance;
	}
	private LastMsgRecordDBUtil(){
		
	}
	
     public boolean insert(LastMsgRecord record){
    	 return record.save();
     }
     
     public void insert(List<LastMsgRecord> list){
    	 DataSupport.saveAll(list);
     }
     
     public void deleteAll(){
    	 DataSupport.deleteAll(LastMsgRecord.class);
     }
     
     public void delete(LocalUserInfo info){
    	 DataSupport.delete(LastMsgRecord.class,info.getId());
     }
     
     public List<LastMsgRecord> findAll(){
    	 return DataSupport.findAll(LastMsgRecord.class);
     }
     
     public void updateMsg(LastMsgRecord newMsg,int id){
 	    newMsg.update(id);
  }
     
}
