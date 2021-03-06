package action;

import ToolUtils.MailUtils;
import ToolUtils.RSAUtils;
import model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    public List<User> users = new ArrayList<User>();
    @Autowired
    private UserService userService;
    private Long userId;
    private HttpServletResponse response;
    private String passwordSECURE;
    private int id;
    private String userName;
    private String password;
    private Integer role;
    private String email;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String save(User user) {
        userService.addUser(user);
        return null;
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public String show(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        User user = userService.getUserById(userId);
        JSONObject result = new JSONObject();
        result.put("user", user);
        response.getWriter().print(result);
        return null;
    }

    /**
     * change the role of the user. only admin allowed.
     *
     * @param userId
     * @param role
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "user/{userId}", method = RequestMethod.PUT)
    public String edit(@PathVariable("userId") Long userId, Integer role, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        userService.changRole(userId, role);
        return null;
    }

    @RequestMapping(value = "picture", method = RequestMethod.POST)
    public void savePicture(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("here");
        System.out.println(file);
        InputStream fileContent = file.getInputStream();
        String url = "url";

        JSONObject result = new JSONObject();
        result.put("imgUrl", url);
        response.getWriter().print(result);
        return;
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return null;
    }

    @RequestMapping(value = "allUsers", method = RequestMethod.GET)
    public String getAllUsers(HttpServletResponse response) throws Exception {
        //get users
        response.setContentType("application/json;charset=UTF-8");
        users = userService.getAllUsers();
        JSONObject result = new JSONObject();
        result.put("users", users);
        response.getWriter().print(result);
        return null;
    }

    @RequestMapping(value = "allUser", method = RequestMethod.GET)
    public String getUsersByPage(HttpServletResponse response, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) throws Exception {
        //get users
        response.setContentType("application/json;charset=UTF-8");
        users = userService.getUsersByPage(page, pageSize);
        JSONObject result = new JSONObject();
        result.put("users", users);
        response.getWriter().print(result);
        return null;
    }


    @RequestMapping(value = "fetchRSA", method = RequestMethod.GET)
    public String fetchRSA(HttpSession session, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
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
        response.setContentType("application/json;charset=UTF-8");
        System.out.print(userName);
        System.out.print("HEREEE");
        System.out.print(passwordSECURE);
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
        String passwordInput = RSAUtils.decryptBase64(passwordSECURE, privateKey);
        System.out.print("HEREEE2");
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

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(HttpSession session, String passwordSECURE, String passwordNewSECURE, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(passwordSECURE);
        System.out.println(passwordNewSECURE);
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
        String passwordInput = RSAUtils.decryptBase64(passwordSECURE, privateKey);
        User user=(User) session.getAttribute("user");
        user=userService.getUserById(user.getId());
        System.out.println(passwordInput);
        if (user==null){response.getWriter().print(0);return null;}
        
        if (!user.getPassword().equals(passwordInput)){
        	response.getWriter().print(-1);return null;
        }
        
        user.setPassword(RSAUtils.decryptBase64(passwordNewSECURE, privateKey));
        userService.updateUser(user);



        response.getWriter().print(1);
        session.removeAttribute("privateKey");
        return null;
    }

    
    
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session,HttpServletResponse response) throws IOException {
        session.removeAttribute("user");
        response.getWriter().print(1);
        return null;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(HttpSession session, String passwordSECURE, String userName, HttpServletResponse response, String email) throws MessagingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
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
        response.setContentType("application/json;charset=UTF-8");
        int flag = userService.registerValidate(email, token);
        response.getWriter().print(flag);
        return null;
    }
    
    @RequestMapping(value = "sendCheckToken", method = RequestMethod.GET)
    public String sendCheckToken( HttpServletResponse response, String email) throws MessagingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(email);
        User user = userService.getUserByEmail(email);
        if (user==null){response.getWriter().print(0);}
        MailUtils.sendCheckToken(user);
        response.getWriter().print(1);
        return null;
    }
    
    @RequestMapping(value = "forgotPassword", method = RequestMethod.GET)
    public String forgotPassword(HttpSession session, String passwordNewSECURE, HttpServletResponse response, String checkToken, String email) throws MessagingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        User user=userService.getUserByEmail(email);
        int flag = userService.checkTokenValid(user, checkToken);
        if (flag==1){
        	RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
        	user.setPassword(RSAUtils.decryptBase64(passwordNewSECURE, privateKey));
        	user.updateToken();
        	userService.updateUser(user);
        }
        response.getWriter().print(flag);
        return null;
    }
    
    

    @RequestMapping(value = "user/role", method = RequestMethod.POST)
    public void changeRole(Long userId, Integer role, HttpServletResponse response) throws IOException {
        Integer integer = userService.changRole(userId, role);
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", 1);
        jsonObject.put("oldRole", integer);
        System.out.print(jsonObject.toString());
        response.getWriter().print(jsonObject);
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
