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
package org.androidpn.server.dao.hibernate;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.dao.UserDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.model.User;
import org.androidpn.server.service.UserNotFoundException;
import org.junit.Test;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/** 
 * This class is the implementation of UserDAO using Spring's HibernateTemplate.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationDaoHibernate extends HibernateDaoSupport implements NotificationDao {

	public void saveNotification(Notification notification) {
		// TODO Auto-generated method stub
		 getHibernateTemplate().saveOrUpdate(notification);
	     getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Notification> findNotificationsByUsername(String username) {
		// TODO Auto-generated method stub
		List<Notification> list =  getHibernateTemplate().find("from Notification where username=?",
                username);//Notification 的n必须大写啊，，这里之前错了
		if(list!=null&&list.size()>0){
		 	return list;
		}
		 return null;
	}

	 
	public void deleteNotification(Notification notification) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(notification);
	}

	public void deleteNotificationByUUID(String uuid) {
		// TODO Auto-generated method stub
		List<Notification> list =  getHibernateTemplate().find("from Notification where uuid=?",
				uuid);//Notification 的n必须大写啊，，这里之前错了
		if(list!=null&&list.size()>0){
			Notification notification = list.get(0);
			deleteNotification(notification);
		}
		
	}


}
