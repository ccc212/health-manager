package com.healthmanager.manage.domain.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class UserLoginVO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String token;

    private Date lastLoginTime;

    public UserLoginVO(Long id, String username, String name, String token, Date lastLoginTime) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
        this.lastLoginTime = lastLoginTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
