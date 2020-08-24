package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.dao.ProductDao;
import com.portfolio.springboot_backend.model.product.ProductImagesVO;
import com.portfolio.springboot_backend.model.product.ProductVO;
import com.portfolio.springboot_backend.model.product.ImageFileVO;
import com.portfolio.springboot_backend.model.product.UserProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("upload")
public class ProductController {

    @Autowired
    ProductDao productDao;
    Date date;

    @GetMapping("/products")
    public List<ProductVO> productsList() {
        System.out.println(date + " 현재 저장된 products 리스트 조회하기");
        System.out.println(productDao.productList());
        return productDao.productList();
    }

    // FormData 연습용
    @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ImageFileVO> uploadPreview(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        // uploadVO 클래스를 담을 수 있는 List 선언 이후에,
        // files에서 하나씩 element 용도 uploadVO를 만들어서,
        // list에 넣어준 이후에 프론트 앤드로 리턴
        List<ImageFileVO> imageFileVOList = new ArrayList<ImageFileVO>();

        for(int i = 0; i < files.length; i++) {
            ImageFileVO elementImageFileVO = new ImageFileVO();
            elementImageFileVO.setKey(i + 1);
            elementImageFileVO.setFileName(files[i].getOriginalFilename());
            elementImageFileVO.setFileType(files[i].getContentType());
            elementImageFileVO.setImageByteData(files[i].getBytes());
            imageFileVOList.add(elementImageFileVO);
        }
        return imageFileVOList;
    }

    @PostMapping(value = "/product")
    public boolean uploadAll(@RequestBody ProductVO product) throws IOException {
        UserProductVO userProductVO = new UserProductVO();
        ProductImagesVO productImagesVO = new ProductImagesVO();

        try {
            // product 테이블에 정보 넣기
            productDao.saveProduct(product);

            // user_product 테이블에 정보 넣기
            int curProductId = productDao.findProductId(product);
            userProductVO.setProduct_id(curProductId);
            int curUserId  = productDao.findUserId(product.getWriter());
            userProductVO.setUser_id(curUserId);
            productDao.saveUserProduct(userProductVO);

            // product_productImages 테이블에 정보 넣기
            int productImage_id = 1;
            for(int i = 0; i < product.getImages().size(); i++) {
                productImagesVO.setProduct_id(curProductId);
                productImagesVO.setProductImage_id(productImage_id++);
                productImagesVO.setFileName(product.getImages().get(i).getFileName());
                productImagesVO.setFileType(product.getImages().get(i).getFileType());
                productImagesVO.setImageByteData(product.getImages().get(i).getImageByteData());

                productDao.saveProductImages(productImagesVO);
            }

        } catch(Exception error) {
            System.out.println(error);
        }


        return true;
    }
}
