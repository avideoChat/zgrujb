/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.push;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.androidpn.server.model.Notification;
import org.androidpn.server.model.User;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/** 
 * This class is to manage sending the notifcations to the users.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {

    private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

    private final Log log = LogFactory.getLog(getClass());

    private SessionManager sessionManager;

    private NotificationService notificationService;
    
    private UserService userService;
    /**
     * Constructor.
     */
    public NotificationManager() {
        sessionManager = SessionManager.getInstance();
        notificationService = ServiceLocator.getNotificationService();
        userService = ServiceLocator.getUserService();
    }

    /**
     * Broadcasts a newly created notification message to all connected users.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendBroadcast(String apiKey, String title, String message,
            String uri,String imageUri) {
        log.debug("sendBroadcast()...");
        
        List<User> allUsers = userService.getUsers();
        for (User user:allUsers) {
        	Random random = new Random();
            String uuid = Integer.toHexString(random.nextInt());
            saveNotification(apiKey, user.getUsername(), title, message, uri,uuid,imageUri);
            IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri,uuid,imageUri);
			ClientSession session = sessionManager.getSession(user.getUsername());
			if(session!=null&&session.getPresence().isAvailable()){
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
				
			} 
			 
		}
        
    }

    /**
     * Sends a newly created notification message to the specific user.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri,String imageUri,boolean shouldSave) {
        log.debug("sendNotifcationToUser()...");
        Random random = new Random();
        String uuid = Integer.toHexString(random.nextInt());
        try {
			User user = userService.getUserByUsername(username);
			if(user!=null&&shouldSave){
				saveNotification(apiKey, username, title, message, uri,uuid,imageUri);
			}
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri,uuid,imageUri);
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }
			 
        }
        
    }

    public void sendNotificationByAlias(String apiKey, String alias,
            String title, String message, String uri,String imageUri,boolean shouldSave){
    	String username = sessionManager.getUsernameByAlias(alias);
    	if(username!=null){
    		sendNotifcationToUser(apiKey, username, title, message, uri, imageUri,shouldSave);
    	}
    }
    public void sendNotificationByTag(String apiKey, String tag,
			String title, String message, String uri,String imageUri,boolean shouldSave){
		Set<String> usernameSet= sessionManager.getUsernamesByTag(tag);
		if(usernameSet!=null && !usernameSet.isEmpty()){
			for(String username:usernameSet){
				
				sendNotifcationToUser(apiKey, username, title, message, uri, imageUri,shouldSave);
			}
		}
		}
    private void saveNotification(String apiKey,String username,String title,String message,String uri,String uuid,String imageUri){
    	Notification notification = new Notification();
    	notification.setApiKey(apiKey);
    	notification.setMessage(message);
    	notification.setTitle(title);
    	notification.setUri(uri);
    	notification.setUsername(username);
    	notification.setUuid(uuid);
    	notification.setImageUri(imageUri);
    	notificationService.saveNotification(notification);
    	
    }
    /**
     * Creates a new notification IQ and returns it.
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri,String uuid,String imageUri) {
       
        // String id = String.valueOf(System.currentTimeMillis());

        Element notification = DocumentHelper.createElement(QName.get(
                "notification", NOTIFICATION_NAMESPACE));
        notification.addElement("id").setText(uuid);
        notification.addElement("apiKey").setText(apiKey);
        notification.addElement("title").setText(title);
        notification.addElement("message").setText(message);
        notification.addElement("uri").setText(uri);
        notification.addElement("imageUri").setText(imageUri);

        IQ iq = new IQ();
        iq.setType(IQ.Type.set);
        iq.setChildElement(notification);

        return iq;
    }
}
