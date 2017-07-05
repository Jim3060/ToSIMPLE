package action;

import java.security.KeyPair;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import model.RSAUtils;
import model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@Controller
public class UserAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private String passwordSECURE;
	private long id;
	private String userName;
	private String password;
	private Integer role;
	private String email;
	public List<User> users= new ArrayList<User> ();

	@RequestMapping(value = "allUsers",method = RequestMethod.GET)
	public String getAllUsers() throws Exception {
		//get users
		users = userService.getAllUsers();
		return "userCRUD";
	}

	@RequestMapping(value = "fetchRSA",method = RequestMethod.POST)
	public String fetchRSA(HttpSession session, HttpServletResponse response) throws Exception{
		KeyPair keyPair = RSAUtils.initKey();
		RSAPublicKey publicKey = RSAUtils.getPublicKey(keyPair);
		RSAPrivateKey privateKey = RSAUtils.getPrivateKey(keyPair);

        session.setAttribute("privateKey", privateKey);

        JSONObject result = new JSONObject();
		result.put("publicKey",RSAUtils.generateBase64PublicKey(publicKey));
        response.getWriter().print(result);
		return null;
		
	}
	
	@RequestMapping(value = "login",method = RequestMethod.POST)
	public String login(HttpSession session) throws Exception{
//		HttpSession session =session();
		RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
		String passwordInput=RSAUtils.decryptBase64(passwordSECURE, privateKey);
		System.out.println(passwordInput);
		return null;
	}
	 


	public long getId() {
		return id;
	}

	public void setId(long id) {
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
