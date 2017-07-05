package dao;

import java.util.List;

import model.User;

public interface UserDao {

	public Long save(User user);

	public void delete(User user);

	public void update(User user);

	public User getUserById(long id);

	public List<User> getAllUsers();
	
	public User getUserByEmail(String email);
	
	public User getUserByUserName(String userName);
	
	

}