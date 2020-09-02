package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.dao.ProductDao;
import com.portfolio.springboot_backend.model.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    ProductDao productDao;
    LocalDateTime localDateTime;

    @GetMapping("/all")
    public List<ProductVO> productsList() {
        System.out.println(localDateTime.now() + " 현재 저장된 products 리스트 조회하기");
        System.out.println(productDao.productList());
        List<ProductVO> productList = productDao.productList();
        for(int i = 0; i < productList.size(); i++) {
            List<ImageFileVO> imageList = productDao.findImageByProductId(productList.get(i).getId());
            productList.get(i).setImages(imageList);
        }
        return productList;
    }

    @GetMapping("/check/{productId}")
    public ProductCartSoldVO ProductCartSoldVO(@PathVariable(value="productId") int id) {
        System.out.println(localDateTime.now() + " ProductCartSoldVO() 함수 호출" + " "+  id);
        ProductCartSoldVO ret = new ProductCartSoldVO();
        try {
            ret.setCart_cnt(productDao.findCartByProductId(id));
        } catch (Exception error) {
            ret.setCart_cnt(0);
        }

        try {
            ret.setPurchase_cnt(productDao.findSoldByProductId(id));
        } catch (Exception error) {
            ret.setCart_cnt(0);
        }

        System.out.println(ret);
        return ret;
    }

    @GetMapping("/{productId}")
    public ProductVO getOneProductInfo(@PathVariable(value="productId") int id) {
        System.out.println(localDateTime.now() + " 요청된 하나의 Product 정보 전달하기" + " "+  id);
        System.out.println(id);
        ProductVO product = productDao.findOneProductByProductId(id);
        List<ImageFileVO> imageList = productDao.findImageByProductId(id);
        product.setImages(imageList);
        return product;
    }

    // FormData 연습용
    @PostMapping(value = "upload/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ImageFileVO> uploadPreview(@RequestParam(value = "files") MultipartFile[] files) throws IOException {
        // uploadVO 클래스를 담을 수 있는 List 선언 이후에,
        // files에서 하나씩 element 용도 uploadVO를 만들어서,
        // list에 넣어준 이후에 프론트 앤드로 리턴
        List<ImageFileVO> imageFileVOList = new ArrayList<ImageFileVO>();

        for(int i = 0; i < files.length; i++) {
            ImageFileVO elementImageFileVO = new ImageFileVO();
            elementImageFileVO.setProductImage_id(i + 1);
            elementImageFileVO.setFileName(files[i].getOriginalFilename());
            elementImageFileVO.setFileType(files[i].getContentType());
            elementImageFileVO.setImageByteData(files[i].getBytes());
            imageFileVOList.add(elementImageFileVO);
        }
        return imageFileVOList;
    }

    @PostMapping(value = "upload/product")
    public boolean uploadAll(@RequestBody ProductVO product) throws IOException {
        UserProductVO userProductVO = new UserProductVO();
        ProductImagesVO productImagesVO = new ProductImagesVO();
        boolean success = true;
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
                success = true;
            }
        } catch(Exception error) {
            System.out.println(error);
            success = false;
        }
        return success;
    }
}
