package com.healthmanager.manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthmanager.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String email;

    private String name;

    private String phoneNumber;

    private Date birthDate;

    private String avatar;

    private String status;

    private Date createTime;
}
