package com.divatt.designer.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;

public interface ProductServiceImp {

	public Map<String, Object> allProductList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy);
	
	GlobalResponce add(ProductMasterEntity productEntity);
	
	Optional<?> viewProductDetails(Integer productId);
	
	public GlobalResponce changeStatus(Integer productId);
	
	public GlobalResponce productDelete(Integer productId);
	
	public Map<String, Object> getAllProductDetails(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy);
	
	static boolean check() throws ClassNotFoundException
	{
		Class<?> forName= Class.forName("com.divatt.productservice.controller.ProductController");
		int length=forName.getDeclaredMethods().length;
		Class<?>[] interfaces= forName.getInterfaces();
		Class<?> class1=interfaces[0];
		int length2=class1.getMethods().length;
		if(length==length2)
		return true;
		
		return false;
	}
}
