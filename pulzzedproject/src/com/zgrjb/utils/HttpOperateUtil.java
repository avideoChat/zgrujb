package com.zgrjb.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Environment;
import android.util.Log;

public class HttpOperateUtil {

	final static String tag = "HttpOperateUtil";
	/**
	 * 将文件上传到指定的网络路径
	 * @param uploadUrl 上传到的网络路径
	 * @param srcPath 欲上传的文件的路径
	 * @return
	 */
	public static boolean uploadFile(String uploadUrl, String srcPath) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		String filename = srcPath.substring(srcPath.lastIndexOf("/") + 1);
		Log.i(tag, "the name of the file to upload: "+srcPath);
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			Log.i(tag, "the url of upload: "+url);
			// 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
			// 此方法用于在预先不知道内容长度时启用,没有进行内部缓冲的 HTTP 请求正文的流。
			httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
			// 允许输入输出流
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			// 使用POST方法
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			httpURLConnection.setRequestProperty("filename", filename);
			Log.v(tag, "HttpURLConnection has setted...");
			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			Log.v(tag, "http get the inputstream: "+dos);
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
					+ filename
					+ "\""
					+ end);
			dos.writeBytes(end);

			// 上传文件内容
			FileInputStream fis = new FileInputStream(srcPath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			// 读取文件
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			// 服务器返回结果
			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			// Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			Log.i(tag, "uploadFile result = " + result);

			try {
				dos.close();
			} catch (Exception e) {
				e.printStackTrace();
				// setTitle(e.getMessage());
			}
			is.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		}
		Log.i(tag, "the file failed to upload --");
		return false;
	}

	/**
	 * 根据文件的网络路径把文件下载到内存卡中的download文件夹，并用给定的文件名命名
	 * @param fileUrl 欲下载的文件的网络路径
	 * @param fileName 下载后的文件名
	 * @return 文件下载后的绝对路径
	 */
	public static String downLoadFile(String fileUrl, String fileName) {
		String fileDir = "";
		Log.i(tag, "downloadfile");
		try {
			// 判断SD卡是否存在，并且是否具有读写权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// 获得存储卡的路径
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				String savePath = sdpath + "download";
				URL url = new URL(fileUrl);
				// 创建连接
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				// 获取文件大小
				int contentLength = conn.getContentLength();
				// 创建输入流
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				// 判断文件目录是否存在
				if (!file.exists()) {
					file.mkdir();
				}
				File apkFile = new File(savePath, fileName);
				FileOutputStream fos = new FileOutputStream(apkFile);
				int count = 0;
				// 缓存
				byte buf[] = new byte[1024];
				// 写入到文件中
				do {
					int numread = is.read(buf);
					// count += numread;
					// // 计算进度条位置
					// mPercent = (int) (((float) count / contentLength) * 100);
					// // 更新进度
					// mHandler.sendEmptyMessage(DOWNLOAD_PERCENT);
					if (numread <= 0) {
						// 下载完成
						fileDir = savePath + "/" + fileName;
						// if (checkUpdateResult(apkFile)) {
						// // 下载成功
						//
						// } else {
						// // 下载失败
						// fos.close();
						// is.close();
						// // throw new RuntimeException("下载文件出错");
						// }
						break;
					}
					// 写入文件
					fos.write(buf, 0, numread);
				} while (true);// !mCancelUpdate);// 点击取消就停止下载.
				fos.close();
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileDir;
	}
	
}
