package com.zgrjb.utils;



import android.content.Context;
import android.content.res.Resources;

import com.zgrjb.R;
import com.zgrjb.model.Customer;

public class UIUtil {

	// tag for log
//	private static String TAG = UIUtil.class.getSimpleName();
	
	public static String getCustomerInfo (Context ctx, Customer customer) {
		Resources resources = ctx.getResources();
		StringBuffer sb = new StringBuffer();
		//sb.append(resources.getString(R.string.customer_blog));
		sb.append(" ");
		 
		sb.append(" | ");
		//sb.append(resources.getString(R.string.customer_fans));
		sb.append(" ");
		 
		return sb.toString();
	}
}