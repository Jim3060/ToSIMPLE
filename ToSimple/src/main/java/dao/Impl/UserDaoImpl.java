package dao.Impl;

import dao.UserDao;
import model.User;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
    public Long save(User user) {
        return (Long) getHibernateTemplate().save(user);
    }

    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    @Override
    public void delete(Long userId) {
        //TODO
        getHibernateTemplate().find("delete from User as u where u.id=?", userId);
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

    @Override
    public Integer changRole(Long userId, Integer role) {
        User user = getHibernateTemplate().get(User.class, userId);
        Integer ans = user.getRole();
        user.setRole(role);
        getHibernateTemplate().update(user);
        return ans;
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

    @Override
    public User getUserByUserName(String userName) {
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) getHibernateTemplate().find(
                "from User as u where u.userName=?", userName);
        User user = users.size() > 0 ? users.get(0) : null;
        return user;

    }

    @Override
    public List<User> getValidUsersByPage(Integer page, Integer pageSize) {
        // TODO Auto-generated method stub
        Session session = this.getSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User as u where u.valid=1").setMaxResults(pageSize).setFirstResult(page * pageSize).list();
        session.getTransaction().commit();
        return list;
    }


    @Override
    public Long getValidUserNumber() {
        // TODO Auto-generated method stub
        @SuppressWarnings("unchecked")
        Long num = (Long) getHibernateTemplate().find("select count(*) from User as u where u.valid=1").listIterator().next();

        return num;

    }


//	
}
