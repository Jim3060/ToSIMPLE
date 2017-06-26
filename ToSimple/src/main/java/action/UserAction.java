package action;

import java.util.ArrayList;
import java.util.List;

import model.User;
import service.UserService;

public class UserAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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
