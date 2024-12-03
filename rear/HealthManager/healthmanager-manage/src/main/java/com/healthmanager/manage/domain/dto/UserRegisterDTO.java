package com.healthmanager.manage.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserRegisterDTO {
    private String username;

    private String email;

    private String password;
}
