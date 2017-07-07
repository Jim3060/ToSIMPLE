package dao;

import java.util.List;

import model.User;

public interface UserDao {

    public Long save(User user);

    /**
     * delete user
     *
     * @param user
     */
    public void delete(User user);

    /**
     * delete user.
     *
     * @param userId
     */
    public void delete(Long userId);

    public void update(User user);

    public User getUserById(long id);


    public List<User> getAllUsers();

    public User getUserByEmail(String email);

    public User getUserByUserName(String userName);
	
	


    /**
     * change the role of userId
     *
     * @param userId
     * @param role
     * @return the old role.
     */
    public Integer changRole(Long userId, Integer role);

}