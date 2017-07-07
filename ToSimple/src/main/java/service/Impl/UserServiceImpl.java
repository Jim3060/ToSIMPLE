package service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      if (validateEmail(user.getEmail())==0){return  -1L;}
      if (validateUserName(user.getUserName())==0){return 0L;}
      user.setValid(0);
      return userDao.save(MailUtils.activateMail(user));
    }


    @Override
    public Integer changRole(Long userId, Integer role) {
        //TODO
        return userDao.changRole(userId, role);
    }




	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.getUserByEmail(email);
		
	}

	@Override
	public int registerValidate(String email, String token) {
		User userFetch=getUserByEmail(email);
		if (userFetch.getToken().equals(token)){
			Date now=new Date();
			if (now.getTime()<=userFetch.getCreateTime().getTime()){
				userFetch.setValid(1);
				String to  = userFetch.getEmail();
		        Long curTime = System.currentTimeMillis();
		        String token2 = to+curTime;
		        userFetch.setToken(MD5Utils.getEncoded(token2));
				updateUser(userFetch);
				return 1;
			}
			else {return -1;}
		}
		return -2;
	}

	@Override
	public User loginByUserName(String userName, String password) {
		User user=userDao.getUserByUserName(userName);
		if (user==null){return null;}
		if (user.getPassword().equals(password)&&user.getValid()==1){
			return user;
		}
		return null;
	}

	@Override
	public User loginByEmail(String email, String password){
		User user=userDao.getUserByEmail(email);
		if (user==null){return null;}
		if (user.getPassword().equals(password)&&user.getValid()==1){
			return user;
		}
		return null;
	}


	@Override
	public int validateUserName(String userName) {
		// TODO Auto-generated method stub
		User user=userDao.getUserByUserName(userName);
		if (user==null){return 1;}
		if (user.getValid()==0){
			Date now=new Date();
			if (now.getTime()>user.getCreateTime().getTime()){
				userDao.delete(user);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int validateEmail(String email) {
		//format check
		String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        if  (!matcher.matches()){
        	return 0;
        }
		//db check
		User user=userDao.getUserByEmail(email);
		if (user==null){return 1;}
		if (user.getValid()==0){
			Date now=new Date();
			if (now.getTime()>user.getCreateTime().getTime()){
				userDao.delete(user);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public List<User> getUsersByPage(Integer page, Integer pageSize) {
		List<User> raw=userDao.getValidUsersByPage(page, pageSize);
		List<User> result=new ArrayList<User>();
		for (int i=0;i<raw.size();i++){
			raw.get(i).setPassword("");
			raw.get(i).setToken("");
			result.add(raw.get(i));
		}
		return result;
	}

	@Override
	public Long getValidUserNumber() {
		return userDao.getValidUserNumber();
		
	}

}
