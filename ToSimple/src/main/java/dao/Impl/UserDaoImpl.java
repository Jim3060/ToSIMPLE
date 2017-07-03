package dao.Impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import model.User;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import dao.UserDao;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	public Long save(User user) {
		return (Long) getHibernateTemplate().save(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	public void update(User user) {
		getHibernateTemplate().merge(user);
	}

	public User getUserById(long id) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find(
				"from User as u where u.id=?", id);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}
	
	public User getUserByEmail(String email) {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate().find(
				"from User as u where u.email=?", email);
		User user = users.size() > 0 ? users.get(0) : null;
		return user;
	}
	

	public List<User> getAllUsers() {
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) getHibernateTemplate()
				.find("from User");
		return users;
	}
	
//	
}
