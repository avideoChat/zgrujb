package com.zgrjb.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zgrjb.R;
import com.zgrjb.application.BaseConfig;
import com.zgrjb.base.BaseMessage;
import com.zgrjb.base.BaseUi;
import com.zgrjb.selfdefindui.CImageView;
import com.zgrjb.utils.AndroidClass;
import com.zgrjb.utils.UploadUtil;

/*
 * 登陆注册轮播图
 * 辉明负责
 */
public class UIRegisterSecondActivity extends BaseUi {
	private CImageView face_im = null;
	private EditText age_etd = null;
	private EditText email_etd = null;
	private RadioGroup redios = null;
	private RadioButton checked_rb = null;

	private Button previous_btn = null;
	private Button finish_btn = null;
	private String name = null;
	private String password = null;
	private String gender = "男";

	private String user_face = null;

	private String[] arrayString = { "拍照", "相册" };
	private String title = "上传照片";

	// 上传的地址
	String uploadUrl = BaseConfig.api.base + "/" + BaseConfig.api.test + "?method=uploadface&";
	String filename = "照片";
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	// 对话框
	android.content.DialogInterface.OnClickListener onDialogClick = new android.content.DialogInterface.OnClickListener() {

		private void startCamearPicCut(DialogInterface dialog) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			// 调用系统的拍照功能
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra("camerasensortype", 2);// 调用前置摄像头
			intent.putExtra("autofocus", true);// 自动对焦
			intent.putExtra("fullScreen", false);// 全屏
			intent.putExtra("showActionIcons", false);
			// 指定调用相机拍照后照片的储存路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
		}

		private void startImageCaptrue(DialogInterface dialog) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

		}

		@Override
		public void onClick(DialogInterface arg0, int which) {
			// TODO Auto-generated method stub
			switch (which) {
			case 0:
				startCamearPicCut(arg0);// 开启照相
				break;
			case 1:
				startImageCaptrue(arg0);// 开启图库
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_registersecond);
		Intent params = getIntent();
		name = params.getStringExtra("name");
		password = params.getStringExtra("password");
		initView();

	}

	private void initView() {
		redios = (RadioGroup) findViewById(R.id.redio_group);
		face_im = (CImageView) findViewById(R.id.register_face);
		age_etd = (EditText) findViewById(R.id.register_age);
		email_etd = (EditText) findViewById(R.id.register_email);
		previous_btn = (Button) findViewById(R.id.register_previous);
		finish_btn = (Button) findViewById(R.id.register_bfinish);

		face_im.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = AndroidClass.getListDialogBuilder(
						UIRegisterSecondActivity.this, arrayString, title,
						onDialogClick);
				dialog.show();
			}
		});
		OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case 0:
					redios.check(0);
					break;

				case 1:
					redios.check(1);
					break;
				}
			}
		};

		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getId()) {
				case R.id.register_previous:
					Bundle params = new Bundle();
					params.putString("name", name);
					params.putString("password", password);
					forward(UIRegisterFirstActivity.class, params);
					break;

				case R.id.register_bfinish:
					if (checkInput()) {
						checked_rb = (RadioButton) findViewById(redios
								.getCheckedRadioButtonId());
						HashMap<String, String> sendparams = new HashMap<String, String>();

						sendparams.put("method", "register");
						sendparams.put("name", name);
						sendparams.put("password", password);
						sendparams.put("face", user_face);
						Log.i("resultint", user_face);
						sendparams.put("gender", checked_rb.getText()
								.toString());
						sendparams.put("email", email_etd.getText().toString());
						sendparams.put("age", age_etd.getText().toString());

						doTaskAsync(BaseConfig.task.register, BaseConfig.api.register, sendparams);
					} else {
						showLongToast("请核对信息！");
					}
					break;
				}
			}
		};
		redios.setOnCheckedChangeListener(onCheckedChangeListener);
		previous_btn.setOnClickListener(onClickListener);
		finish_btn.setOnClickListener(onClickListener);
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private boolean checkInput() {
		if (!email_etd.getText().toString().equals("")
				&& email_etd.getText().toString().length() > 0
				&& !age_etd.getText().toString().equals("")
				&& age_etd.getText().toString().length() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case BaseConfig.task.register:
			String code = message.getCode();
			if ("1".equals(code)) {
				showLongToast("注册成功！");
				forward(UIStartActivity.class);
			} else if ("2".equals(code)) {
				showLongToast("用户名已经存在！");
			} else if ("0".equals(code)) {
				showLongToast("注册失败！");
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			Uri url = Uri.fromFile(tempFile);

		 
			UploadUtil.uploadFile(tempFile, uploadUrl);
			 
			face_im.setImageDrawable(new BitmapDrawable(tempFile
					.getAbsolutePath()));

			String []substring= tempFile.getAbsolutePath().split("/");
				user_face = substring[substring.length - 1];
				 
			break;
		case PHOTO_REQUEST_GALLERY:
			if (data != null) {
				Bitmap bm = null;
				// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
				ContentResolver resolver = getContentResolver();
				Uri originalUri = data.getData(); // 获得图片的uri
				try {
					bm = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					String[] proj = { MediaStore.Images.Media.DATA };

					// 好像是android多媒体数据库的封装接口，具体的看Android文档
					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);
					// 按我个人理解 这个是获得用户选择的图片的索引值
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// 将光标移至开头 ，这个很重要，不小心很容易引起越界
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径
					String path = cursor.getString(column_index);
					String []sub = path.split("/");
					user_face= sub[sub.length-1];
					
					uploadPic(path);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void uploadPic(final String filepath) {

		// TODO Auto-generated method stub
		if (filepath != null) {
			File file = new File(filepath);
			if (file != null) {
			 
				UploadUtil.uploadFile(file, uploadUrl);
				 
				face_im.setImageDrawable(new BitmapDrawable(filepath));

			}

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Bundle params = new Bundle();
			params.putString("name", name);
			params.putString("password", password);
			forward(UIRegisterFirstActivity.class, params);
		}
		return true;
	}

}
