package com.portfolio.springboot_backend.model;

import lombok.Data;


@Data
public class UploadVO {
    private Long id;
    private String fileName;
    private String fileType;
    private byte[] data;
}
