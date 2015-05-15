package com.zgrjb.base;

import org.apache.http.message.BasicNameValuePair;

public final class AppVariable {

	// core settings (important)
	
		public static final class dir {
			public static final String base				= "/sdcard/com.zgrjb.pulzzed";
			public static final String headPortrait			= base + "/headPortrait";//头像
			public static final String images			= base + "/images";//图片
		    public static final String raws              = base + "/raws";//语音
		    public static final String cache              = base + "/cache";//缓存,应该可以取代头像，因为主要的图片缓存就是头像
		}
		
		public static final class api {
//			public static final String base				= "http://10.0.36.209:8080/Campus";//桂深电脑本地IP
//			public static final String base				= "http://192.16.137.1:8080/Campus";
			public static final String base				= "http://10.0.37.62:8080/Cinema";//Genymotion虚拟机IP
//			public static final String base				= "http://192.168.1.116:8080/Cinema";//耿秋IP
//			public static final String base 			= "http://172.17.85.1:8080/Cinema";
			
			public static final String login			= "/manager/UserServlet";
			public static final String register			= "/manager/UserServlet";
			public static final String forgotten		= "/manager/UserServlet";
			public static final String movieList		= "/manager/MovieServlet";
			public static final String cinemaList		= "/manager/CinemaServlet";
			public static final String commentlist		= "/manager/CommentServlet";
			public static final String sendcomment		= "/manager/CommentServlet";
			public static final String collectmovie		= "/manager/CollectionServlet";
			public static final String showcollect		= "/manager/CollectionServlet";
			public static final String payOrder			= "/manager/BuyTicketServlet";
			public static final String movieComments	= "/manager/";
			public static final String buyTicket		= "/manager/BuyTicketServlet";
			public static final String getSeats			= "/manager/SeatServlet";
			public static final String getSchedule		= "/manager/ScheduleServlet";
			
			public static final String logout			= "/index/logout";
			public static final String faceView 		= "/image/faceView";
			public static final String faceList 		= "/image/faceList";
			public static final String commentList		= "/comment/commentList";
			public static final String commentCreate	= "/comment/commentCreate";
			public static final String customerView		= "/customer/customerView";
			public static final String customerEdit		= "/customer/customerEdit";
		}
		
		public static final class task {
			public static final int index				= 1001;
			public static final int login				= 1002;
			public static final int logout				= 1003;
			public static final int faceView			= 1004;
			public static final int faceList			= 1005;
			public static final int movieList			= 1006;
			public static final int payOrder			= 1007;
			public static final int movieComments		= 1008;
			public static final int buyTicket			= 1019;
			public static final int getSeats			= 1020;
			public static final int getSchedule			= 1021;
			public static final int commentlist           = 1020;
			public static final int sendcomment           = 1021;
			public static final int collectmovie           = 1022;
			public static final int showcollect           = 1023;
			
			public static final int commentList			= 1009;
			public static final int commentCreate		= 1010;
			public static final int customerView		= 1011;
			public static final int customerEdit		= 1012;
			public static final int notice				= 1015;
			public static final int register            = 1016;
			public static final int forgotten           = 1017;
			public static final int cinemaList			= 1018;
		}
		
		public static final class err {
			public static final String network			= "网络错误";
			public static final String message			= "消息错误";
			public static final String jsonFormat		= "消息格式错误";
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		// intent & action settings
		
		public static final class intent {
			public static final class action {
				public static final String EDITTEXT		= "com.app.demos.EDITTEXT";
				public static final String EDITBLOG		= "com.app.demos.EDITBLOG";
			}
		}
		
		public static final class action {
			public static final class edittext {
				public static final int CONFIG			= 2001;
				public static final int COMMENT			= 2002;
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		// additional settings
		
		public static final class web {
			public static final String base				= "http://192.16.137.1:8080";
			public static final String index			= base + "/index.php";
			public static final String gomap			= base + "/gomap.php";
		}
	
		public static final class Constant{
			/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
//		    public static final String APP_KEY      = "2045436852"; //Demo的
		    public static final String APP_KEY      = "2925466085";

		    /** 
		     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
		     * 
		     * <p>
		     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
		     * 但是没有定义将无法使用 SDK 认证登录。
		     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
		     * </p>
		     */
//		    public static final String REDIRECT_URL = "http://www.sina.com"; //Demo
		    public static final String REDIRECT_URL = "http://movietogether.bmob.cn/";
			

		    /**
		     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
		     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
		     * 选择赋予应用的功能。
		     * 
		     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
		     * 使用权限，高级权限需要进行申请。
		     * 
		     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
		     * 
		     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
		     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
		     */
//		    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
//		            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//		            + "follow_app_official_microblog," + "invitation_write";
		    public static final String SCOPE = "statuses/update, ";
		    
		}
}
