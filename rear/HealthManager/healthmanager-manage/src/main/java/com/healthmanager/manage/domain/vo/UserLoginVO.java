package com.healthmanager.manage.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserLoginVO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String token;

    private Date lastLoginTime;
}
