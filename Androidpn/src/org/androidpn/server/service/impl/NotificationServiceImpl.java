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
package org.androidpn.server.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.dao.UserDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.model.User;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.UserExistsException;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

/** 
 * This class is the implementation of UserService.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationServiceImpl implements NotificationService {

	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	private NotificationDao notificationDao;
	public void saveNotification(Notification notification) {
		// TODO Auto-generated method stub
		notificationDao.saveNotification(notification);
	}

	@Test
	public List<Notification> findNotificationsByUsername(String username) {
		// TODO Auto-generated method stub
		return notificationDao.findNotificationsByUsername(username);
	}

	public void deleteNotification(Notification notification) {
		// TODO Auto-generated method stub
		notificationDao.deleteNotification(notification);
		
	}

	public void deleteNotificationByUUID(String uuid) {
		// TODO Auto-generated method stub
		notificationDao.deleteNotificationByUUID(uuid);
	}

   
}
