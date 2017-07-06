package service.Impl;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.struts2.ServletActionContext;

import ToolUtils.MD5Utils;
import ToolUtils.MailUtils;
import dao.UserDao;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public Long addUser(User user) {
        return userDao.save(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.delete(userId);
    }


    public void updateUser(User user) {
        userDao.update(user);
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Long registerRequest(User user) throws AddressException, MessagingException {
        user.setValid(0);
        return userDao.save(MailUtils.activateMail(user));
    }

    @Override
    public Integer changRole(Long userId, Integer role) {
        //TODO
        return userDao.changRole(userId, role);
    }

    public int validateEmail(String email) {
        return 0;
    }

    @Override
    public User getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return userDao.getUserByEmail(email);

    }

    @Override
    public int registerValidate(String email, String token) {
        User userFetch = getUserByEmail(email);
        if (userFetch.getToken().equals(token)) {
            Date now = new Date();
            if (now.getTime() <= userFetch.getCreateTime().getTime()) {
                userFetch.setValid(1);
                String to = userFetch.getEmail();
                Long curTime = System.currentTimeMillis();
                String token2 = to + curTime;
                userFetch.setToken(MD5Utils.getEncoded(token2));
                updateUser(userFetch);
                return 1;
            } else {
                return -1;
            }
        }
        return -2;
    }

    @Override
    public User loginByUserName(String userName, String password) {
        User user = userDao.getUserByUserName(userName);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User loginByEmail(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}
