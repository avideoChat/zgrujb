<<<<<<< HEAD

package com.zgrjb.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.model.ChatMsgModel;
import com.zgrjb.utils.CacheUtil;
import com.zgrujb.selfdefindui.AnimatedGifDrawable;
import com.zgrujb.selfdefindui.AnimatedImageSpan;


@TargetApi(Build.VERSION_CODES.FROYO) public class ChatMsgViewAdapter extends BaseAdapter {
	
	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	
    private String tag = ChatMsgViewAdapter.class.getSimpleName();

    private List<ChatMsgModel> coll;
    private Context ctx;
    /** 弹出的更多选择框 */
	private PopupWindow popupWindow;
	/** 复制，删除 */
	private TextView copy, delete;
	/**
	 * 执行动画的时间
	 */
	protected long mAnimationTime = 150;
    private LayoutInflater mInflater;
    
    private final CacheUtil util = CacheUtil.getInstance();
    
    public ChatMsgViewAdapter(Context context, List<ChatMsgModel> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        initPopWindow();
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsgModel entity = coll.get(position);
	 	
	 	if (entity.isComeMsg())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ChatMsgModel entity = coll.get(position);
    	boolean isComMsg = entity.isComeMsg();
    	
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {     viewHolder = new ViewHolder();
	    	 
	    if (isComMsg)
			  {
				  convertView = mInflater.inflate(R.layout.list_item_chat_left, null);
				  viewHolder.headImg = (ImageView) convertView.findViewById(R.id.chat_iv_righthead);
			  }else{
				  convertView = mInflater.inflate(R.layout.list_item_chat_right, null);
				  viewHolder.headImg = (ImageView) convertView.findViewById(R.id.chat_iv_lefthead);
			  }

	    	  
	    	  
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg = isComMsg;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	    
	    viewHolder.tvSendTime.setText(entity.getDate());
	    viewHolder.tvUserName.setText(entity.getName());
	    String url;
	    if((url = entity.getAudioUrl()) != null){
//	    	viewHolder.tvContent.setCompoundDrawablesRelative(null, null, ctx.getResources().getDrawable(R.drawable.ic_audio_sign), null);
	    	viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, ctx.getResources().getDrawable(R.drawable.ic_audio_sign), null);
	    	viewHolder.tvContent.setText("");
	    	viewHolder.tvContent.setTag(url);
	    	Log.i("ChatMsgViewAdapter", "url: "+url+" and the tvContent's content is: -"+viewHolder.tvContent.getText()+"-");
//	    	viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
	    }else{
	    	// 对内容做处理
	    	SpannableStringBuilder sb = handler(viewHolder.tvContent, entity.getText());
	    	viewHolder.tvContent.setText(sb);
	    }
	    
	    viewHolder.tvContent.setOnLongClickListener(new popAction(convertView, position, isComMsg));
	    
	    String imgUrl = coll.get(position).getHeadPortraitUrl();
	    if(imgUrl!=null){
	    	Log.i("msg",imgUrl+"-MSG");
	    	Bitmap bm = util.getBitmapFromDiskLruCache(imgUrl);
	    	if(bm!=null){
	    		Log.i("BMP",viewHolder.headImg+"-BMP");
	    	  viewHolder.headImg.setImageBitmap(bm);
	    	}
	    }
	    return convertView;
    }
    
