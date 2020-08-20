package com.portfolio.springboot_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.portfolio.springboot_backend.dao.UserDao;
import com.portfolio.springboot_backend.model.UserVO;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserDao userDao;
    Date date = new Date();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            // email 값을 통해 encoded된 password가 담긴 VO 가져오기
            UserVO requestedUser = userDao.logInUser(user);

            // requestedUser VO의 encoded password와 user VO의 password를 비교
            // passwordEncoder는 password를 encode할 때 salt 값과 hash 값을 모두 사용하기 때문에
            // encode한 비밀번호 2개를 비교하는 것이 아니라, raw password와 encoded password를 비교 (match())한지 확인하는 방식으로 진행해야 한다.
            // 이해 내용 : https 프로토콜을 통해 packet을 읽어볼 수 없으니, raw password를 사용하는 것이며,
            // 해킹이 된다면 Database 안의 비밀번호가 유출될 것이라는 의도로 설계가 된 것 같다.
            if (passwordEncoder.matches(user.getPassword(), requestedUser.getPassword())) {
                System.out.println(date + " requestedUser: " + requestedUser.getUsername() + " :: " + user.getPassword() + " password matched!");
                return requestedUser;
            } else {
                System.out.println(date + " requestedUser: " + requestedUser.getUsername() + " :: " + user.getPassword() + " password mismatched!");
                UserVO emptyUser = new UserVO();
                return emptyUser;
            }
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
            System.out.print( user.getPassword() + " | <- 실제 비밀번호, 암호화된 비밀번호 -> | " + passwordEncoder.encode(user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

