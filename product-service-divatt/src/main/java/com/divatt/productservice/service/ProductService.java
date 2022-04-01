package com.divatt.productservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ProductService.class);
	public List<ProductMasterEntity> allList() {
		try
		{
			LOGGER.info("Inside - ProductService.allList()");
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
				LOGGER.info("Inside - ProductService.addData()");
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
				LOGGER.info("Inside - ProductService.productDetails()");
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
	public GlobalResponce changeStatus(Integer productId) {
		try
		{
			if(productRepo.existsById(productId))
			{
				Boolean status;
				Optional<ProductMasterEntity> productData= productRepo.findById(productId);
				ProductMasterEntity productEntity= productData.get();
				if(productEntity.getIsActive().equals(true))
				{
					status=false;
				}
				else
				{
					status=true;
				}
				productEntity.setIsActive(status);
				productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
				productEntity.setUpdatedOn(new Date());
				productRepo.save(productEntity);
				return new GlobalResponce("Success", "Status Change Successfully", 200);
			}
			else
			{
				return new GlobalResponce("Bad Request", "Product Does Not Exist", 400);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponce updateProduct(Integer productId, ProductMasterEntity productMasterEntity) {
		try
		{
			if(productRepo.existsById(productId))
			{
				productRepo.save(customFunction.filterDataEntity(productMasterEntity));
				return new GlobalResponce("Success", "Product Updated Successfully", 200);
			}
			else
			{
				return new GlobalResponce("Bad Request", "Product Id Does Not Exist", 400);
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponce deleteProduct(Integer productId) {
		// TODO Auto-generated method stub
		
		try {
			if(productRepo.existsById(productId))
			{
				Boolean isDelete = false;
				Optional<ProductMasterEntity> productData= productRepo.findById(productId);
				ProductMasterEntity productEntity= productData.get();
				if(productEntity.getIsDeleted().equals(false))
				{
					isDelete=true;
				}
				
				productEntity.setIsDeleted(isDelete);
				productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
				productEntity.setUpdatedOn(new Date());
				productRepo.save(productEntity);
				return new GlobalResponce("Success", "Delete Successfully", 200);
			}
			else
			{
				return new GlobalResponce("Bad Request", "Product Does Not Exist", 400);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException(e.getMessage());
		}
		
	}
	
}
