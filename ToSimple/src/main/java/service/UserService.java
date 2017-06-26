package service;

import java.util.List;

import model.User;

public interface UserService {
	public Integer addUser(User user);

	public void deleteUser(User user);

	public void updateUser(User user);

	public User getUserById(int id);

	public List<User> getAllUsers();
}
