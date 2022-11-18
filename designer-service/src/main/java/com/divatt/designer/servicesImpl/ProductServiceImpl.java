package com.divatt.designer.servicesImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.designer.entity.CategoryEntity;
import com.divatt.designer.entity.EmailEntity;
import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.OrderSKUDetailsEntity;
import com.divatt.designer.entity.ProductEntity;
import com.divatt.designer.entity.StockEntity;
import com.divatt.designer.entity.UserProfile;
import com.divatt.designer.entity.UserProfileInfo;
import com.divatt.designer.entity.UserResponseEntity;
import com.divatt.designer.entity.product.ImagesEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.product.StandardSOH;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.helper.EmailSenderThread;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.repo.ProductRepository;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductService;
import com.divatt.designer.services.SequenceGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private RestTemplate restTemplate;

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

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private ProductRepo2 productRepo2;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	public Map<String, Object> allList(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			LOGGER.info("Inside - ProductServiceImpl.allList()");
			List<ProductMasterEntity> productdata = productRepo.findAll();

			List<Integer> productId = productdata.stream().map(e -> e.getDesignerId()).collect(Collectors.toList());

			List<DesignerProfileEntity> profileData = new ArrayList<DesignerProfileEntity>();
			for (int i = 0; i < productId.size(); i++) {
				profileData.add(designerProfileRepo.findBydesignerId(Long.valueOf(productId.get(i))).get());
			}
			LinkedList<ListProduct> allData = new LinkedList<ListProduct>();
			ListProduct listProduct = new ListProduct();
			for (int i = 0; i < productId.size(); i++) {
				listProduct.setDesignerProfileEntity(profileData.get(i));
				listProduct.setProductMasterEntity(productdata.get(i));
				allData.add(listProduct);
			}

			if (allData.isEmpty()) {
				throw new CustomException("Product not found!");
			} else {

				int CountData = (int) allData.size();
				if (limit == 0) {
					limit = CountData;
				}

				Pageable pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				Page<ListProduct> findAll = productRepo.findByDesignerIdIn(allData, pagingSort);

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

	public GlobalResponce addData(ProductMasterEntity productData) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.addData()");
			Query query = new Query();
			query.addCriteria(Criteria.where("designer_id").is(productData.getDesignerId()));
			List<DesignerProfileEntity> designerProfileInfo = mongoOperations.find(query, DesignerProfileEntity.class);
			if (!designerProfileInfo.isEmpty()) {
				Query query2 = new Query();
				query2.addCriteria(
						Criteria.where("profile_status").is("COMPLETED").and("_id").is(productData.getDesignerId()));
				List<DesignerLoginEntity> list = mongoOperations.find(query2, DesignerLoginEntity.class);
				if (!list.isEmpty()) {
					Query query1 = new Query();
					query1.addCriteria(Criteria.where("designerId").is(productData.getDesignerId()).and("productName")
							.is(productData.getProductName()));
					List<UserProfile> userProfiles = new ArrayList<UserProfile>();
					List<ProductMasterEntity> productInfo = mongoOperations.find(query1, ProductMasterEntity.class);
					if (productInfo.isEmpty()) {

						ResponseEntity<String> categoryResponse = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + productData.getCategoryId(),
								String.class);

						ResponseEntity<String> subcategoryResponse = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + productData.getSubCategoryId(),
								String.class);
						productRepo.save(customFunction.filterDataEntity(productData));

						return new GlobalResponce("Success!!", "Product added successfully", 200);
					} else {
						return new GlobalResponce("Error!!", "Product already added", 400);
					}
				} else {
					return new GlobalResponce("Error!!", "Designer doucument is not appoved", 400);
				}
			} else {
				return new GlobalResponce("Error!!", "Designerid does not exist!!", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductEntity productDetails(Integer productId) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.productDetails()");
			if (productRepo.existsById(productId)) {
				LOGGER.info("Inside - ProductService.productDetails()");
				ProductMasterEntity masterEntity = productRepo.findById(productId).get();
//				 RestTemplate restTemplate= new RestTemplate();
				ResponseEntity<Object> categoryEntity = restTemplate.getForEntity(
						"https://localhost:8084/dev/category/view/" + masterEntity.getCategoryId(), Object.class);
				ResponseEntity<Object> subCategoryEntity = restTemplate.getForEntity(
						"https://localhost:8084/dev/subcategory/view/" + masterEntity.getSubCategoryId(), Object.class);
				ProductEntity productData = customFunction.productFilter(masterEntity);
				productData.setCategoryObject(categoryEntity.getBody());
				productData.setSubCategoryObject(subCategoryEntity.getBody());
				return productData;

			} else {
				throw new CustomException("Product not found");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce changeStatus(Integer productId) {
		try {
			LOGGER.info("Inside - ProductServiceImpl.changeStatus()");
			if (productRepo.existsById(productId)) {
				Boolean status;
				Optional<ProductMasterEntity> productData = productRepo.findById(productId);
				ProductMasterEntity productEntity = productData.get();
				if (productEntity.getIsActive().equals(true)) {
					status = false;
					productEntity.setIsActive(status);
					productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
					productEntity.setUpdatedOn(new Date());
					productRepo.save(productEntity);
					return new GlobalResponce("Success", "Status Inactive successfully", 200);
				} else {
					status = true;
					productEntity.setIsActive(status);
					productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
					productEntity.setUpdatedOn(new Date());
					productRepo.save(productEntity);
					return new GlobalResponce("Success", "Status Active successfully", 200);
				}

			} else {
				return new GlobalResponce("Bad request", "Product does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce updateProduct(Integer productId, ProductMasterEntity productMasterEntity) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.updateProduct()");
			if (productRepo.existsById(productId)) {
				Query query = new Query();
				query.addCriteria(Criteria.where("designerId").is(productMasterEntity.getDesignerId()));
				List<ProductMasterEntity> productInfo = mongoOperations.find(query, ProductMasterEntity.class);
				if (productInfo.isEmpty()) {
					throw new CustomException("Designer id can to be change");
				}
				productRepo.save(customFunction.updateFunction(productMasterEntity, productId));
				return new GlobalResponce("Success", "Product updated successfully", 200);
			} else {
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

			System.out.println(findAll.toString());

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

	@SuppressWarnings("unchecked")
	public Map<String, Object> allWishlistProductData(List<Integer> productIdList, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.allWishlistProductData()");
			if (productIdList.isEmpty()) {
				Map<String, Object> response = new HashMap<>();
				List<String> data = new ArrayList<String>();
//				data.add("Wishlist empty");
				response.put("data", data);
				response.put("currentPage", 0);
				response.put("total", 0);
				response.put("totalPage", 0);
				response.put("perPage", 0);
				response.put("perPageElement", 0);
				return response;
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

	public ResponseEntity<?> allCartProductData(List<Integer> productIdList) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.allWishlistProductData()");
			if (productIdList.isEmpty()) {
				throw new CustomException("Product not found!");
			} else {
				List<ProductMasterEntity> list = productRepo.findByProductIdIn(productIdList);

				if (list.size() <= 0) {
					throw new CustomException("Product not found!");
				} else {
					return ResponseEntity.ok(list);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity> designerIdList(Integer designerId1) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("designerId").is(designerId1));
			List<ProductMasterEntity> productList = mongoOperations.find(query, ProductMasterEntity.class);

			return productList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> designerIdListPage(Integer designerId, String status, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit, String keyword, Boolean isActive,
			String sortDateType) {
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

			if (!sortDateType.equals(null)) {

				if (sortDateType.equalsIgnoreCase("new")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, "createdOn");

				} else if (sortDateType.equalsIgnoreCase("old")) {
					pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, "createdOn");

				}
			}

			Page<ProductMasterEntity> findAll = null;
			Integer live = 0;
			Integer pending = 0;
			Integer reject = 0;
			Integer ls = 0;
			Integer oos = 0;

			live = productRepo.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
					"Approved");
			pending = productRepo.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
					isActive, "Pending");
			reject = productRepo.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
					"Rejected");
			oos = productRepo.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, false,
					"Approved");
			// need to do get count for low stock and out of stock
			if (keyword.isEmpty()) {
				if (StringUtils.equals(status, "live")) {
					findAll = productRepo.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Approved", isActive, pagingSort);
				} else if (StringUtils.equals(status, "pending")) {
					LOGGER.info("Status Datata={}", status);
					findAll = productRepo.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Pending", isActive, pagingSort);
					LOGGER.info("Data for finf all={}", findAll.getContent());
				} else if (StringUtils.equals(status, "reject")) {
					findAll = productRepo.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Rejected", isActive, pagingSort);
				} else if (StringUtils.equals(status, "ls")) {
					findAll = productRepo.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							status, isActive, pagingSort);
				} else if (StringUtils.equals(status, "oos")) {
					findAll = productRepo.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Approved", false, pagingSort);
				}
			} else {
				if (StringUtils.equals(status, "live")) {
					findAll = productRepo.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Approved", pagingSort);
				} else if (StringUtils.equals(status, "pending")) {
					findAll = productRepo.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Pending", pagingSort);
				} else if (StringUtils.equals(status, "reject")) {
					findAll = productRepo.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Reject", pagingSort);
				} else if (StringUtils.equals(status, "ls")) {
					findAll = productRepo.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId, "Ls",
							pagingSort);
				} else if (StringUtils.equals(status, "oos")) {
					findAll = productRepo.listDesignerProductsearchByAdminStatusForOos(keyword, isDeleted, designerId,
							"Approved", false, pagingSort);
				}

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
			response.put("live", live);
			response.put("pending", pending);
			response.put("reject", reject);
			response.put("ls", ls);
			response.put("oos", oos);

			if (findAll.getSize() <= 1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity> getApproval() {
		try {
			return this.productRepo.findAll();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getListProduct() {
		long count = sequenceGenarator.getCurrentSequence(ProductMasterEntity.SEQUENCE_NAME);
		Random rd = new Random();

		List<ProductMasterEntity> findAll = productRepo.findByIsDeletedAndAdminStatusAndIsActive(false, "Approved",
				true);
		if (findAll.size() <= 15) {
			return ResponseEntity.ok(findAll);
		}
		List<ProductMasterEntity> productMasterEntity = new ArrayList<>();
		Boolean flag = true;
		while (flag) {
			int nextInt = rd.nextInt((int) count);
			for (ProductMasterEntity obj : findAll) {
				if (obj.getProductId() == nextInt) {
					productMasterEntity.add(obj);
				}
				if (productMasterEntity.size() > 14)
					flag = false;
			}
		}
		return ResponseEntity.ok(productMasterEntity);

	}

	public Map<String, Object> getProductDetailsPerStatus(String status, int page, int limit, String sort,
			String sortName, Boolean isDeleted, String keyword, Optional<String> sortBy) {

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
			Integer all = 0;
			Integer pending = 0;
			Integer approved = 0;
			Integer rejected = 0;

			all = productRepo.countByIsDeleted(isDeleted);
			pending = productRepo.countByIsDeletedAndAdminStatus(isDeleted, "Pending");
			approved = productRepo.countByIsDeletedAndAdminStatus(isDeleted, "Approved");
			rejected = productRepo.countByIsDeletedAndAdminStatus(isDeleted, "Rejected");

			if (keyword.isEmpty()) {

				if (status.equals("all")) {

					findAll = productRepo.findByIsDeleted(isDeleted, pagingSort);

				} else if (status.equals("pending")) {

					findAll = productRepo.findByIsDeletedAndAdminStatus(isDeleted, "Pending", pagingSort);

				} else if (status.equals("approved")) {

					findAll = productRepo.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);

				} else if (status.equals("rejected")) {

					findAll = productRepo.findByIsDeletedAndAdminStatus(isDeleted, "Rejected", pagingSort);
				}
			} else {

				if (status.equals("all")) {

					findAll = productRepo.SearchAndfindByIsDeleted(keyword, isDeleted, pagingSort);

				} else if (status.equals("pending")) {

					findAll = productRepo.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Pending",
							pagingSort);

				} else if (status.equals("approved")) {

					findAll = productRepo.SearchAppAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
							pagingSort);

				} else if (status.equals("rejected")) {

					findAll = productRepo.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Rejected",
							pagingSort);

				}

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
			response.put("all", all);
			response.put("pending", pending);
			response.put("approved", approved);
			response.put("rejected", rejected);

			if (findAll.getSize() <= 1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getDesignerProductListService(Integer page, Integer limit, Optional<String> sortBy,
			String sort, String sortName, String keyword, Boolean isDeleted) {
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
				findAll = productRepo.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);
			} else {
				findAll = productRepo.DesignerSearchfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
						pagingSort);

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

	public Map<String, Object> getPerDesignerProductListService(Integer page, Integer limit, Optional<String> sortBy,
			String sort, String sortName, String keyword, Boolean isDeleted, Integer designerId) {
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
				findAll = productRepo.findByIsDeletedAndAdminStatusAndDesignerId(isDeleted, "Approved", designerId,
						pagingSort);
			} else {
				findAll = productRepo.DesignerSearchfindByIsDeletedAndAdminStatusAndDesignerId(keyword, isDeleted,
						"Approved", designerId, pagingSort);

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

	public List<ProductMasterEntity2> UserDesignerProductList(Integer Id) {
		try {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("designerId").is(Id).and("isActive").is(true).and("adminStatus").is("Approved"));
			List<ProductMasterEntity2> productList = mongoOperations.find(query, ProductMasterEntity2.class);

			if (productList.isEmpty()) {
				throw new CustomException("Product not found");
			}
			long count = sequenceGenarator.getCurrentSequence(ProductMasterEntity.SEQUENCE_NAME);
			Random rd = new Random();
			if (productList.size() < 15) {
				return productList;
			}
			List<ProductMasterEntity2> productMasterEntity = new ArrayList<>();
			Boolean flag = true;
			while (flag) {
				int nextInt = rd.nextInt((int) count);
				for (ProductMasterEntity2 obj : productList) {
					if (obj.getProductId() == nextInt) {
						productMasterEntity.add(obj);
					}
					if (productMasterEntity.size() > 14)
						flag = false;
				}

			}
			return productMasterEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getPerDesignerProductService(Integer designerId) {
		try {
			long count = sequenceGenarator.getCurrentSequence(ProductMasterEntity.SEQUENCE_NAME);
			Random rd = new Random();

			List<ProductMasterEntity> findAll = productRepo
					.findByDesignerIdAndIsDeletedAndAdminStatusAndIsActive(designerId, false, "Approved", true);
			if (findAll.size() <= 15) {
				return ResponseEntity.ok(findAll);
			}
			List<ProductMasterEntity> productMasterEntity = new ArrayList<>();
			Boolean flag = true;
			while (flag) {
				int nextInt = rd.nextInt((int) count);
				for (ProductMasterEntity obj : findAll) {
					if (obj.getProductId() == nextInt) {
						productMasterEntity.add(obj);
					}
					if (productMasterEntity.size() > 14)
						flag = false;
				}
			}
			return ResponseEntity.ok(productMasterEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> ProductListByIdService(List<Integer> productIdList) {
		try {
			LOGGER.info("Inside-ProductServiceImpl.ProductListByIdService()");
			if (productIdList.isEmpty()) {
				throw new CustomException("Product not found!");
			} else {
				List<ProductMasterEntity> list = productRepo.findByProductIdIn(productIdList);

				if (list.size() <= 0) {
					throw new CustomException("Product not found!");
				} else {
					return ResponseEntity.ok(list);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce adminApproval(Integer productId, ProductMasterEntity productData) throws IOException {
		try {
			productData.setProductId(productId);
			productRepo.save(productData);
			Query query3 = new Query();
			query3.addCriteria(Criteria.where("productName").is(productData.getProductName()));
			ProductMasterEntity newProductData = mongoOperations.findOne(query3, ProductMasterEntity.class);
			List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();
			List<Long> userId = new ArrayList<Long>();

			ResponseEntity<String> forEntity = restTemplate.getForEntity(
					"https://localhost:8082/dev/user/followedUserList/" + productData.getDesignerId(), String.class);
			String data = forEntity.getBody();
			JSONArray jsonArray = new JSONArray(data);
			String designerImageData = designerProfileRepo.findBydesignerId(productData.getDesignerId().longValue())
					.get().getDesignerProfile().getProfilePic();
			for (int i = 0; i < jsonArray.length(); i++) {
				ObjectMapper objectMapper = new ObjectMapper();
				UserProfile readValue = objectMapper.readValue(jsonArray.get(i).toString(), UserProfile.class);
				ResponseEntity<UserProfileInfo> userInfo = restTemplate.getForEntity(
						"https://localhost:8082/dev/user/getUserId/" + readValue.getUserId(), UserProfileInfo.class);
				userInfoList.add(userInfo.getBody());
			}
			for (int i = 0; i < userId.size(); i++) {
				ResponseEntity<UserProfileInfo> userProfileList = restTemplate.getForEntity(
						"https://localhost:8080/dev/auth/info/USER/" + userId.get(i), UserProfileInfo.class);

			}
			ProductMasterEntity productMasterEntity = productRepo.findById(productData.getProductId()).get();
			ImagesEntity[] images = productMasterEntity.getImages();
			String image1 = images[0].getName();
			System.out.println(images[0].getName());
			Map<String, Object> data2 = new HashMap<String, Object>();
			for (int i = 0; i < userInfoList.size(); i++) {
				EmailEntity emailEntity = new EmailEntity();
				emailEntity.setProductDesc(productMasterEntity.getProductDescription());
				emailEntity.setProductDesignerName(productMasterEntity.getDesignerName());
				emailEntity.setProductImage(image1);
				emailEntity.setProductName(productMasterEntity.getProductName());
				if (productMasterEntity.getPrice().getIndPrice().getDiscountType().equals("None")) {
					emailEntity.setProducyDiscount("No discount");
				} else if (productMasterEntity.getPrice().getIndPrice().getDiscountType().equals("Flat")) {
					emailEntity.setProducyDiscount(
							"Rs." + productMasterEntity.getPrice().getIndPrice().getDiscountValue().toString());

				} else {
					emailEntity.setProducyDiscount(
							productMasterEntity.getPrice().getIndPrice().getDiscountValue().toString() + "%");
				}
				emailEntity.setProductPrice(productMasterEntity.getPrice().getIndPrice().getDealPrice().toString());
				emailEntity.setUserName(userInfoList.get(i).getFirstName());
				emailEntity.setProductId(productMasterEntity.getProductId().toString());
				emailEntity.setDesignerImage(designerImageData);
				emailEntity.setUserName(userInfoList.get(i).getFirstName());
				System.out.println(userInfoList.get(i).getEmail());
				data2.put("data", emailEntity);
				Context context = new Context();
				context.setVariables(data2);
				String htmlContent = templateEngine.process("emailTemplate", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(userInfoList.get(i).getEmail(),
						"New product Arrived", htmlContent, true, null, restTemplate);
				emailSenderThread.start();
			}
			return new GlobalResponce("Successfull", "Product approved", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce multiDelete(List<Integer> productIdList) {
		try {
			List<ProductMasterEntity> productList = new ArrayList<ProductMasterEntity>();
			for (int i = 0; i < productIdList.size(); i++) {
				ProductMasterEntity productData = productRepo.findById(productIdList.get(i)).get();
				productList.add(productData);
			}
			List<ProductMasterEntity> collect = productList.stream().filter(e -> e.getIsDeleted().equals(false))
					.collect(Collectors.toList());
			for (int i = 0; i < collect.size(); i++) {
				collect.get(i).setIsDeleted(true);
				collect.get(i).setIsActive(false);
			}
			productRepo.saveAll(collect);
			return new GlobalResponce("Success", "The products are deleted successfully", 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce stockClearenceService(List<OrderSKUDetailsEntity> orderSKUDetailsEntities) {
		try {
			for (int i = 0; i < orderSKUDetailsEntities.size(); i++) {
				int productId = orderSKUDetailsEntities.get(i).getProductId();
				int productQty = orderSKUDetailsEntities.get(i).getUnits().intValue();
				String productSize = orderSKUDetailsEntities.get(i).getSize();
//				List<StandardSOH> updatedSOH = new ArrayList<StandardSOH>();
				ProductMasterEntity2 productMasterEntity = productRepo2.findById(productId).get();
				LOGGER.info("Size for the product = {}",productSize);
				if(productSize.equals("Custom") && productMasterEntity.getWithCustomization()) {	
					productRepo2.save(new CustomFunction().setProduDetails(productMasterEntity, productQty));
				}
				List<String> sizes = productMasterEntity.getSizes();
				for(int k = 0; k < sizes.size(); k++) {
					if(sizes.get(i).equals(productSize)) {
						productRepo2.save(new CustomFunction().setProduDetails(productMasterEntity, productQty));
					}
				}
			}
			return new GlobalResponce("Success", "Stock cleared successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity> productListCategorySubcategory(String categoryName, String subcategoryName) {
		try {
			ResponseEntity<CategoryEntity> categoryEntity = restTemplate
					.getForEntity("https://localhost:9095/dev/category/", CategoryEntity.class);
			return null;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity> viewProductByCategorySubcategoryService(String categoryName,
			String subCategoryName) {
		try {

			ResponseEntity<UserResponseEntity> userResponseEntity = restTemplate.getForEntity(
					"https://localhost:9095/dev/category/viewByName/" + categoryName + "/" + subCategoryName,
					UserResponseEntity.class);
			int categoryIdvalue = userResponseEntity.getBody().getCategoryEntity().getId();
			if (userResponseEntity.getBody().getSubCategoryEntity().getParentId().equals("0")) {
				Query query = new Query();
				query.addCriteria(Criteria.where("categoryId").is(categoryIdvalue).and("isDeleted").is(false)
						.and("isActive").is(true).and("adminStatus").is("Approved"));
				List<ProductMasterEntity> productMasterEntities = mongoOperations.find(query,
						ProductMasterEntity.class);
				return productMasterEntities;
			}
			int subcategoryIdvalue = userResponseEntity.getBody().getSubCategoryEntity().getId();
			Query query = new Query();
			query.addCriteria(
					Criteria.where("categoryId").is(categoryIdvalue).and("subCategoryId").is(subcategoryIdvalue)
							.and("isDeleted").is(false).and("isActive").is(true).and("adminStatus").is("Approved"));
			List<ProductMasterEntity> productMasterEntities = mongoOperations.find(query, ProductMasterEntity.class);
			return productMasterEntities;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getProductReminderService(Integer page, Integer limit, Optional<String> sortBy,
			String sort, String sortName, String keyword, Boolean isDeleted) {
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
			List<ProductMasterEntity> findAlls = null;

			if (keyword.isEmpty()) {

				LocalDate date = LocalDate.now();

				findAll = productRepo.findNotify(date, pagingSort);

			} else {
				findAll = productRepo.DesignerSearchfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
						pagingSort);

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

	public GlobalResponce designerNotification() {
		try {
			List<DesignerProfileEntity> designerProfileEntities = designerProfileRepo.findAll();
			HashMap<String, List<StockEntity>> data = new HashMap<String, List<StockEntity>>();
			StockEntity entity = new StockEntity();
			List<Integer> designerList = new ArrayList<Integer>();
			List<String> emails = new ArrayList<String>();
			for (int i = 0; i < designerProfileEntities.size(); i++) {
				designerList.add(designerProfileEntities.get(i).getDesignerId().intValue());
				emails.add(designerProfileEntities.get(i).getDesignerProfile().getEmail());
			}
			for (int i = 0; i < designerList.size(); i++) {
				List<StockEntity> stockList = new ArrayList<StockEntity>();
				List<ProductMasterEntity> productDataList = productRepo
						.findByDesignerIdAndIsDeletedAndAdminStatusAndIsActive(designerList.get(i), false, "Approved",
								true);
				List<Integer> productList = new ArrayList<Integer>();
				for (int a = 0; a < productDataList.size(); a++) {
					ImagesEntity[] imagesEntities = productDataList.get(a).getImages();
					String productImages = imagesEntities[0].getName().toString();
					List<StandardSOH> sohData = productDataList.get(a).getStanderedSOH();
					for (int b = 0; b < sohData.size(); b++) {
						if (sohData.get(b).getSoh() < sohData.get(b).getNotify()) {
							productList.add(productDataList.get(a).getProductId());
							StockEntity stockEntity = new StockEntity();
							stockEntity.setPrice(
									productDataList.get(a).getPrice().getIndPrice().getDealPrice().intValue());
							stockEntity.setProductDescription(productDataList.get(a).getProductDescription());
							stockEntity.setProductImage(productImages);
							stockEntity.setSize(sohData.get(b).getSizeType());
							stockEntity.setStock(sohData.get(b).getNotify());
							stockEntity.setDesignerName(
									designerProfileRepo.findBydesignerId(designerList.get(i).longValue()).get()
											.getDesignerProfile().getFirstName1());
							entity.setDesignerName(designerProfileRepo.findBydesignerId(designerList.get(i).longValue())
									.get().getDesignerProfile().getFirstName1());
							stockEntity.setProductLink(productDataList.get(a).getProductId().toString());
							stockList.add(stockEntity);
						}
					}
				}
				if (!productList.isEmpty()) {

					data.put(designerProfileRepo.findBydesignerId(designerList.get(i).longValue()).get()
							.getDesignerProfile().getEmail(), stockList);
				}
			}
			Map<String, Object> data3 = new HashMap<String, Object>();
			data3.put("data4", entity);
			Map<String, Object> data2 = new HashMap<String, Object>();
			for (int i = 0; i < emails.size(); i++) {
				if (data.containsKey(emails.get(i))) {
					List<StockEntity> productDetails = data.get(emails.get(i));
					data2.put("data", productDetails);
					Context context = new Context();
					context.setVariables(data2);
					context.setVariables(data3);
					String htmlContent = templateEngine.process("lowStockEmailTemplate", context);
					EmailSenderThread emailSenderThread = new EmailSenderThread(emails.get(i), "Product out of stock",
							htmlContent, true, null, restTemplate);
					emailSenderThread.start();
				}
			}
			return new GlobalResponce("Success", "Designer informed successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce stockRecovereService(OrderSKUDetailsEntity orderDetails) {
		try {
			ProductMasterEntity productMasterEntity = productRepo.findById(orderDetails.getProductId()).get();
			List<StandardSOH> standeredSOH = productMasterEntity.getStanderedSOH();
			List<StandardSOH> recoveredSOH = new ArrayList<StandardSOH>();
			for (int i = 0; i < standeredSOH.size(); i++) {
				if (standeredSOH.get(i).getSizeType().equals(orderDetails.getSize())) {
					StandardSOH standardSOH = new StandardSOH();
					standardSOH.setNotify(standeredSOH.get(i).getNotify());
					standardSOH.setOos(standeredSOH.get(i).getOos());
					standardSOH.setSizeType(standeredSOH.get(i).getSizeType());
					standardSOH.setSoh(standeredSOH.get(i).getSoh() + orderDetails.getUnits().intValue());
					recoveredSOH.add(standardSOH);
				}
			}
			productMasterEntity.setProductId(orderDetails.getProductId());
			productMasterEntity.setStanderedSOH(recoveredSOH);
			productRepo.save(productMasterEntity);
			return new GlobalResponce("Successfull", "Stock recovered", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Boolean getColourlist(String colourValue) {

		try {
			LOGGER.info("Inside- ProductService.getColourlist()");
			List<ProductMasterEntity> list = mongoOperations.findAll(ProductMasterEntity.class);

			for (int i = 0; i < list.size(); i++) {

				List<ImagesEntity> entityColour = Arrays.asList(list.get(i).getImages());

				if (entityColour.get(0).getColour().toString().contentEquals("#" + colourValue.toString())) {

					return false;
				}

			}

			return true;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public List<ProductMasterEntity> productSearching(String searchBy, String designerId, String categoryId,
			String subCategoryId, String colour, Boolean cod, Boolean customization, String priceType,
			Boolean returnStatus, String maxPrice, String minPrice, String size, Boolean giftWrap, String searchKey) {

		try {
			LOGGER.info("Inside ProductServiceImpl.productSearching()");
			LOGGER.info("priceType = {}", priceType);
			LOGGER.info("subCategoryId = {}", subCategoryId);

			return !searchKey.equals("") ? productRepo.findbySearchKey(searchKey)
					: productRepo.findAll().stream()
							.filter(product -> !searchBy.equals("") ? product.getGender().equals(searchBy) : true)
							.filter(product -> cod != null ? product.getCod() == cod : true)
							.filter(product -> customization != null ? product.getCustomization() == customization
									: true)
							.filter(product -> !priceType.equals("") ? product.getPriceType().equals(priceType) : true)
							.filter(product -> giftWrap != null ? product.getGiftWrap() == giftWrap : true)
							.filter(product -> !maxPrice.equals("-1") ? product.getPrice().getIndPrice()
									.getMrp() <= Long.parseLong(maxPrice) : true)
							.filter(product -> !minPrice.equals("-1")
									? product.getPrice().getIndPrice().getMrp() >= Long.parseLong(minPrice)
									: true)
							.filter(product -> !size.equals("") ? product.getStanderedSOH().stream()
									.anyMatch(soh -> Arrays.asList(size.split(",")).contains(soh.getSizeType())) : true)
							.filter(product -> !colour.equals("")
									? Arrays.asList(colour.split(",")).stream()
											.anyMatch(color -> Arrays.asList(product.getImages()).stream()
													.anyMatch(image -> Optional.ofNullable(image.getColour())
															.filter(image1 -> image1.equals("#" + color)).isPresent()))
									: true)
							.filter(product -> !categoryId.equals("") ? Arrays.asList(categoryId.split(",")).stream()
									.anyMatch(category -> category.equals(product.getCategoryId().toString()))
									: true)
							.filter(product -> !subCategoryId.equals("")
									? Arrays.asList(subCategoryId.split(",")).stream()
											.anyMatch(subCategory -> subCategory
													.equals(product.getSubCategoryId().toString()))
									: true)
							.filter(product -> !designerId.equals("") ? Arrays.asList(designerId.split(",")).stream()
									.anyMatch(dId -> dId.equals(product.getDesignerId().toString())) : true)
							.collect(Collectors.toList());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
