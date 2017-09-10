package model;

import java.util.Date;

public class User {
    private Long id;
    private String userName;
    private String password;
    private Integer role;
    private String email;
    private int valid;
    private String token;
    private Date createTime;

    /* More infomation of the user. */
    private String address;
    private String phone;
    private String qq;
    private String weixin;

    public void updateUser(String address, String phone, String qq, String weixin) {
        this.address = address;
        this.phone = phone;
        this.qq = qq;
        this.weixin = weixin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

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
