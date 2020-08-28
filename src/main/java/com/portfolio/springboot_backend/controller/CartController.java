package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.dao.CartDao;
import com.portfolio.springboot_backend.dao.UserDao;

import com.portfolio.springboot_backend.model.cart.CartVO;
import com.portfolio.springboot_backend.model.cart.AddInfoCartVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
        boolean success;
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

    @DeleteMapping("/product_id={product_id}&email={email}")
    public boolean deleteCart(@PathVariable int product_id, @PathVariable String email) throws IOException {
        boolean success;
        System.out.println(localDateTime.now() + " deleteSingleCart() 함수 시작 "  + product_id + " " + email);
        try {
            AddInfoCartVO addInfoCartVO = new AddInfoCartVO();
            addInfoCartVO.setUser_id(userDao.findUserIdByEmail(email));
            addInfoCartVO.setProduct_id(product_id);

            cartDao.deleteUserCart(addInfoCartVO);

            success = true;
        } catch (Exception error) {
            success = false;
        }
        return success;
    }
}
