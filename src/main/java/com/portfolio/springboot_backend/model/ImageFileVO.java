package com.portfolio.springboot_backend.model;
import lombok.Data;


@Data
public class ImageFileVO {
    private Long key;
    private String fileName;
    private String fileType;
    private byte[] imageByteData;
}