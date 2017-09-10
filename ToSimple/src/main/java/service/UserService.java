package service;

import model.User;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.List;

public interface UserService {

    Long addUser(User user);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByPage(Integer page, Integer pageSize);

    int validateUserName(String userName);

    int validateEmail(String email);


    void deleteUser(Long userId);


    /**
     * @param user
     * @return user id in db.
     * @throws AddressException
     * @throws MessagingException
     */
    Long registerRequest(User user) throws MessagingException;

    /**
     * change the role of userId
     *
     * @param userId
     * @param role
     * @return the old role.
     */
    Integer changRole(Long userId, Integer role);

    int registerValidate(String email, String token);//correct 1, token error -2, time error -1

    User loginByUserName(String userName, String password);

    User loginByEmail(String email, String password);

    Long getValidUserNumber();


}
