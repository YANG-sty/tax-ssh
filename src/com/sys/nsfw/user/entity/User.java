package com.sys.nsfw.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String id;
    private String department;//部门
    private String account;//账号
    private String name;//名字
    private String password;//密码
    
    private String headImg;//头像
    private boolean gender;//性别
    private String state;//状态，该用户是否可用
    private String mobile;//手机
    private String email;//邮箱
    private Date birthday;//生日
    private String remarks;//备注
    private List<UserRole> userRoles;//用户的角色信息

    //用户状态
    public static String USER_STATE_VALID = "1";//有效
    public static String USER_STATE_INVALID = "0";//无效

    public User() {
    }

    public User(String id, String department, String account, String name, String password, String headImg, boolean gender, String state, String mobile, String email, Date birthday, String remarks) {
        this.id = id;
        this.department = department;
        this.account = account;
        this.name = name;
        this.password = password;
        this.headImg = headImg;
        this.gender = gender;
        this.state = state;
        this.mobile = mobile;
        this.email = email;
        this.birthday = birthday;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", headImg='" + headImg + '\'' +
                ", gender=" + gender +
                ", state='" + state + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
