package com.portfolio.springboot_backend.dao;

import com.portfolio.springboot_backend.model.cart.AddInfoCartVO;
import com.portfolio.springboot_backend.model.cart.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartDao {

    void saveUserCart(AddInfoCartVO addInfoCartVO);
    List<CartVO> findAllCartByUserId(int user_id);
}
