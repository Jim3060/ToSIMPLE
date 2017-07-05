package service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.User;

public interface UserService {
    public Long addUser(User user);

    public void deleteUser(User user);

    public void deleteUser(Long userId);

    public void updateUser(User user);


    public User getUserById(long id);

    public User getUserByEmail(String email);

    public List<User> getAllUsers();

    /**
     * @param user
     * @return user id in db.
     * @throws AddressException
     * @throws MessagingException
     */
    public Long registerRequest(User user) throws AddressException, MessagingException;

    /**
     * change the role of userId
     *
     * @param userId
     * @param role
     * @return the old role.
     */
    public Integer changRole(Long userId, Integer role);

    public int registerValidate(String email, String token);//correct 1, token error -2, time error -1

    public User loginByUserName(String userName, String password);

    public User loginByEmail(String email, String password);

}
