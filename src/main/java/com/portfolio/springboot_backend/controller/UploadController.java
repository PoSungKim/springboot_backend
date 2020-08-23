package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.model.UploadVO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping(value = "/preview/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadVO uploadPreview(@RequestParam(value = "file") MultipartFile file) throws IOException {
        UploadVO uploadVOTest  = new UploadVO();
        uploadVOTest.setFileName(file.getOriginalFilename());
        uploadVOTest.setFileType(file.getContentType());
        uploadVOTest.setData(file.getBytes());
        return uploadVOTest;
    }
}
