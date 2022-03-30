package com.divatt.productservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.divatt.productservice.entity.ProductMasterEntity;
import com.divatt.productservice.exception.CustomException;
import com.divatt.productservice.helper.CustomFunction;
import com.divatt.productservice.repo.ProductRepository;
import com.divatt.productservice.response.GlobalResponce;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private SequenceGenarator sequenceGenarator;
	
	@Autowired
	private CustomFunction customFunction;
	
	public List<ProductMasterEntity> allList() {
		try
		{
			return productRepo.findAll();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponce addData(ProductMasterEntity productData)
	{
		try
		{
			Optional<ProductMasterEntity> findByProductName=productRepo.findByProductName(productData.getProductName());
			if(findByProductName.isPresent())
			{
				return new GlobalResponce("ERROR", "Product Already Exist!", 400);
			}
			else
			{
				productRepo.save(customFunction.filterDataEntity(productData));
				return new GlobalResponce("Success", "Product Added Successfully", 200);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public Optional<?> productDetails(Integer productId) {
		try
		{
			if(productRepo.existsById(productId))
			{
				Optional<ProductMasterEntity> findById = productRepo.findById(productId);
				return findById;
			}
			else
			{
				return Optional.of(new GlobalResponce("Bad Request", "Product Id does not exist", 400));
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
