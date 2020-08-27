package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.dao.CartDao;
import com.portfolio.springboot_backend.dao.UserDao;

import com.portfolio.springboot_backend.model.cart.CartVO;
import com.portfolio.springboot_backend.model.cart.AddInfoCartVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    CartDao cartDao;
    @Autowired
    UserDao userDao;
    LocalDateTime localDateTime;
    boolean success;

    @PostMapping(value="/all")
    public List<CartVO> showMyCart(@RequestBody AddInfoCartVO addInfoCartVO) throws IOException {
        System.out.println(localDateTime.now() + " showMyCart() 함수 시작 " + addInfoCartVO);
        List<CartVO> cartVOList = new ArrayList<CartVO>();
        try {
            cartVOList = cartDao.findAllCartByUserId(userDao.findUserIdByEmail(addInfoCartVO.getEmail()));
        } catch (Exception error) {
            System.out.println(error);
        }

        return cartVOList;
    }
    @PostMapping
    public boolean saveCart(@RequestBody AddInfoCartVO addInfoCartVO) throws IOException {
        System.out.println(localDateTime.now() + " saveCart() 함수 시작 " + addInfoCartVO);

        try {
            addInfoCartVO.setUser_id(userDao.findUserIdByEmail(addInfoCartVO.getEmail()));
            cartDao.saveUserCart(addInfoCartVO);
            success = true;
        } catch (Exception error) {
            System.out.println(error);
            success = false;
        }
        return success;
    }
}
