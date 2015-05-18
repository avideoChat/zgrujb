package com.zgrjb.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.sinaapp.bashell.VoAACEncoder;
import com.zgrjb.R;
import com.zgrjb.adapter.ChatMsgViewAdapter;
import com.zgrjb.adapter.FaceGVAdapter;
import com.zgrjb.adapter.FaceVPAdapter;
import com.zgrjb.model.ChatMsgModel;

import com.zgrjb.utils.HttpOperateUtil;
import com.zgrujb.selfdefindui.HeaderLayout;
import com.zgrujb.selfdefindui.HeaderLayout.HeaderStyle;
import com.zgrujb.selfdefindui.HeaderLayout.onRightImageButtonClickListener;
import com.zgrujb.selfdefindui.MyEditText;

/**
 * 
 * @author gray
 *  CSDN博客http://blog.csdn.net/geniuseoe2012
 *  android开发交流群：200102476
 */
public class UIChatActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnLongClickListener{
    /** Called when the activity is first created. */
	private String tag = getClass().getSimpleName();
	private Button btnSend;
	private Button btnAudio;
	private ToggleButton tbChangInput;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgModel> mDataArrays = new ArrayList<ChatMsgModel>();
	private HeaderLayout hlTitleBar;
	
	private Handler handler = null;
	private AudioRecord recordInstance;
	private boolean isStartRecord;
	private FileOutputStream fos;

	private int SAMPLERATE = 8000;

	private MediaPlayer mMediaPlayer;

	private String mRecordFileName;
	public static String BASE_URL = "http://10.0.35.122:8080/Struts2";
	String URL_UPLOAD_FILE = BASE_URL+"/upload";
	
