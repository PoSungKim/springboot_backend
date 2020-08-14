package com.portfolio.springboot_backend.model;

import lombok.Data;

@Data
public class UserVO {
    int id;
    String username;
    String password;
    String email;
}