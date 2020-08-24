package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.model.UploadVO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping(value = "/preview/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadVO> uploadPreview(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        // uploadVO 클래스를 담을 수 있는 List 선언 이후에,
        // files에서 하나씩 element 용도 uploadVO를 만들어서,
        // list에 넣어준 이후에 프론트 앤드로 리턴
        List<UploadVO> uploadVOList = new ArrayList<UploadVO>();

        for(int i = 0; i < files.length; i++) {
            UploadVO elementUploadVO = new UploadVO();
            elementUploadVO.setId((long)i + 1);
            elementUploadVO.setFileName(files[i].getOriginalFilename());
            elementUploadVO.setFileType(files[i].getContentType());
            elementUploadVO.setImageByteData(files[i].getBytes());
            uploadVOList.add(elementUploadVO);
        }
        return uploadVOList;
    }
}
