package com.divatt.designer.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.response.GlobalResponce;

@Service
public class ProductServiceImp2 implements ProductService2 {
	@Autowired
	private ProductRepo2 productRepo2;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private CustomFunction customFunction;
	@Autowired
	private SequenceGenerator sequenceGenerator;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp2.class);

	@Override
	public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2) {
		try {
			LOGGER.info("Inside-ProductServiceImp2.addProductMasterData()");
			productRepo2.save(customFunction.addProductMasterData(productMasterEntity2));
			return new GlobalResponce("Success!!", "Product added successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

//	@Override
//	public ProductMasterEntity2 getAllProduct() {
//		try {
//			return null;
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//		
//
//	}

	@Override
	public GlobalResponce updateProduct(ProductMasterEntity2 productMasterEntity2, Integer productId) {
		try {
			LOGGER.info("Inside-ProductServiceImp2.updateProduct()");
			if (productRepo2.existsById(productId)) {

				LOGGER.info("inside if");
				productRepo2.save(customFunction.updateProductData(productMasterEntity2, productId));

				return new GlobalResponce("Success", "Product updated successfully", 200);
			} else {
				throw new CustomException("Product Not Found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
