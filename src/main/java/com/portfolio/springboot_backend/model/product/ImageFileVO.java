package com.portfolio.springboot_backend.model.product;
import lombok.Data;


@Data
public class ImageFileVO {
    private int productImage_id;
    private String fileName;
    private String fileType;
    private byte[] imageByteData;
}