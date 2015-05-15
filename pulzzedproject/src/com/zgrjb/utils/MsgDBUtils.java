package com.zgrjb.utils;

import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.database.sqlite.SQLiteDatabase;

import com.zgrjb.domain.MsgRecord;

public class MsgDBUtils {
	
	private static final MsgDBUtils instance= new MsgDBUtils();
	public static MsgDBUtils getInstance(){
		return instance;
	}
	
    
     public SQLiteDatabase createDB(){
    	  return Connector.getDatabase();
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
     
     public void delete(){
    	 
     }
     
     
     
     
     
}
