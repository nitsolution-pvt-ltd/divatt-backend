package com.divatt.designer.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.constant.RestTemplateConstant;
import com.divatt.designer.entity.CategoryEntity;
import com.divatt.designer.entity.EmailEntity;
import com.divatt.designer.entity.LoginEntity;
import com.divatt.designer.entity.SubCategoryEntity;
import com.divatt.designer.entity.UserProfile;
import com.divatt.designer.entity.UserProfileInfo;
import com.divatt.designer.entity.product.ImageEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.entity.profile.DesignerProfile;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.helper.CustomFunction;
import com.divatt.designer.helper.EmailSenderThread;
import com.divatt.designer.repo.DesignerProfileRepo;
import com.divatt.designer.repo.ProductRepo2;
import com.divatt.designer.response.GlobalResponce;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImp2 implements ProductService2 {
	@Autowired
	private ProductRepo2 productRepo2;

	@Autowired
	private CustomFunction customFunction;

	@Autowired
	private SequenceGenerator sequenceGenarator;

//	@Autowired
//	private MongoOperations mongoOperations;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private MongoOperations mongoOperations;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp2.class);

	@Override
	public GlobalResponce addProductData(ProductMasterEntity2 productMasterEntity2) {
		try {
			LOGGER.info("Inside-ProductServiceImp2.addProductMasterData()");
			ProductMasterEntity2 entity2 = productRepo2.save(customFunction.addProductMasterData(productMasterEntity2));
			productMasterEntity2.getProductDetails().getProductName();
			List<UserProfileInfo> userInfoList = new ArrayList<UserProfileInfo>();
			List<Long> userId = new ArrayList<Long>();

			ResponseEntity<String> forEntity = restTemplate.getForEntity(
					RestTemplateConstant.USER_FOLLOWEDUSERLIST.getMessage() + entity2.getDesignerId(), String.class);
			String data = forEntity.getBody();
			JSONArray jsonArray = new JSONArray(data);
			String designerImageData = designerProfileRepo.findBydesignerId(entity2.getDesignerId().longValue()).get()
					.getDesignerProfile().getProfilePic();
			jsonArray.forEach(array -> {
				ObjectMapper objectMapper = new ObjectMapper();
				UserProfile readValue;
				try {
					readValue = objectMapper.readValue(array.toString(), UserProfile.class);
					ResponseEntity<UserProfileInfo> userInfo = restTemplate.getForEntity(
							RestTemplateConstant.USER_GET_USER_ID.getMessage() + readValue.getUserId(),
							UserProfileInfo.class);
					userInfoList.add(userInfo.getBody());
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

			});
			userId.forEach(user -> {
				restTemplate.getForEntity(RestTemplateConstant.INFO_USER.getMessage() + user, UserProfileInfo.class);

			});
			Integer productId = entity2.getProductId();
			ProductMasterEntity2 productMasterEntity = productRepo2.findById(productId).get();
			LOGGER.info(productMasterEntity + "inside");
			DesignerProfileEntity designerProfile = designerProfileRepo
					.findBydesignerId(productMasterEntity.getDesignerId().longValue()).get();
			ImageEntity[] images = productMasterEntity.getImages();
			String image1 = images[0].getLarge();
			System.out.println(images[0].getLarge());
			Map<String, Object> data2 = new HashMap<String, Object>();
			userInfoList.forEach(user -> {
				EmailEntity emailEntity = new EmailEntity();
				emailEntity.setProductDesc(productMasterEntity.getProductDetails().getProductDescription());
				emailEntity.setProductDesignerName(designerProfile.getDesignerProfile().getDisplayName());
				emailEntity.setProductImage(image1);
				emailEntity.setProductName(productMasterEntity.getProductDetails().getProductName());
				if (productMasterEntity.getDeal().getDealType().equals("None")) {
					emailEntity.setProducyDiscount("No discount");
				} else if (productMasterEntity.getDeal().getDealType().equals("Flat")) {
					emailEntity.setProducyDiscount("Rs." + productMasterEntity.getDeal().getDealValue().toString());

				} else {
					emailEntity.setProducyDiscount(productMasterEntity.getDeal().getDealValue().toString() + "%");
				}
				emailEntity.setProductPrice(productMasterEntity.getMrp().toString());
				emailEntity.setUserName(user.getFirstName());
				emailEntity.setProductId(productMasterEntity.getProductId().toString());
				emailEntity.setDesignerImage(designerImageData);
				emailEntity.setUserName(user.getFirstName());
				System.out.println(user.getEmail());
				data2.put("data", emailEntity);
				Context context = new Context();
				context.setVariables(data2);
				String htmlContent = templateEngine.process("emailTemplate", context);
				EmailSenderThread emailSenderThread = new EmailSenderThread(user.getEmail(), "New product Arrived",
						htmlContent, true, null, restTemplate);
				emailSenderThread.start();
			});

			return new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.PRODUCT_ADDED_SUCCESSFULLY.getMessage(), 200);
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
				try {
					LoginEntity forEntity = restTemplate.getForEntity(RestTemplateConstant.ADMIN_ROLE_NAME.getMessage()
							+ MessageConstant.ADMIN_ROLES.getMessage(), LoginEntity.class).getBody();
					String email2 = forEntity.getEmail();
					Integer designerId = productMasterEntity2.getDesignerId();
					DesignerProfileEntity findBydesignerId = designerProfileRepo
							.findBydesignerId(designerId.longValue()).get();
					String email = findBydesignerId.getDesignerProfile().getEmail();
					String designerName = findBydesignerId.getDesignerName();
					Context context = new Context();
					context.setVariable("designerName", designerName);
					String htmlContent = templateEngine.process("productUpdate.html", context);
					EmailSenderThread emailSenderThread = new EmailSenderThread(email, "Product updated", htmlContent,
							true, null, restTemplate);
					emailSenderThread.start();
					String htmlContent1 = templateEngine.process("productUpdateAdmin.html", context);
					EmailSenderThread emailSenderThread1 = new EmailSenderThread(email2, "Product updated",
							htmlContent1, true, null, restTemplate);
					emailSenderThread1.start();

				} catch (Exception e) {
					throw new CustomException(e.getMessage());
				}

				return new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.PRODUCT_UPDATED.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
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
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ProductMasterEntity2 getProduct(Integer productId, Long designerId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("designerId").is(designerId));
			List<ProductMasterEntity2> find = mongoOperations.find(query, ProductMasterEntity2.class);
			List<ProductMasterEntity2> list = new ArrayList<>();
			for (ProductMasterEntity2 data : find) {
				if (data.getProductId().equals(productId)) {
					list.add(data);
				}
			}
			if (list.size() > 0) {
				ProductMasterEntity2 productMasterEntity2 = list.get(0);
				LOGGER.info("Product data by product ID = {}", productMasterEntity2);
				DesignerProfileEntity designerProfileEntity = designerProfileRepo
						.findBydesignerId(productMasterEntity2.getDesignerId().longValue()).get();
				ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
						RestTemplateConstant.SUBCATEGORY_VIEW.getMessage() + productMasterEntity2.getSubCategoryId(),
						SubCategoryEntity.class);
				LOGGER.info("RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()"
						+ RestTemplateConstant.SUBCATEGORY_VIEW.getMessage());
				ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
						RestTemplateConstant.CATEGORY_VIEW.getMessage() + productMasterEntity2.getCategoryId(),
						CategoryEntity.class);
//			LOGGER.info("RestTemplateConstant.CATEGORY_VIEW.getMessage()"+RestTemplateConstant.CATEGORY_VIEW.getMessage());
//			LOGGER.info("MessageConstant.PRODUCT_NOT_FOUND.getMessage()"+MessageConstant.PRODUCT_NOT_FOUND.getMessage());
//			LOGGER.info("MessageConstant.SUCCESS.getMessage()"+MessageConstant.SUCCESS.getMessage());
//			LOGGER.info("MessageConstant.BAD_REQUEST.getMessage()"+MessageConstant.BAD_REQUEST.getMessage());
//			LOGGER.info("MessageConstant.DELETED.getMessage()"+MessageConstant.DELETED.getMessage());
//			LOGGER.info("RestTemplateConstant.USER_FOLLOWEDUSERLIST.getMessage()"+RestTemplateConstant.USER_FOLLOWEDUSERLIST.getMessage());
//			LOGGER.info("RestTemplateConstant.USER_GET_USER_ID.getMessage()"+RestTemplateConstant.USER_GET_USER_ID.getMessage());

				productMasterEntity2.setSubCategoryName(subCatagory.getBody().getCategoryName());
				productMasterEntity2.setCategoryName(catagory.getBody().getCategoryName());
				productMasterEntity2.setDesignerProfile(designerProfileEntity.getDesignerProfile());
				return productMasterEntity2;
			} else
				throw new CustomException("Product not found");
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
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("pending")) {
					LOGGER.info("Behind pending");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Pending", pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("approved")) {
					LOGGER.info("Behind approved");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Approved", pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("rejected")) {
					LOGGER.info("Behind rejected");
					findAll = productRepo2.findByIsDeletedAndAdminStatus(isDeleted, "Rejected", pagingSort);
					LOGGER.info("inside findall getContent" + findAll.getContent());
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
			} else {
				if (adminStatus.equals("all")) {
					LOGGER.info("Behind into else all");
					findAll = productRepo2.SearchAndfindByIsDeleted(keyword, isDeleted, pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("pending")) {
					LOGGER.info("Behind into else pending");
					findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Pending",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("approved")) {
					LOGGER.info("Behind into else approved");
					findAll = productRepo2.SearchAppAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Approved",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				} else if (adminStatus.equals("rejected")) {
					LOGGER.info("Behind into else rejected");
					findAll = productRepo2.SearchAndfindByIsDeletedAndAdminStatus(keyword, isDeleted, "Rejected",
							pagingSort);
					findAll.getContent().forEach(catagoryData -> {
						try {
							ResponseEntity<SubCategoryEntity> subCatagory = restTemplate
									.getForEntity(RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()
											+ catagoryData.getSubCategoryId(), SubCategoryEntity.class);
							ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
									RestTemplateConstant.CATEGORY_VIEW.getMessage() + catagoryData.getCategoryId(),
									CategoryEntity.class);
							DesignerProfile designerProfile = new DesignerProfile();
							DesignerProfileEntity forEntity = restTemplate.getForEntity(
									RestTemplateConstant.DESIGNER_BY_ID.getMessage() + catagoryData.getDesignerId(),
									DesignerProfileEntity.class).getBody();
							LOGGER.info(forEntity + "Inside Forentity");
							designerProfile.setAltMobileNo(forEntity.getDesignerProfile().getAltMobileNo());
							designerProfile.setCity(forEntity.getDesignerProfile().getCity());
							designerProfile.setCountry(forEntity.getDesignerProfile().getCountry());
							designerProfile.setDesignerCategory(forEntity.getDesignerProfile().getDesignerCategory());
							designerProfile.setDigitalSignature(forEntity.getDesignerProfile().getDigitalSignature());
							designerProfile.setDisplayName(forEntity.getDesignerProfile().getDisplayName());
							designerProfile.setDob(forEntity.getDesignerProfile().getDob());
							designerProfile.setEmail(forEntity.getDesignerProfile().getEmail());
							designerProfile.setFirstName1(forEntity.getDesignerProfile().getFirstName1());
							designerProfile.setFirstName2(forEntity.getDesignerProfile().getFirstName2());
							designerProfile.setGender(forEntity.getDesignerProfile().getGender());
							designerProfile.setLastName1(forEntity.getDesignerProfile().getLastName1());
							designerProfile.setLastName2(forEntity.getDesignerProfile().getLastName2());
							designerProfile.setMobileNo(forEntity.getDesignerProfile().getMobileNo());
							designerProfile.setPassword(forEntity.getDesignerProfile().getPassword());
							designerProfile.setPinCode(forEntity.getDesignerProfile().getPinCode());
							designerProfile.setProfilePic(forEntity.getDesignerProfile().getProfilePic());
							designerProfile.setState(forEntity.getDesignerProfile().getState());
							catagoryData.setDesignerProfile(designerProfile);
							catagoryData.setCategoryName(catagory.getBody().getCategoryName());
							catagoryData.setSubCategoryName(subCatagory.getBody().getCategoryName());
						} catch (Exception e) {
							e.printStackTrace();
						}
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
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
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

			live = productRepo2
					.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(false, designerId, "Approved", true).stream()
					.filter(e -> e.getSoh() > 0).collect(Collectors.toList()).size();
			LOGGER.info("Behind live " + live);
			pending = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
					isActive, "Pending");
			LOGGER.info("Behind Pending " + pending);
			reject = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId,
					isActive, "Rejected");
			LOGGER.info("Behind Reject " + reject);
			oos = productRepo2.countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(isDeleted, designerId, false,
					"Approved");

			ls = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(false, designerId, "Approved", true)
					.stream().filter(e -> e.getSoh() == e.getNotify() || e.getSoh() <= e.getNotify())
					.collect(Collectors.toList()).size();

			if (keyword.isEmpty()) {
				if (adminStatus.equals("live")) {
					List<ProductMasterEntity2> collect = productRepo2
							.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId, "Approved",
									isActive, pagingSort)
							.stream().filter(e -> e.getSoh() > 0).collect(Collectors.toList());
					findAll = new PageImpl<>(collect, pagingSort, collect.size());
				} else if (adminStatus.equals("pending")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Pending", isActive, pagingSort);
				} else if (adminStatus.equals("reject")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Rejected", isActive, pagingSort);
				} else if (adminStatus.equals("ls")) {
					List<ProductMasterEntity2> data = productRepo2
							.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(false, designerId, "Approved", true);
					List<ProductMasterEntity2> filter = data.stream()
							.filter(e -> e.getSoh() == e.getNotify() || e.getSoh() <= e.getNotify())
							.collect(Collectors.toList());
					int startOfPage = pagingSort.getPageNumber() * pagingSort.getPageSize();
					int endOfPage = Math.min(startOfPage + pagingSort.getPageSize(), filter.size());

					List<ProductMasterEntity2> subList = startOfPage >= endOfPage ? new ArrayList<>()
							: filter.subList(startOfPage, endOfPage);
					findAll = new PageImpl<ProductMasterEntity2>(subList, pagingSort, filter.size());
					// findAll = new PageImpl<>(filter, pagingSort, filter.size());
				} else if (adminStatus.equals("oos")) {
					findAll = productRepo2.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId,
							"Approved", false, pagingSort);
				}
			} else {
				if (adminStatus.equals("live")) {
					List<ProductMasterEntity2> collect = productRepo2
							.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(isDeleted, designerId, "Approved",
									isActive, pagingSort)
							.stream().filter(e -> e.getSoh() > 0).collect(Collectors.toList());
					findAll = new PageImpl<>(collect, pagingSort, collect.size());
				} else if (adminStatus.equals("pending")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Pending", pagingSort);
				} else if (adminStatus.equals("reject")) {
					findAll = productRepo2.listDesignerProductsearchByAdminStatus(keyword, isDeleted, designerId,
							"Reject", pagingSort);
				} else if (adminStatus.equals("ls")) {
					List<ProductMasterEntity2> data = productRepo2
							.findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(false, designerId, "Approved", true);
					List<ProductMasterEntity2> filter = data.stream()
							.filter(e -> e.getSoh() == e.getNotify() || e.getSoh() <= e.getNotify())
							.collect(Collectors.toList());
					int startOfPage = pagingSort.getPageNumber() * pagingSort.getPageSize();
					int endOfPage = Math.min(startOfPage + pagingSort.getPageSize(), filter.size());

					List<ProductMasterEntity2> subList = startOfPage >= endOfPage ? new ArrayList<>()
							: filter.subList(startOfPage, endOfPage);
					findAll = new PageImpl<ProductMasterEntity2>(subList, pagingSort, filter.size());
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
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (

		Exception e) {
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
					return new GlobalResponce(MessageConstant.BAD_REQUEST.getMessage(),
							MessageConstant.ALREADY_DELETED.getMessage(), 400);
				}
				productMasterEntity2.setIsDeleted(isDelete);
				productMasterEntity2.setUpdatedBy(productMasterEntity2.getDesignerId().toString());
				productMasterEntity2.setUpdatedOn(new Date());
				productRepo2.save(productMasterEntity2);
				return new GlobalResponce(MessageConstant.SUCCESS.getMessage(), MessageConstant.DELETED.getMessage(),
						200);
			} else {
				return new GlobalResponce(MessageConstant.BAD_REQUEST.getMessage(),
						MessageConstant.PRODUCT_NOT_FOUND.getMessage(), 400);
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
		return new GlobalResponce(MessageConstant.SUCCESS.getMessage(), MessageConstant.PRODUCT_APPROVED.getMessage(),
				200);
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
					return new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
							MessageConstant.STATUS_INACTIVATED.getMessage(), 200);
				} else {
					adminStatus = true;
					productEntity.setIsActive(adminStatus);
					productEntity.setUpdatedBy(productEntity.getDesignerId().toString());
					productEntity.setUpdatedOn(new Date());
					productRepo2.save(productEntity);
					return new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
							MessageConstant.STATUS_ACTIVATED.getMessage(), 200);
				}

			} else {
				return new GlobalResponce(MessageConstant.BAD_REQUEST.getMessage(),
						MessageConstant.PRODUCT_NOT_FOUND.getMessage(), 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> productListUser() {

		try {
			long count = sequenceGenarator.getCurrentSequence(ProductMasterEntity2.SEQUENCE_NAME);
			Random random = new Random();
			List<ProductMasterEntity2> findall = new ArrayList<>();

			List<DesignerProfileEntity> findByDesignerByCurrentStatus = designerProfileRepo
					.findByDesignerCurrentStatus("Online");
			findByDesignerByCurrentStatus.forEach(designerRow -> {
				List<ProductMasterEntity2> findProduct = productRepo2
						.findByIsDeletedAndAdminStatusAndIsActiveAndDesignerId(false, "Approved", true,
								designerRow.getDesignerId());
				findall.addAll(findProduct);
			});

			findall.forEach(designerdat -> {
				Optional<DesignerProfileEntity> designerProfileEntity = designerProfileRepo
						.findBydesignerIdAndDesignerCurrentStatus(
								Long.parseLong(designerdat.getDesignerId().toString()), "Online");
				if (designerProfileEntity.orElse(null) != null) {
					designerdat.setDesignerProfile(designerProfileEntity.get().getDesignerProfile());
				}
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
							RestTemplateConstant.SUBCATEGORY_VIEW.getMessage() + productData.getSubCategoryId(),
							SubCategoryEntity.class);
					ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
							RestTemplateConstant.CATEGORY_VIEW.getMessage() + productData.getCategoryId(),
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
					throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
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
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			} else {
				List<ProductMasterEntity2> getProductByLiatOfProductId = productRepo2.findByProductIdIn(productIdList);
				if (getProductByLiatOfProductId.size() <= 0) {
					throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
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
			Boolean returnStatus, String maxPrice, String minPrice, String size, Boolean giftWrap, String searchKey,
			String sortDateType, String sortPrice, String labelType) {

		try {
			LOGGER.info("Inside ProductServiceImpl.productSearching()");

			List<ProductMasterEntity2> findall = new ArrayList<>();
			List<DesignerProfileEntity> findByDesignerByCurrentStatus = designerProfileRepo
					.findByDesignerCurrentStatus("Online");

			findByDesignerByCurrentStatus.forEach(designerRow -> {
				if (designerRow.getDesignerCurrentStatus().equals("Online")) {
					List<ProductMasterEntity2> findProduct = new ArrayList<>();
					if (!searchKey.equals("")) {
						findProduct = productRepo2.findbySearchKey(searchKey);
					} else {
						findProduct = productRepo2.findByIsDeletedAndAdminStatusAndIsActiveAndDesignerId(false,
								"Approved", true, designerRow.getDesignerId());
					}
					findall.addAll(findProduct);
				}
			});

			return customFunction.filterProduct(findall, searchBy, designerId, categoryId, subCategoryId, colour, cod,
					customization, priceType, returnStatus, maxPrice, minPrice, size, giftWrap, searchKey, sortDateType,
					sortPrice, labelType);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ProductMasterEntity2 getProducts(Integer productId) {
		try {

			List<ProductMasterEntity2> findall = new ArrayList<>();
			List<DesignerProfileEntity> findByDesignerByCurrentStatus = designerProfileRepo
					.findByDesignerCurrentStatus("Online");

			findByDesignerByCurrentStatus.forEach(designerRow -> {
				if (designerRow.getDesignerCurrentStatus().equals("Online")) {
					List<ProductMasterEntity2> findProduct = new ArrayList<>();
					findProduct = productRepo2.findByIsDeletedAndAdminStatusAndIsActiveAndDesignerIdAndProductId(false,
							"Approved", true, designerRow.getDesignerId(), productId);
					findall.addAll(findProduct);
				}
			}); 
			ProductMasterEntity2 productMasterEntity22 = null;
			
			if (findall.size() > 0) {
				productMasterEntity22 = findall.get(0);
				DesignerProfileEntity designerProfileEntity = designerProfileRepo.findBydesignerIdAndDesignerCurrentStatus(
						productMasterEntity22.getDesignerId().longValue(), "Online").get();
	
				ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
						RestTemplateConstant.SUBCATEGORY_VIEW.getMessage() + productMasterEntity22.getSubCategoryId(),
						SubCategoryEntity.class);
				
				ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
						RestTemplateConstant.CATEGORY_VIEW.getMessage() + productMasterEntity22.getCategoryId(),
						CategoryEntity.class);
	
				productMasterEntity22.setSubCategoryName(subCatagory.getBody().getCategoryName());
				productMasterEntity22.setCategoryName(catagory.getBody().getCategoryName());
				productMasterEntity22.setDesignerProfile(designerProfileEntity.getDesignerProfile());
			}
			return productMasterEntity22;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@Override
	public ProductMasterEntity2 getProductsAdmin(Integer productId) {
		try {
			
			List<ProductMasterEntity2> findall = new ArrayList<>();
			List<DesignerProfileEntity> findByDesignerByCurrentStatus = designerProfileRepo.findAll();
			
			findByDesignerByCurrentStatus.forEach(designerRow -> {
					List<ProductMasterEntity2> findProduct = new ArrayList<>();
						findProduct = productRepo2
								.findByIsDeletedAndIsActiveAndDesignerIdAndProductId(false, true,
										designerRow.getDesignerId(),productId);
					findall.addAll(findProduct);
			});
			if(findall.size() <= 0) {
				throw new CustomException("Something went wrong!");
			}
			ProductMasterEntity2 productMasterEntity22 = findall.get(0);
//			ProductMasterEntity2 productMasterEntity2 = productRepo2.findById(productId).get();
			
//			LOGGER.info("Product data by product ID = {}", productMasterEntity2);
			DesignerProfileEntity designerProfileEntity = designerProfileRepo
					.findBydesignerId(productMasterEntity22.getDesignerId().longValue()).get();
			
			ResponseEntity<SubCategoryEntity> subCatagory = restTemplate.getForEntity(
					RestTemplateConstant.SUBCATEGORY_VIEW.getMessage() + productMasterEntity22.getSubCategoryId(),
					SubCategoryEntity.class);
			

			LOGGER.info("RestTemplateConstant.SUBCATEGORY_VIEW.getMessage()"
					+ RestTemplateConstant.SUBCATEGORY_VIEW.getMessage());

			ResponseEntity<CategoryEntity> catagory = restTemplate.getForEntity(
					RestTemplateConstant.CATEGORY_VIEW.getMessage() + productMasterEntity22.getCategoryId(),
					CategoryEntity.class);

			productMasterEntity22.setSubCategoryName(subCatagory.getBody().getCategoryName());
			productMasterEntity22.setCategoryName(catagory.getBody().getCategoryName());
			productMasterEntity22.setDesignerProfile(designerProfileEntity.getDesignerProfile());
			return productMasterEntity22;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