    private SpannableStringBuilder handler(final TextView gifTextView,String content) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring("#[face/png/f_static_".length(), tempText.length()- ".png]#".length());
				String gif = "face/gif/f" + num + ".gif";
				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif
				 * 否则说明gif找不到，则显示png
				 * */
				InputStream is = ctx.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("#[".length(),tempText.length() - "]#".length());
				try {
					sb.setSpan(new ImageSpan(ctx, BitmapFactory.decodeStream(ctx.getAssets().open(png))), m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	}

    static class ViewHolder { 
    	public ImageView headImg;
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }

    /**
	 * 屏蔽listitem的所有事件
	 * */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/**
	 * 初始化弹出的pop
	 * */
	private void initPopWindow() {
		View popView = mInflater.inflate(R.layout.chat_item_copy_delete_menu,
				null);
		copy = (TextView) popView.findViewById(R.id.chat_copy_menu);
		delete = (TextView) popView.findViewById(R.id.chat_delete_menu);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		// 设置popwindow出现和消失动画
		// popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
	}

	/**
	 * 显示popWindow
	 * */
	public void showPop(View parent, int x, int y, final View view,
			final int position, final boolean isComeMsg) {
		// 设置popwindow显示位置
		popupWindow.showAtLocation(parent, 0, x, y);
		// 获取popwindow焦点
		popupWindow.setFocusable(true);
		// 设置popwindow如果点击外面区域，便关闭。
		popupWindow.setOutsideTouchable(true);
		// 为按钮绑定事件
		// 复制
		copy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				// 获取剪贴板管理服务
				ClipboardManager cm = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
				// 将文本数据复制到剪贴板
				cm.setText(coll.get(position).getText());
			}
		});
		// 删除
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				if (isComeMsg) {
					// from
					leftRemoveAnimation(view, position);
				}else{
					// to
					rightRemoveAnimation(view, position);
				}
				coll.remove(position);
				notifyDataSetChanged();
			}
		});
		popupWindow.update();
		if (popupWindow.isShowing()) {

		}
	}

	/**
	 * 每个ITEM中more按钮对应的点击动作
	 * */
	public class popAction implements OnLongClickListener {
		int position;
		View view;
		boolean isComeMsg;

		public popAction(View view, int position, boolean isComeMsg) {
			this.position = position;
			this.view = view;
			this.isComeMsg = isComeMsg;
		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			// System.out.println("x: " + x + " y:" + y + " w: " +
			// v.getMeasuredWidth() + " h: " + v.getMeasuredHeight() );
			showPop(v, x, y, view, position, isComeMsg);
			return true;
		}
	}

	/**
	 * item删除动画
	 * */
	private void rightRemoveAnimation(final View view, final int position) {
		final Animation animation = (Animation) AnimationUtils.loadAnimation(
				ctx, R.anim.chatto_remove_anim);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
//				view.setAlpha(0);
//				performDismiss(view, position);
				animation.cancel();
			}
		});

		view.startAnimation(animation);
	}

	/**
	 * item删除动画
	 * */
	private void leftRemoveAnimation(final View view, final int position) {
		final Animation animation = (Animation) AnimationUtils.loadAnimation(ctx, R.anim.chatfrom_remove_anim);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
//				view.setAlpha(0);
//				performDismiss(view, position);
				animation.cancel();
			}
		});

		view.startAnimation(animation);
	}

	/**
	 * 在此方法中执行item删除之后，其他的item向上或者向下滚动的动画，并且将position回调到方法onDismiss()中
	 * 
	 * @param dismissView
	 * @param dismissPosition
	 */
//	private void performDismiss(final View dismissView,
//			final int dismissPosition) {
//		final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();// 获取item的布局参数
//		final int originalHeight = dismissView.getHeight();// item的高度
//
//		ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0)
//				.setDuration(mAnimationTime);
//		animator.start();
//
//		animator.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				coll.remove(dismissPosition);
//				notifyDataSetChanged();
//				// 这段代码很重要，因为我们并没有将item从ListView中移除，而是将item的高度设置为0
//				// 所以我们在动画执行完毕之后将item设置回来
//				ViewHelper.setAlpha(dismissView, 1f);
//				ViewHelper.setTranslationX(dismissView, 0);
//				ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
//				lp.height = originalHeight;
//				dismissView.setLayoutParams(lp);
//			}
//		});
//
//		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//			@Override
//			public void onAnimationUpdate(ValueAnimator valueAnimator) {
//				// 这段代码的效果是ListView删除某item之后，其他的item向上滑动的效果
//				lp.height = (Integer) valueAnimator.getAnimatedValue();
//				dismissView.setLayoutParams(lp);
//			}
//		});
//
//	}

}
=======

package com.zgrjb.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zgrjb.R;
import com.zgrjb.model.ChatMsgModel;
import com.zgrjb.ui.UIUserInfoActivity;
import com.zgrujb.selfdefindui.AnimatedGifDrawable;
import com.zgrujb.selfdefindui.AnimatedImageSpan;

