package action;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

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
	private int id;
	private String userName;
	private String password;
	private Integer role;
	private String email;
	public List<User> users= new ArrayList<User> ();
	
	public String getAllUsers() throws Exception {
		//get users
		users = userService.getAllUsers();
		return SUCCESS;
	}
	
	public String fetchRSA() throws Exception{
		HashMap<String, Object> map = RSAUtils.getKeys();
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        HttpSession session =session();
        session.setAttribute("privateKey", privateKey);
        
        String modulus = publicKey.getModulus().toString();
        String publicExponent = publicKey.getPublicExponent().toString();
        String privateExponent = privateKey.getPrivateExponent().toString();
        //明文
        
        JSONObject result = new JSONObject();
		result.put("modulus", modulus);
		result.put("publicExponent", publicExponent);
		ServletActionContext.getResponse().getWriter().print(result);
		return null;
		
	}
	
	public String login() throws Exception{
		HttpSession session =session();
		RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("privateKey");
		String passwordInput=RSAUtils.decryptByPrivateKey(passwordSECURE, privateKey);
		return null;
	}
	
	
	//helper

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
	
}
