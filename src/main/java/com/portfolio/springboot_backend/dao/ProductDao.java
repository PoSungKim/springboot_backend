package com.portfolio.springboot_backend.dao;

import com.portfolio.springboot_backend.model.product.ProductImagesVO;
import com.portfolio.springboot_backend.model.product.ImageFileVO;
import com.portfolio.springboot_backend.model.product.ProductVO;
import com.portfolio.springboot_backend.model.product.UserProductVO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao {
    List<ProductVO> productList();

    int findProductId(ProductVO product);
    int findUserId(String writer);
    List<ImageFileVO> findImageByProductId(int id);
    ProductVO findOneProductByProductId(int id);

    void saveProduct(ProductVO product);
    void saveUserProduct(UserProductVO userProduct);
    void saveProductImages(ProductImagesVO productImages);
}
