package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.model.ProductVO;
import com.portfolio.springboot_backend.model.ImageFileVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("upload")
public class ProductController {

    // FormData 연습용
    @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ImageFileVO> uploadPreview(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        // uploadVO 클래스를 담을 수 있는 List 선언 이후에,
        // files에서 하나씩 element 용도 uploadVO를 만들어서,
        // list에 넣어준 이후에 프론트 앤드로 리턴
        List<ImageFileVO> imageFileVOList = new ArrayList<ImageFileVO>();

        for(int i = 0; i < files.length; i++) {
            ImageFileVO elementImageFileVO = new ImageFileVO();
            elementImageFileVO.setKey((long)i + 1);
            elementImageFileVO.setFileName(files[i].getOriginalFilename());
            elementImageFileVO.setFileType(files[i].getContentType());
            elementImageFileVO.setImageByteData(files[i].getBytes());
            imageFileVOList.add(elementImageFileVO);
        }
        return imageFileVOList;
    }

    @PostMapping(value = "/product")
    public boolean uploadAll(@RequestBody ProductVO product) throws IOException {
        System.out.println(product.getTitle() + " " + product.getContent() + " " + product.getWriter());
        System.out.println(product.getImages().size());

        for(int i = 0; i < product.getImages().size(); i++) {
            System.out.println(product.getImages().get(i).getFileName() + " " + product.getImages().get(i).getFileType() + " " + product.getImages().get(i).getImageByteData());
        }

        return true;
    }
}
