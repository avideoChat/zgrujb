package com.zgrjb.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Properties;

import org.androidpn.client.LogUtil;
import org.androidpn.client.ServiceManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class IOUtil {
	private static final String LOGTAG = LogUtil
			.makeLogTag(ServiceManager.class);

	// tag for log
	private static String TAG = IOUtil.class.getSimpleName();
	
	// Load image from local
	public static Bitmap getBitmapLocal(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Properties loadProperties(Context context) {
		// InputStream in = null;
		// Properties props = null;
		// try {
		// in = getClass().getResourceAsStream(
		// "/org/androidpn/client/client.properties");
		// if (in != null) {
		// props = new Properties();
		// props.load(in);
		// } else {
		// Log.e(LOGTAG, "Could not find the properties file.");
		// }
		// } catch (IOException e) {
		// Log.e(LOGTAG, "Could not find the properties file.", e);
		// } finally {
		// if (in != null)
		// try {
		// in.close();
		// } catch (Throwable ignore) {
		// }
		// }
		// return props;

		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier("androidpn", "raw",
					context.getPackageName());
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			Log.e(LOGTAG, "Could not find the properties file.", e);
			// e.printStackTrace();
		}
		return props;
	}
	// Load image from network
	public static Bitmap getBitmapRemote(Context ctx, String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			Log.w(TAG, url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = null;
			if (HttpUtil.WAP_INT == HttpUtil.getNetType(ctx)) {
				Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80)); 
				conn = (HttpURLConnection) myFileUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) myFileUrl.openConnection();
			}
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}