package com.portfolio.springboot_backend.dao;

import com.portfolio.springboot_backend.model.cart.CartVO;
import com.portfolio.springboot_backend.model.cart.AddInfoCartVO;
import com.portfolio.springboot_backend.model.cart.PurchaseInfoCartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartDao {

    void saveUserPurchase(PurchaseInfoCartVO purchaseInfoCartVO);
    void saveUserCart(AddInfoCartVO addInfoCartVO);
    void deleteUserCart(AddInfoCartVO addInfoCartVO);
    void deleteAllUserCart(int user_id);
    List<CartVO> findAllCartByUserId(int user_id);
    List<CartVO> findAllPurchaseByUserId(int user_id);
}
