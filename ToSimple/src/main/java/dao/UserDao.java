package dao;

import model.User;

import java.util.List;

public interface UserDao {

    Long save(User user);

    /**
     * delete user
     *
     * @param user
     */
    void delete(User user);

    /**
     * delete user.
     *
     * @param userId
     */
    void delete(Long userId);

    void update(User user);

    User getUserById(long id);


    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserByUserName(String userName);


    /**
     * change the role of userId
     *
     * @param userId
     * @param role
     * @return the old role.
     */
    Integer changRole(Long userId, Integer role);

    List<User> getValidUsersByPage(Integer page, Integer pageSize);

    Long getValidUserNumber();


}