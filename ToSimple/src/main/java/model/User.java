package model;

import java.util.Date;

import ToolUtils.MD5Utils;

public class User {
    private Long id;
    private String userName;
    private String password;
    private Integer role;
    private String email;
    private int valid;
    private String token;
    private Date createTime;


    public User(Long id, String userName, String password, Integer role, String email, int valid, String token,
                Date createTime) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.valid = valid;
        this.token = token;
        this.createTime = createTime;
    }


    public User(Long id, String userName, String password, Integer role, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(String userName, String password, Integer role, String email) {

        this.userName = userName;
        this.password = password;
        this.role = role;
        this.email = email;
    }


    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getValid() {
        return valid;
    }


    public void setValid(int valid) {
        this.valid = valid;
    }


    public String getToken() {
        return token;
    }
    
    public void updateToken(){
    	 String to = this.email;
         Long curTime = System.currentTimeMillis();
         String token = curTime+to;
         this.setToken(MD5Utils.getEncoded(token));
    }
    
    public String fetchCheckToken(){
    	String result=this.token.substring(0, 6);
    	return result;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
