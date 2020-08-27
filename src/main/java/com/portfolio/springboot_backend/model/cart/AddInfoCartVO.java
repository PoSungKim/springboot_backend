package com.portfolio.springboot_backend.model.cart;

import lombok.Data;

@Data
public class AddInfoCartVO {
    private String email;
    private int user_id;
    private int product_id;
}