public class ChatMsgViewAdapter extends BaseAdapter implements OnClickListener{
	
	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	
    private String tag = ChatMsgViewAdapter.class.getSimpleName();

    private List<ChatMsgModel> coll;
    private Context ctx;
    /** 弹出的更多选择框 */
	private PopupWindow popupWindow;
	/** 复制，删除 */
	private TextView copy, delete;
	/**
	 * 执行动画的时间
	 */
	protected long mAnimationTime = 150;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgModel> coll) {
        ctx = context;
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        initPopWindow();
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsgModel entity = coll.get(position);
	 	
	 	if (entity.isComeMsg())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ChatMsgModel entity = coll.get(position);
    	boolean isComMsg = entity.isComeMsg();
    	Log.v(tag, "in getView -- now position: "+position+"/"+coll.size()+" and siComeMsg: "+isComMsg);
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  viewHolder = new ViewHolder();
	    	  if (isComMsg)
			  {
				  convertView = mInflater.inflate(R.layout.list_item_chat_left, null);
				  viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.chat_iv_lefthead);
				  viewHolder.ivHead.setOnClickListener(this);
			  }else{
				  convertView = mInflater.inflate(R.layout.list_item_chat_right, null);
				  viewHolder.ivHead = (ImageView) convertView.findViewById(R.id.chat_iv_righthead);
			  }
	    	  Log.v(tag, "in getView -- the converView is not exist, url: "+entity.getAudioUrl());
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg = isComMsg;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }

//  	  	viewHolder.ivHead.setBackground(background);
	    viewHolder.tvSendTime.setText(entity.getDate());
	    viewHolder.tvUserName.setText(entity.getName());
	    String url;
    	Log.v(tag, "in getView -- url: "+entity.getAudioUrl()+" and the tvContent's content is: -"+entity.getText()+"-");
	    if((url = entity.getAudioUrl()) != null){
//	    	viewHolder.tvContent.setCompoundDrawablesRelative(null, null, ctx.getResources().getDrawable(R.drawable.ic_audio_sign), null);
	    	viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, ctx.getResources().getDrawable(R.drawable.ic_audio_sign), null);
	    	viewHolder.tvContent.setText("");
	    	viewHolder.tvContent.setTag(url);
//	    	viewHolder.tvContent.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
	    }else{
	    	// 对内容做处理
	    	//if the absolute text view has a drawable in right, it should be deleted.
	    	Drawable[] drawables = viewHolder.tvContent.getCompoundDrawables();
	    		for(Drawable d : drawables){
	    			if(d != null){
	    				viewHolder.tvContent.setCompoundDrawables(null, null, null, null);
	    				break;
	    			}
	    		}
	    	
	    	SpannableStringBuilder sb = handler(viewHolder.tvContent, entity.getText());
	    	viewHolder.tvContent.setText(sb);
	    }
	    viewHolder.tvContent.setOnLongClickListener(new popAction(convertView, position, isComMsg));
	    return convertView;
    }
    
    private SpannableStringBuilder handler(final TextView gifTextView,String content) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring("#[face/png/f_static_".length(), tempText.length()- ".png]#".length());
				String gif = "face/gif/f" + num + ".gif";
				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif
				 * 否则说明gif找不到，则显示png
				 * */
				InputStream is = ctx.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("#[".length(),tempText.length() - "]#".length());
				try {
					sb.setSpan(new ImageSpan(ctx, BitmapFactory.decodeStream(ctx.getAssets().open(png))), m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	}

    static class ViewHolder { 
    	public ImageView ivHead;
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }

    /**
	 * 屏蔽listitem的所有事件
	 * */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/**
	 * 初始化弹出的pop
	 * */
	private void initPopWindow() {
		View popView = mInflater.inflate(R.layout.chat_item_copy_delete_menu,
				null);
		copy = (TextView) popView.findViewById(R.id.chat_copy_menu);
		delete = (TextView) popView.findViewById(R.id.chat_delete_menu);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		// 设置popwindow出现和消失动画
		// popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
	}

	/**
	 * 显示popWindow
	 * */
	public void showPop(View parent, int x, int y, final View view,
			final int position, final boolean isComeMsg) {
		// 设置popwindow显示位置
		popupWindow.showAtLocation(parent, 0, x, y);
		// 获取popwindow焦点
		popupWindow.setFocusable(true);
		// 设置popwindow如果点击外面区域，便关闭。
		popupWindow.setOutsideTouchable(true);
		// 为按钮绑定事件
		// 复制
		copy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				// 获取剪贴板管理服务
				ClipboardManager cm = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
				// 将文本数据复制到剪贴板
				cm.setText(coll.get(position).getText());
			}
		});
		// 删除
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				if (isComeMsg) {
					// from
					leftRemoveAnimation(view, position);
				}else{
					// to
					rightRemoveAnimation(view, position);
				}
