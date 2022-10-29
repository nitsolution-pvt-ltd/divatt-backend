package com.divatt.designer.services;

import java.util.List;


import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.response.GlobalResponce;

public interface ProductService2 {
	public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2);
	public GlobalResponce updateProduct(ProductMasterEntity2 productMasterEntity2, Integer productId);
	public List<ProductMasterEntity2> getAllProduct();
	public ProductMasterEntity2 getProduct (Integer productId);

}
