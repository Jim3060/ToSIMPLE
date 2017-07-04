package action;

import java.io.IOException;
import java.security.KeyPair;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import ToolUtils.MD5Utils;
import model.RSAUtils;
import model.User;
import net.sf.json.JSONObject;
import service.UserService;


public class UserAction extends BaseAction{
	private static final long serialVersionUID = 1L;
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
	private String token;//can be used in many way
	public List<User> users= new ArrayList<User> ();
	
	public String getAllUsers() throws Exception {
		//get users
		users = userService.getAllUsers();
		return SUCCESS;
	}
	
	public String registerRequest() throws AddressException, MessagingException{
		
		userName="TEST";
		password="1234";
		role=1;
		email="1072207255@qq.com";
		
		User user= new User(userName,password,role,email);
		userService.registerRequest(user);
		return null;
	}
	
	public String registerValidate() throws IOException{
		
		
		token=request().getParameter("token");
		email=request().getParameter("email");
		ServletActionContext.getResponse().getWriter().print(userService.registerValidate(email, token));
		return null;
	}
	
	public String fetchRSA() throws Exception{
		KeyPair keyPair = RSAUtils.initKey();
		RSAPublicKey publicKey = RSAUtils.getPublicKey(keyPair);
		RSAPrivateKey privateKey = RSAUtils.getPrivateKey(keyPair);
		
        HttpSession session =session();
        session.setAttribute("privateKey", privateKey);

        JSONObject result = new JSONObject();
		result.put("publicKey",RSAUtils.generateBase64PublicKey(publicKey));
		ServletActionContext.getResponse().getWriter().print(result);
		return null;
		
	}
	
	
	public String login() throws Exception{
		HttpSession session =session();
		RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
		System.out.println(privateKey);
		System.out.println(passwordSECURE);
		System.out.println(userName);
		String passwordInput=RSAUtils.decryptBase64(passwordSECURE, privateKey);
		System.out.println(passwordInput);
		ServletActionContext.getResponse().getWriter().print(passwordInput);
		return null;
	}
	
	
	//helper

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