	//------------------------
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;//表情下小圆点
	private MyEditText etContent;
	private LinearLayout chat_face_container;//表情布局
	private ImageView iv_face;//表情图标
	// 7列3行
	private int columns = 6;
	private int rows = 4;
	private List<View> views = new ArrayList<View>();
	private List<String> staticFacesList;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chat);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initStaticFaces();
        initView();
        InitViewPager();
        initData();
        
        handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 1){
//					Toast.makeText(UIChat.this, "发送成功!", Toast.LENGTH_SHORT).show();
					sendAudio();
				}else{
					Toast.makeText(UIChatActivity.this, "发送失败!", Toast.LENGTH_SHORT).show();
				}
				super.handleMessage(msg);
			}
		};
    }
    
    public void initView()
    {
    	hlTitleBar = (HeaderLayout) findViewById(R.id.common_actionbar);
    	hlTitleBar.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
    	hlTitleBar.setTitleAndRightImageButton("chat", R.drawable.icon_home, new onRightImageButtonClickListener() {
			
			@Override
			public void onClick() {
				Log.i(tag, "you press the rightButton of titlebar");
				
			}
		});
    	iv_face = (ImageView) findViewById(R.id.btn_emo);//表情按钮
		iv_face.setOnClickListener(this);
		chat_face_container=(LinearLayout) findViewById(R.id.chat_face_container);
		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		mViewPager.setOnPageChangeListener(new PageChange());
		mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
		
    	mListView = (ListView) findViewById(R.id.listview);
    	tbChangInput = (ToggleButton) findViewById(R.id.tb_changInput);
    	tbChangInput.setOnCheckedChangeListener(this);
    	btnAudio = (Button) findViewById(R.id.btn_audio);
    	btnAudio.setOnLongClickListener(this);
    	btnAudio.setOnTouchListener(new MyTouchListener());
    	btnSend = (Button) findViewById(R.id.btn_send);
    	btnSend.setOnClickListener(this);
    	
    	etContent = (MyEditText) findViewById(R.id.et_sendmessage);
		etContent.setOnClickListener(this);
    }
    
    private final static int COUNT = 8;
    public void initData()
    {

        String[]msgArray = new String[]{"有大吗", "有！你呢？", "我也有", "那上吧", 
        										"打啊！你放大啊", "你tm咋不放大呢？留大抢人头那！Cao的。你个菜b",
        										"2B不解释", "尼滚....",};
        
        String[]dataArray = new String[]{"2012-09-01 18:00", "2012-09-01 18:10", 
        										"2012-09-01 18:11", "2012-09-01 18:20", 
        										"2012-09-01 18:30", "2012-09-01 18:35", 
        										"2012-09-01 18:40", "2012-09-01 18:50"}; 
    	for(int i = 0; i < COUNT; i++)
    	{
    		ChatMsgModel entity = new ChatMsgModel();
    		entity.setDate(dataArray[i]);
    		if (i % 2 == 0)
    		{
    			entity.setName("小黑");
    			entity.setIsComMeg(true);
    		}else{
    			entity.setName("人马");
    			entity.setIsComMeg(false);
    		}
    		
    		entity.setText(msgArray[i]);
    		mDataArrays.add(entity);
    	}

    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
    }
	
	class MyTouchListener implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				setTalkBtnBackground(false);
				// TODO 正常放开，接下来一般做以下事情：发送录音文件到服务器
				if (isStartRecord) {
					// 停止录音
					stopRecord();
					Toast.makeText(UIChatActivity.this, "开始发送文件！",
							Toast.LENGTH_SHORT).show();

					new Thread(new Runnable() {
						
						public void run() {
							// 上传文件
							Message msg = new Message();
							if(HttpOperateUtil.uploadFile(URL_UPLOAD_FILE, mRecordFileName)){
								msg.what = 1;
							}else{
								msg.what = 0;
							}
							handler.sendMessage(msg);
						}
					}).start();
					
				}
				break;

			case MotionEvent.ACTION_CANCEL:
				Log.i(tag, "record cancel!! ");
				setTalkBtnBackground(false);
				// TODO 异常放开，接下来一般做以下事情：删除录音文件
				// 停止录音
				stopRecord();
				break;

			default:
				break;
			}
			return false;
		}
	}
	
	/******************** 逻辑处理方法 **************************/
	/*
	 * 初始表情 *
	 */
	private void InitViewPager() {
		// 获取页数
		for (int i = 0; i < getPagerCount(); i++) {
			Log.i(tag, "initViewPager's get page count "+i);
			views.add(viewPagerItem(i));
			LayoutParams params = new LayoutParams(16, 16);
			mDotsLayout.addView(dotsItem(i), params);
		}
		Log.i(tag, "the mDotsLayout's count: "+mDotsLayout.getChildCount());
		FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
		mViewPager.setAdapter(mVpAdapter);
		View view = mDotsLayout.getChildAt(0);
		if(view != null){
			//why the view is null?~~~!!s
			view.setSelected(true);
		}
	}

	private View viewPagerItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
		GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
		/**
		 * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
		 * */
		List<String> subList = new ArrayList<String>();
		subList.addAll(staticFacesList
				.subList(position * (columns * rows - 1),
						(columns * rows - 1) * (position + 1) > staticFacesList
								.size() ? staticFacesList.size() : (columns
								* rows - 1)
								* (position + 1)));
		/**
		 * 末尾添加删除图标
		 * */
		subList.add("emotion_del_normal.png");
		FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
		gridview.setAdapter(mGvAdapter);
		gridview.setNumColumns(columns);
		// 单击表情执行的操作
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				try {
					String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
					if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
						insert(getFace(png));
					} else {
						delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return gridview;
	}

	private SpannableStringBuilder getFace(String png) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		try {
			/**
			 * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
			 * 所以这里对这个tempText值做特殊处理
			 * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
			 * */
			String tempText = "#[" + png + "]#";
			sb.append(tempText);
			sb.setSpan(
					new ImageSpan(UIChatActivity.this, BitmapFactory
							.decodeStream(getAssets().open(png))), sb.length()
							- tempText.length(), sb.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;
	}

	/**
	 * 向输入框里添加表情
	 * */
	private void insert(CharSequence text) {
		int iCursorStart = Selection.getSelectionStart((etContent.getText()));
		int iCursorEnd = Selection.getSelectionEnd((etContent.getText()));
		if (iCursorStart != iCursorEnd) {
			((Editable) etContent.getText()).replace(iCursorStart, iCursorEnd, "");
		}
		int iCursor = Selection.getSelectionEnd((etContent.getText()));
		((Editable) etContent.getText()).insert(iCursor, text);
	}

	/**
	 * 删除图标执行事件
	 * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
	 * */
	private void delete() {
		if (etContent.getText().length() != 0) {
			int iCursorEnd = Selection.getSelectionEnd(etContent.getText());
			int iCursorStart = Selection.getSelectionStart(etContent.getText());
			if (iCursorEnd > 0) {
				if (iCursorEnd == iCursorStart) {
					if (isDeletePng(iCursorEnd)) {
						String st = "#[face/png/f_static_000.png]#";
						((Editable) etContent.getText()).delete(
								iCursorEnd - st.length(), iCursorEnd);
					} else {
						((Editable) etContent.getText()).delete(iCursorEnd - 1,
								iCursorEnd);
					}
				} else {
					((Editable) etContent.getText()).delete(iCursorStart,
							iCursorEnd);
				}
			}
		}
	}

	/**
	 * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
	 * **/
	private boolean isDeletePng(int cursor) {
		String st = "#[face/png/f_static_000.png]#";
		String content = etContent.getText().toString().substring(0, cursor);
		if (content.length() >= st.length()) {
			String checkStr = content.substring(content.length() - st.length(),
					content.length());
			String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(checkStr);
			return m.matches();
		}
		return false;
	}

	private ImageView dotsItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dot_image, null);
		ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
		iv.setId(position);
		return iv;
	}

	/**
	 * 根据表情数量以及GridView设置的行数和列数计算Pager数量
	 * @return
	 */
	private int getPagerCount() {
		int count = staticFacesList.size();
		return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
				: count / (columns * rows - 1) + 1;
	}

	/**
	 * 初始化表情列表staticFacesList
	 */
	private void initStaticFaces() {
		try {
			staticFacesList = new ArrayList<String>();
			String[] faces = getAssets().list("face/png");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for (int i = 0; i < faces.length; i++) {
				staticFacesList.add(faces[i]);
			}
			//去掉删除图片
			staticFacesList.remove("emotion_del_normal.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表情页改变时，dots效果也要跟着改变
	 * */
	class PageChange implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
				mDotsLayout.getChildAt(i).setSelected(false);
			}
			mDotsLayout.getChildAt(arg0).setSelected(true);
		}

	}
	/**
	 * hide the softInputView if it is showing
	 * @return true if it is showing and can be hided
	 */
	public void hideSoftInputView() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null){
				((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}
	private void sendText()
	{
		String contString = etContent.getText().toString();
		if (contString.length() > 0)
		{
			ChatMsgModel entity = new ChatMsgModel();
			entity.setDate(getCurrentDateTime());
			entity.setName("人马");
			entity.setIsComMeg(false);
			entity.setText(contString);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			
			etContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
		}
	}
	private void sendAudio(){
		ChatMsgModel entity = new ChatMsgModel();
		entity.setDate(getCurrentDateTime());
		entity.setName("人马");
		entity.setIsComMeg(false);
		entity.setText("");
		entity.setAudioUrl(mRecordFileName);
		mDataArrays.add(entity);
		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mListView.getCount() - 1);
	}
	
    private String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        						
        return sbBuffer.toString();
    }

	public void stopRecord() {
		isStartRecord = false;
	}

	/**
	 * 通过本地音频的路径查找文件，如果找不到则上网下载并播放
	 * @param audioPath
	 */
	public void startPlay(String audioPath) {
		final String fileUrl = audioPath;//"http://10.0.33.206:8080/Struts2/download/95533.aac";
		
		Log.i(tag, "startPlay the path: "+audioPath);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Looper.prepare();
//				String fileUrl = "http://192.168.1.147/upload/testAAC.aac";
				
				File file = new File(fileUrl);
				if (!file.exists()) {
//					Toast.makeText(UIChat.this, "没有音乐文件！", Toast.LENGTH_SHORT)
//							.show();
//					return;
					String fileName = BASE_URL+"/download/"+fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
					HttpOperateUtil.downLoadFile(fileName,
							fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
					Log.i("keanbin", "fileName = " + fileName);
				}

				mMediaPlayer = MediaPlayer.create(UIChatActivity.this,
						Uri.parse(fileUrl));
				mMediaPlayer.setLooping(false);
				mMediaPlayer.start();
				
				Looper.loop();
			}
		}).start();

	}

	public void stopPlay() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
		}
	}

	public void startRecord() {
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			Toast.makeText(UIChatActivity.this, "请插入SD卡！", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		try {
			// mRecordFileName = Environment.getExternalStorageDirectory()
			// .toString()
			// + "/testAAC"
			// + System.currentTimeMillis()
			// + ".aac";
			mRecordFileName = Environment.getExternalStorageDirectory()
					.toString() + "/"+UUID.randomUUID()+".aac";

			fos = new FileOutputStream(mRecordFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Log.i(tag, "fos = " + fos);
		new Thread(new Runnable() {
			@Override
			public void run() {
				VoAACEncoder vo = new VoAACEncoder();
				vo.Init(SAMPLERATE, 16000, (short) 1, (short) 1);// 采样率:16000,bitRate:32k,声道数:1，编码:0.raw
				// 1.ADTS
				int min = AudioRecord.getMinBufferSize(SAMPLERATE,
						AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT);
				if (min < 2048) {
					min = 2048;
				}
				byte[] tempBuffer = new byte[2048];
				recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC,
						SAMPLERATE, AudioFormat.CHANNEL_IN_MONO,
						AudioFormat.ENCODING_PCM_16BIT, min);
				recordInstance.startRecording();
				isStartRecord = true;
				while (isStartRecord) {
					int bufferRead = recordInstance.read(tempBuffer, 0, 2048);
					byte[] ret = vo.Enc(tempBuffer);
					if (bufferRead > 0) {
						System.out.println("ret:" + ret.length);
						try {
							fos.write(ret);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				recordInstance.stop();
				recordInstance.release();
				recordInstance = null;
				vo.Uninit();
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	public void setTalkBtnBackground(boolean isTalk) {
		if (isTalk) {
			btnAudio.setBackgroundResource(R.drawable.btn_style_alert_dialog_button_pressed);
		} else {
			btnAudio.setBackgroundResource(R.drawable.btn_style_alert_dialog_button_normal);
		}
	}
	
	/******************* 监听事件回调方法 **********************/
	public void onAudioClick(View v){
		if(v.getTag() != null){
			String text = v.getTag().toString();
			Log.i(tag, "onAudioClick and the text is: "+text);
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			startPlay(text);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(chat_face_container.getVisibility() == View.VISIBLE){
			chat_face_container.setVisibility(View.GONE);
			return true;//make the event not propagate further
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {

		stopPlay();
		stopRecord();
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.et_sendmessage:
			if(chat_face_container.getVisibility()==View.VISIBLE){
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_emo:
			Log.i(tag, "onclick btn_emo getFocus: "+getCurrentFocus());
			hideSoftInputView();//隐藏软键盘
			if(chat_face_container.getVisibility()==View.GONE){
				chat_face_container.setVisibility(View.VISIBLE);
			}else{
				chat_face_container.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_send:
			sendText();
			break;
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			btnAudio.setVisibility(View.VISIBLE);
			btnSend.setVisibility(View.GONE);
			etContent.setVisibility(View.GONE);
			iv_face.setVisibility(View.GONE);
			hideSoftInputView();
			if(chat_face_container.getVisibility()==View.VISIBLE){
				chat_face_container.setVisibility(View.GONE);
			}
			Log.i(tag, "is check, and the current focus: "+getCurrentFocus());
		}else{
			btnAudio.setVisibility(View.GONE);
			btnSend.setVisibility(View.VISIBLE);
			etContent.setVisibility(View.VISIBLE);
			iv_face.setVisibility(View.VISIBLE);
			etContent.requestFocus();
			Log.i(tag, "not check;  current focus: "+getCurrentFocus());
		}
		
	}

	@Override
	public boolean onLongClick(View v) {
		Log.i("keanbin", "onLongClick()");
		setTalkBtnBackground(true);

		// 停止播放声音
		stopPlay();
		// 开始录音
		startRecord();

		return true;
	}

}