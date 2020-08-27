package com.portfolio.springboot_backend.model.user;

import lombok.Data;

@Data
public class UserVO {

    private Long id;
    private String username;
    private String password;
    private String email;
}