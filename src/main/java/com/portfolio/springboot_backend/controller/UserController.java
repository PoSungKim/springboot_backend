package com.portfolio.springboot_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.portfolio.springboot_backend.dao.UserDao;
import com.portfolio.springboot_backend.model.UserVO;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserDao userDao;
    Date date = new Date();

    @GetMapping("users")
    public List<UserVO> usersList() {
        System.out.println(date + " 현재 회원가입된 users 리스트 조회하기");
        System.out.println(userDao.usersList());
        return userDao.usersList();
    }

    // LogIn은 password와 email을 비교해야함
    @PostMapping("login")
    public UserVO logInUser(@RequestBody UserVO user) {
        System.out.println(date + " logInUser 확인 요청처리하기: " + user);
        try {
            UserVO requestedUser = userDao.logInUser(user);
            System.out.println(date + " requestedUser: " + requestedUser.getUsername());
            //requestedUser.setPassword(null);
            return requestedUser;
        } catch (Exception error) {
            UserVO emptyUser = new UserVO();
            return emptyUser;
        }
    }

    @PostMapping("finduser")
    public UserVO findUser(@RequestBody UserVO user) {
        System.out.println(date + " User 확인 요청처리하기: " + user);
        try {
            UserVO requestedUser = userDao.findUser(user);
            System.out.println(date + " requestedUser: " + requestedUser.getUsername());
            requestedUser.setPassword(null);
            return requestedUser;
        } catch (Exception error) {
            UserVO emptyUser = new UserVO();
            return emptyUser;
        }
    }

    @PostMapping("register")
    public boolean registerUser(@RequestBody UserVO user) {
        System.out.println(date + " 회원가입 요청 처리하기: " + user);
        try{
            userDao.registerUser(user);
            return true;
        } catch (Exception error) {
            System.out.println("회원가입 요청 에러: " + error);
            return false;
        } finally {
            System.out.println("registerUser() 처리 끝");
        }
    }

}

