package com.divatt.designer.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
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

	@Autowired
	private DesignerLoginRepo designerLoginRepo;
	
	
	@Autowired
	private DesignerProfileRepo designerProfileRepo;
	
	
	@Autowired
	private MongoOperations mongoOperations;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	public List<ListProduct> allList() {
		try {
			LOGGER.info("Inside - ProductService.allList()");
			List<ProductMasterEntity> productdata= productRepo.findAll();
			List<Integer>productId=productdata.stream().map(e->e.getDesignerId()).collect(Collectors.toList());
			//System.out.println(productId);
			List<DesignerProfileEntity> profileData=new ArrayList<DesignerProfileEntity>();
			for(int i=0;i<productId.size();i++)
			{
				profileData.add(designerProfileRepo.findBydesignerId(Long.valueOf(productId.get(i))).get());
			}
			LinkedList<ListProduct> allData=new LinkedList<ListProduct>();
			ListProduct listProduct= new ListProduct();
			for(int i=0;i<productId.size();i++)
			{
				listProduct.setDesignerProfileEntity(profileData.get(i));
				listProduct.setProductMasterEntity(productdata.get(i));
				allData.add(listProduct);
			}
			return allData;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce addData(ProductMasterEntity productData) {
		try {
			LOGGER.info("Inside-ProductService.addData()");
			Query query = new Query();
			query.addCriteria(Criteria.where("designer_id").is(productData.getDesignerId()));
			List<DesignerProfileEntity> designerProfileInfo= mongoOperations.find(query, DesignerProfileEntity.class);
			if(!designerProfileInfo.isEmpty())
			{
				Query query1= new Query();
				query1.addCriteria(Criteria.where("designerId").is(productData.getDesignerId()).and("productName").is(productData.getProductName()));
				List<ProductMasterEntity> productInfo= mongoOperations.find(query1, ProductMasterEntity.class);
				//System.out.println(productInfo.size());
				if(productInfo.isEmpty())
				{
					productRepo.save(customFunction.filterDataEntity(productData));
					return new  GlobalResponce("Success!!", "Product added successfully", 200);
				}
				else
				{
					return new GlobalResponce("Error!!", "Product already added", 400);
				}
			} else {
				return new GlobalResponce("Error!!", "Designerid does not exist!!", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Optional<?> productDetails(Integer productId) {
		try {
			LOGGER.info("Inside-ProductService.productDetails()");
			if (productRepo.existsById(productId)) {
				LOGGER.info("Inside - ProductService.productDetails()");
				Optional<ProductMasterEntity> findById = productRepo.findById(productId);
				return findById;
			} else {
				return Optional.of(new GlobalResponce("Bad request", "Product id does not exist", 400));
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce changeStatus(Integer productId) {
		try {
			LOGGER.info("Inside - ProductService.changeStatus()");
			if (productRepo.existsById(productId)) {
				Boolean status;
				Optional<ProductMasterEntity> productData = productRepo.findById(productId);
				ProductMasterEntity productEntity = productData.get();
				if (productEntity.getIsActive().equals(true)) {
					status = false;
				} else {
					status = true;
				}
				productEntity.setIsActive(status);
				productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
				productEntity.setUpdatedOn(new Date());
				productRepo.save(productEntity);
				return new GlobalResponce("Success", "Status change successfully", 200);
			} else {
				return new GlobalResponce("Bad request", "Product does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce updateProduct(Integer productId, ProductMasterEntity productMasterEntity) {
		try {
			LOGGER.info("Inside-ProductService.updateProduct()");
			if(productRepo.existsById(productId))
			{
				Query query= new Query();
				query.addCriteria(Criteria.where("designerId").is(productMasterEntity.getDesignerId()));
				List<ProductMasterEntity>productInfo= mongoOperations.find(query, ProductMasterEntity.class);
				if(productInfo.isEmpty())
				{
					throw new CustomException("Designer id can to be change");
				}
				productRepo.save(customFunction.updateFunction(productMasterEntity, productId));
				return new GlobalResponce("Success", "Product updated successfully", 200);
			}
			else
			{
				throw new CustomException("Product not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce deleteProduct(Integer productId) {

		try {
			LOGGER.info("Inside - ProductService.deleteProduct()");
			if (productRepo.existsById(productId)) {
				Boolean isDelete = false;
				Optional<ProductMasterEntity> productData = productRepo.findById(productId);
				ProductMasterEntity productEntity = productData.get();
				if (productEntity.getIsDeleted().equals(false)) {
					isDelete = true;
				} else {
					return new GlobalResponce("Bad request!!", "Product allReady deleted", 400);
				}
				productEntity.setIsDeleted(isDelete);
				productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
				productEntity.setUpdatedOn(new Date());
				productRepo.save(productEntity);
				return new GlobalResponce("Success", "Deleted successfully", 200);
			} else {
				return new GlobalResponce("Bad request", "Product does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getProductDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
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
				findAll = productRepo.findByIsDeleted(isDeleted, pagingSort);
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
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> allWishlistProductData(List<Integer> productIdList, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit) {
		try {
			LOGGER.info("Inside-ProductService.allWishlistProductData()");
			if (productIdList.isEmpty()) {
				throw new CustomException("Product not found!");
			} else {
				List<ProductMasterEntity> list = productRepo.findByProductIdIn(productIdList);

				int CountData = (int) list.size();
				if (limit == 0) {
					limit = CountData;
				}

				Pageable pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				Page<ProductMasterEntity> findAll = productRepo.findByProductIdIn(productIdList, pagingSort);

				int totalPage = findAll.getTotalPages() - 1;
				if (totalPage < 0) {
					totalPage = 0;
				}

				Map<String, Object> response = new HashMap<>();
				response.put("data", findAll.getContent());
				response.put("currentPage", findAll.getNumber());
				response.put("total", CountData);
				response.put("totalPage", totalPage);
				response.put("perPage", findAll.getSize());
				response.put("perPageElement", findAll.getNumberOfElements());

				if (findAll.getSize() <= 0) {
					throw new CustomException("Product not found!");
				} else {
					return response;
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity> designerIdList(Integer designerId1) {
		try
		{
			Query query= new Query();
			query.addCriteria(Criteria.where("designerId").is(designerId1));
			List<ProductMasterEntity> productList=mongoOperations.find(query, ProductMasterEntity.class);
			System.out.println(productList);
			return productList;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> designerIdListPage(Integer designerId, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit,String keyword) {
		try {
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
				findAll = productRepo.findByIsDeletedAndDesignerId(isDeleted, designerId,pagingSort);
			} else {
				findAll = productRepo.listDesignerProductsearch(keyword, isDeleted, designerId,pagingSort);

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

			if (findAll.getSize() <=1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	

}
