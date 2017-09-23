package com.cms.user.domain.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 8646368486848495706L;
    //主键ID
    private Long id;
    //用户真实名称
    private String realName;
    //用户登录名称
    private String loginName;
    //登录密码
    private String password;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
