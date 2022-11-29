package com.divatt.designer.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.CategoryEntity;
import com.divatt.designer.entity.SubCategoryEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.response.GlobalResponce;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;

@Service
public class ProductServiceImp2 implements ProductService2 {
	@Autowired
	private ProductRepo2 productRepo2;

	@Autowired
	private CustomFunction customFunction;

	@Autowired
	private SequenceGenerator sequenceGenarator;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp2.class);

	@Override
	public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2) {
		try {
			LOGGER.info("Inside-ProductServiceImp2.addProductMasterData()");
			productRepo2.save(customFunction.addProductMasterData(productMasterEntity2));
			return new GlobalResponce("Success", "Product added successfully", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

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

	@Override
	public Map<String, Object> getAllProduct(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int Count = (int) productRepo2.count();
			Pageable pageable = null;
			if (limit == 0) {
				limit = Count;
			}

			if (sort.equals("ASC")) {
				pageable = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pageable = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ProductMasterEntity2> findall = null;

			if (keyword.isEmpty()) {
				findall = productRepo2.findByIsDeleted(isDeleted, pageable);
			} else {
				findall = productRepo2.findByKeyword(keyword, isDeleted, pageable);
			}
			int totalPage = findall.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}
			Map<String, Object> response = new HashMap<>();
			response.put("data", findall.getContent());
			response.put("currentPage", findall.getNumber());
			response.put("total", findall.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findall.getSize());
			response.put("perPageElement", findall.getNumberOfElements());

			if (findall.getSize() < 1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ProductMasterEntity2 getProduct(Integer productId) {
		try {
			ProductMasterEntity2 productMasterEntity2 = productRepo2.findById(productId).get();
			LOGGER.info("Product data by product ID = {}", productMasterEntity2);
			DesignerProfileEntity designerProfileEntity = designerProfileRepo
					.findBydesignerId(productMasterEntity2.getDesignerId().longValue()).get();
			ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
					"https://localhost:8084/dev/subcategory/view/" + productMasterEntity2.getSubCategoryId(),
					SubCategoryEntity.class);
			ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
					"https://localhost:8084/dev/category/view/" + productMasterEntity2.getCategoryId(),
					CategoryEntity.class);
			productMasterEntity2.setSubCategoryName(subCatagory.getBody().getCategoryName());
			productMasterEntity2.setCategoryName(catagory.getBody().getCategoryName());
			productMasterEntity2.setDesignerProfile(designerProfileEntity.getDesignerProfile());
			return productMasterEntity2;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getProductDetailsallStatus(String adminStatus, int page, int limit, String sort,
			String sortName, Boolean isDeleted, String keyword, Optional<String> sortBy) {
		try {
			int Count = (int) productRepo2.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = Count;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ProductMasterEntity2> findAll = null;
			Integer all = 0;
			Integer pending = 0;
			Integer approved = 0;
			Integer rejected = 0;

			all = productRepo2.countByIsDeleted(isDeleted);
			LOGGER.info("Behind all" + all);
			pending = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Pending");
			approved = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Approved");
			rejected = productRepo2.countByIsDeletedAndAdminStatus(isDeleted, "Rejected");

			LOGGER.info(adminStatus);
			if (keyword.isEmpty()) {
				if (adminStatus.equals("all")) {
					LOGGER.info("Behind all");
					findAll = productRepo2.findByIsDeleted(isDeleted, pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("pending")) {
					LOGGER.info("Behind pending");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Pending", pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("approved")) {
					LOGGER.info("Behind approved");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("rejected")) {
					LOGGER.info("Behind rejected");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Rejected", pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				}
			} else {
				if (adminStatus.equals("all")) {
					LOGGER.info("Behind into else all");
					findAll = productRepo2.SearchAndfindByIsDeleted(keyword, isDeleted, pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("pending")) {
					LOGGER.info("Behind into else pending");
					findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Pending",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("approved")) {
					LOGGER.info("Behind into else approved");
					findAll = productRepo2.SearchAppAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
				} else if (adminStatus.equals("rejected")) {
					LOGGER.info("Behind into else rejected");
					findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Rejected",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/subcategory/view/" + catagoryData.getSubCategoryId(),
								SubCategoryEntity.class);
						ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
								"https://localhost:8084/dev/category/view/" + catagoryData.getCategoryId(),
								CategoryEntity.class);
						catagoryData.setCategoryName(catagory.getBody().getCategoryName());
						catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
					});
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

			if (findAll.getSize() < 1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> getDesignerProductByDesignerId(Integer designerId, String adminStatus, Boolean isActive,
			int page, int limit, String sort, String sortName, Boolean isDeleted, String keyword,
			Optional<String> sortBy, String sortDateType) {
		try {
			int Count = (int) productRepo2.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = Count;
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

			Page<ProductMasterEntity2> findAll = null;
			Integer live = 0;
			Integer pending = 0;
			Integer reject = 0;
			Integer ls = 0;
			Integer oos = 0;

			live = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, isActive,
					"Approved");
			LOGGER.info("Behind live " + live);
			pending = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
					isActive, "Pending");
			LOGGER.info("Behind Pending " + pending);
			reject = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
					isActive, "Rejected");
			LOGGER.info("Behind Reject " + reject);
			oos = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, false,
					"Approved");
			if (keyword.isEmpty()) {
				if (adminStatus.equals("live")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Approved", isActive, pagingSort);
				} else if (adminStatus.equals("pending")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Pending", isActive, pagingSort);
				} else if (adminStatus.equals("reject")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Rejected", isActive, pagingSort);
				} else if (adminStatus.equals("ls")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"ls", isActive, pagingSort);
				} else if (adminStatus.equals("oos")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Approved", false, pagingSort);
				}
			} else {
				if (adminStatus.equals("live")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Approved", pagingSort);
				} else if (adminStatus.equals("pending")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Pending", pagingSort);
				} else if (adminStatus.equals("reject")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Reject", pagingSort);
				} else if (adminStatus.equals("ls")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"notify", pagingSort);
				} else if (adminStatus.equals("oos")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatusForOos(keyword, isDeleted, designerId,
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

			if (findAll.getSize() < 1) {
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponce productDeleteByproductId(Integer productId) {
		try {
			LOGGER.info("Inside - ProductServiceImp2.productDeleteByproductId()");
			if (productRepo2.existsById(productId)) {
				Boolean isDelete = false;
				Optional<ProductMasterEntity2> productData = productRepo2.findById(productId);
				ProductMasterEntity2 productMasterEntity2 = productData.get();
				if (productMasterEntity2.getIsDeleted().equals(false)) {
					isDelete = true;
				} else {
					return new GlobalResponce("Bad request!!", "Product allready deleted", 400);
				}
				productMasterEntity2.setIsDeleted(isDelete);
				productMasterEntity2.setUpdatedBy(productMasterEntity2.getDesignerId().toString());
				productMasterEntity2.setUpdatedOn(new Date());
				productRepo2.save(productMasterEntity2);
				return new GlobalResponce("Success", "Deleted successfully", 200);
			} else {
				return new GlobalResponce("Bad request", "Product does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public GlobalResponce adminApprovalUpdate(Integer productId, ProductMasterEntity2 entity2) {
		try {
			entity2.setProductId(productId);
			productRepo2.save(entity2);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		return new GlobalResponce("Sucess", "Product Approved", 200);
	}

	@Override
	public GlobalResponce changeAdminStatus(Integer productId) {
		try {
			LOGGER.info("Inside - ProductServiceImpl.changeStatus()");
			if (productRepo2.existsById(productId)) {
				Boolean adminStatus;
				Optional<ProductMasterEntity2> productData = productRepo2.findById(productId);
				ProductMasterEntity2 productEntity = productData.get();
				if (productEntity.getIsActive().equals(true)) {
					adminStatus = false;
					productEntity.setIsActive(adminStatus);
					productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
					productEntity.setUpdatedOn(new Date());
					productRepo2.save(productEntity);
					return new GlobalResponce("Success", "Status Inactive successfully", 200);
				} else {
					adminStatus = true;
					productEntity.setIsActive(adminStatus);
					productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
					productEntity.setUpdatedOn(new Date());
					productRepo2.save(productEntity);
					return new GlobalResponce("Success", "Status Active successfully", 200);
				}

			} else {
				return new GlobalResponce("Bad request", "Product does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> productListUser() {
		try {
			long count = sequenceGenarator.getCurrentSequence(ProductMasterEntity2.SEQUENCE_NAME);
			Random random = new Random();
			List<ProductMasterEntity2> findall = productRepo2.findByIsDeletedAndAdminStatusAndIsActive(false,
					"Approved", true);
			findall.forEach(designerdat -> {
				DesignerProfileEntity designerProfileEntity = designerProfileRepo
						.findBydesignerId(Long.parseLong(designerdat.getDesignerId().toString())).get();
				designerdat.setDesignerProfile(designerProfileEntity.getDesignerProfile());
			});
			if (findall.size() <= 15) {
				return ResponseEntity.ok(findall);
			}
			List<ProductMasterEntity2> productMasterEntity2 = new ArrayList<>();
			Boolean check = true;
			while (check) {
				int nextInt = random.nextInt((int) count);
				for (ProductMasterEntity2 prod : findall) {
					if (prod.getProductId() == nextInt) {
						productMasterEntity2.add(prod);
					}
					if (productMasterEntity2.size() > 14)
						check = false;
				}
			}
			return ResponseEntity.ok(productMasterEntity2);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> allWishlistProductData(List<Integer> productIdList, Optional<String> sortBy, int page,
			String sort, String sortName, Boolean isDeleted, int limit) {
		try {
			LOGGER.info("inside - ProductServiceImp2.allWishlistProductData()");
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
				List<ProductMasterEntity2> getProductByLiatOfProductId = productRepo2.findByProductIdIn(productIdList);
				int CountData = (int) getProductByLiatOfProductId.size();
				if (limit == 0) {
					limit = CountData;
				}
				Pageable pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				Page<ProductMasterEntity2> findByProductIdIn = productRepo2.findByProductIdIn(productIdList,
						pagingSort);
//				List<ProductMasterEntity2> findByProductIdIn1 = new ArrayList<>();
				findByProductIdIn.getContent().forEach(productData -> {
					ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
							"https://localhost:8084/dev/subcategory/view/" + productData.getSubCategoryId(),
							SubCategoryEntity.class);
					ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
							"https://localhost:8084/dev/category/view/" + productData.getCategoryId(),
							CategoryEntity.class);
					DesignerProfileEntity designerProfileEntity = designerProfileRepo
							.findBydesignerId(Long.parseLong(productData.getDesignerId().toString())).get();
					productData.setDesignerProfile(designerProfileEntity.getDesignerProfile());
					productData.setCategoryName(catagory.getBody().getCategoryName());
					productData.setSubCategoryName(subCatagory.getBody().getCategoryName());
				});
				int totalPage = findByProductIdIn.getTotalPages() - 1;
				if (totalPage < 0) {
					totalPage = 0;
				}
				Map<String, Object> response = new HashMap<>();
				response.put("data", findByProductIdIn.getContent());
				response.put("currentPage", findByProductIdIn.getNumber());
				response.put("total", CountData);
				response.put("totalPage", totalPage);
				response.put("perPage", findByProductIdIn.getSize());
				response.put("perPageElement", findByProductIdIn.getNumberOfElements());
				if (findByProductIdIn.getSize() <= 0) {
					throw new CustomException("Product not found!");
				} else {
					return response;
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
//		return null;
	}

	@Override
	public ResponseEntity<?> allCartProductData(List<Integer> productIdList) {
		try {
			LOGGER.info("Inside - ProductServiceImp2.allCartProductData()");
			if (productIdList.isEmpty()) {
				throw new CustomException("Product not found!");
			} else {
				List<ProductMasterEntity2> getProductByLiatOfProductId = productRepo2.findByProductIdIn(productIdList);
				if (getProductByLiatOfProductId.size() <= 0) {
					throw new CustomException("Product not found!");
				} else {
					return ResponseEntity.ok(getProductByLiatOfProductId);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<ProductMasterEntity2> productSearching(String searchBy, String designerId, String categoryId,
			String subCategoryId, String colour, Boolean cod, Boolean customization, String priceType,
			Boolean returnStatus, String maxPrice, String minPrice, String size, Boolean giftWrap, String searchKey) {

		try {
			LOGGER.info("Inside ProductServiceImpl.productSearching()");
			LOGGER.info("priceType = {}", priceType);
			LOGGER.info("subCategoryId = {}", subCategoryId);

			return !searchKey.equals("") ? productRepo2.findbySearchKey(searchKey)
					: productRepo2.findAll().stream().filter(product -> cod != null ? product.getCod() == cod : true)
							.filter(product -> customization != null ? product.getWithCustomization() == customization
									: true)
							.filter(product -> !priceType.equals("") ? product.getPriceType().equals(priceType) : true)
							.filter(product -> giftWrap != null ? product.getWithGiftWrap() == giftWrap : true)
							.filter(product -> !maxPrice.equals("-1") ? product.getMrp() <= Long.parseLong(maxPrice)
									: true)
							.filter(product -> !minPrice.equals("-1") ? product.getMrp() >= Long.parseLong(minPrice)
									: true)
							.filter(product -> !size.equals("") 
									? Arrays.asList(size.split(",")).stream()
											.anyMatch(s -> product.getSizes().stream()
													.anyMatch(sizee -> sizee.equals(s))) 
									: true)
//							.filter(product -> !colour.equals("")
//									? Arrays.asList(colour.split(",")).stream()
//											.anyMatch(color -> Arrays.asList(product.getImages()).stream()
//													.anyMatch(image -> Optional.ofNullable(image.getLarge())
//															.filter(image1 -> image1.equals("#" + color)).isPresent()))
//									: true)
							.filter(product -> !colour.equals("")
									? Arrays.asList(colour.split(",")).stream().anyMatch(
											color -> product.getColour().equals(color))
									: true)
							.filter(product -> !categoryId.equals("")
									? Arrays.asList(categoryId.split(",")).stream()
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
