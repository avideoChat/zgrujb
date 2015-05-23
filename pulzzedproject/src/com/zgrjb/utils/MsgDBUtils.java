package com.zgrjb.utils;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.database.sqlite.SQLiteDatabase;

import com.zgrjb.model.MsgRecord;

public class MsgDBUtils {
	
	private static final MsgDBUtils instance= new MsgDBUtils();
	public static MsgDBUtils getInstance(){
		return instance;
	}
	private MsgDBUtils(){
		
	}
    
     public SQLiteDatabase createDB(){
    	 SQLiteDatabase db = Connector.getDatabase();
    	 return db;
    	 
     }
     
     public boolean insert(MsgRecord mr){
    	 return mr.save();
     }
     
     public void insert(List<MsgRecord> list){
    	 DataSupport.saveAll(list);
     }
     
     public void deleteAll(){
    	 DataSupport.deleteAll(MsgRecord.class);
     }
     
     public void delete(MsgRecord mr){
    	 DataSupport.delete(MsgRecord.class,mr.getId());
     }
     /**
      * 默认读取10条数据
      * @param chatIds
      * @return
      */
     public List<MsgRecord> searchByChatId(String chatId){
      return DataSupport.where("chatId=?",""+chatId).order("time asc")
    		            .limit(10).offset(10).find(MsgRecord.class);
     }
     
     public List<MsgRecord> loardAllByChatId(String chatId){
    	 return DataSupport.where("chatId=?",""+chatId)
    			           .order("time asc").find(MsgRecord.class);
     }
     
     
     public MsgRecord getOne(){
    	 return DataSupport.find(MsgRecord.class, 1);
     }
     
     
     
     
     
}
