package com.portfolio.springboot_backend.model.cart;

import lombok.Data;

@Data
public class CartVO {
    private int count;
    private int product_id;
    private String writer;
    private String title;
    private String content;
    private String price;
    private String continent;
}
