package com.divatt.designer.services;


import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.response.GlobalResponce;

public interface ProductService2 {
    public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2);
    public GlobalResponce updateProduct(ProductMasterEntity2 productMasterEntity2, Integer productId);
    Map<String, Object> getAllProduct(int page, int limit, String sort, String sortName, Boolean isDeleted,
            String keyword, Optional<String> sortBy);
    public ProductMasterEntity2 getProduct(Integer productId);
    Map<String, Object> getProductDetailsallStatus(String adminStatus, int page, int limit, String sort,
            String sortName, Boolean isDeleted, String keyword, Optional<String> sortBy);

    Map<String, Object> getDesignerProductByDesignerId(Integer designerId, String adminStatus, Boolean isActive,
            int page, int limit, String sort, String sortName, Boolean isDeleted, String keyword,
            Optional<String> sortBy,String sortDateType);
    public GlobalResponce productDeleteByproductId(@PathVariable Integer productId);
}
