package model;

public class User {
	private Integer id;
	private String userName;
	private String password;
	private Integer role;
	private String email;

	public User(Integer id, String userName, String password, Integer role, String email) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.email = email;
	}


	public User() {
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
