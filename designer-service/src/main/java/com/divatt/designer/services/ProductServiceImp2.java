package com.divatt.designer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.ProductRepo2;

@Service
public class ProductServiceImp2 implements ProductService2 {
	@Autowired
	private ProductRepo2 productRepo2;

	@Override
	public ProductMasterEntity2 getAllProduct() {
		try {
			return null;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