//				coll.remove(position);
//				notifyDataSetChanged();
			}
		});
		popupWindow.update();
		if (popupWindow.isShowing()) {

		}
	}

	/**
	 * 每个ITEM中more按钮对应的点击动作
	 * */
	public class popAction implements OnLongClickListener {
		int position;
		View view;
		boolean isComeMsg;

		public popAction(View view, int position, boolean isComeMsg) {
			this.position = position;
			this.view = view;
			this.isComeMsg = isComeMsg;
		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			int[] arrayOfInt = new int[2];
			// 获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
			int x = arrayOfInt[0];
			int y = arrayOfInt[1];
			// System.out.println("x: " + x + " y:" + y + " w: " +
			// v.getMeasuredWidth() + " h: " + v.getMeasuredHeight() );
			showPop(v, x, y, view, position, isComeMsg);
			return true;
		}
	}

	/**
	 * item删除动画
	 * */
	private void rightRemoveAnimation(final View view, final int position) {
		final Animation animation = (Animation) AnimationUtils.loadAnimation(
				ctx, R.anim.chatto_remove_anim);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
//				view.setAlpha(0);
//				performDismiss(view, position);
//				animation.cancel();
				coll.remove(position);
				notifyDataSetChanged();
			}
		});

		view.startAnimation(animation);
	}

	/**
	 * item删除动画
	 * */
	private void leftRemoveAnimation(final View view, final int position) {
		final Animation animation = (Animation) AnimationUtils.loadAnimation(ctx, R.anim.chatfrom_remove_anim);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
//				view.setAlpha(0);
//				performDismiss(view, position);
//				animation.cancel();
				coll.remove(position);
				notifyDataSetChanged();
			}
		});

		view.startAnimation(animation);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("user_id", "leftID");
		intent.setClass(ctx, UIUserInfoActivity.class);
		ctx.startActivity(intent);
	}

	/**
	 * 在此方法中执行item删除之后，其他的item向上或者向下滚动的动画，并且将position回调到方法onDismiss()中
	 * 
	 * @param dismissView
	 * @param dismissPosition
	 */
//	private void performDismiss(final View dismissView,
//			final int dismissPosition) {
//		final ViewGroup.LayoutParams lp = dismissView.getLayoutParams();// 获取item的布局参数
//		final int originalHeight = dismissView.getHeight();// item的高度
//
//		ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 0)
//				.setDuration(mAnimationTime);
//		animator.start();
//
//		animator.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				coll.remove(dismissPosition);
//				notifyDataSetChanged();
//				// 这段代码很重要，因为我们并没有将item从ListView中移除，而是将item的高度设置为0
//				// 所以我们在动画执行完毕之后将item设置回来
//				ViewHelper.setAlpha(dismissView, 1f);
//				ViewHelper.setTranslationX(dismissView, 0);
//				ViewGroup.LayoutParams lp = dismissView.getLayoutParams();
//				lp.height = originalHeight;
//				dismissView.setLayoutParams(lp);
//			}
//		});
//
//		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//			@Override
//			public void onAnimationUpdate(ValueAnimator valueAnimator) {
//				// 这段代码的效果是ListView删除某item之后，其他的item向上滑动的效果
//				lp.height = (Integer) valueAnimator.getAnimatedValue();
//				dismissView.setLayoutParams(lp);
//			}
//		});
//
//	}

}
>>>>>>> 85d64227dc55862eb178950c801d7fd55e65e170
