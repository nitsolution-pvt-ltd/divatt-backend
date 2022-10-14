package com.divatt.user.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
=======
import org.springframework.http.ResponseEntity;
>>>>>>> efff8dd07bf3c44035222dbba1afeb03ad4cc60b

import com.divatt.user.entity.DesignerLoginEntity;
import com.divatt.user.entity.StateEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.response.GlobalResponse;

public interface UserService {

	public GlobalResponse postWishlistService(ArrayList<WishlistEntity> wishlistEntity);

<<<<<<< HEAD
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
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
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private UserLoginRepo userLoginRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private MongoTemplate mongoTemplate;
	public GlobalResponse postWishlistService(ArrayList<WishlistEntity> wishlistEntity) {
		LOGGER.info("Inside - UserService.postWishlistService()");

		try {
			WishlistEntity filterCatDetails = new WishlistEntity();

			for (WishlistEntity getRow : wishlistEntity) {
				Optional<WishlistEntity> findByCategoryName = wishlistRepo
						.findByProductIdAndUserId(getRow.getProductId(), getRow.getUserId());
				if (wishlistEntity.size() <= 1 && findByCategoryName.isPresent()) {
					throw new CustomException("Wishlist already exist");
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

			return new GlobalResponse("SUCCESS", "Wishlist added succesfully", 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteWishlistService(Integer productId, Integer userId) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findByProductIdAndUserId(productId, userId);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Product not exist!");
			} else {
				wishlistRepo.deleteByProductIdAndUserId(productId, userId);
				return new GlobalResponse("SUCCESS", "Wishlist removed succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
=======
	public GlobalResponse deleteWishlistService(Integer productId, Integer userId);
>>>>>>> efff8dd07bf3c44035222dbba1afeb03ad4cc60b

	public Map<String, Object> getWishlistDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy);

	public ResponseEntity<?> getUserWishlistDetails(Integer userId, Integer page, Integer limit);

	public GlobalResponse postCartDetailsService(List<UserCartEntity> userCartEntity);

	public ResponseEntity<?> putCartDetailsService(UserCartEntity userCartEntity);

	public GlobalResponse deleteCartService(Integer pId);

	public ResponseEntity<?> getUserCartDetailsService(Integer userId, Integer page, Integer limit);

	public GlobalResponse postProductCommentService(ProductCommentEntity<?> productCommentEntity);

	public GlobalResponse putProductCommentService(ProductCommentEntity<?> productCommentEntity);

	public GlobalResponse putProductCommentStatusService(ProductCommentEntity<?> productCommentEntity);

	public GlobalResponse deleteProductCommentService(Integer Id);

	public ResponseEntity<?> getProductUser();

	public ResponseEntity<?> postfollowDesignerService(@Valid UserDesignerEntity userDesignerEntity);

	public ResponseEntity<?> getDesignerDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	public ResponseEntity<?> productDetails(Integer productId, String userId);

	public ResponseEntity<?> getDesignerUser();

	public ResponseEntity<?> getDesignerProfileDetailsService(Integer designerId, Long userId);

	public ResponseEntity<?> getPerDesignerProductListService(int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, Optional<String> sortBy, Integer designerId);

	public GlobalResponse multipleDelete(Integer userId);

	public List<Integer> viewProductService(String orderId);

	public Map<String, Object> getUserListService(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	public List<UserDesignerEntity> followedUserListService(Integer designerIdvalue);

	public UserLoginEntity getUserById(Long userId);

	public GlobalResponse getCountFollowers(Long designerId);

	public UserLoginEntity getUserDetailsService(String token);

	public Map<String, Object> getUserStatus();

<<<<<<< HEAD
			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Wishlist not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

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

	public UserLoginEntity getUserById(Long userId) {
		try {
			UserLoginEntity userDetails = userLoginRepo.findById(userId).get();
			return userDetails;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse getCountFollowers(Long designerId) {
		try {
			Long countByDesignerId = userDesignerRepo.countByDesignerId(designerId);
			return new GlobalResponse("Successfull", ""+countByDesignerId, 200);
			//return null;
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public UserLoginEntity getUserDetailsService(String token) {
		try {
			String userName=jwtUtil.extractUsername(token);
			LOGGER.debug(userName);
			return userLoginRepo.findByEmail(userName).get();
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	public Map<String, Object> getUserStatus() {
         try {
        	 LOGGER.info("Inside - UserService.getUserStatus()");
			Pageable pagingSort = PageRequest.of(0, 10);
			Page<UserLoginEntity> findAllActive = userLoginRepo.findByIsActive(false,true, pagingSort);
			Page<UserLoginEntity> findAllInActive = userLoginRepo.findByIsActive(false,false, pagingSort);
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

	public List<StateEntity> getStateDataService() {
		
		try {
			List<StateEntity> findAll = stateRepo.findAll();
			return findAll;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	
	}
=======
	public List<StateEntity> getStateDataService();

	public List<Object> getListDesignerData(String userEmail);
>>>>>>> efff8dd07bf3c44035222dbba1afeb03ad4cc60b

}
