package com.divatt.designer.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.response.GlobalResponce;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	
	private SequenceGenerator sequenceGenarator;
	
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
				return new GlobalResponce("ERROR", "Product Already Exist!", 400);
			}
			else
			{
				//System.out.println(customFunction.dbWrite(customFunction.filterDataEntity(productData)));
				productRepo.save(customFunction.filterDataEntity(productData));
				return new GlobalResponce("Success!!", "Added Successfully", 200);
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
			LOGGER.info("Inside - ProductService.changeStatus()");
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
			LOGGER.info("Inside - ProductService.updateProduct()");
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
			LOGGER.info("Inside - ProductService.deleteProduct()");
			if(productRepo.existsById(productId))
			{
				Boolean isDelete = false;
				Optional<ProductMasterEntity> productData= productRepo.findById(productId);
				ProductMasterEntity productEntity= productData.get();
				if(productEntity.getIsDeleted().equals(false))
				{
					isDelete=true;
				}
				else
				{
					return new GlobalResponce("Bad Request!!", "Product AllReady deleted", 400);
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
	public Map<String, Object> getProductDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try
		{
			int CountData = (int) productRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}
			
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ProductMasterEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = productRepo.findByIsDeleted(isDeleted,pagingSort);
			} else {				
				findAll = productRepo.Search(keyword, isDeleted, pagingSort);

			}
			

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Product Not Found!");
			} else {
				return response;
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
}
