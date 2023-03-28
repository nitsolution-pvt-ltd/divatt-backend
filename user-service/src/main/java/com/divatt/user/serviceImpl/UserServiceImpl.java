package com.divatt.user.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstants;
import com.divatt.user.entity.ProductCommentEntity;
import com.divatt.user.entity.StateEntity;
import com.divatt.user.entity.UserAddressEntity;
import com.divatt.user.entity.UserCartEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.WishlistEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.entity.product.DesignerProfile;
import com.divatt.user.entity.product.DesignerProfileEntity;
import com.divatt.user.entity.product.ProductMasterEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.OrderSKUDetailsRepo;
import com.divatt.user.repo.ProductCommentRepo;
import com.divatt.user.repo.StateRepo;
import com.divatt.user.repo.UserAddressRepo;
import com.divatt.user.repo.UserCartRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.repo.WishlistRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;

import springfox.documentation.spring.web.json.Json;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private UserCartRepo userCartRepo;

	@Autowired
	private ProductCommentRepo productCommentRepo;

	@Autowired
	private UserDesignerRepo userDesignerRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private UserAddressRepo addressRepo;

	@Autowired
	private TemplateEngine engine;

	@Autowired
	private OrderSKUDetailsRepo orderSKUDetailsRepo;

	@Autowired
	private OrderDetailsRepo detailsRepo;
	
	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USERS}")
	private String USER_SERVICE;

	
	@Override
	public GlobalResponse postWishlistService(ArrayList<WishlistEntity> wishlistEntity) {

		LOGGER.info("Inside - UserServiceImpl.postWishlistService()");

		try {
			WishlistEntity filterCatDetails = new WishlistEntity();

			for (WishlistEntity getRow : wishlistEntity) {
				Optional<WishlistEntity> findByCategoryName = wishlistRepo
						.findByProductIdAndUserId(getRow.getProductId(), getRow.getUserId());
				if (wishlistEntity.size() <= 1 && findByCategoryName.isPresent()) {
					throw new CustomException(MessageConstant.WISHLIST_ALREADY_EXIST.getMessage());
				}
				if (!findByCategoryName.isPresent() && !getRow.getUserId().equals(null)
						&& !getRow.getProductId().equals(null)) {
					filterCatDetails.setId(sequenceGenerator.getNextSequence(WishlistEntity.SEQUENCE_NAME));
					filterCatDetails.setUserId(getRow.getUserId());
					filterCatDetails.setProductId(getRow.getProductId());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					String format = formatter.format(date);
					filterCatDetails.setAddedOn(new SimpleDateFormat("yyyy/MM/dd").parse(format));
					wishlistRepo.save(filterCatDetails);
				}
			}

			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.WISHLIST_ADDED_SUCESSFULLY.getMessage(), 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public GlobalResponse deleteWishlistService(Integer productId, Integer userId) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findByProductIdAndUserId(productId, userId);
			if (!findByProductRow.isPresent()) {
				throw new CustomException(MessageConstant.PRODUCT_NOT_EXIST.getMessage());
			} else {
				wishlistRepo.deleteByProductIdAndUserId(productId, userId);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.WISHLIST_REMOVED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> getWishlistDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - UserServiceImpl.getWishlistDetails()");
		try {
			int CountData = (int) wishlistRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<WishlistEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = wishlistRepo.findAll(pagingSort);
			} else {
				findAll = wishlistRepo.Search(keyword, pagingSort);

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
				throw new CustomException(MessageConstant.WISHLIST_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getUserWishlistDetails(Integer userId, Integer page, Integer limit) {
		LOGGER.info("Inside - UserServiceImpl.getUserWishlistDetails()");
		try {
			List<WishlistEntity> findByUserId = wishlistRepo.findByUserId(userId);
			List<Integer> productIds = new ArrayList<>();

			findByUserId.forEach((e) -> {
				productIds.add(e.getProductId());
			});

			Map<String, Object> map = new HashMap<>();
			map.put("productId", productIds.toString());
			map.put("limit", limit);
			map.put("page", page);

			ResponseEntity<String> response1 = null;
			if (productIds != null) {

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
				response1 = restTemplate.postForEntity(DESIGNER_SERVICE+RestTemplateConstants.WISHLIST_PRODUCTLIST, entity,
						String.class);

			}
			return ResponseEntity.ok(new Json(response1.getBody()));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public GlobalResponse postCartDetailsService(List<UserCartEntity> userCartEntity) {
		LOGGER.info("Inside - UserServiceImpl.postCartDetailsService()");

		try {
			UserCartEntity filterCatDetails = new UserCartEntity();

			for (UserCartEntity getRow : userCartEntity) {
				Optional<UserCartEntity> findByCategory = userCartRepo.findByProductIdAndUserIdAndSelectedSize(
						getRow.getProductId(), getRow.getUserId(), getRow.getSelectedSize());

				if (userCartEntity.size() <= 1 && findByCategory.isPresent()) {

					Integer qty = getRow.getQty() + findByCategory.get().getQty();
					getRow.setId(getRow.getId());
					getRow.setQty(qty);
					getRow.setAddedOn(new Date());

					userCartRepo.save(getRow);

					throw new CustomException(MessageConstant.CART_QUANTITY_UPDATE.getMessage());

				} else {
					if (!findByCategory.isPresent() && !getRow.getUserId().equals(null)
							&& !getRow.getProductId().equals(null)) {
						filterCatDetails.setId(sequenceGenerator.getNextSequence(UserCartEntity.SEQUENCE_NAME));
						filterCatDetails.setUserId(getRow.getUserId());
						filterCatDetails.setProductId(getRow.getProductId());
						filterCatDetails.setQty(getRow.getQty());
						filterCatDetails.setAddedOn(new Date());
						filterCatDetails.setSelectedSize(getRow.getSelectedSize());
						filterCatDetails.setCustomization(getRow.getCustomization());
						if (getRow.getCustomization()) {
							filterCatDetails.setMeasurementObject(getRow.getMeasurementObject());
						}
						userCartRepo.save(filterCatDetails);
					}
				}
			}
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), MessageConstant.CART_ADDED.getMessage(),
					200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> putCartDetailsService(UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserServiceImpl.putCartDetailsService()");

		try {
			Map<String, Object> map = new HashMap<>();

			Optional<UserCartEntity> findByCat = userCartRepo.findByProductIdAndUserId(userCartEntity.getProductId(),
					userCartEntity.getUserId());
			org.json.simple.JSONObject body = restTemplate
					.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_PRODUCT + userCartEntity.getProductId(),
							org.json.simple.JSONObject.class)
					.getBody();
			String soh = body.get("soh").toString();
			if (Integer.parseInt(soh) < userCartEntity.getQty()) {
				throw new CustomException(MessageConstant.PRODUCT_OUT_STOCK.getMessage());
			}

			if (!findByCat.isPresent()) {
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND_IN_CART.getMessage());
			} else {

				UserCartEntity RowsDetails = findByCat.get();
				RowsDetails.setUserId(userCartEntity.getUserId());
				RowsDetails.setProductId(userCartEntity.getProductId());
				RowsDetails.setQty(userCartEntity.getQty());
				RowsDetails.setAddedOn(new Date());

				UserCartEntity getdata = userCartRepo.save(RowsDetails);

				map.put("reason", MessageConstant.SUCCESS.getMessage());
				map.put("message", MessageConstant.CART_UPDATED_SUCESSFULLY.getMessage());
				map.put("status", 200);
				map.put("qty", getdata.getQty());
				return ResponseEntity.ok(map);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public GlobalResponse deleteCartService(Integer pId) {
		LOGGER.info("Inside - UserServiceImpl.deleteCartService()");
		try {
			Optional<UserCartEntity> findByProductRow = userCartRepo.findById(pId);
			if (!findByProductRow.isPresent()) {
				throw new CustomException(MessageConstant.CART_NOT_EXIST.getMessage());
			} else {
				userCartRepo.deleteById(pId);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.CART_REMOVED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> getUserCartDetailsService(Integer userId, Integer page, Integer limit) {
		LOGGER.info("Inside - UserServiceImpl.getUserCartDetailsService()");
		try {

			List<UserCartEntity> findByUserId = userCartRepo.findByUserId(userId);
			List<Integer> productIds = new ArrayList<>();

			findByUserId.forEach((e) -> {
				productIds.add(e.getProductId());
			});

			Map<String, Object> maps = new HashMap<>();
			maps.put("productId", productIds.toString());
			maps.put("limit", limit);
			maps.put("page", page);

			Map<String, Object> map = new HashMap<>();
			map.put("reason", "ERROR");
			map.put("message", MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			map.put("status", 400);

			if (productIds.isEmpty()) {
				return ResponseEntity.ok(map);
			}

			try {
				ResponseEntity<String> response1 = null;

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Map<String, Object>> entity = new HttpEntity<>(maps, headers);

				response1 = restTemplate.postForEntity(DESIGNER_SERVICE+RestTemplateConstants.CART_PRODUCTLIST, entity,
						String.class);

				String body = response1.getBody();
				Arrays.asList(body);

				JsonNode jn1 = new JsonNode(body);
				JSONArray object1 = jn1.getArray();

				List<Object> l1 = new ArrayList<>();
				object1.forEach(e -> {
					JsonNode jn = new JsonNode(e.toString());
					JSONObject object = jn.getObject();
					ResponseEntity<org.json.simple.JSONObject> getDesignerById = restTemplate.getForEntity(DESIGNER_SERVICE+
							RestTemplateConstants.DESIGNER_BYID + object.get("designerId"),
							org.json.simple.JSONObject.class);
					UserCartEntity cart = userCartRepo
							.findByUserIdAndProductId(userId, Integer.parseInt(object.get("productId").toString()))
							.get(0);
					String selectedSize = userCartRepo
							.findByUserIdAndProductId(userId, Integer.parseInt(object.get("productId").toString()))
							.get(0).getSelectedSize();
					ObjectMapper obj = new ObjectMapper();
					String writeValueAsString = null;
					try {
						writeValueAsString = obj.writeValueAsString(cart);
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode cartJN = new JsonNode(writeValueAsString);
					JSONObject cartObject = cartJN.getObject();
					object.put("cartData", cartObject);
					object.put("selectedSize", selectedSize);
					object.put("designerProfile", getDesignerById.getBody().get("designerProfile"));
					l1.add(object);
				});

				return ResponseEntity.ok(new Json(l1.toString()));
			} catch (Exception e2) {
				return ResponseEntity.ok(e2.getMessage());
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public GlobalResponse postProductCommentService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserServiceImpl.postWishlistService()");

		try {
			Optional<ProductCommentEntity> findByTitle = productCommentRepo
					.findByProductIdAndUserId(productCommentEntity.getProductId(), productCommentEntity.getUserId());
			if (findByTitle.isPresent()) {
				throw new CustomException(MessageConstant.REVIEWED_EXIST.getMessage());
			} else {
				ProductCommentEntity<?> RowsDetails = new ProductCommentEntity<Object>();

				RowsDetails.setId(sequenceGenerator.getNextSequence(ProductCommentEntity.SEQUENCE_NAME));
				RowsDetails.setUserId(productCommentEntity.getUserId());
				RowsDetails.setProductId(productCommentEntity.getProductId());
				RowsDetails.setRating(productCommentEntity.getRating());
				RowsDetails.setComment(productCommentEntity.getComment());
				RowsDetails.setIsVisible(false);
				RowsDetails.setUploads(productCommentEntity.getUploads());
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.REVIEWED_ADDED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public GlobalResponse putProductCommentService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserServiceImpl.putProductCommentService()");

		try {

			Optional<ProductCommentEntity> findByRow = productCommentRepo.findById(productCommentEntity.getId());

			if (!findByRow.isPresent()) {
				throw new CustomException(MessageConstant.REVIEW_NOT_EXIST.getMessage());
			} else {
				ProductCommentEntity<?> RowsDetails = findByRow.get();

				RowsDetails.setUserId(productCommentEntity.getUserId());
				RowsDetails.setProductId(productCommentEntity.getProductId());
				RowsDetails.setRating(productCommentEntity.getRating());
				RowsDetails.setComment(productCommentEntity.getComment());
				RowsDetails.setIsVisible(false);
				RowsDetails.setUploads(productCommentEntity.getUploads());
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.REVIEW_UPDATE.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GlobalResponse putProductCommentStatusService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserServiceImpl.putProductCommentStatusService()");

		try {

			Optional<ProductCommentEntity> findByRow = productCommentRepo.findById(productCommentEntity.getId());

			if (!findByRow.isPresent()) {
				throw new CustomException(MessageConstant.REVIEW_NOT_EXIST.getMessage());
			} else {
				ProductCommentEntity<?> RowsDetails = findByRow.get();
				Boolean Status = false;
				String message = null;
				if (findByRow.get().getIsVisible() == true) {
					Status = false;
					message = "actived";
				} else {
					Status = true;
					message = "inactive";
				}
				RowsDetails.setIsVisible(Status);
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.REVIEW_STATUS.getMessage() + message
								+ MessageConstant.SUCCESSFULLY.getMessage(),
						200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public GlobalResponse deleteProductCommentService(Integer Id) {
		try {
			Optional<ProductCommentEntity> findByProductRow = productCommentRepo.findById(Id);
			if (!findByProductRow.isPresent()) {
				throw new CustomException(MessageConstant.REVIEW_NOT_EXIST.getMessage());
			} else {
				productCommentRepo.deleteById(Id);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.REVIEW_REMOVED.getMessage(), 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> getProductUser() {
		try {
			
			String body = restTemplate.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.PRODUCT_LIST_USER, String.class)
					.getBody();

			Json js = new Json(body);
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> postfollowDesignerService(@Valid UserDesignerEntity userDesignerEntity) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);

			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userDesignerEntity.getUserId()));
			List<UserDesignerEntity> followDesignerList = mongoOperations.find(query, UserDesignerEntity.class);
			List<UserDesignerEntity> collect = followDesignerList.stream()
					.filter(e -> e.getDesignerId().equals(userDesignerEntity.getDesignerId()))
					.collect(Collectors.toList());

			if (collect.isEmpty()) {
				userDesignerEntity.setId(sequenceGenerator.getNextSequence(UserDesignerEntity.SEQUENCE_NAME));
				userDesignerEntity.setIsFollowing(true);
				userDesignerEntity.setCreatedOn(date.toString());
				userDesignerRepo.save(userDesignerEntity);
				return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.DESIGNER_FOLLOWING.getMessage(), 200));
			} else {
				userDesignerRepo.deleteById(collect.get(0).getId());
				return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.DESIGNER_UNFOLLOWING.getMessage(), 200));
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getDesignerDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			ResponseEntity<?> Response = restTemplate.getForEntity(DESIGNER_SERVICE+
					RestTemplateConstants.DESIGNER_PRODUCT_LIST_USER + page + "&limit=" + limit + "&",
					String.class);
			Json jsons = new Json((String) Response.getBody());
			return ResponseEntity.ok(jsons);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> productDetails(Integer productId, String userId) {
		try {
			LOGGER.info("Inside - UserServiceImpl.productDetails()");
			ResponseEntity<String> exchange = restTemplate.exchange(DESIGNER_SERVICE+
					RestTemplateConstants.DESIGNER_PRODUCT + productId, HttpMethod.GET, null, String.class);

			Json js = new Json(exchange.getBody());

			if (!userId.equals("")) {
				List<UserCartEntity> cart = userCartRepo.findByUserIdAndProductId(Integer.parseInt(userId), productId);

				if (!cart.isEmpty()) {

					try {
						JsonNode jn = new JsonNode(exchange.getBody().toString());
						JSONObject object = jn.getObject();

						ObjectMapper obj = new ObjectMapper();
						String writeValueAsString = null;
						ResponseEntity<org.json.simple.JSONObject> categoryById = restTemplate.getForEntity(ADMIN_SERVICE+
								RestTemplateConstants.CATEGORY_VIEW + object.get("categoryId"),
								org.json.simple.JSONObject.class);
						Object categoryName = categoryById.getBody().get("categoryName");
					
						try {
							writeValueAsString = obj.writeValueAsString(cart);
						} catch (JsonProcessingException e1) {
							e1.printStackTrace();
						}
						
						JsonNode cartJN = new JsonNode(writeValueAsString);
						JSONObject cartObject = cartJN.getObject();
						object.put("cartData", cartObject);
						object.put("categoryName", categoryName);

						return ResponseEntity.ok(new Json(jn.toString()));
					} catch (Exception e2) {
						return ResponseEntity.ok(e2.getMessage());
					}
				}
			}
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@Override
	public ResponseEntity<?> productDetailsAdmin(Integer productId, String userId) {
		try {
			LOGGER.info("Inside - UserServiceImpl.productDetails()");
			ResponseEntity<String> exchange = restTemplate.exchange(
					DESIGNER_SERVICE +"designerProducts/productAdmin/"+ productId, HttpMethod.GET, null, String.class);

			Json js = new Json(exchange.getBody());

			if (!userId.equals("")) {
				List<UserCartEntity> cart = userCartRepo.findByUserIdAndProductId(Integer.parseInt(userId), productId);

				if (!cart.isEmpty()) {

					try {
						JsonNode jn = new JsonNode(exchange.getBody().toString());
						JSONObject object = jn.getObject();

						ObjectMapper obj = new ObjectMapper();
						String writeValueAsString = null;
						ResponseEntity<org.json.simple.JSONObject> categoryById = restTemplate.getForEntity(ADMIN_SERVICE+
								RestTemplateConstants.CATEGORY_VIEW + object.get("categoryId"),
								org.json.simple.JSONObject.class);
						Object categoryName = categoryById.getBody().get("categoryName");
						
						try {
							writeValueAsString = obj.writeValueAsString(cart);
						} catch (JsonProcessingException e1) {
							e1.printStackTrace();
						}
						
						JsonNode cartJN = new JsonNode(writeValueAsString);
						JSONObject cartObject = cartJN.getObject();
						object.put("cartData", cartObject);
						object.put("categoryName", categoryName);

						return ResponseEntity.ok(new Json(jn.toString()));
					} catch (Exception e2) {
						return ResponseEntity.ok(e2.getMessage());
					}
				}
			}
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> getDesignerUser() {
		try {

			String body = restTemplate.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.USER_DESIGNER_LIST, String.class)
					.getBody();
			Json js = new Json(body);	
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getDesignerProfileDetailsService(Integer designerId, Long userId) {
		try {

			String body = restTemplate
					.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_USER + designerId, String.class).getBody();
			JsonNode jn = new JsonNode(body);
			JSONObject object = jn.getObject();
			
			object.put("follwerCount", userDesignerRepo
					.findByDesignerIdAndIsFollowing(Long.parseLong(object.get("dId").toString()), true).size());
			
			if (userId != 0) {
				Query query = new Query();
				query.addCriteria(Criteria.where("designerId").is(designerId));

				List<UserDesignerEntity> userDesignerList = this.userDesignerRepo
						.findByDesignerIdAndUserId(designerId.longValue(), userId);

				if (!userDesignerList.isEmpty()) {
					object.put("isFollowing", userDesignerList.get(0).getIsFollowing());
					object.put("rating", userDesignerList.get(0).getRaiting());
				} else {
					object.put("isFollowing", false);
					object.put("rating", 0);
				}
			}

			return ResponseEntity.ok(new Json(object.toString()));

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getPerDesignerProductListService(int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, Optional<String> sortBy, Integer designerId) {
		try {
			ResponseEntity<?> Response = restTemplate.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_PER_PRODUCT
					+ designerId + "?page=" + page + "&limit=" + limit + "&", String.class);
			Json jsons = new Json((String) Response.getBody());
			return ResponseEntity.ok(jsons);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse multipleDelete(Integer userId) {
		try {
			List<UserCartEntity> allData = userCartRepo.findByUserId(userId);
			if (allData.isEmpty()) {
				return new GlobalResponse(MessageConstant.ERROR.getMessage(),
						MessageConstant.PRODUCT_NOT_FOUND.getMessage(), 400);
			}
			userCartRepo.deleteByUserId(userId);
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.CART_DATA_DELETED.getMessage(), 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<Integer> viewProductService(String orderId) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("order_id").is(orderId));
			mongoOperations.findOne(query, OrderDetailsEntity.class);
			return null;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getUserListService(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) userLoginRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<UserLoginEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = userLoginRepo.findByIsDeleted(isDeleted, pagingSort);
			} else {
				findAll = userLoginRepo.Search(keyword, isDeleted, pagingSort);

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
				throw new CustomException(MessageConstant.WISHLIST_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<UserDesignerEntity> followedUserListService(Integer designerIdvalue) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("designerId").is(designerIdvalue));
			List<UserDesignerEntity> userData = mongoOperations.find(query, UserDesignerEntity.class);
			return userData;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public UserLoginEntity getUserById(Long userId) {
		try {
			UserLoginEntity userDetails = userLoginRepo.findById(userId).get();
			return userDetails;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getCountFollowers(Long designerId) {
		try {
			Long countByDesignerId = userDesignerRepo.countByDesignerId(designerId);
			return new GlobalResponse(MessageConstant.SUCCESSFULLY.getMessage(), "" + countByDesignerId, 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public UserLoginEntity getUserDetailsService(String token) {
		try {
			String userName = jwtUtil.extractUsername(token);
			LOGGER.debug(userName);
			return userLoginRepo.findByEmail(userName).get();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getUserStatus() {
		try {
			LOGGER.info("Inside - UserServiceImpl.getUserStatus()");
			Pageable pagingSort = PageRequest.of(0, 10);
			Page<UserLoginEntity> findAllActive = userLoginRepo.findByIsActive(false, true, pagingSort);
			Page<UserLoginEntity> findAllInActive = userLoginRepo.findByIsActive(false, false, pagingSort);
			Page<UserLoginEntity> findAllDeleted = userLoginRepo.findByIsDeleted(true, pagingSort);
			Map<String, Object> response = new HashMap<>();
			response.put("Active", findAllActive.getTotalElements());
			response.put("InActive", findAllInActive.getTotalElements());
			response.put("Deleted", findAllDeleted.getTotalElements());

			return response;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<StateEntity> getStateDataService() {

		try {
			List<StateEntity> findAll = stateRepo.findAll();
			return findAll;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("all")
	@Override
	public List<org.json.simple.JSONObject> getListDesignerData(String userEmail) {
		LOGGER.info("inside - getListDesignerData");
		try {
			List<org.json.simple.JSONObject> designerList = new ArrayList<>();
			Long userId = userLoginRepo.findByEmail(userEmail).get().getId();

			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			List<UserDesignerEntity> userDesignerList = mongoOperations.find(query, UserDesignerEntity.class);

			userDesignerList.stream().forEach(e -> {
				designerList.add(restTemplate
						.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_BYID + e.getDesignerId(), org.json.simple.JSONObject.class)
						.getBody());
			});
			designerList.stream().forEach(data -> {
				data.put("isFollowing", true);
			});
			return designerList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public String complaintMail(String token, Integer productId) {

		Context context = new Context();
		try {

			Optional<UserAddressEntity> byUserEmail = addressRepo.findByUserEmail(jwtUtil.extractUsername(token.substring(7)));
			String fullName = byUserEmail.get().getFullName();
			String email = byUserEmail.get().getEmail();
			String mobile = byUserEmail.get().getMobile();
			Long id = byUserEmail.get().getUserId();

			try {
				UserLoginEntity entity = restTemplate.getForObject(USER_SERVICE+RestTemplateConstants.USER_BY_ID + id,
						UserLoginEntity.class);

				String dob = entity.getDob();
				context.setVariable("Username", fullName);
				context.setVariable("Useremail", email);
				context.setVariable("Usermobileno", mobile);
				context.setVariable("Userdob", dob);
				try {

					ProductMasterEntity entity2;
					entity2 = restTemplate
							.getForEntity(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_PRODUCT_VIEW + productId,
									ProductMasterEntity.class)
							.getBody();

					String productDescription = entity2.getProductDescription();

					try {
						Optional<OrderSKUDetailsEntity> findByProductId = orderSKUDetailsRepo
								.findByProductId(productId);
						Long mrp = findByProductId.get().getMrp();
						String size = findByProductId.get().getSize();
						Long units = findByProductId.get().getUnits();

						context.setVariable("Product", productId);
						context.setVariable("Description", productDescription);
						context.setVariable("MRP", mrp);
						context.setVariable("Unit", units);
						context.setVariable("Size", size);
					} catch (Exception e) {
						throw new CustomException(e.getMessage());
					}
					try {
						DesignerProfileEntity profile = restTemplate.getForObject(DESIGNER_SERVICE+
								RestTemplateConstants.DESIGNER_BYID + entity2.getDesignerId(),
								DesignerProfileEntity.class);

						String name = profile.getDesignerName();
						String dob2 = profile.getDesignerProfile().getDob();
						String email2 = profile.getDesignerProfile().getEmail();
						String gender = profile.getDesignerProfile().getGender();
						String mobileNo = profile.getDesignerProfile().getMobileNo();

						context.setVariable("Designername", name);
						context.setVariable("Designeremail", email2);
						context.setVariable("Designermobileno", mobileNo);
						context.setVariable("Designergender", gender);
						context.setVariable("Designerdob", dob2);

					} catch (Exception e) {
						throw new CustomException(e.getMessage());
					}

				} catch (Exception e) {
					throw new CustomException(e.getMessage());
				}
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
			return engine.process("complaintMail.html", context);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public Page<OrderDetailsEntity> findByOrder(int page, int limit, String sort, String orderId, Boolean isDeleted,
			Optional<String> sortBy) {
		try {
			int CountData = (int) detailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(orderId));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(orderId));
			}
			return this.detailsRepo.findOrderId(orderId, pagingSort);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public Map<String, Object> find(int page, int limit, String sort, String orderItemStatus, Boolean isDeleted,
			Optional<String> sortBy) {
		try {
			int CountData = (int) orderSKUDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(orderItemStatus));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(orderItemStatus));
			}
			Page<OrderSKUDetailsEntity> findAll = null;
			if (orderItemStatus.isEmpty()) {
				findAll = orderSKUDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderSKUDetailsRepo.Search(orderItemStatus, pagingSort);
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
				throw new CustomException(MessageConstant.INVOICE_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	@Override
	public List<OrderSKUDetailsEntity> findByorderID(String orderId) {
		try {
			return this.orderSKUDetailsRepo.findByOrderId(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public List<UserDesignerEntity> getUserDesignerDetails(String userEmail) {
		try {
			Long userId = userLoginRepo.findByEmail(userEmail).get().getId();
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId));
			List<UserDesignerEntity> userDesignerList = mongoOperations.find(query, UserDesignerEntity.class);
			return userDesignerList;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}