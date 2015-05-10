package com.zgrjb.utils;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class DrawableUtils {
   
	
   public byte[] Bitmap2Bytes(Bitmap bm) {  
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
	    return baos.toByteArray();  
	}  
   
    public Bitmap Bytes2Bimap(byte[] b) {  
	    if (b.length != 0) {  
	        return BitmapFactory.decodeByteArray(b, 0, b.length);  
	    } else {  
	        return null;  
	    }  
	}
    //bitmap缩放
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {  
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
        Matrix matrix = new Matrix();  
        float scaleWidth = ((float) width / w);  
        float scaleHeight = ((float) height / h);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);  
        return newbmp;  
    }  
    
    //将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {  
        // 取 drawable 的长宽  
        int w = drawable.getIntrinsicWidth();  
        int h = drawable.getIntrinsicHeight();  
  
        // 取 drawable 的颜色格式  
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                : Bitmap.Config.RGB_565;  
        // 建立对应 bitmap  
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);  
        // 建立对应 bitmap 的画布  
        Canvas canvas = new Canvas(bitmap);  
        drawable.setBounds(0, 0, w, h);  
        // 把 drawable 内容画到画布中  
        drawable.draw(canvas);  
        return bitmap;  
    }  
    
  
    //Drawable缩放
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {  
        int width = drawable.getIntrinsicWidth();  
        int height = drawable.getIntrinsicHeight();  
        // drawable转换成bitmap  
        Bitmap oldbmp = drawableToBitmap(drawable);  
        // 创建操作图片用的Matrix对象  
        Matrix matrix = new Matrix();  
        // 计算缩放比例  
        float sx = ((float) w / width);  
        float sy = ((float) h / height);  
        // 设置缩放比例  
        matrix.postScale(sx, sy);  
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图  
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,  
                matrix, true);  
        return new BitmapDrawable(newbmp);  
    }  
    
}
