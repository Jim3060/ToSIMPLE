package action;

import java.security.KeyPair;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
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
		String passwordInput=RSAUtils.decryptBase64(passwordSECURE, privateKey);
		System.out.println(passwordInput);
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
	
	public String getPasswordSECURE() {
		return passwordSECURE;
	}

	public void setPasswordSECURE(String passwordSECURE) {
		this.passwordSECURE = passwordSECURE;
	}

	
}
