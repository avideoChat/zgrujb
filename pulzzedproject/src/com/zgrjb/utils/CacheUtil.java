package com.zgrjb.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import libcore.io.DiskLruCache;
import libcore.io.DiskLruCache.Snapshot;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

//缓存头像图片的工具类
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1) public class CacheUtil {
	final static String tag = "CacheUtil";
	
	/** 
     * 记录所有正在下载或等待下载的任务。 
     */  
    private Set<BitmapWorkerTask> taskCollection;  
    /** 
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。 
     */  
    private LruCache<String, Bitmap> mMemoryCache; 
    /** 
     * 图片硬盘缓存核心类。 
     */  
    private DiskLruCache mDiskLruCache; 
    
    private float cacheSizeScale = 0.125f;//默认值
    
    private View holderView;//使用该工具的控件
    
    private  File cacheDir;//硬盘缓存的目录
    
    private int width;//图片缓存的宽度
    
    private int height;//图片缓存的高度
    
    private final static CacheUtil instance = new CacheUtil();
    private CacheUtil(){
    	
    }
    public static CacheUtil getInstance(){
    	return instance;
    }
    /**
     * 设置缓存图片的高和宽    
     * @param width 
     * @param height
     */
    public void setWidthAndHeight(int width,int height){
    	this.width = width;
    	this.height = height;
    }
    /**
     * 
     * @param cacheSizeScale 缓存与内存间的比例因子
     */
    public void setCacheSize(float cacheSizeScale){
    	this.cacheSizeScale = cacheSizeScale;
    }
    
    public void setHolderView(View holderView){
    	this.holderView = holderView;
    }
    
    public void deleteCache() throws Exception{
    	if (mDiskLruCache.size()>0 && mDiskLruCache!=null){
    		mDiskLruCache.delete();
    		 Log.i("close","i am delete");

    	}
    }
    
    public void closeCache() throws Exception{
    	if ( mDiskLruCache!=null){
    		 mDiskLruCache.close();
             Log.i("close","i am close");
    	}
    }
    /**
     * 
     * @param context
     * @param dir 缓存目录地址
     * @param n 缓存最大大小，单位是MB
     */
    
    public void init(Context context,String dir,int n){
    	taskCollection = new HashSet<BitmapWorkerTask>();  
         // 获取应用程序最大可用内存  
         int maxMemory = (int) Runtime.getRuntime().maxMemory();  
         int cacheSize = (int) (maxMemory * cacheSizeScale);  
         
         mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {  
             @Override  
             protected int sizeOf(String key, Bitmap bitmap) {  
                 return bitmap.getByteCount();  
             }  
         };  
         try {  
             // 获取图片缓存路径  
             cacheDir = getDiskCacheDir(context, dir);  
             if (!cacheDir.exists()) {  
                 cacheDir.mkdirs();  
             }  
             // 创建DiskLruCache实例，初始化缓存数据  
             mDiskLruCache = DiskLruCache  
                         .open(cacheDir, getAppVersion(context), 1, n * 1024 * 1024);  
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
    }
    
    /** 
     * 将一张图片存储到LruCache中。 
     *  
     * @param key 
     *            LruCache的键，这里传入图片的URL地址。 
     * @param bitmap 
     *            LruCache的键，这里传入从网络上下载的Bitmap对象。 
     */  
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
        if (getBitmapFromMemoryCache(key) == null) {  
            mMemoryCache.put(key, bitmap);  
        }  
    }  
  
    /** 
     * 从LruCache中获取一张图片，如果不存在就返回null。 
     *  
     * @param key 
     *            LruCache的键，这里传入图片的URL地址。 
     * @return 对应传入键的Bitmap对象，或者null。 
     */  
    public Bitmap getBitmapFromMemoryCache(String key) {  
        return mMemoryCache.get(key);  
    } 
    
    /**
     * 从DiskLruCache读取缓存
     */
    public Bitmap getBitmapFromDiskLruCache(String imageUrl) {  
    	 String key = hashKeyForDisk(imageUrl);  
    	 try {
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			FileDescriptor fileDescriptor = null;  
            FileInputStream fileInputStream = null; 
			if (snapShot != null) {  
                fileInputStream = (FileInputStream) snapShot.getInputStream(0);  
                fileDescriptor = fileInputStream.getFD();  
            }  
            // 将缓存数据解析成Bitmap对象  
            Bitmap bitmap = null;  
            if (fileDescriptor != null) {  
            	bitmap = GraphicsBitmapUtils.decodeSampledFileDescriptor(fileDescriptor,width, height);
               // bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);  
                return bitmap;
            }  
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	 return null;
    }
    
  
    /** 
     * 加载Bitmap对象。此方法会在LruCache中检查所有屏幕中可见的ImageView的Bitmap对象， 
     * 如果发现任何一个ImageView的Bitmap对象不在缓存中，就会开启异步线程去下载图片。 
     */  
    public void loadBitmaps(ImageView imageView, String imageUrl) {  
        try {
        	Log.i("URL",imageUrl);
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl); 
            if (bitmap==null){
            	bitmap = getBitmapFromDiskLruCache(imageUrl);
            	Log.i("Bitmap","not null");
            }
            if (bitmap == null) {  
                BitmapWorkerTask task = new BitmapWorkerTask();  
                taskCollection.add(task);  
                task.execute(imageUrl);  
            } else {  
                if (imageView != null && bitmap != null) {  
                      imageView.setImageBitmap(bitmap);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    
    
    
  
    /** 
     * 取消所有正在下载或等待下载的任务。 
     */  
    public void cancelAllTasks() {  
        if (taskCollection != null) {  
            for (BitmapWorkerTask task : taskCollection) {  
                task.cancel(false);  
            }  
        }  
    }  
  
    /** 
     * 根据传入的uniqueName获取硬盘缓存的路径地址。 
     */  
    public File getDiskCacheDir(Context context, String uniqueName) {  
        String cachePath;  
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
                || !Environment.isExternalStorageRemovable()) {  
            cachePath = context.getExternalCacheDir().getPath();  
        } else {  
            cachePath = context.getCacheDir().getPath();  
        }  
        return new File(cachePath + File.separator + uniqueName);  
    }  
  
    /** 
     * 获取当前应用程序的版本号。 
     */  
    public int getAppVersion(Context context) {  
        try {  
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),  
                    0);  
            return info.versionCode;  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
        return 1;  
    }  
    
    /** 
     * 使用MD5算法对传入的key进行加密并返回。 
     */  
    public String hashKeyForDisk(String key) {  
        String cacheKey;  
        try {  
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");  
            mDigest.update(key.getBytes());  
            cacheKey = bytesToHexString(mDigest.digest());  
        } catch (NoSuchAlgorithmException e) {  
            cacheKey = String.valueOf(key.hashCode());  
        }  
        return cacheKey;  
    }  
      
    /** 
     * 将缓存记录同步到journal文件中。 
     */  
    public void fluchCache() {  
        if (mDiskLruCache != null) {  
            try {  
                mDiskLruCache.flush(); 
                Log.i("fluch","i am fluch");
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private String bytesToHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < bytes.length; i++) {  
            String hex = Integer.toHexString(0xFF & bytes[i]);  
            if (hex.length() == 1) {  
                sb.append('0');  
            }  
            sb.append(hex);  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 异步下载图片的任务。 
     *  
     * @author guolin 
     */  
    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {  
  
        /** 
         * 图片的URL地址 
         */  
        private String imageUrl;  
  
        @Override  
        protected Bitmap doInBackground(String... params) {  
            imageUrl = params[0];  
            FileDescriptor fileDescriptor = null;  
            FileInputStream fileInputStream = null;  
            Snapshot snapShot = null;  
            try {  
                // 生成图片URL对应的key  
                final String key = hashKeyForDisk(imageUrl);  
                // 查找key对应的缓存  
                snapShot = mDiskLruCache.get(key);  
                if (snapShot == null) {  
                    // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存  
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);  
                    if (editor != null) {  
                        OutputStream outputStream = editor.newOutputStream(0);  
                        if (downloadUrlToStream(imageUrl, outputStream)) {  
                            editor.commit();  
                        } else {  
                            editor.abort();  
                        }  
                    }  
                    // 缓存被写入后，再次查找key对应的缓存  
                    snapShot = mDiskLruCache.get(key);  
                }  
                if (snapShot != null) {  
                    fileInputStream = (FileInputStream) snapShot.getInputStream(0);  
                    fileDescriptor = fileInputStream.getFD();  
                }  
                // 将缓存数据解析成Bitmap对象  
                Bitmap bitmap = null;  
                if (fileDescriptor != null) {  
                	bitmap = GraphicsBitmapUtils.decodeSampledFileDescriptor(fileDescriptor,width, height);
                   // bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);  
                }  
                if (bitmap != null) {  
                    // 将Bitmap对象添加到内存缓存当中  
                    addBitmapToMemoryCache(params[0], bitmap);  
                }  
                return bitmap;  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                if (fileDescriptor == null && fileInputStream != null) {  
                    try {  
                        fileInputStream.close();  
                    } catch (IOException e) {  
                    }  
                }  
            }  
            return null;  
        }  
  
        @Override  
        protected void onPostExecute(Bitmap bitmap) {  
            super.onPostExecute(bitmap);  
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView imageView = null;
            if (holderView != null){
            	imageView = (ImageView) holderView.findViewWithTag(imageUrl);  
            }
            
            if (imageView != null && bitmap != null) {  
                imageView.setImageBitmap(bitmap);  
            }  
            taskCollection.remove(this);  
        }  
  
        /** 
         * 建立HTTP请求，并获取Bitmap对象。 
         *  
         * @param imageUrl 
         *            图片的URL地址 
         * @return 解析后的Bitmap对象 
         */  
        private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {  
            HttpURLConnection urlConnection = null;  
            BufferedOutputStream out = null;  
            BufferedInputStream in = null;  
            try {  
                final URL url = new URL(urlString);  
                urlConnection = (HttpURLConnection) url.openConnection();  
                in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);  
                out = new BufferedOutputStream(outputStream, 8 * 1024);  
                int b;  
                while ((b = in.read()) != -1) {  
                    out.write(b);  
                }  
                return true;  
            } catch (final IOException e) {  
                e.printStackTrace();  
            } finally {  
                if (urlConnection != null) {  
                    urlConnection.disconnect();  
                }  
                try {  
                    if (out != null) {  
                        out.close();  
                    }  
                    if (in != null) {  
                        in.close();  
                    }  
                } catch (final IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            return false;  
        }
        
         
  
    }
}
