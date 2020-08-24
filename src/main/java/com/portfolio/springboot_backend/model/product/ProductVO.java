package com.portfolio.springboot_backend.model.product;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {
    private int id;
    private String writer;
    private String title;
    private String content;
    private String price;
    private String continent;
    private List<ImageFileVO> images;
}