package action;

import java.io.IOException;
import java.security.KeyPair;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.struts2.ServletActionContext;

import model.RSAUtils;
import model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@Controller
public class UserAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserService userService;
    private Long userId;
    private HttpServletResponse response;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private String passwordSECURE;
    private int id;
    private String userName;
    private String password;
    private Integer role;
    private String email;
    public List<User> users = new ArrayList<User>();

    @RequestMapping(value = "user",method = RequestMethod.POST)
    public String save(User user){
        //TODO
        userService.addUser(user);
        return null;
    }

    @RequestMapping(value = "user/userId",method = RequestMethod.GET)
    public String show(@PathVariable("userId") Long userId,HttpServletResponse response) throws IOException {
        User user = userService.getUserById(userId);
        JSONObject result = new JSONObject();
        result.put("user", user);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(result);
        return null;
    }

    @RequestMapping(value = "user/userId", method = RequestMethod.PUT)
    public String edit(@PathVariable("userId") Long userId, Integer role, HttpServletResponse response) throws IOException {
        userService.changRole(userId, role);
        return null;
    }

    @RequestMapping(value = "user/userId", method = RequestMethod.DELETE)
    public String delete(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return null;
    }

    @RequestMapping(value = "allUsers", method = RequestMethod.GET)
    public String getAllUsers(HttpServletResponse response) throws Exception {
        //get users
        users = userService.getAllUsers();
        JSONObject result = new JSONObject();
    	result.put("users",users);
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
    	response.getWriter().print(result);
        return null;
    }
    
    @RequestMapping(value = "allUser", method = RequestMethod.GET)
    public String getUsersByPage(HttpServletResponse response,@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize ) throws Exception {
        //get users
        users = userService.getUsersByPage(page,pageSize);
        JSONObject result = new JSONObject();
    	result.put("users",users);
    	result.put("userNum",userService.getValidUserNumber());
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
    	response.getWriter().print(result);
    	return null;
      
    }

    @RequestMapping(value = "fetchRSA", method = RequestMethod.GET)
    public String fetchRSA(HttpSession session, HttpServletResponse response) throws Exception {
        KeyPair keyPair = RSAUtils.initKey();
        RSAPublicKey publicKey = RSAUtils.getPublicKey(keyPair);
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(keyPair);


        session.setAttribute("privateKey", privateKey);

        JSONObject result = new JSONObject();
        result.put("publicKey", RSAUtils.generateBase64PublicKey(publicKey));
        response.getWriter().print(result);



        return null;

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpSession session, String passwordSECURE, String userName, HttpServletResponse response) throws Exception {
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
        String passwordInput = RSAUtils.decryptBase64(passwordSECURE, privateKey);
        System.out.println(passwordInput);
        Integer loginSuccess = 1;
        User user = userService.loginByEmail(userName, passwordInput);
        if (user == null) {
            user = userService.loginByUserName(userName, passwordInput);
            if (user == null) {
                loginSuccess = 0;
            }
        }
        
        JSONObject result = new JSONObject();
        result.put("loginSuccess", loginSuccess);
        if (loginSuccess == 1) {
        	user.setPassword(null);
            session.setAttribute("user", user);
            result.put("user", user);
        }
        
        response.getWriter().print(result);
        session.removeAttribute("privateKey");
        return null;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return null;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(HttpSession session, String passwordSECURE, String userName, HttpServletResponse response, String email) throws MessagingException, IOException {
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
        System.out.println(passwordSECURE);
        String password = RSAUtils.decryptBase64(passwordSECURE, privateKey);
        role = 0;
        User user = new User(userName, password, role, email);
        Long id = userService.registerRequest(user);
        user.setId(id);
        session.removeAttribute("privateKey");
        response.getWriter().print(1);
        return null;
    }
    
    @RequestMapping(value = "registerValidate", method = RequestMethod.GET)
    public String registerValidate(HttpSession session, @RequestParam("token") String token, HttpServletResponse response, @RequestParam("email") String email) throws MessagingException, IOException {
    	int flag=userService.registerValidate(email, token);
    	response.getWriter().print(flag);
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordSECURE() {
        return passwordSECURE;
    }

    public void setPasswordSECURE(String passwordSECURE) {
        this.passwordSECURE = passwordSECURE;
    }



}
