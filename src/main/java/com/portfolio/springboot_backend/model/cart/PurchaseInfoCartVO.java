package com.portfolio.springboot_backend.model.cart;

import lombok.Data;
import java.util.List;

@Data
public class PurchaseInfoCartVO {
    private int user_id;
    private String email;
    private int product_id;
    private List<CartVO> product_id_list;
}